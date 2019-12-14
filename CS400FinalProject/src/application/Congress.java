package application;


import java.io.File;
import java.io.FileNotFoundException;
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
  private static BTree<Integer, Legislator> congress = new BTree<Integer, Legislator>();
  private static String filename = "legislators-current (1).csv"; // TODO pick the file

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
        congress.addKey(
            (legislator.getFirstName().hashCode() + legislator.getLastName().hashCode()),
            legislator);
      }


    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }


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
    List<Legislator> femReps = reps.stream()
        .filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getGender().equals("F"))
        .collect(Collectors.toList());
    
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
    List<Legislator> maleReps = reps.stream()
        .filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getGender().equals("M"))
        .collect(Collectors.toList());
    
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
    List<Legislator> numDemsSenate = allCongress.stream()
        .filter(e -> e.getBody().equals("sen"))
        .filter(e -> e.getParty().equals("Democrat"))
        .collect(Collectors.toList());
    
    num = numDemsSenate.size();
    
    return num;
  }
  
  /**
   * Returns the number of Republicans in the Senate
   * @return
   */
  public int getNumRepsSenate() {
    int num = 0;
    
    List<Legislator> allCongress = congress.getAllValues();
    List<Legislator> numRepsSenate = allCongress.stream()
        .filter(e -> e.getBody().equals("sen"))
        .filter(e -> e.getParty().equals("Republican"))
        .collect(Collectors.toList());
    
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
    List<Legislator> numIndsSenate = allCongress.stream()
        .filter(e -> e.getBody().equals("sen"))
        .filter(e -> e.getParty().equals("Independent"))
        .collect(Collectors.toList());
    
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
    List<Legislator> numDemsHouse = allCongress.stream()
        .filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getParty().equals("Democrat"))
        .collect(Collectors.toList());
    
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
    List<Legislator> numRepsHouse = allCongress.stream()
        .filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getParty().equals("Republican"))
        .collect(Collectors.toList());
    
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
    List<Legislator> numIndsHouse = allCongress.stream()
        .filter(e -> e.getBody().equals("rep"))
        .filter(e -> e.getParty().equals("Independent"))
        .collect(Collectors.toList());
    
    num = numIndsHouse.size();
    
    return num;
  }
  
  public List<Legislator> getAllLegislators(){
    
    return congress.getAllValues();
  }

  public static void main(String[] args) {
    try {
      parseCSV();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Congress congress1 = new Congress();
    System.out.println("Percent Female Senators: " + congress1.getPercentFemaleSenate());
    System.out.println("Percent Male Senators: " + congress1.getPercentMaleSenate());
    
    System.out.println("Percent Female Reps: " + congress1.getPercentFemaleHouse());
    System.out.println("Percent Male Reps: " + congress1.getPercentMaleHouse());
    
    System.out.println("Number of Democrats in the Senate: " + congress1.getNumDemsSenate());
    System.out.println("Percentage of Democrats in the Senate: " + congress1.getPercentDemsSenate());
    
    System.out.println("Number of Republicans in the Senate: " + congress1.getNumRepsSenate());
    System.out.println("Percentage of Republicans in the Senate: " + congress1.getPercentRepsSenate());
    
    System.out.println("Number of Independents in the Senate: " + congress1.getNumIndsSenate());
    System.out.println("Percentage of Independents in the Senate: " + congress1.getPercentIndsSenate());
    
    System.out.println("Number of Democrats in the House: " + congress1.getNumDemsHouse());
    System.out.println("Percentage of Democrats in the House: " + congress1.getPercentDemsHouse());
    
    System.out.println("Number of Republicans in the Hosue: " + congress1.getNumRepsHouse());
    System.out.println("Percentage of Republicans in the House: " + congress1.getPercentRepsHouse());
    
    System.out.println("Number of Independents in the House: " + congress1.getNumIndsHouse());
    System.out.println("Percentage of Independents in the House: " + congress1.getPercentIndsHouse());
    
    System.out.println(congress.getAllValues().size());
    congress.printSideways();


  }
}
