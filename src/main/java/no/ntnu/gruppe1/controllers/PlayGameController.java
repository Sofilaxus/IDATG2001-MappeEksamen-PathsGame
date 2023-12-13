package no.ntnu.gruppe1.controllers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import no.ntnu.gruppe1.model.Game;
import no.ntnu.gruppe1.model.Link;
import no.ntnu.gruppe1.model.Passage;
import no.ntnu.gruppe1.model.goals.Goal;
import no.ntnu.gruppe1.model.player.Player;
import no.ntnu.gruppe1.utility.LinkClicked;
import no.ntnu.gruppe1.view.PlayGameScene;

/**
 * Controller for PlayGameScene.
 * Gets the goals from the game and puts them into a list,
 * creates the goals fulfillment confirmation checkboxes,
 * gets information about the player and updates them according to
 * actions when going through a link.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.05.05
 */
public class PlayGameController implements PropertyChangeListener {

  //Fields
  private final PlayGameScene playGameScene;
  private final Game game;
  private final Passage currentPassage;

  /**
   * Constructor for the controller.
   *
   * @param playGameScene the view this controller is connected to
   */
  public PlayGameController(PlayGameScene playGameScene, Game game) {
    this.playGameScene = playGameScene;
    this.game = game;
    currentPassage = game.begin();
    game.getPlayer().addPropertyChangeListener(this);
  }

  /**
   * Method for getting the goals for the game that is to be played.
   *
   * @return iterator of goals for the game that is to be played
   */
  public Iterator<Text> getGoals() {
    List<Text> listOfGoals = new ArrayList<>();
    game.getGoals().forEachRemaining(goal -> listOfGoals.add(createGoalText(goal)));

    return listOfGoals.iterator();
  }

  /**
   * Creates text with the goal, and checks if its fulfilled or not.
   *
   * @param goal the goal to display
   * @return text with the goal
   */
  private Text createGoalText(Goal<?> goal) {
    String goalCriteria = String.valueOf(goal.getFulfillmentCriteria());
    String goalTitle = goal.isFulfilled(getPlayer()) ? "☑" : "☐";
    switch (goal.getClass().getSimpleName()) {
      case "GoldGoal" -> goalTitle += "Get " + goalCriteria + " gold.";
      case "HealthGoal" -> goalTitle += "Have " + goalCriteria + " health.";
      case "ScoreGoal" -> goalTitle += "Get " + goalCriteria + " score points.";
      case "InventoryGoal" -> goalTitle += "Get item " + goalCriteria;
      default -> throw new IllegalArgumentException("Unknown goal type");
    }
    Text goalText = new Text(goalTitle);
    goalText.setId("normal-text");
    return goalText;
  }

  /**
   * Method for getting the player that plays the game.
   *
   * @return player playing the game
   */
  public Player getPlayer() {
    return game.getPlayer();
  }

  /**
   * Method for getting the player's gold.
   *
   * @return gold of player as StringProperty
   */
  public String getPlayerGold() {
    return String.valueOf(getPlayer().getGold());
  }

  /**
   * Method for getting the player's name.
   *
   * @return health of player as StringProperty
   */
  public String getPlayerHealth() {
    return String.valueOf(getPlayer().getHealth());
  }


  /**
   * Method for getting the player's score.
   *
   * @return score of player as StringProperty
   */
  public String getPlayerScore() {
    return String.valueOf(getPlayer().getScore());
  }

  /**
   * Method for getting a passage.
   *
   * @return currentPassage the passage the game is on
   */
  public Passage getCurrentPassage() {
    return currentPassage;
  }

  /**
   * Method for getting the inventory of the game.
   *
   * @return inventory to the player
   */
  public Iterator<String> getInventory() {
    List<String> listOfItems = new ArrayList<>();
    getPlayer().getInventory().forEachRemaining(listOfItems::add);

    return listOfItems.iterator();
  }

  /**
   * Gets the clicked link and updates the game to show the next passage.
   * Updates the player statistics with actions happening in the game.
   * Shows an error if a link clicked does not lead to a passage.
   *
   * @param event the event that triggered the method
   */
  public void goThroughLink(LinkClicked event) {
    try {
      Link link = event.getLink();
      Player player = game.getPlayer();
      link.getActions().forEachRemaining(action -> action.execute(player));
      playGameScene.updatePassage(game.getStory().getPassage(link));
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText(e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Updates goals and player statistics when player object is changed.
   *
   * @param evt A PropertyChangeEvent object describing
   *            the event source and the property that has changed.
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    playGameScene.updatePlayer(evt);
    playGameScene.updateGoals();
  }
}
