import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCompression {

    public static class Node{      
        Integer Value, frequency;
        Node left, right;
    
        public Node(Integer Value, Integer frequency, Node left, Node right){
            this.Value = Value;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
        
        public static void Traverse(Node root, String[] huffmanCodes, String code){     
            if (root.left == null && root.right == null && root.Value != 128) {
                huffmanCodes[root.Value] = code;
                code = null;
                return;
            }else{
                Traverse(root.left, huffmanCodes, code + "0");
                Traverse(root.right, huffmanCodes, code + "1");
            }
        }
    }

    public static Node constructTree(Node[] nodes){    
        while(nodes.length > 1){
            int n = nodes.length;
            for(int i = 0; i < n-1; i++){
                for(int j = 0; j < n-i-1; j++){
                    if(nodes[j].frequency > nodes[j+1].frequency){
                        Node tempNode = nodes[j];
                        nodes[j] = nodes[j+1];
                        nodes[j+1] = tempNode;
                }
            }
        }

            Node combine = new Node(128, nodes[0].frequency + nodes[1].frequency, nodes[0], nodes[1]);
            Node[] temporary = new Node[nodes.length - 1];

            temporary[0] = combine;
            int z = 1;
            while( z < nodes.length - 1){
                temporary[z] = nodes[z + 1];
                z++;
            }
            nodes = temporary;
        }
        return nodes[0];
    }

    public static String getCompressedCode(String inputText, String[] huffmanCodes) {
        String compressedCode = null;
        String temp = "";
        for (int i = 0; i<inputText.length(); i++){
            char current = inputText.charAt(i);
            int a = current;

            temp += huffmanCodes[a];
        }
        compressedCode = temp;
        return compressedCode;
    }

    public static String[] getHuffmanCode(String inputText) {
        String[] huffmanCodes = new String[128];
        char[] characters = inputText.toCharArray();        
        int counter = 0;    
        Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();      

        for(char i : characters){           
            if(myMap.get((int) i ) == null){
                myMap.put((int) i, 1);
                counter++;
            }else{
                myMap.put((int) i, myMap.get((int) i) + 1);
            }
        }

        Node[] nodes = new Node[counter];    
        int n = 0;
        for(Integer i : myMap.keySet()){       
            Node node_1 = new Node(i, myMap.get(i), null, null);
            nodes[n] = node_1;
            n++;
        }
        
        String code = "";
        Node.Traverse(constructTree(nodes), huffmanCodes, code);       
        return huffmanCodes;
    }

    public static void main(String[] args) throws Exception {
        // obtain input text from a text file encoded with ASCII code
        String inputText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[0])), "US-ASCII");
        // get Huffman codes for each character and write them to a dictionary file
        String[] huffmanCodes = HuffmanCompression.getHuffmanCode(inputText);
        FileWriter fwriter1 = new FileWriter(args[1], false);
        BufferedWriter bwriter1 = new BufferedWriter(fwriter1);
        for (int i = 0; i < huffmanCodes.length; i++) 
            if (huffmanCodes[i] != null) {
                bwriter1.write(Integer.toString(i) + ":" + huffmanCodes[i]);
                bwriter1.newLine();
            }
        bwriter1.flush();
        bwriter1.close();
        // get compressed code for input text based on huffman codes of each character
        String compressedCode = HuffmanCompression.getCompressedCode(inputText, huffmanCodes);
        FileWriter fwriter2 = new FileWriter(args[2], false);
        BufferedWriter bwriter2 = new BufferedWriter(fwriter2);
        if (compressedCode != null) 
            bwriter2.write(compressedCode);
        bwriter2.flush();
        bwriter2.close();
    }
}