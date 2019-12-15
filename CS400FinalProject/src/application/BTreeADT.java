package application;

import java.util.List;

/**
 * A generic B-Tree interface. Branching factor is 3.
 * 
 * @author Nate
 *
 * @param <T> node type
 */
public interface BTreeADT<K, V> {

  /**
   * Add a given node to the tree
   * 
   * @param node the node to add
   * @throws IllegalArgumentException
   */
  public void addKey(K key, V value) throws IllegalArgumentException;

  /**
   * Remove a given node from the tree
   * 
   * @param node
   * @throws IllegalArgumentException
   */
  public void removeKey(K key) throws IllegalArgumentException, KeyNotFoundException;

  /**
   * Search for a given node in the tree
   * 
   * @param node
   * @throws IllegalArgumentException
   * @return the node if found, null otherwise
   */
  public List<V> getAllValues();
  

}
