package com.project;
import java.io.*;

public class HuffmanCompression {

    private Node root = null;
    private int padding = 0;

    private String[] huffmancode = new String[257];

    private int[] inputStream;
    private StringBuffer outputStream = new StringBuffer();

    private HuffmanCompression() {
        this.root = null;
    }

    private Node buildHeap(int[] heap) {

        BuildMinHeap minHeap = new BuildMinHeap(heap);

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


    private String[] getHuffmanCode(Node rootNode) {

        if (rootNode.leftChildNode == null && rootNode.rightChildNode == null) {

            if (rootNode.parentNode != null)
                rootNode.hcode.append(rootNode.parentNode.hcode);

            System.out.print(rootNode.hcode.toString());
            huffmancode[rootNode.key] = rootNode.hcode.toString();
            System.out.println(" " + rootNode.key);
        } else {
            if (rootNode.leftChildNode != null) {
                //System.out.print("0");
                System.out.print("");
                rootNode.leftChildNode.hcode.append(rootNode.hcode);

                rootNode.leftChildNode.hcode.append("0");
                getHuffmanCode(rootNode.leftChildNode);

            }
            if (rootNode.rightChildNode != null) {

                rootNode.rightChildNode.hcode.append(rootNode.hcode);

                rootNode.rightChildNode.hcode.append("1");
                getHuffmanCode(rootNode.rightChildNode);

            }
        }
        return huffmancode;

    }


    private int[] readData(FileInputStream in) throws IOException {
        try {

            int count;
            byte[] buffer = new byte[1];
            int[] frequency = new int[257];
            int index = 0;
            inputStream = new int[in.available()];
            while ((count = in.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {

                    inputStream[index] = (int) buffer[i] + 129;
                    //System.out.println((int) buffer[i]);
                    frequency[((int) buffer[i] + 129)]++;
                }

                index++;
            }
            System.out.println("original size = " + index);
            int[] heap = new int[256];
            for (int i = 1; i < frequency.length; i++) {
                heap[i - 1] = frequency[i];
                //System.out.println(heap[i - 1]);

            }
            return heap;
        } finally {
            if (in != null) {
                in.close();
            }

        }
    }

    private void writeData(FileOutputStream out,  String[] huffmanTree) throws IOException {




        try {
            int byteArray;

            for (int i = 1; i < huffmanTree.length; i++) {
                //System.out.println(huffmanTree[i]);
                if (huffmanTree[i] != null) {
                    //out.write(Integer.parseInt(huffmanTree[i],2));
                    out.write(huffmanTree[i].getBytes());
                    out.write(10);
                    out.write(i);
                }
            }
            out.write(100);


            for (int i : inputStream) {
                outputStream.append(huffmancode[i]);
                String extraChars;

                if(outputStream.length()>8192)
                {
                    extraChars = outputStream.substring(8192,(outputStream.length()));
                    for (int j = 0; j < 8192; j = j + 8) {
                        out.write(Integer.parseInt(outputStream.substring(j, j + 8), 2));
                        //System.out.print(outputStream.toString());

                    }

                    //out.write(Integer.parseInt(outputStream.substring(0,8191),2));
                    outputStream = new StringBuffer();
                    outputStream.append(extraChars);
                }
            }

            padding = (8 - (outputStream.length() % 8));

            if ((padding) != 0) {
                for (int i = 0; i < padding; i++) {
                    outputStream.append("0");

                }
                System.out.println("Yes! it has padding of " + padding + " bits");

            }
            //System.out.println(outputStream.toString());


            System.out.println("Compression done Successfully!!!");






            //int[] byteArray = new int[outputStream.length() / 8];
            //System.out.println("outstream length " + outputStream.length());
            for (int i = 0, j = 0; i < outputStream.length(); i = i + 8, j++) {
                if (i + 7 < outputStream.length()) {
                    byteArray = Integer.parseInt(outputStream.substring(i, i + 8), 2);
                    //System.out.println(Integer.parseInt(outputStream.substring(i, i + 8), 2));
                    out.write(byteArray);
                }
            }
            out.write(padding);


        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream("100.pdf"); // setting the input file path and creating an object of file input stream
        FileOutputStream out = new FileOutputStream("out.huff"); // setting the output file path and creating an object of file output stream

        HuffmanCompression e = new HuffmanCompression(); // creating an object of this class

        //building a heap from heap[]
        int[] frequency = e.readData(in); // reading the file using above file input stream object and getting a heap array(frequency table of all the input data)

        Node rootNode = e.buildHeap(frequency); // building a heap and huffman tree using frequency table

        String[] huffmanTree = e.getHuffmanCode(rootNode); // getting a huffman code for each data

        //e.compress(); // compressing inpute stream using huffman tree

        e.writeData(out, huffmanTree);// writing to output file with given path

    }
}
