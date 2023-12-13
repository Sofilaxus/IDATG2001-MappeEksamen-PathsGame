package no.ntnu.gruppe1.controllers;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.gruppe1.model.Game;
import no.ntnu.gruppe1.model.goals.*;
import no.ntnu.gruppe1.model.player.Player;
import no.ntnu.gruppe1.model.player.PlayerTypes;
import no.ntnu.gruppe1.view.ChoiceBoxes;
import no.ntnu.gruppe1.view.LoadGameScene;
import no.ntnu.gruppe1.view.PathsGui;

/**
 * Controller for the LoadGameScene.
 * Makes the difficulty box for a player,
 * makes it possible to add goals for a player in a story,
 * gets the added goals and puts them in the game,
 * starts the game.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.05.05
 */
public class LoadGameController {

  //Fields
  private final LoadGameScene loadGameScene;
  private final SceneController sceneController;

  /**
   * Constructor for the controller.
   *
   * @param pathsgui the stage the scene will be set upon
   * @param loadGameScene the view this controller is connected to
   */
  public LoadGameController(PathsGui pathsgui, LoadGameScene loadGameScene) {
    this.loadGameScene = loadGameScene;
    sceneController = new SceneController(pathsgui);
  }

  /**
   * Adds new field inputs for a new goal.
   * Has a ChoiceBox, which makes it impossible for a user to type in a non-existing goal.
   *
   * @param event the event that triggered the method
   */
  public void addGoalField(ActionEvent event) {
    HBox goalFields = new HBox();
    goalFields.setSpacing(10);
    goalFields.setPadding(new Insets(10));

    ChoiceBox<ChoiceBoxes<Class<? extends Goal<?>>>> goalType = new ChoiceBox<>();
    goalType.getItems().addAll(List.of(
        new ChoiceBoxes<>("Gold goal", GoldGoal.class),
            new ChoiceBoxes<>("Health goal", HealthGoal.class),
            new ChoiceBoxes<>("Item goal", InventoryGoal.class),
            new ChoiceBoxes<>("Score goal", ScoreGoal.class)));
    goalType.setId("goalType");
    goalType.setAccessibleText("Choose goal");

    TextField goalValue = new TextField();
    goalValue.setId("goalValue");

    goalFields.getChildren().addAll(goalType, goalValue);
    VBox goalsList = ((VBox) loadGameScene.lookup("#goalsList"));
    goalsList.getChildren().add(goalFields);
  }

  /**
   * Starts the game. Adds the user inputs for the game, such as difficulty, name, and goals.
   */
  public void startStory() {
    try {
      List<Goal<?>> goals = getGoalsFromFields();
      PlayerTypes difficulty = getDifficulty();
      TextField playerNameField = ((TextField) loadGameScene.lookup("#playerNameField"));
      Game game;
      game = new Game(makePlayerWithDifficulty(difficulty,
          playerNameField.getText()), loadGameScene.getStory(), goals);
      sceneController.goToPlayGame(game);
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText("Could not start game. Problems found.");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Retrieves the goals from the ChoiceBoxes and fields.
   *
   * @return the list of goals the user has put
   */
  private List<Goal<?>> getGoalsFromFields() {
    List<Goal<?>> goals = new ArrayList<>();
    Set<Node> goalTypeFields = loadGameScene.lookupAll("#goalType");
    Set<Node> goalValueFields = loadGameScene.lookupAll("#goalValue");
    if (Objects.isNull(goalValueFields) || Objects.isNull(goalTypeFields)) {
      return null;
    }
    Iterator<Node> goalTypeFieldsIterator = goalTypeFields.iterator();
    Iterator<Node> goalValueFieldsIterator = goalValueFields.iterator();
    boolean anyErrors = false;

    while (goalTypeFieldsIterator.hasNext() && goalValueFieldsIterator.hasNext()) {
      Node typeNode = goalTypeFieldsIterator.next();
      Node valueNode = goalValueFieldsIterator.next();

      ChoiceBoxes<?> selectedOption =
          ((ChoiceBoxes<?>) ((ChoiceBox<?>) typeNode).getValue());

      if (selectedOption == null) {
        continue;
      }

      @SuppressWarnings("unchecked")
      Class<Goal<?>> goalClass = ((Class<Goal<?>>) selectedOption.getValue());

      TextField valueField = (TextField) valueNode;
      try {
        goals.add(GoalFactory.getGoalFactory()
            .createGoal(goalClass.getSimpleName(), valueField.getText()));
      } catch (IllegalArgumentException e) {
        anyErrors = true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning: Goal could not be added");
        alert.setHeaderText(e.getMessage());
        alert.showAndWait();
      }
    }
    return anyErrors ? null : goals;
  }

  /**
   * Get the user chosen difficultly for the game.
   *
   * @return the difficulty selected by the user for the game
   */
  private PlayerTypes getDifficulty() {
    Node difficultyChoiceBox = (loadGameScene.lookup("#difficultyChoiceBox"));

    ChoiceBoxes<?> selectedOption =
        ((ChoiceBoxes<?>) ((ChoiceBox<?>) difficultyChoiceBox).getValue());
    return ((PlayerTypes) selectedOption.getValue());
  }

  /**
   * Makes the player with the statistics from the chosen difficulty.
   *
   * @param difficulty the difficulty for a player
   * @param name the player name
   * @return the chosen difficulty
   */
  private Player makePlayerWithDifficulty(PlayerTypes difficulty, String name) {
    return switch (difficulty) {
      case EASY -> new Player.PlayerBuilder(name)
          .setGold(10)
          .setHealth(25)
          .build();
      case MEDIUM -> new Player.PlayerBuilder(name)
          .setGold(5)
          .setHealth(15)
          .build();
      case HARD -> new Player.PlayerBuilder(name)
          .setGold(0)
          .setHealth(5)
          .build();
      default -> throw new IllegalArgumentException("unrecognizable player type");
    };
  }
}
