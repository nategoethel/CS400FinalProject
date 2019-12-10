package application;

/**
 * Represents a member of Congress
 * 
 * @author Nate Goethel
 *
 */
public class Legislator {
  // instance variables
  private String firstName;
  private String lastName;
  private String state;
  private String party;
  private String body;
  

  // constructor
  public Legislator() {
    this.firstName = null;
    this.lastName = null;
    this.state = null;
    this.party = null;
    this.body = null;
    
  }

  // overloaded constructor
  public Legislator(String firstName, String lastName, String state, String party, String body) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.state = state;
    this.party = party;
    this.body = body;
    
  }

  // getters and setters

  /**
   * Returns the legislator's first name
   * 
   * @return the legislator's first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * TODO
   * 
   * @param firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * TODO
   * 
   * @return
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * TODO
   * 
   * @param lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * TODO
   * 
   * @return
   */
  public String getState() {
    return state;
  }

  /**
   * TODO
   * 
   * @param state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * TODO
   * 
   * @return
   */
  public String getParty() {
    return party;
  }

  /**
   * TODO
   * 
   * @param party
   */
  public void setParty(String party) {
    this.party = party;
  }

}
