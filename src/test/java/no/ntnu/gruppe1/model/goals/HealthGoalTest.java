package no.ntnu.gruppe1.model.goals;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.gruppe1.model.goals.Goal;
import no.ntnu.gruppe1.model.goals.GoalFactory;
import no.ntnu.gruppe1.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the HealthGoal class.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 23.02.2023
 */

class HealthGoalTest {

  private Goal<?> goal;
  private Player testPlayer;

  /**
   * Creates objects and assigns standard values for testing.
   */
  @BeforeEach
  void setUp() {
    goal = GoalFactory.getGoalFactory().createGoal("HealthGoal", "21");
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
  void newHealthGoal() {
    IllegalArgumentException madeGoal =
        assertThrows(IllegalArgumentException.class, () ->
            goal = GoalFactory.getGoalFactory()
                .createGoal("HealthGoal", "-1"));
    assertEquals(
        "value of health Goal invalid The goal value can not be negative, the player can not reach it",
        madeGoal.getMessage());
  }

  /**
   * Tests for when the player has 21 Health and the minimum is 21 Health.
   */
  @Test
  void testIsFulfilled() {
    assertTrue(goal.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player has 22 Health and the minimum is 21 Health.
   */
  @Test
  void testIsOverFulfilled() {
    testPlayer.addHealth(1);
    assertTrue(goal.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player has 20 Health and the minimum is 21 Health.
   */
  @Test
  void testIsUnderFulfilled() {
    testPlayer.addHealth(-1);
    assertFalse(goal.isFulfilled(testPlayer));
  }

  /**
   * Test for when there is no player to test the goal.
   */
  @Test
  void testPlayerNull() {
    assertThrows(NullPointerException.class, () ->
        goal.isFulfilled(null));
  }

  /**
   * Test for getting the goalValue.
   */
  @Test
  void testGetFulfillmentCriteria() {
    assertEquals(21, goal.getFulfillmentCriteria());
  }
}