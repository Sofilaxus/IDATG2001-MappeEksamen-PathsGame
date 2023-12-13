package no.ntnu.gruppe1.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.gruppe1.model.actions.Action;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import no.ntnu.gruppe1.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldActionTest {
  private Action<?> action;
  private Player testPlayer;

  @BeforeEach
  void setUp() {
    action = ActionFactory.getActionFactory().createAction("gold", "2");
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
            action = ActionFactory.getActionFactory().createAction("gold", "0"));
    assertEquals("value of gold Action invalid This action does nothing", madeAction.getMessage());
  }

  @Test
  void testGoldAmount() {
    assertEquals(10, testPlayer.getGold());
  }

  @Test
  void testIsFulfilled() {
    action.execute(testPlayer);
    assertEquals(12, testPlayer.getGold());
  }

  @Test
  void testDobbleGoldAction() {
    action.execute(testPlayer);
    action.execute(testPlayer);
    assertEquals(14, testPlayer.getGold());
  }

  @Test
  void testOtherAmount() {
    Action<?> action2 = ActionFactory.getActionFactory()
        .createAction("gold", "-3");
    action.execute(testPlayer);
    action2.execute(testPlayer);
    assertEquals(9, testPlayer.getGold());
  }

  @Test
  void testIsNotFulfilled() {
    action.execute(testPlayer);
    assertNotEquals(10, testPlayer.getGold());
  }

  @Test
  void testThrow() {
    action = ActionFactory.getActionFactory().createAction("gold", "-50");
    IllegalStateException thrownGold =
        assertThrows(IllegalStateException.class, () ->
            action.execute(testPlayer));
    assertEquals("Marie does not have enough gold to continue!", thrownGold.getMessage());

    assertEquals(0, testPlayer.getGold());
  }

  @Test
  void testPlayerNull() {
    NullPointerException nullThrow =
        assertThrows(NullPointerException.class, () ->
            action.execute(null));
    assertEquals("action needs a player to execute", nullThrow.getMessage());
  }
}