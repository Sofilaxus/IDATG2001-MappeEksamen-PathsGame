package no.ntnu.gruppe1.model.goals;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.gruppe1.model.goals.Goal;
import no.ntnu.gruppe1.model.goals.GoalFactory;
import no.ntnu.gruppe1.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the InventoryGoal class.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 23.02.2023
 */

class InventoryGoalTest {

  private Goal<?> goal;
  private Player testPlayer;

  /**
   * Creates objects and assigns standard values for testing.
   */
  @BeforeEach
  void setUp() {
    goal = GoalFactory.getGoalFactory().createGoal("InventoryGoal", "rusty sword");
    testPlayer = new Player.PlayerBuilder("Marie")
        .setHealth(21)
        .setGold(10)
        .setScore(17)
        .build();
  }

  /**
   * Test for when the goal is null
   */
  @Test
  void newInventoryGoal() {
    IllegalArgumentException madeGoal =
        assertThrows(IllegalArgumentException.class, () ->
            goal = GoalFactory.getGoalFactory()
                .createGoal("InventoryGoal", null));
    assertEquals("value of inventory Goal invalid inventory goal can not be blank",
        madeGoal.getMessage());

    madeGoal =
        assertThrows(IllegalArgumentException.class, () ->
            goal = GoalFactory.getGoalFactory()
                .createGoal("InventoryGoal", ""));
    assertEquals("value of inventory Goal invalid inventory goal can not be blank",
        madeGoal.getMessage());

    madeGoal =
        assertThrows(IllegalArgumentException.class, () ->
            goal = GoalFactory.getGoalFactory()
                .createGoal("InventoryGoal", "  "));
    assertEquals("value of inventory Goal invalid inventory goal can not be blank",
        madeGoal.getMessage());
  }

  /**
   * Tests for when the player has "rusty sword" in inventory.
   * The "rusty sword" is a mandatory Item in the inventory.
   */
  @Test
  void testIsFulfilled() {
    testPlayer.addToInventory("rusty sword");
    assertTrue(goal.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player has "rusty sword" and "Shiny Pendant" in inventory.
   * The "rusty sword" is a mandatory Item in the inventory.
   */
  @Test
  void testIsOverFulfilled() {
    testPlayer.addToInventory("rusty sword");
    testPlayer.addToInventory("shiny pendant");
    assertTrue(goal.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player has "rusty sword" and "Health Potion" in inventory.
   * The "rusty sword" and "Health Potion" is a mandatory Item in the inventory.
   */
  @Test
  void testMultipleItems() {
    goal = GoalFactory.getGoalFactory().createGoal("InventoryGoal", "rusty sword");
    Goal<?> goal2 = GoalFactory.getGoalFactory().createGoal("InventoryGoal", "health potion");
    testPlayer.addToInventory("rusty sword");
    testPlayer.addToInventory("health potion");
    assertTrue(goal.isFulfilled(testPlayer));
    assertTrue(goal2.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player has "rusty sword", "Health Potion" and "Shiny Pendant" in inventory.
   * The "rusty sword" and "Health Potion" is a mandatory Item in the inventory.
   */
  @Test
  void testMultipleItemsOverFilled() {
    goal = GoalFactory.getGoalFactory().createGoal("InventoryGoal", "rusty sword");
    testPlayer.addToInventory("rusty sword");
    testPlayer.addToInventory("shiny pendant");
    testPlayer.addToInventory("health potion");
    assertTrue(goal.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player has "rusty sword" and "Shiny Pendant" in inventory.
   * but goal is another mandatory item.
   */
  @Test
  void testFalseMultipleItems() {
    goal = GoalFactory.getGoalFactory().createGoal("InventoryGoal", "mandatoryItem");
    testPlayer.addToInventory("rusty sword");
    testPlayer.addToInventory("shiny pendant");
    assertFalse(goal.isFulfilled(testPlayer));
  }

  /**
   * Tests for when the player does not have "rusty sword" in the inventory
   * but does have "Shiny Pendant".
   * The "rusty sword" is a mandatory Item in the inventory.
   */
  @Test
  void testIsUnderFulfilled() {
    testPlayer.addToInventory("shiny pendant");
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
    assertEquals("rusty sword", goal.getFulfillmentCriteria());
  }
}