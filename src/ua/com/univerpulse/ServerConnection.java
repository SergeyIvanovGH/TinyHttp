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
<<<<<<< HEAD
    private static int connectionNumber = 0;
=======
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
    private BufferedReader reader;
    private PrintWriter writer;

    ServerConnection(Socket client) throws SocketException {
        this.client = client;
<<<<<<< HEAD
        this.connectionNumber++;
=======
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
    }

    @Override
    public void run() {
        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            OutputStream out = client.getOutputStream();
//            PrintWriter pout = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

<<<<<<< HEAD
            System.out.println("Получили Request от клиента " + connectionNumber);
=======
            System.out.println("Получили Request от клиента ");
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
            String requestURI = getRequestURIFromRequestLine(); // Получим ссылку в запросе
//            System.out.println("RequestURI: " + requestURI);
            System.out.println();

<<<<<<< HEAD
            System.out.println("Отправляем Response клиенту "+connectionNumber);
            System.out.println();

            System.out.println("Формирование заголовка ... ");
            saveResponseHeader();

            System.out.println("Формирование тела ... ");
=======
            System.out.println("Отправляем Response клиенту ");
            System.out.println();

            System.out.println("Формирование заголовка ... ");
            pushResponseHeader();

            System.out.println("Формирование тела ... ");
            // Формирование HTML-заголовка тела
			pushResponseHTMLHeader();

>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
            if (requestURI.contains("calculate")) {
                // Подсчитаем результат операции
                int result = saveCalculateResult(requestURI);
                writer.println("Результат выполнения операции равно: " + result);
            } else {
                TinyHttpFile file = new TinyHttpFile();
                file.getFile(writer, requestURI);
            }

<<<<<<< HEAD
            System.out.println();
            System.out.println("Закроем соединение с клиентом " + connectionNumber);
=======
			// Формирование HTML-подвала тела
			pushResponseHTMLFooter();

            System.out.println();
            System.out.println("Закроем соединение с клиентом ");
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59

            reader.close();
            writer.close();
        } catch (IOException e) {
<<<<<<< HEAD
            System.out.println("Ошибка ввода/вывода " + e);;
=======
            System.out.println("Ошибка ввода/вывода " + e);
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
        } finally {
            try {
                client.close();
            } catch (IOException e) {
<<<<<<< HEAD
                System.out.println("Ошибка закрытия соединения клиента "+e);;
=======
                System.out.println("Ошибка закрытия соединения клиента "+e);
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
            }
        }
    }

    private String getRequestURIFromRequestLine() throws IOException {
        String requestURI = "/";
        requestURI = reader.readLine();
//        System.out.println("Full request line is "+requestURI);

        String[] partsRequestLine = requestURI.split(" ");
<<<<<<< HEAD
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
=======
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
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
<<<<<<< HEAD
            System.out.println("Ошибка ввода/вывода " + e);;
        } catch (NullPointerException e) {
            System.out.println("Ошибка " + e);;
=======
            System.out.println("Ошибка ввода/вывода " + e);
        } catch (NullPointerException e) {
            System.out.println("Ошибка " + e);
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
        }
        System.out.println("Заголовок прочитан.");
    }

<<<<<<< HEAD
    private void saveResponseHeader() {
=======
    private void pushResponseHeader() {
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
//        Calendar date = Calendar.getInstance();

        writer.println("HTTP/1.1 200 OK");
//        writer.println("Date: "+date.getTime());
//        date.add(Calendar.MINUTE, 1);
//        writer.println("Expires: "+date.getTime());
//            pout.println("Content-Length: "+sbResponse.length()*2);
//            pout.println("Content-Type: text/html; charset=8859_1");
<<<<<<< HEAD
        writer.println("Content-Type: text/plain; charset=UTF-8");
=======
        writer.println("Content-Type: text/html; charset=UTF-8");
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
        writer.println("");

    }

<<<<<<< HEAD
    private void saveResponseSimpleBody() {
=======
    private void pushResponseHTMLHeader() {
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
        StringBuilder sbResponse = new StringBuilder();

        sbResponse.append("<!DOCTYPE html>");
        sbResponse.append("<html><head><title>TinyHttpServer</title></head>");
        sbResponse.append("<body>");
<<<<<<< HEAD
        sbResponse.append("Сервер ответил Ok на запрос номер ");
        sbResponse.append(connectionNumber);
        sbResponse.append("</body></html>");
=======
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59

        writer.println(sbResponse.toString());
    }

<<<<<<< HEAD
=======
    private void pushResponseHTMLFooter() {
        writer.println("</body></html>");
    }

>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
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
