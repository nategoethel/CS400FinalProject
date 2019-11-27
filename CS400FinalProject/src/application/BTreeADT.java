package application;

import java.util.List;

/**
 * A generic B-Tree interface. Branching factor is 3.
 * 
 * @author Nate
 *
 * @param <T> node type
 */
public interface BTreeADT<T> {

  /**
   * Add a given node to the tree
   * 
   * @param node the node to add
   * @throws IllegalArgumentException
   */
  public void addNode(T node) throws IllegalArgumentException;

  /**
   * Remove a given node from the tree
   * 
   * @param node
   * @throws IllegalArgumentException
   */
  public void removeNode(T node) throws IllegalArgumentException;

  /**
   * Search for a given node in the tree
   * 
   * @param node
   * @throws IllegalArgumentException
   * @return the node if found, null otherwise
   */
  public T search(T node) throws IllegalArgumentException;

  /**
   * In order traversal of the tree
   * 
   * @return a list of all of the nodes
   */
  public List<T> getAllNodes();
}
