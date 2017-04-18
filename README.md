## Data Compression Encryption using Huffman Algorithm

#### Algo steps -
1. Calculate the frequency of all the characters present in a given string or file.
2. Build a Min Heap based on frequency of the table(using arrayList of Nodes)
3. Extract Min from minHeap = Node L
4. Extract another Min from minHeap = Node R
5. Now, Node P = L + R; and add this new Node P to MinHeap again.
6. Repeat from 3 to 5 recursively until we get the Root Node of the tree.
7. Assign 0 to left child and 1 to right child.
8. Assign each character at the leaf of the tree and Fetch the huffman code as shown in below diagram.
#### check the following example of Huffman coding for better understanding

![Alt text](https://github.com/vishalk90/DataCompressionEncryption/blob/master/huffman.gif "http://www.data-compression.com/lossless.html")

X    : Characters present in given string.
p(X) : Normalized frequency of all the characters; so that Sum of All p(X) = 1.



Image Source: http://www.data-compression.com/lossless.html
