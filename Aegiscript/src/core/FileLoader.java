package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileLoader {

    public static HashMap<String, String> stringVariables = new HashMap<>();

    public static void variables(String line){
        int start = line.indexOf(" ");
        int end = line.indexOf("=") - 1;
        String variableName = line.substring(start + 1, end);

        start = line.indexOf("\"") + 1;
        end = line.lastIndexOf("\"");

        stringVariables.put(variableName, line.substring(start, end));
    }

    public static void print(String line){

        // Printing with variable
        if (line.contains("+")){
            int start = line.indexOf("\"");
            int end = line.lastIndexOf("\"");
            System.out.print(line.substring(start + 1, end));

            String variableName;
            start = line.indexOf("+");
            end = line.indexOf(")");
            variableName = line.substring(start + 1, end).trim();
            System.out.println(stringVariables.getOrDefault(variableName, "Variable not found"));
            return;
        }

        // Just a normal printing
        if (line.contains("\"")) {
            int start = line.indexOf("\"");
            int end = line.lastIndexOf("\"");
            System.out.println(line.substring(start + 1, end));
            return;
        }

        // Printing variables
        String variableName;
        int start = line.indexOf("(") + 1;
        int end = line.indexOf(")") - 1;
        variableName = line.substring(start, end);
        System.out.println(stringVariables.getOrDefault(variableName, "Variable not found"));

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("scripts/hello.ags"));
        String line;
        while ((line = reader.readLine()) != null){
            if (line.startsWith("print"))
                print(line);

            if (line.startsWith("let"))
                variables(line);
        }
    }
}
