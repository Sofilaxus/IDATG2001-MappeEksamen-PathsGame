package no.ntnu.gruppe1.view;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.gruppe1.controllers.SceneController;
import no.ntnu.gruppe1.controllers.StoryArchiveController;
import no.ntnu.gruppe1.model.Story;

/**
 * The Story Archive scene. This scene should show when you press "Play" in the Main Menu.
 * You can come back to this scene.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.03.16
 */
public class StoryArchiveScene extends BorderPane {

  //Fields
  private final SceneController sceneController;
  private ObservableList<Story> storyObservableList;
  private final StoryArchiveController storyArchiveController;
  private TableView<Story> storyListView;

  /**
   * The scene for StoryArchiveScene.
   *
   * @param main the stage the scene is set upon
   */
  public StoryArchiveScene(PathsGui main) {
    sceneController = new SceneController(main);
    storyArchiveController = new StoryArchiveController(this);
    setId("basic-background");

    Text welcome = new Text("The Story Archive");
    welcome.setId("title-text");
    Text question = new Text("Which story do you wish to play?");
    question.setId("sub-title-text");

    VBox vbox = new VBox(welcome, question, storyList(), buttons());
    vbox.setId("box-style");
    vbox.setSpacing(10);
    vbox.setMaxHeight(450);
    vbox.setMaxWidth(600);
    vbox.setAlignment(Pos.BOTTOM_LEFT);
    vbox.setPadding(new Insets(5, 5, 5, 5));

    setCenter(vbox);
  }

  /**
   * A table that shows a list of stories.
   *
   * @return the table with stories
   */
  private TableView<Story> storyList() {
    storyListView = new TableView<>();
    storyListView.setItems(getStoryObservableList());
    storyListView.setMaxHeight(500);

    TableColumn<Story, String> storyName = new TableColumn<>("Story Title");
    storyName.setPrefWidth(300);
    storyName.setSortable(false);
    storyName.setResizable(false);
    storyName.setCellValueFactory(new PropertyValueFactory<>("title"));
    storyName.setOnEditStart(e -> sceneController.goToLoadGame(e.getRowValue()));
    storyListView.getColumns().add(storyName);

    TableColumn<Story, Integer> deadLinks = new TableColumn<>("Broken Links");
    deadLinks.setMinWidth(250);
    deadLinks.setResizable(false);
    deadLinks.setSortable(false);
    deadLinks.setCellValueFactory(new PropertyValueFactory<>("brokenLinks"));
    storyListView.getColumns().add(deadLinks);

    storyListView.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        Story selectedStoryToPlay = storyListView.getSelectionModel().getSelectedItem();
        sceneController.goToLoadGame(selectedStoryToPlay);
      }
    });

    storyListView.setMinWidth(450);
    storyListView.setPadding(new Insets(5, 5, 5, 5));
    return storyListView;
  }

  /**
   * Gets an ObservableList holding the Story information.
   *
   * @return an ObservableList holding the Story information
   */
  private ObservableList<Story> getStoryObservableList() {

    storyObservableList = FXCollections.observableArrayList();
    storyArchiveController.getStoryArchive().forEachRemaining(storyObservableList::add);

    return storyObservableList;
  }

  /**
   * Updates the Story observable list with the current content in the storyList.
   */
  public void updateStoryList() {
    storyObservableList.clear();
    storyArchiveController.getStoryArchive().forEachRemaining(storyObservableList::add);
  }

  /**
   * An HBox with the buttons for importing a story and for going back to the main menu.
   *
   * @return the HBox with buttons
   */
  private HBox buttons() {
    Button backToMainMenu = new Button("Back to Main Menu");
    backToMainMenu.setOnAction(e -> sceneController.goBackToMainMenu());
    Button deleteButton = new Button("Delete Story");
    deleteButton.setOnAction(e -> {
      Story selectedItem = storyListView.getSelectionModel().getSelectedItem();
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmation Dialog");
      alert.setHeaderText("Delete story?");
      alert.setContentText("Are you sure you want to delete"
          + selectedItem.getTitle() + "? This cannot be undone.");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == ButtonType.OK) {
          storyArchiveController.deleteStory(selectedItem);
        }
      }
    });
    Button importStory = new Button("Import Story");
    importStory.setOnAction(e -> storyArchiveController.importStoryAndUpdateTable());
    HBox buttons = new HBox(backToMainMenu, importStory, deleteButton);
    buttons.setSpacing(20);
    return buttons;
  }

}
