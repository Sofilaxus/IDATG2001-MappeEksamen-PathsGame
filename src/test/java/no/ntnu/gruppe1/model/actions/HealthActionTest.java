package no.ntnu.gruppe1.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.gruppe1.model.actions.Action;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import no.ntnu.gruppe1.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HealthActionTest {

  private Action<?> action;
  private Player testPlayer;

  @BeforeEach
  void setUp() {

    action = ActionFactory.getActionFactory().createAction("health", "2");
    testPlayer = new Player.PlayerBuilder("Marie")
        .setHealth(21)
        .setGold(10)
        .setScore(17)
        .setItem("rusty sword")
        .build();

  }

  @Test
  void testActionValue() {
    assertEquals(2, action.getValue());
  }

  @Test
  void testNewGoldActionNull() {
    IllegalArgumentException madeAction =
        assertThrows(IllegalArgumentException.class, () ->
            action = ActionFactory.getActionFactory().createAction("health", "0"));
    assertEquals("value of health Action invalid This action does nothing",
        madeAction.getMessage());
  }

  @Test
  void testHealthAmount() {
    assertEquals(21, testPlayer.getHealth());
  }

  @Test
  void testIsFulfilled() {
    action.execute(testPlayer);
    assertEquals(23, testPlayer.getHealth());
  }

  @Test
  void testDoubleHealthAction() {
    action.execute(testPlayer);
    action.execute(testPlayer);
    assertEquals(25, testPlayer.getHealth());
  }

  @Test
  void testOtherAmount() {
    Action<?> action2 = ActionFactory.getActionFactory().createAction("health", "-3");
    action.execute(testPlayer);
    action2.execute(testPlayer);
    assertEquals(20, testPlayer.getHealth());
  }

  @Test
  void testIsNotFulfilled() {
    action.execute(testPlayer);
    assertNotEquals(21, testPlayer.getHealth());
  }

  @Test
  void testThrow() {
    action = ActionFactory.getActionFactory().createAction("health", "-50");
    IllegalStateException thrownHealth =
        assertThrows(IllegalStateException.class, () ->
            action.execute(testPlayer));
    assertEquals("Marie died and can't continue the game!", thrownHealth.getMessage());

    assertEquals(0, testPlayer.getHealth());

  }

  @Test
  void testPlayerNull() {
    NullPointerException nullThrow =
        assertThrows(NullPointerException.class, () ->
            action.execute(null));
    assertEquals("action needs a player to execute", nullThrow.getMessage());
  }
}