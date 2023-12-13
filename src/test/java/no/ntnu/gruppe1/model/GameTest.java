package no.ntnu.gruppe1.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import no.ntnu.gruppe1.model.Game;
import no.ntnu.gruppe1.model.Link;
import no.ntnu.gruppe1.model.Passage;
import no.ntnu.gruppe1.model.Story;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import no.ntnu.gruppe1.model.goals.Goal;
import no.ntnu.gruppe1.model.goals.GoalFactory;
import no.ntnu.gruppe1.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
  private Game testGame;
  private Player testPlayer;
  private Story testStory;
  private List<Goal<?>> testGoals;

  @BeforeEach
  void setUp() {

    Passage openingPassage = new Passage.PassageBuilder()
        .setTitle("Beginnings")
        .setContent("You are in a small, dimly lit room. There is a door in front of you.")
        .setLink(new Link.LinkBuilder()
            .setText("Try to open the door")
            .setReference("Another room")
            .setAction(ActionFactory.getActionFactory()
                .createAction("health", "-2"))
            .build())
        .build();

    Passage passage = new Passage.PassageBuilder()
        .setTitle("Another room")
        .setContent("The door opens to another room.You see a desk with a large, dusty book.")
        .setLink(new Link.LinkBuilder()
            .setText("Open the book")
            .setReference("The book of spells")
            .setAction(ActionFactory.getActionFactory()
                .createAction("item", "book"))
            .setAction(ActionFactory.getActionFactory()
                .createAction("points", "5"))
            .setAction(ActionFactory.getActionFactory()
                .createAction("gold", "2"))
            .build())
        .setLink(new Link.LinkBuilder()
            .setText("Go back")
            .setReference("Beginnings")
            .build())
        .build();

    testStory = new Story("Haunted House", openingPassage);
    testStory.addPassage(passage);

    testPlayer = new Player.PlayerBuilder("Marie")
        .setHealth(21)
        .setGold(10)
        .setScore(17)
        .build();

    testGoals = new ArrayList<>();
    testGoals.add(GoalFactory.getGoalFactory().createGoal("GoldGoal", "10"));
    testGoals.add(GoalFactory.getGoalFactory().createGoal("InventoryGoal", "Sword"));
    testGoals.add(GoalFactory.getGoalFactory().createGoal("HealthGoal", "5"));
    testGoals.add(GoalFactory.getGoalFactory().createGoal("ScoreGoal", "15"));

    testGame = new Game(testPlayer, testStory, testGoals);
  }

  @Test
  void testCreateGameWithNewPlayer() {
    testPlayer = new Player.PlayerBuilder("Marie")
        .setHealth(21)
        .setGold(17)
        .setScore(10)
        .setItem("rusty sword")
        .build();
    assertNotEquals(testGame, new Game(testPlayer, testStory, testGoals));
  }

  @Test
  void testGameNull() {
    NullPointerException thrownNull =
        assertThrows(NullPointerException.class, () -> {
          new Game(null, testStory, testGoals);
        });
    assertEquals("You need a player to create a game", thrownNull.getMessage());

    thrownNull =
        assertThrows(NullPointerException.class, () -> {
          new Game(testPlayer, null, testGoals);
        });
    assertEquals("You need a story to create a game", thrownNull.getMessage());

    thrownNull =
        assertThrows(NullPointerException.class, () -> {
          new Game(testPlayer, testStory, null);
        });
    assertEquals("Goals have not been initialised correctly", thrownNull.getMessage());
  }

  @Test
  void testGameIncompleteCreation() {
    IllegalArgumentException thrownNull =
        assertThrows(IllegalArgumentException.class, () -> {
          Player player = new Player.PlayerBuilder("")
              .setHealth(0)
              .setScore(0)
              .build();
          new Game(player, testStory, testGoals);
        });
    assertEquals("Name cannot be empty.", thrownNull.getMessage());

    thrownNull =
        assertThrows(IllegalArgumentException.class, () -> {
          Story story = new Story("  ", null);
          new Game(testPlayer, story, testGoals);
        });
    assertEquals("Title of story can not be blank or null", thrownNull.getMessage());

    thrownNull =
        assertThrows(IllegalArgumentException.class, () -> {
          List<Goal<?>> goal = new ArrayList<>();
          goal.add(GoalFactory.getGoalFactory()
              .createGoal("GoldGoal", "-1"));
          assertEquals(0, goal.size());
          new Game(testPlayer, testStory, goal);
        });
    assertEquals(
        "value of gold Goal invalid The goal value can not be negative, the player can not reach it",
        thrownNull.getMessage());

    thrownNull =
        assertThrows(IllegalArgumentException.class, () -> {
          List<Goal<?>> goal = new ArrayList<>();
          goal.add(GoalFactory.getGoalFactory()
              .createGoal("Goal", "Value"));
          assertEquals(0, goal.size());
          new Game(testPlayer, testStory, goal);
        });
    assertEquals("Goal type not recognised Goal", thrownNull.getMessage());
  }

  @Test
  void testGameGetStory() {
    assertEquals(testStory, testGame.getStory());

    Passage openingPassage = new Passage.PassageBuilder()
        .setTitle("Beginnings")
        .setContent("You are in a small, dimly lit room. There is a door in front of you.")
        .setLink(new Link.LinkBuilder()
            .setText("Try to open the door")
            .setReference("Another room")
            .setAction(ActionFactory.getActionFactory()
                .createAction("health", "-2"))
            .build())
        .build();

    Passage passage = new Passage.PassageBuilder()
        .setTitle("Another room")
        .setContent("The door opens to another room.You see a desk with a large, dusty book.")
        .setLink(new Link.LinkBuilder()
            .setText("Open the book")
            .setReference("The book of spells")
            .setAction(ActionFactory.getActionFactory()
                .createAction("item", "book"))
            .setAction(ActionFactory.getActionFactory()
                .createAction("points", "5"))
            .setAction(ActionFactory.getActionFactory()
                .createAction("gold", "2"))
            .build())
        .setLink(new Link.LinkBuilder()
            .setText("Go back")
            .setReference("Beginnings")
            .build())
        .build();

    Story testStory2 = new Story("Haunted House", openingPassage);
    testStory2.addPassage(passage);

    assertNotEquals(testStory2, testGame.getStory());
    assertEquals(testStory2.getOpeningPassage(), testGame.begin());
    assertEquals(testStory2.getTitle(), testGame.getStory().getTitle());
  }

  @Test
  void testGameGetPlayer() {
    assertEquals(testPlayer, testGame.getPlayer());

    Player newPlayer = new Player.PlayerBuilder("Marie")
        .setHealth(21)
        .setGold(17)
        .setScore(10)
        .build();
    assertNotEquals(newPlayer, testPlayer);
  }

  @Test
  void testGameGetGoals() {
    ArrayList<Goal<?>> goals = new ArrayList<>();
    testGame.getGoals().forEachRemaining(goals::add);


    assertEquals(10,goals.get(0).getFulfillmentCriteria());
    assertEquals("Sword",goals.get(1).getFulfillmentCriteria());
    assertEquals(5,goals.get(2).getFulfillmentCriteria());
    assertEquals(15,goals.get(3).getFulfillmentCriteria());
  }

  @Test
  void testGameMovePassage() {
    Link linkOP = new Link.LinkBuilder().setReference("Another room").build();

    Passage passage = new Passage.PassageBuilder()
        .setTitle("Another room")
        .setContent(
            "The door opens to another room.You see a desk with a large, dusty book.")
        .build();

    assertEquals(passage, testGame.go(linkOP));
  }

  @Test
  void testGameGoThrows() {
    NullPointerException thrownNull =
        assertThrows(NullPointerException.class, () -> {
          testGame.go(null);
        });
    assertEquals("Link can not be null.", thrownNull.getMessage());

    NoSuchElementException thrownNoElement =
        assertThrows(NoSuchElementException.class, () -> {
          testGame.go(new Link.LinkBuilder()
              .setReference("Wrong")
              .build());
        });
    assertEquals("Passage Wrong was not found in the story.", thrownNoElement.getMessage());
  }
}