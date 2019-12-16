package application;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BTreeTests {

  private BTree<Integer, String> tree;
  
  @Rule
  public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);
  
  @Before
  public void setUp() throws Exception {

    this.tree = new BTree<Integer, String>();
  }
  
  @After
  public void tearDown() throws Exception {
    this.tree = null;
  }
  
  @Test
  public void test00_addKeyNull() {
    try {
      tree.addKey(null, null);
      fail("test00 failed: Should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // passed
    } catch (Exception e) {
      fail("test00 failed: Should throw IllegalArgumentException");
    }
  }
  
  @Test
  public void test01_addValidKey() {
    try {
      tree.addKey(10, "Ten");
      assertEquals("test 01 failed - key insertion error", true, tree.getAllKeys().contains(10));
    } catch (Exception e) {
      fail("test01 failed: unexpected error");
    }
  }
 
  @Test
  public void test02_removeNonExistentKey() {
    try {
      tree.addKey(10, "Ten");
      tree.removeKey(5);
      fail("test02 failed: Should throw KeyNotFoundException");
    } catch (KeyNotFoundException e) {
      // pass
    } catch (Exception e){
      fail("test02 failed: unexpected error");
    }
  }
  
  @Test
  public void test03_removeValidKey() {
    try {
      tree.addKey(10, "Ten");
      tree.addKey(11, "Eleven");
      tree.removeKey(10);
      assertEquals("test03 failed - removal error", true, tree.getAllKeys().contains(10) == false);
    } catch (Exception e) {
      fail("test03 failed: unexpected error");
    }
  }
  
  @Test
  public void test04_removeNullKey() {
    try {
      tree.addKey(10, "Ten");
      tree.removeKey(null);
      fail("test04 failed - should throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // pass
    } catch (Exception e) {
      fail("test04 failed: unexpected error");
    }
  }
  
  @Test
  public void test05_containsNullKey() {
    try {
      tree.addKey(10, "Ten");
      assertEquals("test05 failed: should return false", true, tree.contains(null) == false);
    } catch (Exception e) {
      fail("test05 failed: unexpected error");
    }
  }
  
  @Test public void test06_containsNonExistentKey() {
    try {
      tree.addKey(10, "Ten");
      assertEquals("test06 failed: should return false", true, tree.contains(11) == false);
    } catch (Exception e) {
      fail("test06 failed: unexpected error");
    }
  }
  
  @Test public void test07_containsValidKey() {
    try {
      tree.addKey(10, "Ten");
      assertEquals("test07 failed: should return true", true, tree.contains(10));
    } catch (Exception e) {
      fail("test07 failed: unexpected error");
    }
  }
}
