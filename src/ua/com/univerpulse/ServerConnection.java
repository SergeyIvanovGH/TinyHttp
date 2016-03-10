package ua.com.univerpulse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by svivanov on 08.03.16.
 */
public class ServerConnection extends Thread {
    private Socket client;
    private static int connectionNumber = 0;
    private BufferedReader reader;
    private PrintWriter writer;

    ServerConnection(Socket client) throws SocketException {
        this.client = client;
        this.connectionNumber++;
    }

    @Override
    public void run() {
        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            OutputStream out = client.getOutputStream();
//            PrintWriter pout = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

            System.out.println("Получили Request от клиента " + connectionNumber);
            String requestURI = getRequestURIFromRequestLine(); // Получим ссылку в запросе
//            System.out.println("RequestURI: " + requestURI);
            System.out.println();

            System.out.println("Отправляем Response клиенту "+connectionNumber);
            System.out.println();

            System.out.println("Формирование заголовка ... ");
            saveResponseHeader();

            System.out.println("Формирование тела ... ");
            if (requestURI.contains("calculate")) {
                // Подсчитаем результат операции
                int result = saveCalculateResult(requestURI);
                writer.println("Результат выполнения операции равно: " + result);
            } else {
                TinyHttpFile file = new TinyHttpFile();
                file.getFile(writer, requestURI);
            }

            System.out.println();
            System.out.println("Закроем соединение с клиентом " + connectionNumber);

            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода " + e);;
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.out.println("Ошибка закрытия соединения клиента "+e);;
            }
        }
    }

    private String getRequestURIFromRequestLine() throws IOException {
        String requestURI = "/";
        requestURI = reader.readLine();
//        System.out.println("Full request line is "+requestURI);

        String[] partsRequestLine = requestURI.split(" ");
//        for (String parts : partsRequestLine) {
//            System.out.println(parts);
//        }

//        Matcher get = Pattern.compile("GET /?(\\S*).*").matcher(requestURI);
//
//        if (get.matches()) {
//            requestURI = get.group(1);
//            if (requestURI.endsWith("/") || requestURI.equals("")) {
//                requestURI = "/web/index.html";
//            }
//        }
        requestURI = partsRequestLine[1];

        return requestURI;
    }

    private void showRequestHeader() {
        System.out.println("Заголовок содержит:");
        String request;
        try {
            while(!(request = reader.readLine()).isEmpty()) {
                System.out.println(request);
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода " + e);;
        } catch (NullPointerException e) {
            System.out.println("Ошибка " + e);;
        }
        System.out.println("Заголовок прочитан.");
    }

    private void saveResponseHeader() {
//        Calendar date = Calendar.getInstance();

        writer.println("HTTP/1.1 200 OK");
//        writer.println("Date: "+date.getTime());
//        date.add(Calendar.MINUTE, 1);
//        writer.println("Expires: "+date.getTime());
//            pout.println("Content-Length: "+sbResponse.length()*2);
//            pout.println("Content-Type: text/html; charset=8859_1");
        writer.println("Content-Type: text/plain; charset=UTF-8");
        writer.println("");

    }

    private void saveResponseSimpleBody() {
        StringBuilder sbResponse = new StringBuilder();

        sbResponse.append("<!DOCTYPE html>");
        sbResponse.append("<html><head><title>TinyHttpServer</title></head>");
        sbResponse.append("<body>");
        sbResponse.append("Сервер ответил Ok на запрос номер ");
        sbResponse.append(connectionNumber);
        sbResponse.append("</body></html>");

        writer.println(sbResponse.toString());
    }

    private int saveCalculateResult(String requestURI) {
        // Распарсим строку http://localhost:8000/calculate?operation=add&operand1=5&operand2=10
        String str = requestURI.substring(requestURI.indexOf("?")+1);
        String[] requestParams = str.split("&");

        // Выберем тип операции
        String[] requestOperation = requestParams[0].split("=");

        // Операнд 1
        String[] requestOperand1 = requestParams[1].split("=");

        // Операнд 2
        String[] requestOperand2 = requestParams[2].split("=");


        Method classMethod = null;
        int result = 0;
        TinyHttpCalculate tinyHttpCalculateClassInstance = new TinyHttpCalculate();

        try {
            int x = Integer.parseInt(requestOperand1[1]);
            int y = Integer.parseInt(requestOperand2[1]);

            // установим параметры поля operand1 класса
            classMethod = TinyHttpCalculate.class.getMethod(requestOperand1[0], Integer.class);
            // вызываем метод на экземпляре
            classMethod.invoke(tinyHttpCalculateClassInstance, x);

            // установим параметры поля operand2 класса
            classMethod = TinyHttpCalculate.class.getMethod(requestOperand2[0], Integer.class);
            // вызываем метод на экземпляре
            classMethod.invoke(tinyHttpCalculateClassInstance, y);

            // мы получаем сам экземпляр метода
            classMethod = TinyHttpCalculate.class.getMethod(requestOperation[1]);
            // вызываем метод расчета на экземпляре
            result = (int) classMethod.invoke(tinyHttpCalculateClassInstance);
        } catch (NoSuchMethodException e) {
            System.out.println("Не верно указана операция " +e);
        } catch (InvocationTargetException e) {
            System.out.println("Ошибка выполнения операции " +e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка преобразования операндов " +e);
        }

        return result;
    }
}
