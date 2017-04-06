package com.project;

import java.io.*;

public class HuffmanCompression {

    Node root = null;

    String[] huffmancode = new String[257];
    //String[] atr = new String[512];
    private int[] inputStream;
    StringBuffer outputStream = new StringBuffer();

    public HuffmanCompression() {
        this.root = null;
    }

    public Node buildHeap(int[] heap) {

        BuildMinHeap minHeap = new BuildMinHeap(heap);
        //Node currentNode = root;
        root = minHeap.heap.get(0);
        int count = heap.length;
        for (int i : heap) {
            if (i == 0) {
                count--;
            }
        }
        if (count == 1) {
            root.hcode.append("0");
        }

        while (minHeap.getSize() != 0) {
            Node leftChildNode = minHeap.extractMin();
            Node rightChildNode = minHeap.extractMin();
            if (rightChildNode != null) {
                int data = leftChildNode.data + rightChildNode.data;
                root = new Node(data, null, leftChildNode, rightChildNode);
                minHeap.insert(root);
            }
        }
        return root;

    }




    public void compress(int[] inputStream, String[] huffmanTree) {


        for (int i : inputStream) {
            outputStream.append(huffmancode[i]);
            //System.out.println(outputStream.toString());
            //outputStream.append("*");

        }
        int padding = 0;
        //outputStream.append("*");
        if ((padding = outputStream.length() % 8) != 0) {
            for (int i = 0; i < padding; i++) {
                outputStream.append("0");

            }
            System.out.println("Yes! it has padding of " + padding + " bits");

        }
        //System.out.println(outputStream.toString());
        System.out.println("Compression done Successfully!!!");

    }




    public String[] getHuffmanCode(Node rootNode) {
        //BitArray[] b = new BitArray[8];
        //System.out.println(b.toString());

        if (rootNode.leftChildNode == null && rootNode.rightChildNode == null) {

            //br.append(rootNode.key);
            if (rootNode.parentNode != null)
                rootNode.hcode.append(rootNode.parentNode.hcode);

            //rootNode.hcode.append("_" + rootNode.key);
            System.out.print(rootNode.hcode.toString());
            huffmancode[rootNode.key] = rootNode.hcode.toString();
            System.out.println(" " + rootNode.key);
            //System.out.println("@" + rootNode.key);
        } else {
            if (rootNode.leftChildNode != null) {
                //System.out.print("0");
                System.out.print("");
                rootNode.leftChildNode.hcode.append(rootNode.hcode);

                rootNode.leftChildNode.hcode.append("0");
                getHuffmanCode(rootNode.leftChildNode);

            }
            if (rootNode.rightChildNode != null) {
                //System.out.print("1");

                rootNode.rightChildNode.hcode.append(rootNode.hcode);

                rootNode.rightChildNode.hcode.append("1");
                getHuffmanCode(rootNode.rightChildNode);

            }
        }
        return huffmancode;

    }


    public int[] readData(FileInputStream in) throws IOException {
        try {

            int count;
            byte[] buffer = new byte[1];
            int[] frequency = new int[257];
            int index = 0;
            inputStream = new int[in.available()];
            while ((count = in.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {
                    //System.out.print((int) buffer[i] + 129);

                    inputStream[index] = (int) buffer[i] + 129;
                    //out.write(buffer[i]);
                    //using count sort for counting the frequency of inputstream in each buffer
                    frequency[((int) buffer[i] + 129)]++;
                }
                //System.out.println(" : value of count: " + count);
                index++;
            }
            int[] heap = new int[256];
            for (int i = 1; i < frequency.length; i++) {
                heap[i - 1] = frequency[i];
                System.out.println(heap[i - 1]);

            }
            return heap;
        } finally {
            if (in != null) {
                in.close();
            }
//            if (out != null) {
//                out.close();
//            }
        }
    }

    public void writeData(FileOutputStream out, StringBuffer outputStream, String[] huffmanTree) throws IOException {
        try {
            int byteArray;
            System.out.println(outputStream.length());


            for (int i = 1; i < huffmanTree.length; i++) {
                //System.out.println(huffmanTree[i]);
                if (huffmanTree[i] != null) {
                    out.write(huffmanTree[i].getBytes());
                    out.write(10);

                }
            }



            //int[] byteArray = new int[outputStream.length() / 8];
            for (int i = 0, j = 0; i < outputStream.length(); i = i + 8, j++) {
                if (i + 7 < outputStream.length()) {
                    byteArray = Integer.parseInt(outputStream.substring(i, i + 7), 2);
                    out.write(byteArray);
                }
            }


        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream("dsa.pdf"); // setting the input file path and creating an object of file input stream
        FileOutputStream out = new FileOutputStream("out.huff"); // setting the output file path and creating an object of file output stream


        HuffmanCompression e = new HuffmanCompression(); // creating an object of this class

        //building a heap from heap[]
        int[] frequency = e.readData(in); // reading the file using above file input stream object and getting a heap array(frequency table of all the input data)

        Node rootNode = e.buildHeap(frequency); // building a heap and huffman tree using frequency table

        String[] huffmanTree = e.getHuffmanCode(rootNode); // getting a huffman code for each data

        e.compress(e.inputStream, huffmanTree); // compressing inpute stream using huffman tree

        e.writeData(out, e.outputStream, huffmanTree);// writing to output file with given path

    }
}
