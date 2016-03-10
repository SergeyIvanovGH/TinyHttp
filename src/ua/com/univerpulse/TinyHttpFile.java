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
            System.out.println("Не могу прочитать файл: "+file.getPath());
            return;
        }
        try {
            if (file.isDirectory()) {
                String[] files = file.list();
                writer.println("Вы указали директорию, которая содержит следующие файлы:");
                for (String oneFile : files) {
                    writer.print("- ");
                    writer.println(oneFile);
                }
            } else {
                Reader ir = new InputStreamReader(new FileInputStream(file));
                BufferedReader in = new BufferedReader(ir);

                String line;
                while ((line = in.readLine()) != null) {
                    writer.println(line);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("File is absent " + e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
