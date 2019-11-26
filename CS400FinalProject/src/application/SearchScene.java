package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SearchScene extends Scene {

  public SearchScene(Parent root) {
    super(root);
    // TODO Auto-generated constructor stub

    /**
     * Create controls
     */

    // list of states for the State ComboBox
    ObservableList<String> states = FXCollections.observableArrayList("", "Alabama", "Alaska",
        "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida",
        "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
        "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
        "Missouri", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
        "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
        "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
        "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming");

    // two options for gender (male or female)
    ObservableList<String> gender = FXCollections.observableArrayList("", "Male", "Female");

    // options for legislative body
    ObservableList<String> body = FXCollections.observableArrayList("", "House", "Senate");

    // three options for party
    ObservableList<String> party =
        FXCollections.observableArrayList("", "Democrat", "Republican", "Independent");

    // options for tenure
    ObservableList<String> tenure = FXCollections.observableArrayList("", "0-2 years", "2-4 years",
        "4-6 years", "6-10 years", "10+ years");

    // options for next election cycle
    ObservableList<String> nextElections =
        FXCollections.observableArrayList("", "2020", "2022", "2024");

    // create a ComboBox for the list of states
    ComboBox<String> stateBox = new ComboBox<String>(states);
    stateBox.setPromptText("State");
    // create a ComboBox for party
    ComboBox<String> partyBox = new ComboBox<String>(party);
    partyBox.setPromptText("Party");
    // create a ComboBox for tenure
    ComboBox<String> tenureBox = new ComboBox<String>(tenure);
    tenureBox.setPromptText("Tenure");
    // create a ComboBox for gender
    ComboBox<String> genderBox = new ComboBox<String>(gender);
    genderBox.setPromptText("Gender");
    // create a ComboBox for legislative body
    ComboBox<String> bodyBox = new ComboBox<String>(body);
    bodyBox.setPromptText("Body");
    // create a ComboBox for the next election cycles
    ComboBox<String> electionBox = new ComboBox<String>(nextElections);
    electionBox.setPromptText("Next Election");
    // create a button for generating an average legislator
    Button averageLegislatorButton = new Button();
    averageLegislatorButton.setText("Generate Average Legislator");

    // create a button for generating a random legislator
    Button randomLegislatorButton = new Button();
    randomLegislatorButton.setText("Generate Random Legislator");

    /**
     * Create layouts to house controls
     */

    // use a BorderPane layout (top, bottom, left, right, center)
    BorderPane mainLayout = new BorderPane();
    HBox topPane = new HBox(5);
    GridPane centerGrid = new GridPane();
    HBox bottomPane = new HBox(5);



    // add controls to the layouts
    topPane.getChildren().addAll(bodyBox, stateBox, partyBox, tenureBox, genderBox, electionBox);
    bottomPane.getChildren().addAll(averageLegislatorButton, randomLegislatorButton);

    // add the inner layouts to the BorderPane
    mainLayout.setTop(topPane);
    topPane.setAlignment(Pos.TOP_CENTER);
    // BorderPane.setMargin(topPane, new Insets(0, 0, 0, 50));
    mainLayout.setBottom(bottomPane);
    bottomPane.setAlignment(Pos.BASELINE_CENTER);
    mainLayout.setCenter(centerGrid);
    centerGrid.setAlignment(Pos.CENTER);
  }

}
