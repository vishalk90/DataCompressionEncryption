package com.project;

/**
 * Created by vishalkulkarni on 4/3/17.
 */
public class Node {
    //HuffmanNode huffmanNode;
    int data;
    int key;
    StringBuffer hcode = new StringBuffer();
    Node parentNode;
    Node leftChildNode;
    Node rightChildNode;

    public Node(int data, Node parentNode, Node leftChildNode, Node rightChildNode) {
        //this.huffmanNode = huffmanNode;
        this.data = data;
        //this.key = key;
        this.parentNode = parentNode;
        this.leftChildNode = leftChildNode;
        this.rightChildNode = rightChildNode;
    }

    public Node(int data, int key) {
        this.data = data;
        this.key = key;
    }

    public Node(Node parentNode, Node leftChildNode, Node rightChildNode) {
        this.parentNode = parentNode;
        this.leftChildNode = leftChildNode;
        this.rightChildNode = rightChildNode;
    }
}
