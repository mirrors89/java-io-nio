package io;

import java.io.*;

public class CharStreams {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/lorem_ipsum.txt"));
        PrintWriter printWriter = new PrintWriter(new FileWriter("src/main/resources/lorem_ipsum_copt.txt"));

        try {

            String line = bufferedReader.readLine();
            while(line != null) {
                System.out.println(line);
                printWriter.println(line);

                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
            printWriter.close();
        }
    }

}
