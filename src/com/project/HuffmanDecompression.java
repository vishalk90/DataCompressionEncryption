package com.project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vishal kulkarni on 4/6/17
 */
public class HuffmanDecompression {
    private int[] inputStream;
    private int padding;
    private ArrayList<Integer> outputStream = new ArrayList<Integer>();

    private Node rootNode = null;
    private int index;

    private void buildTree() {
        rootNode = new Node(0, 0);
        Node n = rootNode;
        boolean flag1 = true;
        index = 0;

        while (inputStream[index] != 100) {
            if (inputStream[index] == 48) {
                if (n.leftChildNode == null) {
                    n.leftChildNode = new Node(0, n, null, null);
                }
                n = n.leftChildNode;
                flag1 = true;
            } else if (inputStream[index] == 49) {
                if (n.rightChildNode == null) {
                    n.rightChildNode = new Node(1, n, null, null);
                }
                n = n.rightChildNode;
                flag1 = true;
            } else if (inputStream[index] == 10) {
                if (n.leftChildNode == null && n.rightChildNode == null) {
                    n.key = (inputStream[index + 1]);
                    if (n.key < 0) {
                        n.key = 256 + n.key;
                    }
                }
                //System.out.println("tree " + n.key);
                n = rootNode;
                index++;
            }
            index++;

        }
    }

    String EXTRA = "";

    private void getValueFromTree(StringBuffer huffmanCode) {
        Node n = rootNode;
        int extra = 0;
        //System.out.println("huffmancodelength " + huffmanCode.length());

        for (int i = 0; i < ((huffmanCode.length())); i++) {
            if (huffmanCode.charAt(i) == '0') {
                EXTRA = EXTRA + "0";
                n = n.leftChildNode;
                if (n.leftChildNode == null && n.rightChildNode == null) {
                    int key = (n.key - 129);

                    outputStream.add(key);
                    //System.out.println(key);
                    n = rootNode;
                    EXTRA = "";

                }
            } else if (huffmanCode.charAt(i) == '1') {
                EXTRA = EXTRA + "1";
                n = n.rightChildNode;
                if (n.leftChildNode == null && n.rightChildNode == null) {
                    int key = (n.key - 129);

                    outputStream.add(key);
                    //System.out.println(key);
                    n = rootNode;
                    EXTRA = "";
                }
            } else {
                System.out.println("not found!!!!!!!!!!");
            }


        }
    }

    private void decompress() throws Exception {
        index++; // pointer shifting to the starting position of the input sequence

        StringBuffer inputStringInByte = new StringBuffer();
        //ArrayList<String> inputStringInByte = new ArrayList<>();

        while (index < ((inputStream.length) - 2)) {
            StringBuilder temp = new StringBuilder();
            if (inputStringInByte.length() < 8192) {
                if (inputStream[index] < 0) {

                    temp.append(String.format("%8s", Integer.toBinaryString(inputStream[index] + 256)).replace(' ', '0'));

                    inputStringInByte.append(temp);
                } else {

                    temp.append(String.format("%8s", Integer.toBinaryString(inputStream[index])).replace(' ', '0'));

                    inputStringInByte.append(temp);

                }
                index++;
            } else {
                inputStringInByte.insert(0, EXTRA);
                getValueFromTree(inputStringInByte);
                inputStringInByte = new StringBuffer();
                //EXTRA = "";
            }


        }
        // this is to get value from tree if inputStringInByte is less than 8192
        inputStringInByte.insert(0, EXTRA);
        getValueFromTree(inputStringInByte);
        inputStringInByte = new StringBuffer();
        //EXTRA = "";

        // this is to get the value from tree of 2nd last index
        if (inputStream[index] < 0) {
            inputStringInByte.insert(0, EXTRA);
            inputStringInByte.append(String.format("%8s", Integer.toBinaryString(inputStream[index] + 256)).replace(' ', '0'));
            getValueFromTree(inputStringInByte.replace(inputStringInByte.length() - padding, inputStringInByte.length(), ""));

        } else {
            inputStringInByte.insert(0, EXTRA);
            inputStringInByte.append(String.format("%8s", Integer.toBinaryString(inputStream[index])).replace(' ', '0'));
            getValueFromTree(inputStringInByte.replace(inputStringInByte.length() - padding, inputStringInByte.length(), ""));

        }
    }


    private void readData(FileInputStream in) throws IOException {
        try {
            int count;
            byte[] buffer = new byte[1];
            int index = 0;
            inputStream = new int[in.available()];
            int c = 0;
            System.out.print("Reading out.huff file");
            while ((count = in.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {
                    c++;
                    if (c % 1000000 == 0) {
                        System.out.print(".");
                    }
                    inputStream[index] = (int) buffer[i];

                }
                index++;
            }
            System.out.println();
            padding = inputStream[inputStream.length - 1];

            System.out.println("padding " + padding);


        } finally {
            if (in != null) {
                in.close();
            }
        }
    }


    private void writeData(FileOutputStream out) throws IOException {

        try {

            // this is to write the output to external file
            for (int i : outputStream) {
                out.write(i);
            }
            System.out.println("Decompressed successfully!!!");

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("out.huff"); // setting the input file path and creating an object of file input stream
        FileOutputStream out = new FileOutputStream("output.jpeg"); // setting the output file path and creating an object of file output stream

        HuffmanDecompression d = new HuffmanDecompression();

        d.readData(in);

        d.buildTree();

        System.out.println();

        d.decompress();

        System.out.println();

        d.writeData(out);


    }
}
