import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.io.*;
import java.util.*;

class Nodes{
    int character;
    Nodes left;
    Nodes right;
    }

public class HuffmanDecompression{
    public static void main(String[] args) throws Exception {
        BufferedReader dictionary = new BufferedReader(new FileReader(args[1]));
        List <Integer> listChar = new ArrayList <Integer>();
        List <String> listCode = new ArrayList <String>();
        String dictLine = dictionary.readLine();
        while (dictLine.length() != 0){
            String [] split = dictLine.split(":");
            int character = Integer.parseInt(split[0]);
            String uniqueCode = split[1];
            listChar.add(character);
            listCode.add(uniqueCode);

            dictLine = dictionary.readLine();
        }
        
        Nodes root = new Nodes();
        root.left = null;
        root.right = null;
        Nodes current = root;

        int i = 0;
        while(i < listCode.size()){
            String Character = listCode.get(i);
            Integer stop = Character.length() -1;
            for (int n = 0; n < stop; n++){
                char x = Character.charAt(n);
                if (x == '0'){
                    if (current.left == null){
                        Nodes node1 = new Nodes();
                        node1.character = 128;
                        node1.left = null;
                        node1.right = null;

                        current.left = node1;
                    }
                    current = current.left;
                } else{
                    if (current.right == null){
                        Nodes node1 = new Nodes();
                        node1.character = 128;
                        node1.left = null;
                        node1.right = null;

                        current.right = node1;
                    }
                    current = current.right;
                }
            }
            char x = Character.charAt(stop);
            if (x == '0'){
                if (current.left == null){
                    Nodes node1 = new Nodes();
                    node1.character = listChar.get(i);
                    node1.left = null;
                    node1.right = null;

                    current.left = node1;
                }
            } else{
                if (current.right == null){
                    Nodes node1 = new Nodes();
                    node1.character = listChar.get(i);
                    node1.left = null;
                    node1.right = null;

                    current.right = node1;
                }
            }

            i += 1;
            current = root;
        }

        BufferedReader reader2 = new BufferedReader(new FileReader(args[0]));
        String line = reader2.readLine();
        FileWriter fwriter = new FileWriter(args[2], false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);

        while (line != null){
            String result = "";
            for (int n = 0; i < line.length(); n++){
                char binaryCode = line.charAt(i);
                if (binaryCode == '0'){
                    current = current.left;
                } else{
                    current = current.right;
                }
                if (current.left== null && current.right== null && current.character <= 127){
                    int currentChar = current.character;
                    result = result + (char)currentChar;
                    current = root;
                }
            }
            bwriter.write(result);
            line = reader2.readLine();
        }
        bwriter.flush();
        bwriter.close();
        return;
    }
}