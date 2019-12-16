package application;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
// Online Sources: https://docs.oracle.com/javafx/2/charts/pie-chart.htm#CIHFDADD -
// helped with pie charts
// https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx -
// helped with popups
// https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx
// https://stackoverflow.com/questions/59339538/show-a-dialog-window-when-a-user-clicks-the-x-button
// https://examples.javacodegeeks.com/desktop-java/javafx/dialog-javafx/javafx-dialog-example/
// https://docs.oracle.com/javafx/2/charts/pie-chart.htm
// https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
// https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
// https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
// https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/ - help with filtering
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


public class Main extends Application {

  String fileName = null;

  Congress congress = new Congress();


  private Desktop desktop = Desktop.getDesktop();

  @Override
  public void start(Stage primaryStage) {
    try {

      /**
       * Create controls
       */

      // Data for percentage pie chart for senate gender
      ObservableList<PieChart.Data> senateGenderData = FXCollections.observableArrayList(
          new PieChart.Data("Female", (1.0 * congress.getNumFemSenate())),
          new PieChart.Data("Male", (1.0 * congress.getNumMaleSenate())));

      // Pie chart object for senate gender
      PieChart senateGender = new PieChart(senateGenderData);
      senateGender.setTitle("Senate Split by Gender");

      // Data for percentage pie chart for senate parties
      ObservableList<PieChart.Data> senatePartyData = FXCollections.observableArrayList(
          new PieChart.Data("Democrat", congress.getNumDemsSenate()),
          new PieChart.Data("Republican", congress.getNumRepsSenate()),
          new PieChart.Data("Independent", congress.getNumIndsSenate()));

      // Pie chart object for senate parties
      PieChart senateParties = new PieChart(senatePartyData);
      senateParties.setTitle("Senate Split by Party");

      // Data for percentage pie chart for house gender
      ObservableList<PieChart.Data> houseGenderData = FXCollections.observableArrayList(
          new PieChart.Data("Female", (1.0 * congress.getNumFemHouse())),
          new PieChart.Data("Male", (1.0 * congress.getNumMaleHouse())));

      // Pie chart object for house gender
      PieChart houseGender = new PieChart(houseGenderData);
      houseGender.setTitle("House Split by Gender");

      // Data for percentage pie chart for house parties
      ObservableList<PieChart.Data> housePartyData = FXCollections.observableArrayList(
          new PieChart.Data("Democrat", (1.0 * congress.getNumDemsHouse())),
          new PieChart.Data("Republican", (1.0 * congress.getNumRepsHouse())),
          new PieChart.Data("Independent", (1.0 * congress.getNumIndsHouse())));

      // Pie chart object for house party data
      PieChart houseParties = new PieChart(housePartyData);
      houseParties.setTitle("House Split by Party");
      // houseParties.setMaxSize(200, 200);

      // create a button for opening the Senate stats scene
      Button seeSenateStatsButton = new Button();
      seeSenateStatsButton.setText("Senate Stats");

      // create a button for opening the House stats scene
      Button seeHouseStatsButton = new Button();
      seeHouseStatsButton.setText("House Stats");

      // create a TextField for searching
      TextField search = new TextField();
      Label searchLabel = new Label("Search table:");

      // button for adding a legislator
      Button addLegislator = new Button("Add Legislator");

      // button for removing a legislator
      Button removeLegislator = new Button("Remove Legislator");

      // create search button and tooltip
      Button searchButton = new Button("Search Current Congress");
      Tooltip searchTip = new Tooltip("Search Congress based on certain criteria");
      searchButton.setTooltip(searchTip);

      // button to find a new file
      Button openFileButton = new Button("Open a Different .CSV File");

      // create back button
      Button backButton = new Button("Back");

      // create save button
      Button saveButton = new Button();
      Tooltip saveTip = new Tooltip("Save to CSV file");
      saveButton.setTooltip(saveTip);
      Image saveImage = new Image(this.getClass().getResourceAsStream("floppyDiscIcon.png"));
      ImageView saveImageView = new ImageView(saveImage);
      saveImageView.setFitHeight(20);
      saveImageView.setFitWidth(20);
      saveButton.setGraphic(saveImageView);

      // create back button for metric scenes
      Button houseMetricBackButton = new Button("Back");
      Button senateMetricBackButton = new Button("Back");

      /**
       * Create layouts to house controls
       */

      // use a BorderPane layout (top, bottom, left, right, center) for search scene
      BorderPane root = new BorderPane();
      VBox leftSidePane = new VBox(5);
      VBox rightSidePane = new VBox(5);
      TableView<Legislator> centerList = new TableView<Legislator>();
      HBox bottomPane = new HBox(5);

      // use a BorderPane layout for the house metric scene, and two VBoxes for left and right
      BorderPane houseMetricLayout = new BorderPane();
      VBox leftHouseMetricPane = new VBox(houseParties);
      VBox rightHouseMetricPane = new VBox(houseGender);
      HBox houseMetricBottomPane = new HBox();
      houseMetricBottomPane.getChildren().add(houseMetricBackButton);

      // same for Senate layout
      BorderPane senateMetricLayout = new BorderPane();
      VBox leftSenateMetricPane = new VBox(senateParties);
      VBox rightSenateMetricPane = new VBox(senateGender);
      HBox senateMetricBottomPane = new HBox();
      senateMetricBottomPane.getChildren().add(senateMetricBackButton);

      // add to layout
      houseMetricLayout.setLeft(leftHouseMetricPane);
      houseMetricLayout.setRight(rightHouseMetricPane);
      houseMetricLayout.setBottom(houseMetricBottomPane);
      houseMetricBottomPane.setAlignment(Pos.BASELINE_LEFT);
      houseMetricBottomPane.setPadding(new Insets(5));

      senateMetricLayout.setLeft(leftSenateMetricPane);
      senateMetricLayout.setRight(rightSenateMetricPane);
      senateMetricLayout.setBottom(senateMetricBottomPane);
      senateMetricBottomPane.setAlignment(Pos.BASELINE_LEFT);
      senateMetricBottomPane.setPadding(new Insets(5));

      // add initial list of items to centerList
      List<Legislator> legislators = congress.getAllLegislators();
      // legislators.sort(null);
      ObservableList<Legislator> data = FXCollections.observableArrayList(legislators);

      // create columns for centerList
      TableColumn<Legislator, String> firstNameColumn = new TableColumn<>("First Name");
      firstNameColumn
          .setCellValueFactory(new PropertyValueFactory<Legislator, String>("FirstName"));

      TableColumn<Legislator, String> lastNameColumn = new TableColumn<>("Last Name");
      lastNameColumn.setCellValueFactory(new PropertyValueFactory<Legislator, String>("LastName"));

      TableColumn<Legislator, String> genderColumn = new TableColumn<>("Gender");
      genderColumn.setCellValueFactory(new PropertyValueFactory<Legislator, String>("Gender"));

      TableColumn<Legislator, String> partyColumn = new TableColumn<>("Party");
      partyColumn.setCellValueFactory(new PropertyValueFactory<Legislator, String>("Party"));

      TableColumn<Legislator, String> stateColumn = new TableColumn<>("State");
      stateColumn.setCellValueFactory(new PropertyValueFactory<Legislator, String>("State"));

      TableColumn<Legislator, String> bodyColumn = new TableColumn<>("Body");
      bodyColumn.setCellValueFactory(new PropertyValueFactory<Legislator, String>("Body"));

      // populate the table

      // wrap data in a FilteredList
      FilteredList<Legislator> filteredData = new FilteredList<Legislator>(data, p -> true);

      // set predicate for the filtered table
      search.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(legislator -> {
          // if filter text is empty, display all
          if (newValue == null || newValue.isEmpty()) {
            return true;
          }

          // compare last and first name
          String lowercase = newValue.toLowerCase();

          if (legislator.getFirstName().toLowerCase().contains(lowercase)) {
            return true; // match
          } else if (legislator.getLastName().toLowerCase().contains(lowercase)) {
            return true; // match
          } else {
            return false; // no matches
          }
        });
      });

      // wrap filtered data in a SortedList
      SortedList<Legislator> sortedData = new SortedList<Legislator>(filteredData);

      // bind comparators
      sortedData.comparatorProperty().bind(centerList.comparatorProperty());

      centerList.setItems(sortedData);
      centerList.getColumns().add(firstNameColumn);
      centerList.getColumns().add(lastNameColumn);
      centerList.getColumns().add(genderColumn);
      centerList.getColumns().add(partyColumn);
      centerList.getColumns().add(stateColumn);
      centerList.getColumns().add(bodyColumn);

      // add controls to the layouts for searchScene
      leftSidePane.getChildren().addAll(searchLabel, search, seeSenateStatsButton,
          seeHouseStatsButton, openFileButton);
      bottomPane.getChildren().addAll(addLegislator, removeLegislator, saveButton);

      // add the inner layouts to the BorderPane for searchScene
      root.setLeft(leftSidePane);
      root.setCenter(centerList);
      leftSidePane.setPadding(new Insets(5));
      rightSidePane.setPadding(new Insets(5));

      // BorderPane.setMargin(topPane, new Insets(0, 0, 0, 50));
      root.setBottom(bottomPane);
      bottomPane.setAlignment(Pos.BASELINE_CENTER);
      bottomPane.setPadding(new Insets(5));

      // create scenes
      Scene searchScene = new Scene(root, 1000, 475);
      Scene houseMetricScene = new Scene(houseMetricLayout, 1000, 475);
      Scene senateMetricScene = new Scene(senateMetricLayout, 1000, 475);

      searchScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      // mainLayout.setBackground(mainBackground);

      // set actions for buttons
      searchButton.setOnAction(e -> primaryStage.setScene(searchScene));

      // House metric button
      seeHouseStatsButton.setOnAction(e -> primaryStage.setScene(houseMetricScene));

      // Senate metric button
      seeSenateStatsButton.setOnAction(e -> primaryStage.setScene(senateMetricScene));

      // metric back button
      houseMetricBackButton.setOnAction(e -> primaryStage.setScene(searchScene));
      senateMetricBackButton.setOnAction(e -> primaryStage.setScene(searchScene));


      openFileButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent e) {
          FileChooser fileChooser = new FileChooser();
          fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));

          File file = fileChooser.showOpenDialog(primaryStage);
          if (file != null) {
            congress = new Congress(file.getName()); // TODO replace the current TableView
            primaryStage.setScene(searchScene);
          }
        }
      });

      EventHandler<ActionEvent> saveAction = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Save File");
          File file = fileChooser.showSaveDialog(primaryStage);
          BufferedWriter writer = null;
          
          if (file == null) {
            return;
          }
          
          try {
            writer = new BufferedWriter(new FileWriter(file, true));
            if (file != null) {
              String header = "last_name" + "," + "first_name" + "," + "gender" + "," + "type" + ","
                  + "state" + "," + "party";
              writer.write(header);
              writer.newLine();

              for (Legislator l : data) {
                String row = l.getLastName() + "," + l.getFirstName() + "," + l.getGender() + ","
                    + l.getBody() + "," + l.getState() + "," + l.getParty();
                writer.write(row);
                writer.newLine();
              }
            }
          } catch (IOException e) {
            Alert saveFailAlert = new Alert(AlertType.ERROR);
            saveFailAlert.setTitle("Error!");
            saveFailAlert.setContentText("File could not be saved!");
            saveFailAlert.show();
          } finally {
            try {
              if (writer != null) {
              writer.flush();
              writer.close();
              Alert saveConfirmationAlert = new Alert(AlertType.CONFIRMATION);
              saveConfirmationAlert.setTitle("Success!");
              saveConfirmationAlert.setContentText("File saved successfully!");
              saveConfirmationAlert.show();
              }
            } catch (IOException e) {
              Alert saveFailAlert = new Alert(AlertType.ERROR);
              saveFailAlert.setTitle("Error!");
              saveFailAlert.setContentText("File could not be saved!");
              saveFailAlert.show();
            }
          }

        }

      };

      // EventHandler to pass to removeLegislator's action
      EventHandler<ActionEvent> removeAction = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          Stage removeStage = new Stage();

          Alert removeConfirmationAlert = new Alert(AlertType.CONFIRMATION);
          removeConfirmationAlert.setTitle("Success!");
          removeConfirmationAlert.setContentText("The legislator was successfully removed.");

          Alert removeErrorAlert = new Alert(AlertType.ERROR);
          removeErrorAlert.setTitle("Error!");
          removeErrorAlert.setContentText("The legislator could not be removed.");

          // override the close request event to close this pop up and the alert
          removeConfirmationAlert.setOnCloseRequest(new EventHandler<DialogEvent>() {

            @Override
            public void handle(DialogEvent event) {
              removeConfirmationAlert.close();
              removeStage.close();
            }

          });

          // make the Stage modal so the user can't click elsewhere
          removeStage.initModality(Modality.APPLICATION_MODAL);
          removeStage.initOwner(primaryStage);

          Button removeButton = new Button("Remove");
          Button cancelButton = new Button("Cancel");
          TextField firstName = new TextField();
          Label firstNameLabel = new Label("First Name:");
          TextField lastName = new TextField();
          Label lastNameLabel = new Label("Last Name:");

          FlowPane centerBox = new FlowPane(Orientation.VERTICAL);
          centerBox.setHgap(10);


          centerBox.getChildren().addAll(firstNameLabel, firstName, lastNameLabel, lastName);
          centerBox.setAlignment(Pos.CENTER);

          HBox buttonPane = new HBox(5);
          buttonPane.getChildren().addAll(removeButton, cancelButton);
          buttonPane.setAlignment(Pos.BASELINE_CENTER);
          buttonPane.setPadding(new Insets(5));

          BorderPane removeBox = new BorderPane();
          removeBox.setCenter(centerBox);
          removeBox.setBottom(buttonPane);

          // create scene to add layout to
          Scene removeScene = new Scene(removeBox, 300, 400);

          removeStage.setScene(removeScene);

          removeStage.show();

          cancelButton.setOnAction(e -> removeStage.close());
          removeButton.setOnAction(e -> {

            boolean wasRemoved = false;
            Legislator legislatorToRemove = null;

            if (firstName.getText() == null || firstName.getText().isEmpty()) {
              // throw alert
              Alert blankFName = new Alert(AlertType.ERROR);
              blankFName.initModality(Modality.APPLICATION_MODAL);
              blankFName.setTitle("Error!");
              blankFName.setContentText("First Name is empty!");
              blankFName.show();
              return;
            } else if (lastName.getText() == null || lastName.getText().isEmpty()) {
              Alert blankLName = new Alert(AlertType.ERROR);
              blankLName.initModality(Modality.APPLICATION_MODAL);
              blankLName.setTitle("Error!");
              blankLName.setContentText("Last Name is empty!");
              blankLName.show();
              return;
            } else {

              for (Legislator l : data) {
                if (l.getFirstName().toLowerCase().equals(firstName.getText().toLowerCase())
                    && l.getLastName().toLowerCase().equals(lastName.getText().toLowerCase())) {
                  legislatorToRemove = l;
                  wasRemoved = true;
                  break;
                }
              }
            }
            // if the new object is added successfully, show confirmation
            if (wasRemoved) {

              // remove legislator from data
              data.remove(legislatorToRemove);
              // show confirmation message
              removeConfirmationAlert.show();
            } else {

              // otherwise, show an error
              removeErrorAlert.show();
            }
          });
        }
      };

      // EventHandler to pass addLegislator's action
      EventHandler<ActionEvent> addAction = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          // create a new Stage
          Stage addStage = new Stage();

          Alert addErrorAlert = new Alert(AlertType.ERROR);
          addErrorAlert.setTitle("Error!");
          addErrorAlert.setContentText("The new legislator could not be added.");

          Alert addConfirmationAlert = new Alert(AlertType.CONFIRMATION);
          addConfirmationAlert.setTitle("Success!");
          addConfirmationAlert.setContentText("The new legislator was sucessfully added.");

          // override the close request event to close this pop up and the alert
          addConfirmationAlert.setOnCloseRequest(new EventHandler<DialogEvent>() {

            @Override
            public void handle(DialogEvent arg0) {
              addConfirmationAlert.close();
              addStage.close();

            }

          });

          // make the Stage modal so the user can't click elsewhere
          addStage.initModality(Modality.APPLICATION_MODAL);
          addStage.initOwner(primaryStage);
          // create controls for the form
          Button addButton = new Button("Add");
          Button cancelButton = new Button("Cancel");
          TextField firstName = new TextField();
          Label firstNameLabel = new Label("First Name:");
          TextField lastName = new TextField();
          Label lastNameLabel = new Label("Last Name:");

          // list of states for the State ComboBox
          ObservableList<String> states = FXCollections.observableArrayList("", "AL", "AK", "AZ",
              "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY",
              "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "NE", "NV", "NH", "NJ", "NM", "NY",
              "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA",
              "WA", "WV", "WI", "WY");

          // two options for gender (male or female)
          ObservableList<String> genderList = FXCollections.observableArrayList("", "M", "F");

          // options for legislative body
          ObservableList<String> bodyList =
              FXCollections.observableArrayList("", "House", "Senate");

          // three options for party
          ObservableList<String> partyList =
              FXCollections.observableArrayList("", "Democrat", "Republican", "Independent");
          ComboBox<String> genderBox = new ComboBox<String>(genderList);
          Label genderLabel = new Label("Gender:");
          ComboBox<String> partyBox = new ComboBox<String>(partyList);
          Label partyLabel = new Label("Party:");
          ComboBox<String> stateBox = new ComboBox<String>(states);
          Label stateLabel = new Label("State:");

          ComboBox<String> bodyBox = new ComboBox<String>(bodyList);
          Label bodyLabel = new Label("Body:");

          FlowPane centerBox = new FlowPane(Orientation.VERTICAL);
          centerBox.setHgap(10);
          centerBox.getChildren().addAll(bodyLabel, bodyBox, firstNameLabel, firstName,
              lastNameLabel, lastName, genderLabel, genderBox, partyLabel, partyBox, stateLabel,
              stateBox);
          centerBox.setAlignment(Pos.CENTER);

          HBox buttonPane = new HBox(5);
          buttonPane.getChildren().addAll(addButton, cancelButton);
          buttonPane.setAlignment(Pos.BASELINE_CENTER);
          buttonPane.setPadding(new Insets(5));

          BorderPane addBox = new BorderPane();
          addBox.setCenter(centerBox);
          addBox.setBottom(buttonPane);

          // create scene to add layout to
          Scene addScene = new Scene(addBox, 300, 400);

          addStage.setScene(addScene);

          addStage.show();

          cancelButton.setOnAction(e -> addStage.close());
          addButton.setOnAction(e -> {

            boolean wasAdded = false;
            Legislator legislator = new Legislator(firstName.getText(), lastName.getText(),
                genderBox.getValue(), stateBox.getValue(), partyBox.getValue(), bodyBox.getValue());

            if (firstName.getText() == null || firstName.getText().isEmpty()) {
              // throw alert
              Alert blankFName = new Alert(AlertType.ERROR);
              blankFName.initModality(Modality.APPLICATION_MODAL);
              blankFName.setTitle("Error!");
              blankFName.setContentText("First Name is empty!");
              blankFName.show();
              return;
            } else if (lastName.getText() == null || lastName.getText().isEmpty()) {
              Alert blankLName = new Alert(AlertType.ERROR);
              blankLName.initModality(Modality.APPLICATION_MODAL);
              blankLName.setTitle("Error!");
              blankLName.setContentText("Last Name is empty!");
              blankLName.show();
              return;
            } else if (genderBox.getValue() == null || genderBox.getValue().isEmpty()) {
              Alert blankGender = new Alert(AlertType.ERROR);
              blankGender.initModality(Modality.APPLICATION_MODAL);
              blankGender.setTitle("Error!");
              blankGender.setContentText("Gender is empty!");
              blankGender.show();
              return;
            } else if (stateBox.getValue() == null || stateBox.getValue().isEmpty()) {
              Alert blankState = new Alert(AlertType.ERROR);
              blankState.initModality(Modality.APPLICATION_MODAL);
              blankState.setTitle("Error!");
              blankState.setContentText("Gender is empty!");
              blankState.show();
              return;
            } else if (partyBox.getValue() == null || partyBox.getValue().isEmpty()) {
              Alert blankParty = new Alert(AlertType.ERROR);
              blankParty.initModality(Modality.APPLICATION_MODAL);
              blankParty.setTitle("Error!");
              blankParty.setContentText("Gender is empty!");
              blankParty.show();
              return;
            } else if (bodyBox.getValue() == null || partyBox.getValue().isEmpty()) {
              Alert blankBody = new Alert(AlertType.ERROR);
              blankBody.initModality(Modality.APPLICATION_MODAL);
              blankBody.setTitle("Error!");
              blankBody.setContentText("Gender is empty!");
              blankBody.show();
              return;
            } else {
              wasAdded = congress.addLegislator(legislator);
            }

            // if the new object is added successfully, show confirmation
            if (wasAdded) {

              // show confirmation message
              addConfirmationAlert.show();

              // add a legislator to data
              data.add(legislator);

            } else {

              // otherwise, show an error
              addErrorAlert.show();
            }
          });
        }
      };


      addLegislator.setOnAction(addAction);
      removeLegislator.setOnAction(removeAction);
      saveButton.setOnAction(saveAction);

      // add the scene to the stage
      primaryStage.setScene(searchScene);
      primaryStage.setTitle("Representation Tracker");

      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

        @Override
        public void handle(WindowEvent event) {

          Alert saveOnClose = new Alert(AlertType.CONFIRMATION);
          saveOnClose.setTitle("Warning!");
          saveOnClose
              .setContentText("Are you sure you want to close without saving? Hit Cancel to save.");
          // saveOnClose.getButtonTypes().add(e)
          Optional<ButtonType> result = saveOnClose.showAndWait();

          if (!result.isPresent()) {

          } else if (result.get() == ButtonType.OK) {
            Platform.exit();
          } else if (result.get() == ButtonType.CANCEL) {
            saveButton.fire();
          }


        }

      });

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

