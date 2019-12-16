package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents all of the members of Congress. Uses a B Tree as the backbone.
 * 
 * @author Nate
 *
 */
public class Congress {
  private static BTree<Integer, Legislator> congress;
  private static String filename; // TODO pick the file


  public Congress() {
    Congress.congress = new BTree<Integer, Legislator>();
    Congress.filename = "legislators-current (1).csv";
    try {
      parseCSV();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public Congress(String filename) {
    Congress.filename = filename;
    Congress.congress = new BTree<Integer, Legislator>();
    try {
      parseCSV();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Parses the CSV file
   */
  private static void parseCSV() throws Exception {

    try {

      Scanner csvScnr = new Scanner(new File(filename));
      String header = csvScnr.nextLine();

      while (csvScnr.hasNextLine()) {

        String row = csvScnr.nextLine();
        String[] data = row.split(",");

        String lastName = data[0];
        String firstName = data[1];
        String gender = data[2];
        String state = data[4];
        String party = data[5];
        String body = data[3];

        // create Legislator object
        Legislator legislator = new Legislator(firstName, lastName, gender, state, party, body);

        // insert into the tree
        congress.addKey((legislator.getFirstName().toLowerCase().trim().hashCode()
            + legislator.getLastName().toLowerCase().trim().hashCode()), legislator);
      }

      csvScnr.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }

  }

  /**
   * Creates a new Legislator and adds it to the BTree
   * 
   * @param firstName
   * @param lastName
   * @param gender
   * @param state
   * @param party
   * @param body
   */
  public boolean addLegislator(String firstName, String lastName, String gender, String state,
      String party, String body) {

    Legislator newLegislator =
        new Legislator(firstName.trim(), lastName.trim(), gender, state, party, body);
    int key = firstName.trim().toLowerCase().hashCode() + lastName.trim().toLowerCase().hashCode();
    congress.addKey(key, newLegislator);

    if (congress.contains(key)) {
      return true;
    }

    return false;

  }

  /**
   * Adds a legislator to the tree from an existing Legislator
   * 
   * @param l the Legislator object to add
   * @return true if the Legislator was added successfully, false otherwise
   */
  public boolean addLegislator(Legislator l) {
    int key = l.getFirstName().trim().toLowerCase().hashCode()
        + l.getLastName().trim().toLowerCase().hashCode();

    congress.addKey(key, l);

    if (congress.contains(key)) {
      return true;
    }

    return false;
  }

  /**
   * Removes a legislator from the tree
   * 
   * @param firstName the legislator's first name
   * @param lastName  the legislator's last name
   * @return true if the legislator was removed, false otherwise
   */
  public boolean removeLegislator(String firstName, String lastName) {
    firstName = firstName.toLowerCase().trim();
    lastName = lastName.toLowerCase().trim();
    int key = firstName.hashCode() + lastName.hashCode();

    try {
      congress.removeKey(key);
    } catch (IllegalArgumentException | KeyNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (congress.contains(key)) {
      return false;
    } else {
      return true;
    }
  }


  public int getNumFemSenate() {
    List<Legislator> allCongress = congress.getAllValues();

    List<Legislator> femReps = allCongress.stream().filter(e -> e.getBody().equals("sen"))
        .filter(e -> e.getGender().equals("F")).collect(Collectors.toList());

    return femReps.size();
  }


  public int getNumMaleSenate() {
    List<Legislator> allCongress = congress.getAllValues();

    List<Legislator> femReps = allCongress.stream().filter(e -> e.getBody().equals("sen"))
        .filter(e -> e.getGender().equals("M")).collect(Collectors.toList());

    return femReps.size();
  }

  /**
   * Returns the number of females in the House
   * 
   * @return the number of females in the House
   */
  public int getNumFemHouse() {

    List<Legislator> allCongress = congress.getAllValues();

    List<Legislator> femReps = allCongress.stream().filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getGender().equals("F")).collect(Collectors.toList());

    return femReps.size();
  }

  /**
   * Returns the number of males in the House
   * 
   * @return the number of males in the House
   */
  public int getNumMaleHouse() {
    List<Legislator> allCongress = congress.getAllValues();

    List<Legislator> maleReps = allCongress.stream().filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getGender().equals("M")).collect(Collectors.toList());

    return maleReps.size();
  }

  /**
   * Determines the percentage of the Senate that is female
   * 
   * @return a integer representing the percent of the Senate that is female, rounded.
   */
  public int getPercentFemaleSenate() {

    int numFemSenators = 0;

    List<Legislator> senators = congress.getAllValues(); // get all legislators

    List<Legislator> femSenators = senators.stream() // create a stream
        .filter(e -> e.getBody().equals("sen")) // filter on senators
        .filter(e -> e.getGender().equals("F")) // filter on females
        .collect(Collectors.toList()); // collect into a list

    numFemSenators = femSenators.size();

    return numFemSenators;
  }


  /**
   * Determines the percentage of senators that are male
   * 
   * @return a integer representing the percentage of senators who are male, rounded.
   */
  public int getPercentMaleSenate() {

    int numMaleSenators = 0;

    List<Legislator> senators = congress.getAllValues(); // get all legislators

    List<Legislator> maleSenators = senators.stream() // create a stream
        .filter(e -> e.getBody().equals("sen")) // filter on senators
        .filter(e -> e.getGender().equals("M")) // filter on males
        .collect(Collectors.toList()); // collect into a list

    numMaleSenators = maleSenators.size();

    return numMaleSenators;
  }

  /**
   * Determines the percentage of representatives that are female
   * 
   * @return a integer representing the percentage of senators who are female, rounded.
   */
  public int getPercentFemaleHouse() {
    int percent = 0;
    Double numFemReps = 0.0;

    List<Legislator> reps = congress.getAllValues();
    List<Legislator> femReps = reps.stream().filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getGender().equals("F")).collect(Collectors.toList());

    numFemReps = 1.0 * femReps.size();
    percent = (int) ((numFemReps / 441) * 100); // 435 voting members + 6 non-voting members

    return percent;
  }

  /**
   * Determines the percentage of representatives that are male
   * 
   * @return a Double representing the percentage of senators who are male
   */
  public int getPercentMaleHouse() {
    int percent = 0;
    Double numMaleReps = 0.0;

    List<Legislator> reps = congress.getAllValues();
    List<Legislator> maleReps = reps.stream().filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getGender().equals("M")).collect(Collectors.toList());

    numMaleReps = 1.0 * maleReps.size();
    percent = (int) ((numMaleReps / 441) * 100);

    return percent;
  }

  /**
   * Return the percentage of senators who are Democrats as a whole number
   * 
   * @return the percentage of Democratic senators
   */
  public int getPercentDemsSenate() {

    return getNumDemsSenate();
  }

  /**
   * Return the percentage of senators who are Republicans as a whole number
   * 
   * @return the percentage of Republican senators
   */
  public int getPercentRepsSenate() {

    return getNumRepsSenate();
  }

  /**
   * Return the percentage of senators who are Independents as a whole number
   * 
   * @return the percentage of Independent senators
   */
  public int getPercentIndsSenate() {

    return getNumIndsSenate();
  }

  /**
   * Return the percentage of representatives who are Democrats as a whole number
   * 
   * @return the percentage of Democratic representatives
   */
  public int getPercentDemsHouse() {

    return (getNumDemsHouse() * 100) / 441;
  }

  /**
   * Return the percentage of representatives who are Republicans as a whole number
   * 
   * @return the percentage of Republican representatives
   */
  public int getPercentRepsHouse() {

    return ((getNumRepsHouse() * 100) / 441);
  }

  /**
   * Return the percentage of representatives who are Independents as a whole number
   * 
   * @return the percentage of Independent representatives
   */
  public int getPercentIndsHouse() {

    return ((getNumIndsHouse() * 100) / 441);
  }

  /**
   * Returns the number of Democrats in the Senate
   * 
   * @return
   */
  public int getNumDemsSenate() {
    int num = 0;

    List<Legislator> allCongress = congress.getAllValues();
    List<Legislator> numDemsSenate = allCongress.stream().filter(e -> e.getBody().equals("sen"))
        .filter(e -> e.getParty().equals("Democrat")).collect(Collectors.toList());

    num = numDemsSenate.size();

    return num;
  }

  /**
   * Returns the number of Republicans in the Senate
   * 
   * @return
   */
  public int getNumRepsSenate() {
    int num = 0;

    List<Legislator> allCongress = congress.getAllValues();
    List<Legislator> numRepsSenate = allCongress.stream().filter(e -> e.getBody().equals("sen"))
        .filter(e -> e.getParty().equals("Republican")).collect(Collectors.toList());

    num = numRepsSenate.size();

    return num;
  }

  /**
   * Returns the number of Independents in the Senate
   * 
   * @return the number of Independents in the Senate
   */
  public int getNumIndsSenate() {
    int num = 0;

    List<Legislator> allCongress = congress.getAllValues();
    List<Legislator> numIndsSenate = allCongress.stream().filter(e -> e.getBody().equals("sen"))
        .filter(e -> e.getParty().equals("Independent")).collect(Collectors.toList());

    num = numIndsSenate.size();

    return num;
  }

  /**
   * Returns the number of Democrats in the House
   * 
   * @return
   */
  public int getNumDemsHouse() {
    int num = 0;

    List<Legislator> allCongress = congress.getAllValues();
    List<Legislator> numDemsHouse = allCongress.stream().filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getParty().equals("Democrat")).collect(Collectors.toList());

    num = numDemsHouse.size();

    return num;
  }

  /**
   * Returns the number of Republicans in the House
   * 
   * @return
   */
  public int getNumRepsHouse() {
    int num = 0;

    List<Legislator> allCongress = congress.getAllValues();
    List<Legislator> numRepsHouse = allCongress.stream().filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getParty().equals("Republican")).collect(Collectors.toList());

    num = numRepsHouse.size();

    return num;
  }

  /**
   * Returns the number of Independents in the House
   * 
   * @return the number of Independents in the House
   */
  public int getNumIndsHouse() {
    int num = 0;

    List<Legislator> allCongress = congress.getAllValues();
    List<Legislator> numIndsHouse = allCongress.stream().filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getParty().equals("Independent")).collect(Collectors.toList());

    num = numIndsHouse.size();

    return num;
  }

  /**
   * Get a list of all of the legislators in Congress
   * 
   * @return a list of Legislator objects
   */
  public List<Legislator> getAllLegislators() {

    return congress.getAllValues();
  }
  
  /**
   * Finds a specific legislator
   * @param firstName the legislator's first name
   * @param lastName the legislator's last name
   * @return the Legislator if found, null otherwise.
   */
  public Legislator getLegislator(String firstName, String lastName) {
    int key = firstName.trim().toLowerCase().hashCode() + lastName.trim().toLowerCase().hashCode();
    
    Legislator legislator = null;
    try {
      legislator = congress.findValue(key);
    } catch (KeyNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return legislator;
  }

  /**
   * Returns a list containing all of the names of all of the legislators
   * 
   * @return a list of strings that are the full names of all legislators
   */
  public List<String> getAllNames() {

    List<Legislator> allLegislators = this.getAllLegislators();

    List<String> names = new ArrayList<String>();

    for (Legislator l : allLegislators) {
      if (l != null) {
        names.add(l.getFullName());
      }
    }

    names.sort(null);
    return names;
  }
}
