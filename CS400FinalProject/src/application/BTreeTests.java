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

  private Congress congress;
  
  @Rule
  public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);
  
  @Before
  public void setUp() throws Exception {

    this.congress = new Congress();
  }
  
  @After
  public void tearDown() throws Exception {
    this.congress = null;
  }
  
 
}
