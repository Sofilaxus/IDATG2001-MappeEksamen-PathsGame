package no.ntnu.gruppe1.view;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import no.ntnu.gruppe1.controllers.PlayGameController;
import no.ntnu.gruppe1.controllers.SceneController;
import no.ntnu.gruppe1.model.Game;
import no.ntnu.gruppe1.model.Passage;
import no.ntnu.gruppe1.utility.LinkClicked;

/**
 * The scene where a story is played on. The game.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.05.21
 */
public class PlayGameScene extends BorderPane {

  //Fields
  private final SceneController sceneController;
  private final PlayGameController gameController;
  private Passage currentPassage;
  private Label playerHealth;
  private Label playerGold;
  private Label playerScore;
  private ObservableList<String> playerInventory;
  private ObservableList<Text> playerGoals;
  private final Game game;

  /**
   * Constructor for the scene of a game.
   *
   * @param game the game being displayed
   */
  public PlayGameScene(Game game) {
    this.game = game;
    PathsGui pathsgui = new PathsGui();
    sceneController = new SceneController(pathsgui);
    gameController = new PlayGameController(this, game);
    currentPassage = gameController.getCurrentPassage();

    setCenter(playerSide());
    setLeft(passageBox());
    setId("basic-background");
  }

  /**
   * A VBox with the display for the player side of the scene.
   *
   * @return the VBox with player info, goals, and button.
   */
  public VBox playerSide() {
    Button backToMainMenu = new Button("Back to Story Archive");
    backToMainMenu.setOnAction(e -> sceneController.goBackToStoryArchive());

    Text currentlyPlaying = new Text("Currently playing:");
    Text storyTitle = new Text(game.getStory().getTitle());
    storyTitle.setId("normal-text");

    VBox scrollingBox = new VBox();
    scrollingBox.getChildren().addAll(currentlyPlaying, storyTitle, playerInfoBox(),
        goalsForGame(), backToMainMenu);
    scrollingBox.setAlignment(Pos.CENTER);
    scrollingBox.setSpacing(5);

    ScrollPane scrollPane = new ScrollPane(scrollingBox);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setMinHeight(200);
    scrollPane.setMaxHeight(1000);
    scrollPane.setId("box-style");

    VBox stuff = new VBox(scrollPane);
    stuff.setSpacing(5);
    stuff.setPadding(new Insets(5, 5, 5, 5));
    return stuff;
  }

  /**
   * Method for creating a box with player goal.
   *
   * @return goalBox with player goals
   */
  public VBox goalsForGame() {

    VBox goalBox = new VBox();

    Text title = new Text("Goals:");
    title.setId("goal-list-title");
    goalBox.getChildren().add(title);
    ListView<Text> goals = new ListView(getPlayerGoals());
    goalBox.getChildren().add(goals);
    goalBox.setId("box-style");
    goalBox.setPadding(new Insets(5, 5, 5, 5));
    goalBox.setSpacing(10);
    goalBox.setMaxHeight(170);
    goalBox.setPrefWidth(150);

    return goalBox;
  }

  /**
   * Returns an ObservableList holding the status.
   *
   * @return an ObservableList holding the statuses as string.
   */
  public ObservableList<Text> getPlayerGoals() {
    playerGoals = FXCollections.observableArrayList();
    gameController.getGoals().forEachRemaining(playerGoals::add);
    return playerGoals;
  }

  /**
   * Method for creating player Info box with player stats and inventory.
   *
   * @return playerInfo vbox.
   */
  public VBox playerInfoBox() {
    VBox playerInfo = new VBox();
    playerInfo.getChildren().addAll(playerInfo(), playerInventory());
    playerInfo.setSpacing(15);
    playerInfo.setPrefWidth(150);
    return playerInfo;
  }

  /**
   * A GridPane with player statistics.
   *
   * @return GridPane with the player info
   */
  public GridPane playerInfo() {
    getChildren().clear();
    GridPane playerInfo = new GridPane();

    playerInfo.add(new Label("Name:"), 0, 0);

    ImageView health = new ImageView("images/health.PNG");
    health.setFitHeight(30);
    health.setFitWidth(30);
    health.setPreserveRatio(true);
    playerInfo.add(health, 0, 1);

    ImageView gold = new ImageView("images/gold.PNG");
    gold.setFitHeight(30);
    gold.setFitWidth(30);
    gold.setPreserveRatio(true);
    playerInfo.add(gold, 0, 2);

    playerInfo.add(new Label("Score:"), 0, 3);

    playerInfo.add(new Label(gameController.getPlayer().getName()), 1, 0);

    playerHealth = new Label(gameController.getPlayerHealth());
    playerInfo.add(playerHealth, 1, 1);
    playerGold = new Label(gameController.getPlayerGold());
    playerInfo.add(playerGold, 1, 2);
    playerScore = new Label(gameController.getPlayerScore());
    playerInfo.add(playerScore, 1, 3);

    playerInfo.setHgap(10);
    playerInfo.setVgap(10);
    playerInfo.setId("box-style");
    playerInfo.setPadding(new Insets(5, 5, 5, 5));
    return playerInfo;
  }

  /**
   * Returns an ObservableList holding the inventory of the player.
   *
   * @return an ObservableList holding the inventory as string
   */
  public ObservableList<String> getPlayerInventory() {

    playerInventory = FXCollections.observableArrayList();
    gameController.getInventory().forEachRemaining(playerInventory::add);

    return playerInventory;
  }

  /**
   * Creates a VBox containing the player's inventory.
   *
   * @return the VBox with the player's inventory
   */
  private VBox playerInventory() {
    ListView<String> items = new ListView(getPlayerInventory());
    items.setId("normal-text");
    Label inventoryLabel = new Label("Inventory:");
    VBox inventory = new VBox(inventoryLabel, items);
    inventory.setId("box-style");
    inventory.setPadding(new Insets(5, 5, 5, 5));
    inventory.setMaxHeight(100);
    return inventory;
  }


  /**
   * Method for making a box with the passage information.
   *
   * @return passageInfoBox with passage information
   */
  public VBox passageBox() {

    Text passageTitle = createPassageTitle();
    Text passageContent = createPassageContent();
    VBox passageLinks = createPassageLinks();

    passageLinks.visibleProperty().bind(new SimpleBooleanProperty(true));

    VBox passageInfoBox = new VBox(passageTitle, passageContent);
    passageInfoBox.getChildren().addAll(passageLinks);
    passageInfoBox.setAlignment(Pos.CENTER);
    passageInfoBox.setSpacing(50);
    passageInfoBox.setPadding(new Insets(30));

    VBox passageBox = new VBox();
    passageBox.setId("passage-box");
    passageBox.getChildren().add(passageInfoBox);
    passageBox.setAlignment(Pos.CENTER);
    passageBox.setPadding(new Insets(5, 5, 5, 5));

    return passageInfoBox;
  }

  /**
   * The passage title.
   *
   * @return text with the passage title
   */
  private Text createPassageTitle() {
    Text title = new Text(currentPassage.getTitle());
    title.setId("sub-title-text");
    title.setTextAlignment(TextAlignment.CENTER);
    return title;
  }

  /**
   * The passage content.
   *
   * @return text with the passage content
   */
  private Text createPassageContent() {
    Text text = new Text(currentPassage.getContent());
    text.setId("title-text");
    text.setWrappingWidth(730);
    text.setTextAlignment(TextAlignment.CENTER);
    return text;
  }

  /**
   * A list of buttons with links.
   * Uses the controller to give action to the buttons.
   *
   * @return list of buttons with the passage links
   */
  private VBox createPassageLinks() {
    VBox passageLinkBox = new VBox();
    passageLinkBox.setAlignment(Pos.CENTER);

    currentPassage.getLinks().forEachRemaining(link -> {
      Button linkButton = new Button();
      linkButton.setId("link-button");
      linkButton.setText(link.getText());
      linkButton.setOnAction(e -> {
        LinkClicked linkEvent = new LinkClicked(link);
        gameController.goThroughLink(linkEvent);
      });
      passageLinkBox.getChildren().add(linkButton);
    });



    passageLinkBox.setSpacing(5);
    return passageLinkBox;
  }

  /**
   * Makes a new passageBox with the new passage and replace it.
   *
   * @param passage the new passage the player moved to
   */
  public void updatePassage(Passage passage) {
    currentPassage = passage;
    setLeft(passageBox());
  }

  /**
   * Updates the appropriate labels if the player changes.
   *
   * @param playerChange the event delivered when the player changes
   */
  public void updatePlayer(PropertyChangeEvent playerChange) {
    switch (playerChange.getPropertyName()) {
      case "GOLD" -> playerGold.setText(String.valueOf(playerChange.getNewValue()));
      case "HEALTH" -> playerHealth.setText(String.valueOf(playerChange.getNewValue()));
      case "SCORE" -> playerScore.setText(String.valueOf(playerChange.getNewValue()));
      case "INVENTORY" -> playerInventory.add(String.valueOf(playerChange.getNewValue()));
      default -> throw new IllegalArgumentException("unrecognizable event name");
    }
  }

  /**
   * Updates the goals and if the goals are fulfilled or not.
   */
  public void updateGoals() {
    playerGoals.clear();
    gameController.getGoals().forEachRemaining(playerGoals::add);
  }
}

