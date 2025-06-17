package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public static void print(String line){
        StringBuilder str = new StringBuilder();
        int start = line.indexOf("\"");
        int end = line.lastIndexOf("\"");
        System.out.println(line.substring(start + 1, end));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("scripts/hello.ags"));
        String line;
        while ((line = reader.readLine()) != null){
            if (line.startsWith("print"))
                print(line);
        }
    }
}
