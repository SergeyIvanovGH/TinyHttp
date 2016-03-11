package ua.com.univerpulse;

import java.io.*;

/**
 * Created by svivanov on 10.03.16.
 */
public class TinyHttpFile {
    private final String pathWithContent = "./web";

    public void getFile(PrintWriter writer, String fileName) {
        File file = new File(pathWithContent+fileName);
        if (!file.exists() || !file.canRead()) {
            System.out.println("Can't read file: "+file.getPath());
            return;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            if (file.isDirectory()) {
                String[] files = file.list();
                stringBuilder.append("You have specified a directory that contains the following files:");
                stringBuilder.append("<ul>");
                for (String oneFile : files) {
                    stringBuilder.append("<li>");
                    stringBuilder.append(oneFile);
                    stringBuilder.append("</li>");
                }
                stringBuilder.append("</ul>");
            } else {
                Reader ir = new InputStreamReader(new FileInputStream(file));
                BufferedReader in = new BufferedReader(ir);

                String line;
                while ((line = in.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            writer.println(stringBuilder.toString());
        } catch(FileNotFoundException e) {
            System.out.println("File is absent: " + e);
        } catch (IOException e) {
            System.out.println("Input/Output error: " + e);
        }
    }
}
