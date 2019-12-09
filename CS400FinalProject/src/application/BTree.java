package application;


import java.util.ArrayList;
import java.util.List;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Representation Tracker
// Files:
//
// Course: CS400, Fall 2019
//
// Author: Nate Goethel
// Email: ngoethel@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
//
// Online Sources: https://condor.depaul.edu/slytinen/301w15/twothree/
// TwoThreeTree.java - helped get my 2-3 Tree implementation
// started
// https://pages.cs.wisc.edu/~deppeler/cs400/readings/
// 23Trees/#insert - helped me understand the algorithms for
// the major operations
// http://ranger.uta.edu/~kosmopo/cse5311/lectures/LeftLeaningRedBlackTrees.pdf
// https://www.geeksforgeeks.org/left-leaning-red-black-tree-insertion/
// https://medium.com/100-days-of-algorithms/day-76-2-3-tree-f20935b0e78b
// https://www.youtube.com/watch?v=9PiitpHLvRM
// https://stackoverflow.com/questions/18782110/
// best-structure-for-list-of-key-value-integer-string-to-be-shuffled
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
/**
 * Represents a B-Tree
 * 
 * @author Nate
 *
 * @param <T>
 */
public class BTree<K extends Comparable<K>, V> implements BTreeADT<K, V> {

  /**
   * private inner class for the nodes of the tree
   * 
   * A node has 1, 2, or 3 values and 2, 3, or 4 children, respectively.
   * 
   * @author Nate
   *
   * @param <T>
   */
  // BTree instance variables
  private int size; // number of nodes in the tree
  private int numKeysTree; // number of keys in the tree
  private BTreeNode root;

  // inner class for B Tree Nodes
  private class BTreeNode {
    private int numKeys;
    private BTreeNode leftChild;
    private BTreeNode midLeftChild;
    private BTreeNode midRightChild;
    private BTreeNode rightChild;
    private Pair leftPair;
    private Pair midPair;
    private Pair rightPair;
    private BTreeNode parent;

    private BTreeNode() {
      this.numKeys = 0;
      this.leftChild = null;
      this.midLeftChild = null;
      this.midRightChild = null;
      this.rightChild = null;
      this.leftPair = null;
      this.leftPair = null;
      this.rightPair = null;
      this.parent = null;
    }

    private BTreeNode(Pair P, BTreeNode leftChild, BTreeNode rightChild) {
      this.leftChild = leftChild;
      this.midLeftChild = rightChild;

      if (this.leftChild != null) {
        this.leftChild.parent = this;
      }

      if (this.midLeftChild != null) {
        this.midLeftChild.parent = this;
      }

      // TODO need to set parent here?
      this.setLeftPair(P);
      this.numKeys = 1;
    }

    public int getNumKeys() {
      return this.numKeys;
    }

    public void addNumKeys() {
      this.numKeys++;
    }

    public int getNumChildren() {
      int count = 0;
      ArrayList<BTreeNode> children = new ArrayList<BTreeNode>();
      children.add(leftChild);
      children.add(midLeftChild);
      children.add(midRightChild);
      children.add(rightChild);

      for (BTreeNode child : children) {
        if (child != null) {
          count++;
        }
      }

      return count;
    }

    public BTreeNode getLeftChild() {
      return leftChild;
    }

    public void setLeftChild(BTreeNode child) {
      this.leftChild = child;
      if (child != null) {
        child.setParent(this);
      }
    }

    public BTreeNode getMidLeftChild() {
      return midLeftChild;
    }

    public void setMidLeftChild(BTreeNode child) {
      this.midLeftChild = child;
      if (child != null) {
        child.setParent(this);
      }
    }

    public BTreeNode getMidRightChild() {
      return midRightChild;
    }

    public void setMidRightChild(BTreeNode child) {
      this.midRightChild = child;
      if (child != null) {
        child.setParent(this);
      }
    }

    public BTreeNode getRightChild() {
      return rightChild;
    }

    public void setRightChild(BTreeNode child) {
      this.rightChild = child;
      if (child != null) {
        child.setParent(this);
      }
    }

    public Pair getLeftPair() {
      return leftPair;
    }

    public void setLeftPair(Pair leftPair) {
      this.leftPair = leftPair;
    }

    public Pair getMidPair() {
      return this.midPair;
    }

    public void setMidPair(Pair midPair) {
      this.midPair = midPair;
    }

    public Pair getRightPair() {
      return this.rightPair;
    }

    public void setRightPair(Pair rightPair) {
      this.rightPair = rightPair;
    }

    public BTreeNode getParent() {
      return parent;
    }

    public void setParent(BTreeNode parent) {
      this.parent = parent;
    }

    public boolean isFullNode() {
      if (this.getNumKeys() == 3) {
        return true;
      }
      return false;
    }

    public boolean isLeaf() {

      if (this.getLeftChild() == null && this.getMidLeftChild() == null
          && this.getMidRightChild() == null && this.getRightChild() == null) {
        return true;
      }

      return false;
    }

    /**
     * Checks whether the node contains a certain key
     * 
     * @param key the key to check for
     * @return true if any of the key/value pairs in the node contain the key, false otherwise
     */
    public boolean containsKey(K key) {

      if (this.isEmpty()) {
        return false;
      }

      ArrayList<Pair> pairs = new ArrayList<Pair>();
      pairs.add(this.getLeftPair());
      pairs.add(this.getMidPair());
      pairs.add(this.getRightPair());

      for (Pair p : pairs) {
        if (p != null && p.key.equals(key)) {
          return true;
        }
      }

      return false;
    }

    /**
     * Checks whether the node has no keys
     * 
     * @return true if all keys are null, false otherwise
     */
    public boolean isEmpty() {

      if (this.leftPair == null && this.midPair == null && this.rightPair == null) {
        return true;
      }

      return false;
    }

    public List<K> getKeys() {
      ArrayList<K> keys = new ArrayList<K>();
      ArrayList<Pair> pairs = new ArrayList<Pair>();
      pairs.add(this.getLeftPair());
      pairs.add(this.getMidPair());
      pairs.add(this.getRightPair());

      for (Pair p : pairs) {
        if (p != null) {
          keys.add(p.getKey());
        }
      }

      return keys;
    }
  }

  // inner class for key value pairs
  private class Pair {
    private K key;
    private V value;

    private Pair() {
      this.key = null;
      this.value = null;
    }

    private Pair(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey() {
      return key;
    }

    public void setKey(K key) {
      this.key = key;
    }

    public V getValue() {
      return value;
    }

    public void setValue(V value) {
      this.value = value;
    }
  }

  // constructors
  public BTree() {
    this.root = null;
    this.size = 0;
    this.numKeysTree = 0;
  }

  public BTree(int branchingFactor) {
    this.root = null;
    this.size = 0;
    this.numKeysTree = 0;
  }

  /**
   * Adds a new key/value pair to the tree
   */
  @Override
  public void addKey(K key, V value) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    root = addKey(root, key, value);
    while (root.parent != null) {
      root = root.parent;
    }

  }

  /**
   * Removes a key/value pair from the tree
   */
  @Override
  public void removeKey(K key) throws IllegalArgumentException {
    removeKey(root, key);
  }

  /**
   * Finds the pair with a given key
   * 
   * @param key
   * @return
   * @throws IllegalArgumentException
   */
  @Override
  public V search(K key) throws IllegalArgumentException {

    return null;
    // TODO Auto-generated method stub

  }

  /**
   * Returns a list of all of the objects in the tree.
   * 
   * @return
   */
  public List<BTreeNode> getAllValues() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getSize() {
    return size;
  }


  public int getNumKeys() {
    return numKeysTree;
  }

  /**
   * source: Building Java Programs, 4thEd., by Reges and Stepp, Ch. 17
   * 
   * Prints the tree sideways
   */
  public void printSideways() {
    System.out.println("------------------------------------------");
    printSideways(this.root, "");
    System.out.println("------------------------------------------");
  }

  /**
   * private recursive helper method for printSideways() above.
   * 
   * @param current the node to print from
   * @param indent
   */
  private void printSideways(BTreeNode current, String indent) {
    if (current != null) {
      if (current.getNumChildren() == 1) {
        printSideways(current.getLeftChild(), indent + "    ");
      } else if (current.getNumChildren() == 2) {
        printSideways(current.getMidLeftChild(), indent + "    ");
        System.out.println(indent + current.getKeys());
        printSideways(current.getLeftChild(), indent + "    ");
      } else if (current.getNumChildren() == 3) {
        printSideways(current.getMidRightChild(), indent + "    ");
        System.out.println(indent + current.getKeys());
        printSideways(current.getMidLeftChild(), indent + "    ");
        printSideways(current.getLeftChild(), indent + "    ");
      } else {
        printSideways(current.getRightChild(), indent + "    ");
        printSideways(current.getMidRightChild(), indent + "    ");
        System.out.println(indent + current.getKeys());
        printSideways(current.getMidLeftChild(), indent + "    ");
        printSideways(current.getLeftChild(), indent + "    ");
      }
    }
  }

  /**
   * Private helper method for addKey() above.
   * 
   * Implement as a left-leaning Red Black tree https://www.youtube.com/watch?v=9PiitpHLvRM
   * 
   * @param current
   * @param value
   * @return
   */
  private BTreeNode addKey(BTreeNode current, K key, V value) {

    if (current == null) {
      current = new BTreeNode();
      return addKeyLeaf(current, key, value);
    }

    // check whether the key already exists in the tree
    if (current.containsKey(key)) {
      return null;
    }

    if (current.isFullNode()) { // the current node is full, split it
      current = BTreeSplit(this, current);
    }

    if (current.isLeaf() == false) { // if the current node isn't a leaf node

      // if the key is smaller than current's smallest key, go to the left child
      if (key.compareTo(current.getLeftPair().getKey()) < 0) {
        return addKey(current.getLeftChild(), key, value);

        // if the current node's middle key is null or key is less than the current node's middle
        // key,
        // go to the middle left child
      } else if (current.getMidPair() == null || key.compareTo(current.getMidPair().getKey()) < 0) {
        return addKey(current.getMidLeftChild(), key, value);

        // if the current node's largest key is null or key is less than the largest key, go
        // to the middle right child
      } else if (current.getRightPair() == null
          || key.compareTo(current.getRightPair().getKey()) < 0) {
        return addKey(current.getMidRightChild(), key, value);

        // otherwise, go to the right child
      } else {
        return addKey(current.getRightChild(), key, value);
      }
    } else {
      addKeyLeaf(current, key, value);
    }
    return current;
  }

  private BTreeNode addKeyLeaf(BTreeNode current, K key, V value) {

    if (current.isEmpty()) {
      Pair newPair = new Pair(key, value);
      current.setLeftPair(newPair);
      current.addNumKeys();
      numKeysTree++;
      return current;
    }


    // case 1: New key equals an existing key in the node
    if (current.containsKey(key)) {
      return null;
    }

    // case 2: New key is < node's first key -> Existing keys are shifted right
    if (key.compareTo(current.getLeftPair().getKey()) < 0) {
      Pair newPair = new Pair(key, value); // create a new key/pair value
      current.setRightPair(current.getMidPair());
      current.setMidPair(current.getLeftPair());
      current.setLeftPair(newPair);
      current.addNumKeys();
      numKeysTree++;
      return current;
    }

    // case 3: Node has only 1 key or new key is < node's middle key -> new key is middle key
    if (current.getNumKeys() == 1 || key.compareTo(current.getMidPair().getKey()) < 0) {
      Pair newPair = new Pair(key, value);
      if (current.getMidPair() != null) { // if middle pair isn't null, move it to the right
        current.setRightPair(current.getMidPair());
        current.setMidPair(newPair); // insert the new pair in the middle position
        current.addNumKeys(); // increment the number of keys in the node
        numKeysTree++; // increment the number of keys in the tree
        return current;
      } else { // if the tree has only one key
        current.setMidPair(newPair); // set the middle key to the new pair and increment counts
        current.addNumKeys();
        numKeysTree++;
        return current;
      }
    }

    // case 4: None of the above -> New key becomes last key
    Pair newPair = new Pair(key, value);
    current.setRightPair(newPair);;
    current.addNumKeys();
    numKeysTree++;

    return current;
  }


  private void addKeyInterior(BTreeNode parent, Pair P, BTreeNode leftChild, BTreeNode rightChild) {

    // if the key is smaller than the parent's smallest key, push parent's keys to the right
    if (P.getKey().compareTo(parent.getLeftPair().getKey()) < 0) {
      parent.setRightPair(parent.getMidPair());
      parent.setMidPair(parent.getLeftPair());
      parent.setLeftPair(P);
      parent.addNumKeys();

      // set the children
      parent.setRightChild(parent.getMidRightChild());
      parent.setMidRightChild(parent.getMidLeftChild());
      parent.setMidLeftChild(rightChild);
      parent.setLeftChild(leftChild);

    } else if (parent.getMidPair() == null
        || P.getKey().compareTo(parent.getMidPair().getKey()) < 0) {
      parent.setRightPair(parent.getMidPair());
      parent.setMidPair(P);
      parent.addNumKeys();
      parent.setRightChild(parent.getMidRightChild());
      parent.setMidRightChild(rightChild);
      parent.setMidLeftChild(leftChild);
    } else {
      parent.setRightPair(P);
      parent.addNumKeys();
      parent.setRightChild(rightChild);
      parent.setMidRightChild(leftChild);
    }

  }

  /**
   * Recursively find the node to remove
   * 
   * @param node the node to start searching from
   * @param key  the key to search for
   * @return the
   */
  private boolean removeKey(BTreeNode node, K key) {

    if (node == null) {
      return false;
    }

    // preemptive merge
    if (node.getNumKeys() == 1 && node.equals(root) == false) {
      node = BTreeMerge(node);
    }


    if (node.containsKey(key)) {// base case
      
      // <1> get the index of the key
      int keyIndex = getKeyIndex(node, key);
      // </1>
      
      // <2> Check whether the node is a leaf
      if (node.isLeaf()) {
        
        removeKeyFromIndex(node, keyIndex);
        return true;
        
      } else {
        // <A> get the child node to the right of node FIXME add keyIndex + 1 back in if not working
        BTreeNode tempNode = getChild(node, keyIndex);
        //</A>
        
        //<B> Get the smallest key in the subtree of that child node
        Pair tempKey = getMinKey(tempNode);
        //</B>
        
        //<C> Remove the temp key from the tree
        removeKey(tempKey.getKey());
        //</C>
        
        //<D> Swap the key that's being removed with temp key
        
        //FIXME testing with keySwapPair()
        Pair currPair = find(node, key);
        keySwapPair(root, currPair, tempKey);
        // FIXME keySwap(root, key, tempKey.getKey());
        return true;
        //</D>
      }
      // </2>
      
      
      /*
       * // if node is a leaf if (node.isLeaf()) { removeKeyLeaf(node, key); return true; } else {
       * removeKeyInterior(node, key); return true; }
       */

    } else { // recursive part
      if (key.compareTo(node.leftPair.getKey()) < 0) { // go to left child
        return removeKey(node.getLeftChild(), key);
        
        // go to the middle left child
      } else if (node.getMidPair() == null || key.compareTo(node.getMidPair().getKey()) < 0) {
        return removeKey(node.getMidLeftChild(), key);
        
        // go to the middle right child
      } else if (node.getRightPair() == null || key.compareTo(node.getRightPair().getKey()) < 0) {
        return removeKey(node.getMidRightChild(), key);
        
      } else { // go to right child
        return removeKey(node.getRightChild(), key);
      }
    }
  }

  private Pair find(BTreeNode node, K key) {
    
    
    Pair pairToReturn = null;
    
    if (node != null && node.containsKey(key)) {// base case
      if (node.getLeftPair().getKey().equals(key)) {
        pairToReturn = node.getLeftPair();
        return pairToReturn;
      } else if (node.getMidPair().getKey().equals(key)) {
        pairToReturn = node.getMidPair();
        return pairToReturn;
      } else {
        pairToReturn = node.getRightPair();
        return pairToReturn;
      }

    } else { // recursive part
      if (key.compareTo(node.getLeftPair().getKey()) < 0) { // go to left child
        pairToReturn = find(node.getLeftChild(), key);
        // go to the middle left child
      } else if (node.getMidPair() == null || key.compareTo(node.getMidPair().getKey()) < 0) {
        pairToReturn = find(node.getMidLeftChild(), key);
        
        // go to the middle right child
      } else if (node.getRightPair() == null || key.compareTo(node.getRightPair().getKey()) < 0) {
        pairToReturn = find(node.getMidRightChild(), key);
        
      } else { // go to right child
        pairToReturn = find(node.getRightChild(), key);
      }
    }

    return pairToReturn;

  }

  /**
   * Moves a key from the given node to the parent and moves a key from the parent to the node's
   * sibling.
   * 
   * @param node the node to rotate
   */
  private void leftRotate(BTreeNode node) {

    BTreeNode leftSibling = getLeftSibling(node);
    Pair leftSibKey = getParentKeyOfLeftChild(node.parent, node);
    addKeyAndChild(leftSibling, node.getLeftChild(), leftSibKey);
    setParentKeyOfLeftChild(node.parent, node, node.getLeftPair());
    removeKeyFromIndex(node, 0);

  }

  /**
   * Moves a key from the given node to the parent and moves a key from the parent to the node's
   * sibling.
   * 
   * @param node
   */
  private void rightRotate(BTreeNode node) {

    BTreeNode rightSibling = getRightSibling(node);
    Pair rightSibKey = getParentKeyOfRightChild(node.parent, node);
    addKeyAndChild(rightSibling, node.getRightChild(), rightSibKey);
    setParentKeyOfRightChild(node.parent, node, node.getRightPair());
    removeKeyFromIndex(node, 2);
  }

  /**
   * 
   * @param tree
   * @param node
   * @return
   */
  private BTreeNode BTreeSplit(BTree<K, V> tree, BTreeNode node) {

    if (node.isFullNode() == false) { // if the node isn't full, don't split and return.
      return null;
    }

    BTreeNode parent = node.parent;
    BTreeNode splitLeft =
        new BTreeNode(node.getLeftPair(), node.getLeftChild(), node.getMidLeftChild());
    BTreeNode splitRight =
        new BTreeNode(node.getRightPair(), node.getMidRightChild(), node.getRightChild());

    if (parent != null) {
      addKeyInterior(parent, node.getMidPair(), splitLeft, splitRight);
    } else {
      parent = new BTreeNode(node.getMidPair(), splitLeft, splitRight);
      splitLeft.setParent(parent);
      splitRight.setParent(parent);
      // tree.root = parent; //FIXME comment out if this doesn't fix it.

    }

    return parent;

  }

  /**
   * Merges a node if necessary
   * 
   * @param node the node to merge
   * @return the merged node
   */
  private BTreeNode BTreeMerge(BTreeNode node) {

    BTreeNode leftSibling = getLeftSibling(node);
    BTreeNode rightSibling = getRightSibling(node);

    if (leftSibling != null && leftSibling.getNumKeys() >= 2) {
      rightRotate(leftSibling);
    } else if (rightSibling != null && rightSibling.getNumKeys() >= 2) {
      leftRotate(rightSibling);
    } else {
      if (leftSibling == null) {
        node = fuse(node, rightSibling);
      } else {
        node = fuse(leftSibling, node);
      }
    }

    return node;
  }

  /**
   * Returns the left sibling of the given node
   * 
   * @param node the node whose sibling to find
   * @return the left sibling of the node, null otherwise
   */
  private BTreeNode getLeftSibling(BTreeNode node) {

    BTreeNode parent = node.getParent();

    if (parent != null) {
      // if node is parent's left child, return null
      if (parent.getLeftChild() != null && parent.getLeftChild().equals(node)) {
        return null;
      }

      // if node is parent's middle left child, return left child
      if (parent.getMidLeftChild() != null && parent.getMidLeftChild().equals(node)) {
        return parent.getLeftChild();
      }

      // if node is parent's middle right child, return the middle left child
      if (parent.getMidRightChild() != null && parent.getMidRightChild().equals(node)) {
        return parent.getMidLeftChild();
      }

      // if node is parent's right child, return the middle right child
      if (parent.getRightChild() != null && parent.getRightChild().equals(node)) {
        return parent.getMidRightChild();
      }
    }

    return null;
  }

  /**
   * Returns the right sibling of the given node
   * 
   * @param node the node whose sibling to find
   * @return the right sibling of the node, null otherwise
   */
  private BTreeNode getRightSibling(BTreeNode node) {

    // get the parent
    BTreeNode parent = node.getParent();

    // check that the node isn't root
    if (parent != null) {
      // if node is parent's right child, return null
      if (parent.getRightChild() != null && parent.getRightChild().equals(node)) {
        return null;
      }

      // if node is parent's middle right child, return right child
      if (parent.getMidRightChild() != null && parent.getMidRightChild().equals(node)) {
        return parent.getRightChild();
      }

      // if node is parent's middle left child, return the middle right child
      if (parent.getMidLeftChild() != null && parent.getMidLeftChild().equals(node)) {
        return parent.getMidRightChild();
      }

      // if node is parent's left child, return the middle left child
      if (parent.getLeftChild() != null && parent.getLeftChild().equals(node)) {
        return parent.getMidLeftChild();
      }
    }

    return null;

  }

  /**
   * Returns the key that is immediately left of the child
   * 
   * @param parent the parent node
   * @param child  the child node
   * @return the key to the left of the child, null otherwise
   */
  private Pair getParentKeyOfLeftChild(BTreeNode parent, BTreeNode child) {

    // if child is the left child, return null
    if (parent.getLeftChild().equals(child)) {
      return null;
    }

    // if child is the middle left child, return the left key
    if (parent.getMidLeftChild().equals(child)) {
      return parent.getLeftPair();
    }

    // if child is the middle right child, return the middle key
    if (parent.getMidRightChild().equals(child)) {
      return parent.getMidPair();
    }

    // if child is the right child, return the right key
    if (parent.getRightChild().equals(child)) {
      return parent.getRightPair();
    }


    return null;
  }

  /**
   * Sets the key in the parent node that is immediately left of the child node
   * 
   * @param parent the parent node
   * @param child  the child node
   * @param p      the pair (key) to set
   */
  private void setParentKeyOfLeftChild(BTreeNode parent, BTreeNode child, Pair p) {

    // if child is the left child, do nothing

    // if child is the middle left child, set the parent's left pair
    if (parent.getMidLeftChild().equals(child)) {
      parent.setLeftPair(p);
      return;
    }

    // if child is the middle right child, set the parent's middle pair
    if (parent.getMidRightChild().equals(child)) {
      parent.setMidPair(p);
      return;
    }

    // if child is the right child, set the parent's right pair
    
    //System.out.println("Right child is: " + parent.getRightChild().getKeys());
    if (parent.getRightChild().equals(child)) {
      parent.setRightPair(p);
      return;
    }
  }

  /**
   * Get the key/pair in the parent that is immediately to the right of the child node
   * 
   * @param parent
   * @param child
   * @return
   */
  private Pair getParentKeyOfRightChild(BTreeNode parent, BTreeNode child) {
    // if the child is the right child, return null
    if (parent.getRightChild().equals(child)) {
      return null;
    }

    // if the child is the middle right child, return the right key
    if (parent.getMidRightChild().equals(child)) {
      return parent.getRightPair();
    }

    // if the child is the middle left child, return the middle key
    if (parent.getMidLeftChild().equals(child)) {
      return parent.getMidPair();
    }

    // if the child is the left child, return the left key
    if (parent.getLeftChild().equals(child)) {
      return parent.getLeftPair();
    }

    return null;
  }

  /**
   * Sets the key in the parent node that is immediately right of the child node
   * 
   * @param parent
   * @param child
   * @param p
   */
  private void setParentKeyOfRightChild(BTreeNode parent, BTreeNode child, Pair p) {

    // if child is the right child, do nothing

    // if child is the middle right child, set the parent's right pair
    if (parent.getMidRightChild().equals(child)) {
      parent.setRightPair(p);
    }

    // if the child is the middle left child, set the parent's middle pair
    if (parent.getMidLeftChild().equals(child)) {
      parent.setMidPair(p);
    }

    // if the child is the left child, set the parent's left pair
    if (parent.getLeftChild().equals(child)) {
      parent.setLeftPair(p);
    }


  }

  /**
   * Adds one new key and one new child to the parent node. The new key must be greater than the
   * other keys in the node, and the child subtree must be greater than the key.
   * 
   * Ex: If parent has 1 key, the new key becomes the middle key and the child becomes the middle
   * right child. If parent has 2 keys, the new key becomes the right key and the child becomes the
   * right child.
   * 
   * @param node  the node to modify
   * @param child the child node to add
   * @param p     the key to add
   */
  private void addKeyAndChild(BTreeNode node, BTreeNode child, Pair p) {

    // make sure that the node we're modifying isn't full and isn't empty
    if (node.isFullNode() == false && node.isEmpty() == false) {
      if (node.getNumKeys() == 1) {
        node.setMidPair(p);
        node.setMidRightChild(child);
        node.addNumKeys();
      } else if (node.getNumKeys() == 2) {
        node.setRightPair(p);
        node.setRightChild(child);
        node.addNumKeys();
      }
    }
  }

  /**
   * Removes a key from the given node.
   * 
   * @param node     the node to remove a key from
   * @param keyIndex the index of the key
   */
  private void removeKeyFromIndex(BTreeNode node, int keyIndex) {

    // remove left key
    if (keyIndex == 0) {
      node.setLeftPair(node.getMidPair());
      node.setMidPair(node.getRightPair());
      node.setRightPair(null);
      node.numKeys--;
      numKeysTree--;


      node.setLeftChild(node.getMidLeftChild());
      node.setMidLeftChild(node.getMidRightChild());
      node.setMidRightChild(node.getRightChild());
      node.setRightChild(null);

      // remove middle key
    } else if (keyIndex == 1) {
      node.setMidPair(node.getRightPair());
      node.setRightPair(null);
      node.numKeys--;
      numKeysTree--;

      node.setMidRightChild(node.getRightChild());
      node.setRightChild(null);

      // remove right key
    } else if (keyIndex == 2) {
      node.setRightPair(null);
      node.numKeys--;
      numKeysTree--;


      node.setRightChild(null);
    }
  }

  /**
   * Performs a fusion on the root node
   * 
   * @param node
   */
  private BTreeNode fuseRoot(BTreeNode node) {


    return null;
  }

  /**
   * Fuses two non-root nodes together
   * 
   * @param node1
   * @param node2
   */
  private BTreeNode fuse(BTreeNode node1, BTreeNode node2) {
    BTreeNode parent = node1.getParent();

    if (parent.getParent() == null && parent.getNumKeys() == 1) {
      return fuseRoot(parent);
    }

    Pair midKey = getParentKeyOfLeftChild(parent, node2);
    BTreeNode fusedNode = new BTreeNode();
    fusedNode.setLeftPair(node1.getLeftPair());
    fusedNode.setMidPair(midKey);
    fusedNode.setRightPair(node2.getLeftPair());
    fusedNode.setLeftChild(node1.getLeftChild());
    fusedNode.setMidLeftChild(node1.getMidLeftChild());
    fusedNode.setMidRightChild(node2.getLeftChild());
    fusedNode.setRightChild(node2.getMidLeftChild());
    int keyIndex = getKeyIndex(parent, midKey.getKey());
    removeKeyFromIndex(parent, keyIndex);
    setChild(parent, fusedNode, keyIndex);

    return fusedNode;
  }

  /**
   * Sets the left, middle left, middle right, or right child of the parent node.
   * 
   * @param parent the node whose child to set
   * @param child  the node to become a child of parent
   * @param index  the index (0-3) at which to set the child. 0 = left, 1 = middle left, etc.
   */
  private void setChild(BTreeNode parent, BTreeNode child, int index) {

    if (index == 0) {
      parent.setLeftChild(child);
    }

    if (index == 1) {
      parent.setMidLeftChild(child);
    }

    if (index == 2) {
      parent.setMidRightChild(child);
    }

    if (index == 3) {
      parent.setRightChild(child);
    }


  }

  /**
   * Returns a given node's child based on it's index
   * 
   * @param node
   * @param index
   * @return
   */
  private BTreeNode getChild(BTreeNode node, int index) {

    if (index == 0) {
      return node.getLeftChild();
    }

    if (index == 1) {
      return node.getMidLeftChild();
    }

    if (index == 2) {
      return node.getMidRightChild();
    }

    if (index == 3) {
      return node.getRightChild();
    }

    return null;
  }

  /**
   * Returns the index of the key in the node
   * 
   * @param node the node to search
   * @param p    the pair/key to find
   * @return the index (0-2) of the pair in the node, -1 otherwise
   */
  private int getKeyIndex(BTreeNode node, K key) {
    // TODO remove these statements System.out.println("Here " + Math.random());
    
    
    //System.out.println("Comparing " + key + " to " + node.getLeftPair().getKey());
    if (node.getLeftPair() != null && node.getLeftPair().getKey().equals(key)) {
      return 0;
    }

    //System.out.println("Comparing " + key + " to " + node.getMidPair().getKey());
    if (node.getMidPair() != null && node.getMidPair().getKey().equals(key)) {
      return 1;
    }
    
    //System.out.println("Comparing " + key + " to " + node.getRightPair().getKey());
    if (node.getRightPair() != null && node.getRightPair().getKey().equals(key)) {
      return 2;
    }


    return -1;
  }

  /**
   * Returns the smallest key in a subtree
   * 
   * @param node the node to start searching from.
   * @return
   */
  private Pair getMinKey(BTreeNode node) {
    BTreeNode current = node;

    while (current.getLeftChild() != null) {
      current = current.getLeftChild();
    }

    return current.getLeftPair();
  }

  /**
   * Returns the next node that would be visited in a traversal
   * 
   * @param node
   * @param p
   * @return
   */
  private BTreeNode nextNode(BTreeNode node, K key) {

    if (key.compareTo(node.getLeftPair().getKey()) < 0) {
      return node.getLeftChild();
    } else if (node.getMidPair() == null || key.compareTo(node.getMidPair().getKey()) < 0) {
      return node.getMidLeftChild();
    } else if (node.getRightPair() == null || key.compareTo(node.getRightPair().getKey()) < 0) {
      return node.getMidRightChild();
    } else {
      return node.getRightChild();
    }

  }

  private boolean keySwapPair(BTreeNode node, Pair currPair, Pair newPair) {
    if (node == null) {
      return false;
    }
    
    int keyIndex = getKeyIndex(node, currPair.getKey());
    
    if (keyIndex == -1) {
      BTreeNode next = nextNode(node, currPair.getKey());
      return keySwapPair(next, currPair, newPair);
    } else if (keyIndex == 0) {
      node.setLeftPair(newPair);
      return true;
    } else if (keyIndex == 1) {
      node.setMidPair(newPair);
      return true;
    } else if (keyIndex == 2) {
      node.setRightPair(newPair);
      return true;
    }
    
    return false;
  }

  public static void main(String[] args) {
    BTree<Integer, String> tree = new BTree<Integer, String>();
    tree.addKey(10, "Ten");
    tree.addKey(12, "Twelve");
    tree.addKey(11, "Eleven");
    tree.addKey(13, "Thirteen");
    tree.addKey(14, "Fourteen");
    tree.addKey(15, "Fifteen");
    tree.addKey(9, "Nine");
    tree.addKey(17, "Seventeen");
    tree.addKey(8, "Eight");
    tree.addKey(7, "Seven");
    tree.addKey(18, "Eighteen");
    tree.addKey(19, "Nineteen");
    // System.out.println(tree.getNumKeys());
    System.out.println("The tree after adding keys");
    tree.printSideways();

    System.out.println("The tree after removing 19");
    //tree.removeKey(19);
    //tree.removeKey(18);
    //tree.removeKey(17);
    tree.removeKey(11);
    //tree.removeKey(11);
    tree.printSideways();

  }
}
