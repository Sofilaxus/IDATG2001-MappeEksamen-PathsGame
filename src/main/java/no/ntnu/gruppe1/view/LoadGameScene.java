package no.ntnu.gruppe1.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.gruppe1.controllers.LoadGameController;
import no.ntnu.gruppe1.controllers.SceneController;
import no.ntnu.gruppe1.model.Story;
import no.ntnu.gruppe1.model.player.PlayerTypes;

/**
 * The scene before playing a game.
 * Contains options for creating a player, goals, and starting the game.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.05.05
 */
public class LoadGameScene extends BorderPane {

  //Fields
  private final LoadGameController loadGameController;
  private final SceneController sceneController;
  private final Story story;

  /**
   * Constructor for the scene.
   *
   * @param story the story that is to be played in the game.
   */
  public LoadGameScene(Story story) {
    this.story = story;
    PathsGui pathsgui = new PathsGui();
    sceneController = new SceneController(pathsgui);
    loadGameController =  new LoadGameController(pathsgui, this);
    setId("basic-background");

    HBox buttons = new HBox();
    Button back = new Button("Back to Story Archive");
    back.setOnAction(e -> sceneController.goToStoryArchive());
    Button start = new Button("Start Game");
    start.setOnAction(e -> loadGameController.startStory());
    buttons.getChildren().addAll(back, start);
    buttons.setSpacing(10);
    buttons.setAlignment(Pos.CENTER);

    Text title = new Text(story.getTitle());
    title.setId("title-text");

    HBox playerAndGoals = new HBox(createPlayer(), createGoals());

    VBox box = new VBox(title, playerAndGoals, buttons);
    box.setAlignment(Pos.CENTER);
    box.setSpacing(10);
    box.setPadding(new Insets(10, 10, 10, 10));

    setCenter(box);
  }

  /**
   * Get the story that is to be played.
   *
   * @return the story
   */
  public Story getStory() {
    return story;
  }

  /**
   * A box where a player can input their name and choose the difficulty in the game.
   *
   * @return the VBox containing player and difficulty box
   */
  private VBox createPlayer() {
    HBox playerNameBox = new HBox();
    playerNameBox.setAlignment(Pos.CENTER);
    playerNameBox.setSpacing(10);

    Label playerNameTitle = new Label("Player name:");
    playerNameTitle.setId("normal-text");
    TextField playerNameField = new TextField();
    playerNameField.setId("playerNameField");
    playerNameField.setPromptText("Write your player name");
    playerNameBox.getChildren().addAll(playerNameTitle, playerNameField);

    VBox player = new VBox();
    player.getChildren().addAll(playerNameBox, difficultyBox());
    player.setId("box-style");
    player.setSpacing(10);
    player.setPadding(new Insets(5, 5, 5, 5));
    return player;
  }

  /**
   * A box where a player can input goals for the game.
   *
   * @return VBox with the goals
   */
  private VBox createGoals() {
    Text goalsTitle = new Text("Goals for the game:" + "\n"
        + "Leave empty if you do not want any goals.");
    goalsTitle.setId("normal-text");

    VBox scrollingBox = new VBox();
    scrollingBox.setSpacing(20);

    VBox goalsList = new VBox();
    goalsList.setSpacing(10);
    goalsList.setId("goalsList");

    Button newGoalButton = new Button("Add goal");
    newGoalButton.setAlignment(Pos.CENTER);
    newGoalButton.setOnAction(loadGameController::addGoalField);

    scrollingBox.getChildren().addAll(newGoalButton, goalsList);
    scrollingBox.setAlignment(Pos.CENTER);

    ScrollPane scrollPane = new ScrollPane(scrollingBox);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setMinHeight(200);
    scrollPane.setMaxHeight(200);
    scrollPane.setId("box-style");

    VBox goalsBox = new VBox();
    goalsBox.getChildren().addAll(goalsTitle, scrollPane);
    goalsBox.setId("box-style");
    goalsBox.setSpacing(10);
    goalsBox.setPadding(new Insets(5, 5, 5, 5));

    return goalsBox;
  }

  /**
   * A VBox containing a choice box for difficulty.
   *
   * @return a VBox containing a choice box for game difficulty
   */
  private VBox difficultyBox() {
    Text difficultyTitle = new Text("Choose difficulty:");
    difficultyTitle.setId("normal-text");

    HBox difficultyHbox = new HBox();
    difficultyHbox.setAlignment(Pos.CENTER);
    difficultyHbox.setSpacing(10);

    ChoiceBox<ChoiceBoxes<PlayerTypes>> difficultyChooser = new ChoiceBox<>();
    difficultyChooser.getItems().addAll(List.of(
        new ChoiceBoxes<>("Easy", PlayerTypes.EASY),
            new ChoiceBoxes<>("Medium", PlayerTypes.MEDIUM),
            new ChoiceBoxes<>("Hard", PlayerTypes.HARD)));
    difficultyChooser.getSelectionModel().selectFirst();
    difficultyHbox.getChildren().addAll(difficultyChooser);

    Text difficultyInfo = new Text("The difficulty will affect your player starting statistics. \n"
        + "\nEasy: 25 health, 10 gold. \n"
        + "Medium: 15 health, 5 gold. \n"
        + "Hard: 5 health, 0 gold. \n");
    difficultyTitle.setId("text");

    VBox difficultyBox = new VBox();
    difficultyBox.getChildren().addAll(difficultyTitle, difficultyHbox, difficultyInfo);
    difficultyChooser.setId("difficultyChoiceBox");
    difficultyBox.setAlignment(Pos.CENTER);
    return difficultyBox;
  }
}
