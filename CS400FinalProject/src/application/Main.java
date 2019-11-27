package application;

import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    try {
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

      // create a ComboBox for the list of states
      ComboBox<String> stateBox = new ComboBox<String>(states);
      Label stateLabel = new Label("State:");

      // create a ComboBox for party
      ComboBox<String> partyBox = new ComboBox<String>(party);
      Label partyLabel = new Label("Party:");

      // create a ComboBox for gender
      ComboBox<String> genderBox = new ComboBox<String>(gender);
      Label genderLabel = new Label("Gender:");

      // create a ComboBox for legislative body
      ComboBox<String> bodyBox = new ComboBox<String>(body);
      Label bodyLabel = new Label("Body:");

      // create a button for generating an average legislator
      Button averageLegislatorButton = new Button();
      averageLegislatorButton.setText("Generate Average Legislator NOT WORKING");

      // create a button for generating a random legislator
      Button randomLegislatorButton = new Button();
      randomLegislatorButton.setText("Generate Random Legislator NOT WORKING");

      // button for adding a legislator
      Button addLegislator = new Button("Add Legislator NOT WORKING");

      Button removeLegislator = new Button("Remove Legislator NOT WORKING");

      // create search button and tooltip
      Button searchButton = new Button("Search Congress");
      Tooltip searchTip = new Tooltip("Search Congress based on certain criteria");
      searchButton.setTooltip(searchTip);

      Button clearButton = new Button("Clear");

      Button backButton = new Button("Back");

      /**
       * Create layouts to house controls
       */

      // use a BorderPane layout (top, bottom, left, right, center) for search scene
      BorderPane root = new BorderPane();
      HBox topPane = new HBox(5);
      GridPane centerGrid = new GridPane();
      HBox bottomPane = new HBox(5);


      // add controls to the layouts for searchScene
      topPane.getChildren().addAll(bodyLabel, bodyBox, stateLabel, stateBox, partyLabel, partyBox,
          genderLabel, genderBox, clearButton, backButton);
      bottomPane.getChildren().addAll(addLegislator, removeLegislator);

      // add the inner layouts to the BorderPane for searchScene
      root.setTop(topPane);
      topPane.setAlignment(Pos.TOP_CENTER);


      // BorderPane.setMargin(topPane, new Insets(0, 0, 0, 50));
      root.setBottom(bottomPane);
      bottomPane.setAlignment(Pos.BASELINE_CENTER);
      root.setCenter(centerGrid);
      centerGrid.setAlignment(Pos.CENTER);


      // use a VBox for main scene
      VBox mainLayout = new VBox(10);
      mainLayout.setAlignment(Pos.CENTER);

      // add buttons to the main layout
      mainLayout.getChildren().addAll(searchButton, averageLegislatorButton,
          randomLegislatorButton);


      // create scenes
      Scene searchScene = new Scene(root, 700, 400);
      Scene mainScene = new Scene(mainLayout, 700, 400);

      // background for mainScene
      BackgroundImage mainBackImage =
          new BackgroundImage(new Image(this.getClass().getResourceAsStream("flagBackground.jpg")),
              null, null, null, null);
      Background mainBackground = new Background(mainBackImage);

      searchScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      mainLayout.setBackground(mainBackground);

      searchButton.setOnAction(e -> primaryStage.setScene(searchScene));
      backButton.setOnAction(e -> primaryStage.setScene(mainScene));


      // private EventHandler to pass to the clearButton's action
      EventHandler<ActionEvent> clearAction = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          // get the list of nodes from the pane
          List<Node> nodes = topPane.getChildren();
          
          // for each ComboBox in the pane, clear the selection
          for (Node node : nodes) {
            if (node instanceof ComboBox) {
              ((ComboBox<?>) node).getSelectionModel().clearSelection();
            }
          }
        }

      };
      
      
      clearButton.setOnAction(clearAction);

      // add the scene to the stage
      primaryStage.setScene(mainScene);
      primaryStage.setTitle("Representation Tracker");


      // update the taskbar icon
      primaryStage.getIcons()
          .add(new Image(this.getClass().getResourceAsStream("capitolLogo.jpg")));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  public static void main(String[] args) {
    launch(args);
  }
}
