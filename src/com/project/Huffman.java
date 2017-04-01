package com.project;
import java.io.*;

public class Huffman {

    public static void main(String[] args) throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("vishal_kulkarni_Resume.pdf");
            out = new FileOutputStream("output.pdf");


            int count;
            byte[] buffer = new byte[1];
            int [] frequency = new int[256];

            while ((count = in.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {
                    System.out.print((int) buffer[i]+128);
                    out.write(buffer[i]);
                    //using count sort for counting the freqency of inputstream in each buffer
                    frequency[((int) buffer[i]+128)]++;
                }
                System.out.println(" : value of c: "+count);
                //out.write(c);
            }
            for (int x:frequency
                    ) {

                System.out.println(x);

            }

        }finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }
}
