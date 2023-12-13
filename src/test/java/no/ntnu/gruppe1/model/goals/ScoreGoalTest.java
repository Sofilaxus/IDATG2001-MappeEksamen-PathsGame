package no.ntnu.gruppe1.model.goals;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.gruppe1.model.goals.Goal;
import no.ntnu.gruppe1.model.goals.GoalFactory;
import no.ntnu.gruppe1.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing tscoreGoal class.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 19.04.2023
 */

class ScoreGoalTest {

  private Goal<?> goal;
  private Player testPlayer;

  /**
   * Creates objects and assigns standard values for testing.
   */
  @BeforeEach
  void setUp() {
    goal = GoalFactory.getGoalFactory().createGoal("ScoreGoal", "17");
    testPlayer = new Player.PlayerBuilder("Marie")
        .setHealth(21)
        .setGold(10)
        .setScore(17)
        .setItem("rusty sword")
        .build();
  }

  /**
   * Test for when the goal is an invalid number
   */
  @Test
  void newScoreGoal() {

    IllegalArgumentException madeGoal =
        assertThrows(IllegalArgumentException.class, () ->
            goal = GoalFactory.getGoalFactory().createGoal("ScoreGoal", "-1")
        );
    assertEquals(
        "value of score Goal invalid The goal value can not be negative, the player can not reach it",
        madeGoal.getMessage());
  }

  /**
   * Tests for when the player has score 17 and the minimum is 17 score.
   */
  @Test
  void testIsFulfilled() {
    assertTrue(goal.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player has score 18 and the minimum is 17 score.
   */
  @Test
  void testIsOverFulfilled() {
    testPlayer.addScore(1);
    assertTrue(goal.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player has score 16 and the minimum is 17 score.
   */
  @Test
  void testIsUnderFulfilled() {
    testPlayer.addScore(-1);
    assertFalse(goal.isFulfilled(testPlayer));
  }

  /**
   * Test for when there is no player to test the goal.
   */
  @Test
  void testPlayerNull() {
    assertThrows(NullPointerException.class, () -> goal.isFulfilled(null));
  }

  /**
   * Test for getting the goalValue.
   */
  @Test
  void testGetFulfillmentCriteria() {
    assertEquals(17, goal.getFulfillmentCriteria());
  }
}