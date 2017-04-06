package com.project;

import java.util.ArrayList;

/**
 * Created by vishal kulkarni on 4/3/17
 */
public class BuildMinHeap {

    protected ArrayList<Node> heap;

    private void swap(int x, int y) // simply swapping array element of indices x and y
    {
        Node temp = heap.get(x);
        heap.add(x, heap.get(y));
        heap.remove(x + 1);
        heap.add(y, temp);
        heap.remove(y + 1);

    }

    private int getLeftChild(int index) // method to get the index of left child
    {
        return (2 * index) + 1;
    }

    private int getRightChild(int index) // method to get the index of right child
    {
        return (2 * index) + 2;
    }

    private boolean isLeaf(int index) {
        if (index < (heap.size() - 2) / 2)
            return true;
        return false;
    }

    private boolean isRightChildPresent(int index) {
        int heapSize = heap.size();
        if ((index * 2) + 2 < heapSize) //(heapSize / 2) * 2 != heapSize
            return true;
        return false;
    }

    public BuildMinHeap(int array[]) {

        heap = new ArrayList<Node>();

        int key = 1;
        for (int data : array) {
            if (data != 0) {
                heap.add(new Node(data, key));
            }
            key++;
        }
        int heapSize = heap.size();

        for (int i = (heapSize - 2) / 2; i >= 0; i--) {
            int leftChildIndex = getLeftChild(i);
            int rightChildIndex = getRightChild(i);
            int smallest;
            if (rightChildIndex >= heapSize) {
                if (leftChildIndex >= heapSize) {
                    return;
                } else {
                    smallest = leftChildIndex;
                }
            } else {
                if (heap.get(leftChildIndex).data > heap.get(rightChildIndex).data) // check here later for modification regarding similar elements. i.e. if its stable or not.
                {
                    // smallest = rightChildIndex;
                    swap(leftChildIndex, rightChildIndex);
                    smallest = leftChildIndex;
                } else {
                    smallest = leftChildIndex;
                }

            }
            if (i < heapSize) {
                if (heap.get(i).data > heap.get(smallest).data) {
                    swap(i, smallest);
                }
            }
        }
    }

    private void MinHepify(int index) {
        int heapSize = heap.size();
        int leftChildIndex = getLeftChild(index);
        int rightChildIndex = getRightChild(index);
        int smallest;

        if (rightChildIndex >= heapSize) {
            if (leftChildIndex >= heapSize) {
                return;
            } else {
                smallest = leftChildIndex;
            }
        } else {
            if (heap.get(leftChildIndex).data > heap.get(rightChildIndex).data) // check here later for modification regarding similar elements. i.e. if its stable or not.
            {
                swap(leftChildIndex, rightChildIndex);
                MinHepify(rightChildIndex);
                smallest = leftChildIndex;
            } else {
                smallest = leftChildIndex;
            }

        }
        if (heap.get(index).data > heap.get(smallest).data) {
            swap(index, smallest);
            MinHepify(smallest);
        }

    }

    public int getSize() {
        return heap.size();
    }

    public Node extractMin() //extract minValue element form ArrayList
    {
        if (heap.size() == 0) {
            System.out.println("Heap is empty!");
            return null;
        } else {
            Node minNode = heap.get(0);
            Node temp = heap.get(heap.size() - 1);
            heap.remove(0);
            heap.add(0, temp);
            heap.remove(heap.size() - 1);
            MinHepify(0);
            return minNode;
        }
        //throw new NullPointerException("No data since Node is not present - heap is empty!");
        //return null;
    }

    public void insert(Node n)// method to insert a new node to min heap
    {
        heap.add(0, n);
        this.MinHepify(0);
    }

//    public static void main(String[] args) {
//        int x[] = {9, 8, 7, 6, 5, 4, 3,6,7, 2, 1}; // created simple array with 9 to 1 values
//        for (int i : x) {
//            System.out.print(i + " ");
//        }
//        System.out.println();
//        BuildMinHeap mh = new BuildMinHeap(x); // here building min heap by calling constructor
//
//        for (int i = 0; i < mh.heap.size(); i++) {
//            System.out.print(mh.heap.get(i).data + " ");
//        }
//        System.out.println();
//
//        System.out.println(mh.extractMin().data);
//
//    }
}
