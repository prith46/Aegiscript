package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileLoader {

    public static HashMap<String, String> stringVariables = new HashMap<>();
    public static HashMap<String, Integer> integerVariables = new HashMap<>();

    public static void variables(String line){
        int start = line.indexOf(" ");
        int end = line.indexOf("=") - 1;
        String variableName = line.substring(start + 1, end);

        start = line.indexOf("\"") + 1;
        end = line.lastIndexOf("\"");

        stringVariables.put(variableName, line.substring(start, end));
    }

    public static void integerMethod(String line){
        int start = line.indexOf(" ");
        int end = line.indexOf("=") - 1;
        String variableName = line.substring(start + 1, end).trim();

        start = end + 2;
        end = line.length();
        int value = Integer.parseInt(line.substring(start, end).trim());

        integerVariables.put(variableName, value);
    }

    public static void printVariable(String name){
        if (stringVariables.containsKey(name))
            System.out.print(stringVariables.get(name));

        else if (integerVariables.containsKey(name))
            System.out.print(integerVariables.get(name));

        else
            System.out.println("\n\n*** Variable Not Found ***");
    }

    public static void print(String line){

        int i = 0;
        boolean isValid = true;

        while (line.charAt(i) != '(')
            i++;

        // Check if the print is valid;
        if (line.charAt(i) != '(')
            isValid = false;
        else
            i++;

        for(; i < line.length(); i++){
            if (line.charAt(i) == '"'){
                int end = line.indexOf("\"", i+1);
                String value = line.substring(i+1, end);
                System.out.print(value);
                i = end;
            }

            else if (line.charAt(i) == '+'){
                int end = line.indexOf("+", i+1);
                if (end == -1)
                    end = line.lastIndexOf(")");
                String variableName = line.substring(i+1, end).trim();
                printVariable(variableName);
                i = end;
            }

            else if (line.charAt(i) == ')'){
                isValid = true;
                break;
            }

            else if (line.charAt(i) == ' ')
                continue;

            // Just variables to print
            else {
                int end = line.indexOf(" ", i+1);
                if (end == -1) {
                    end = i + 1;
                    while (Character.isAlphabetic(line.charAt(end)))
                        end++;
                }
                String variableName = line.substring(i, end).trim();
                printVariable(variableName);
                i = end;
            }
        }

        System.out.println();
        if (!isValid)
            System.out.println("\n\n*** Syntax Error ***");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("scripts/hello.ags"));
        String line;
        while ((line = reader.readLine()) != null){
            if (line.startsWith("print"))
                print(line);

            if (line.startsWith("let")) {

                // Separate Strings
                if (line.contains("\""))
                    variables(line);

                // Separate Integers
                else
                    integerMethod(line);
            }
        }
    }
}
