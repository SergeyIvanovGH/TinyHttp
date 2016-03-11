package ua.com.univerpulse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

/**
 * Created by svivanov on 08.03.16.
 */
public class ServerConnection extends Thread {
    private Socket client;
    private BufferedReader reader;
    private PrintWriter writer;

    ServerConnection(Socket client) throws SocketException {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

            String requestURI = getRequestURIFromRequestLine();
            pushResponseHeader();
			pushResponseHTMLHeader();

            if (requestURI.contains("calculate")) {
                int result = saveCalculateResult8(requestURI);
                writer.println("The result of the operation is: " + result);
            } else {
                TinyHttpFile file = new TinyHttpFile();
                file.getFile(writer, requestURI);
            }
			pushResponseHTMLFooter();

            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("Input/Output error: " + e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: "+e);
            }
        }
    }

    private String getRequestURIFromRequestLine() throws IOException {
        String requestURI = "/";
        requestURI = reader.readLine();

        String[] partsRequestLine = requestURI.split(" ");
        requestURI = partsRequestLine[1];

        return requestURI;
    }

    private void showRequestHeader() {
        System.out.println("Request consist of:");
        String request;
        try {
            while(!(request = reader.readLine()).isEmpty()) {
                System.out.println(request);
            }
        } catch (IOException e) {
            System.out.println("Input/Output error: " + e);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        System.out.println("Request read");
    }

    private void pushResponseHeader() {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println("");
    }

    private void pushResponseHTMLHeader() {
        StringBuilder sbResponse = new StringBuilder();

        sbResponse.append("<!DOCTYPE html>");
        sbResponse.append("<html><head><title>TinyHttpServer</title></head>");
        sbResponse.append("<body>");

        writer.println(sbResponse.toString());
    }

    private void pushResponseHTMLFooter() {
        writer.println("</body></html>");
    }

    private int saveCalculateResult(String requestURI) {
        // http://localhost:8000/calculate?operation=add&operand1=5&operand2=10
        String str = requestURI.substring(requestURI.indexOf("?")+1);
        String[] requestParams = str.split("&");

        // Fetch operation, operand1, operand2
        String[] requestOperation = requestParams[0].split("=");
        String[] requestOperand1 = requestParams[1].split("=");
        String[] requestOperand2 = requestParams[2].split("=");

        Method classMethod = null;
        int result = 0;
        TinyHttpCalculate tinyHttpCalculateClassInstance = new TinyHttpCalculate();

        try {
            int x = Integer.parseInt(requestOperand1[1]);
            int y = Integer.parseInt(requestOperand2[1]);

            // Set value for fields operand1 and operand2
            classMethod = TinyHttpCalculate.class.getMethod(requestOperand1[0], Integer.class);
            classMethod.invoke(tinyHttpCalculateClassInstance, x);

            classMethod = TinyHttpCalculate.class.getMethod(requestOperand2[0], Integer.class);
            classMethod.invoke(tinyHttpCalculateClassInstance, y);

            classMethod = TinyHttpCalculate.class.getMethod(requestOperation[1]);
            result = (int) classMethod.invoke(tinyHttpCalculateClassInstance);
        } catch (NoSuchMethodException e) {
            System.out.println("Invalid operation: " +e);
        } catch (InvocationTargetException e) {
            System.out.println("Operation failed: " +e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error convert operands: " +e);
        }

        return result;
    }

    private int saveCalculateResult8(String requestURI) {
        // http://localhost:8000/calculate?operation=add&operand1=5&operand2=10
        String str = requestURI.substring(requestURI.indexOf("?")+1);
        String[] requestParams = str.split("&");

        // Fetch operation, operand1, operand2
        String[] requestOperation = requestParams[0].split("=");
        String[] requestOperand1 = requestParams[1].split("=");
        String[] requestOperand2 = requestParams[2].split("=");

        HashMap<String,TinyHttpCalculateInterface> hm = new HashMap<String,TinyHttpCalculateInterface>();

        hm.put("add", (x,y) -> x+y);
        hm.put("sub", (x,y) -> x-y);
        hm.put("div", (x,y) -> x/y);
        hm.put("mul", (x,y) -> x*y);

        int result = 0;

        try {
            int x = Integer.parseInt(requestOperand1[1]);
            int y = Integer.parseInt(requestOperand2[1]);

            result = hm.get(requestOperation[1]).getResult(x,y);
        } catch (NumberFormatException e) {
            System.out.println("Error convert operands: " +e);
        } catch (NullPointerException e) {
            System.out.println("Invalid operation: " +e);
        }

        return result;
    }
}
