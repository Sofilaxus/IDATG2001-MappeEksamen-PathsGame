package no.ntnu.gruppe1.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.ntnu.gruppe1.model.actions.Action;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import no.ntnu.gruppe1.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryActionTest {

  private Action<?> action;
  private Player testPlayer;
  List<String> testInventory = new ArrayList<>();

  @BeforeEach
  void setUp() {
    action = ActionFactory.getActionFactory().createAction("item", "shiny pendant");
    testPlayer = new Player.PlayerBuilder("Marie")
        .setHealth(21)
        .setGold(10)
        .setScore(17)
        .setItem("rusty sword")
        .build();
  }

  @Test
  void testActionValue() {
    assertEquals("shiny pendant", action.getValue());
  }

  @Test
  void testNewInventoryActionNull() {
    IllegalArgumentException madeAction =
        assertThrows(IllegalArgumentException.class, () ->
            action = ActionFactory.getActionFactory().createAction("item", null));
    assertEquals("value of inventory Action invalid This action does nothing.",
        madeAction.getMessage());

    madeAction =
        assertThrows(IllegalArgumentException.class, () ->
            action = ActionFactory.getActionFactory().createAction("item", ""));
    assertEquals("value of inventory Action invalid This action does nothing.",
        madeAction.getMessage());

    madeAction =
        assertThrows(IllegalArgumentException.class, () ->
            action = ActionFactory.getActionFactory().createAction("item", "  "));
    assertEquals("value of inventory Action invalid This action does nothing.",
        madeAction.getMessage());
  }

  @Test
  void testInventoryContent() {
    assertEquals("rusty sword", testPlayer.getInventory().next());
  }

  @Test
  void testAddedInventoryContent() {
    action.execute(testPlayer);
    Iterator<String> inventoryIterator = testPlayer.getInventory();
    assertEquals("rusty sword", inventoryIterator.next());
    assertEquals("shiny pendant", inventoryIterator.next());
  }

  /**
   * This test might be changed to assertNotEquals if we decide to
   * not have duplicate items in inventory. If stackable items become a thing.
   */
  @Test
  void testDoubleInventoryAction() {
    action.execute(testPlayer);
    action.execute(testPlayer);
    Iterator<String> inventoryIterator = testPlayer.getInventory();
    assertEquals("rusty sword", inventoryIterator.next());
    assertEquals("shiny pendant", inventoryIterator.next());
    assertEquals("shiny pendant", inventoryIterator.next());
  }

  @Test
  void testOtherAmount() {
    Action<?> action2 = ActionFactory.getActionFactory()
        .createAction("item", "health potion");
    action.execute(testPlayer);
    action2.execute(testPlayer);

    Iterator<String> inventoryIterator = testPlayer.getInventory();
    assertEquals("rusty sword", inventoryIterator.next());
    assertEquals("shiny pendant", inventoryIterator.next());
    assertEquals("health potion", inventoryIterator.next());
  }

  @Test
  void testPlayerNull() {
    NullPointerException nullThrow =
        assertThrows(NullPointerException.class, () ->
            action.execute(null));
    assertEquals("action needs a player to execute", nullThrow.getMessage());
  }
}