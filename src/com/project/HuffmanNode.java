package com.project;

/**
 * Created by vishalkulkarni on 4/3/17.
 */
public class HuffmanNode {
    int data;
    HuffmanNode parentNode;
    HuffmanNode leftChildNode;
    HuffmanNode rightChildNode;

    public HuffmanNode(int data, HuffmanNode parentNode, HuffmanNode leftChildNode, HuffmanNode rightChildNode) {
        this.data = data;
        this.parentNode = parentNode;
        this.leftChildNode = leftChildNode;
        this.rightChildNode = rightChildNode;
    }
}
