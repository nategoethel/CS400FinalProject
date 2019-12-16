package application;

import javafx.beans.property.Property;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Representation Tracker
// Files:BTree.java, BTreeADT.java, Congress.java, CongressTests.java,
// Legislator.java, Main.java
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
// Online Sources: https://stackify.com/streams-guide-java-8/ - stream help
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

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
  private String gender;
  private String fullName;


  // constructor
  public Legislator() {
    this.firstName = "";
    this.lastName = "";
    this.state = "";
    this.party = "";
    this.body = "";
    this.gender = "";
    this.fullName = this.firstName + " " + this.lastName;

  }

  // overloaded constructor
  public Legislator(String firstName, String lastName, String gender, String state, String party,
      String body) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.state = state;
    this.party = party;
    this.body = body;
    this.fullName = this.firstName + " " + this.lastName;

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

  public String getFullName() {
    return this.fullName;
  }
  /**
   * 
   * @return
   */
  public String getGender() {
    return this.gender;
  }

  /**
   * 
   * @param gender
   */
  public void setGender(String gender) {
    this.gender = gender;
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

  /**
   * 
   * @return
   */
  public String getBody() {
    return this.body;
  }

  /**
   * 
   * @param body
   */
  public void setBody(String body) {
    this.body = body;
  }

}
