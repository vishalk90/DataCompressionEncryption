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

        while (inputStream[index]!=100) {
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

    private void getValueFromTree(StringBuffer huffmanCode) throws Exception {
        Node n = rootNode;
        System.out.println("huffmancodelength "+ huffmanCode.length());
        for (int i = 0; i < (huffmanCode.length() - padding); i++) {

            if (huffmanCode.charAt(i) == '0') {
                n = n.leftChildNode;
                if (n.leftChildNode == null && n.rightChildNode == null) {
                    int key = (n.key - 129);

                    outputStream.add(key);
                    //System.out.println(key);
                    n = rootNode;

                }
            } else if (huffmanCode.charAt(i) == '1') {
                n = n.rightChildNode;
                if (n.leftChildNode == null && n.rightChildNode == null) {
                    int key = (n.key - 129);

                    outputStream.add(key);
                    //System.out.println(key);
                    n = rootNode;

                }
            } else {
                System.out.println("not found!!!!!!!!!!");
            }

        }
        System.out.println("outputStream.size() "+outputStream.size());
    }

    private void decompress() throws Exception {

        ++index;

        StringBuffer inputStringInByte = new StringBuffer();

        while (index < ((inputStream.length)-1)) {

            StringBuilder temp = new StringBuilder();
            if (inputStream[index] < 0) {

                temp.append(Integer.toBinaryString(inputStream[index] + 256));
                int temp_len = (8 - temp.length());
                if (temp_len != 0) {
                    for (int i = 0; i < temp_len; i++) {
                        temp.insert(0, 0);
                    }

                }
                inputStringInByte.append(temp);
            } else {
                temp.append(Integer.toBinaryString(inputStream[index]));
                int temp_len = (8 - temp.length());
                if (temp_len != 0) {
                    for (int i = 0; i < temp_len; i++) {
                        temp.insert(0, 0);
                    }
                }
                inputStringInByte.append(temp);
            }
            index++;
        }
        //System.out.println(inputStringInByte);
        //System.out.println("inputStringInByte.length() "+inputStringInByte.length());
        getValueFromTree(inputStringInByte);
    }


    private void readData(FileInputStream in) throws IOException {
        try {
            int count;
            byte[] buffer = new byte[1];
            int index = 0;
            inputStream = new int[in.available()];
            while ((count = in.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {

                    inputStream[index] = (int) buffer[i];

                }
                index++;
            }
            padding = inputStream[inputStream.length-1];

            System.out.println("padding "+padding);


        } finally {
            if (in != null) {
                in.close();
            }
        }
    }


    private void writeData(FileOutputStream out) throws IOException {
        try {

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
