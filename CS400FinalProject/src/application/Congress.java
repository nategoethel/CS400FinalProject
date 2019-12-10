package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents all of the members of Congress. Uses a B Tree as the backbone.
 * 
 * @author Nate
 *
 */
public class Congress {
  private static BTree<Integer, Legislator> congress = new BTree<Integer, Legislator>();
  private static String filename = "legislators-current.csv"; // TODO pick the file

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
        String state = data[3];
        String party = data[4];
        String body = data[2];

        // create Legislator object
        Legislator legislator = new Legislator(firstName, lastName, state, party, body);

        // insert into the tree
        congress.addKey(
            (legislator.getFirstName().hashCode() + legislator.getLastName().hashCode()),
            legislator);
      }


    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }


  }
  
  public static void main(String [] args) {
    try {
      parseCSV();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(congress.getAllValues().size());
    congress.printSideways();
  }
}
