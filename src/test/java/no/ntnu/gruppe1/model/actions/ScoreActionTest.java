package no.ntnu.gruppe1.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.gruppe1.model.actions.Action;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import no.ntnu.gruppe1.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreActionTest {

  private Action<?> action;
  private Player testPlayer;

  @BeforeEach
  void setUp() {
    action = ActionFactory.getActionFactory().createAction("points", "2");
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
            action = ActionFactory.getActionFactory().createAction("points", "0"));
    assertEquals("value of score Action invalid This action does nothing", madeAction.getMessage());
  }

  @Test
  void testScoreAmount() {
    assertEquals(17, testPlayer.getScore());
  }

  @Test
  void testScoreAction() {
    action.execute(testPlayer);
    assertEquals(19, testPlayer.getScore());
  }

  @Test
  void testDobbleScoreAction() {
    action.execute(testPlayer);
    action.execute(testPlayer);
    assertEquals(21, testPlayer.getScore());
  }

  @Test
  void testNegativeAmount() {
    Action<?> action2 = ActionFactory.getActionFactory().createAction("points", "-3");
    action.execute(testPlayer);
    action2.execute(testPlayer);
    assertEquals(16, testPlayer.getScore());
  }

  @Test
  void testIsNotFulfilled() {
    action.execute(testPlayer);
    assertNotEquals(17, testPlayer.getScore());
  }

  @Test
  void testThrow() {
    action = ActionFactory.getActionFactory().createAction("points", "-50");
    IllegalStateException thrownScore =
        assertThrows(IllegalStateException.class, () -> action.execute(testPlayer));
    assertEquals("Marie does not have enough points to continue!", thrownScore.getMessage());

    assertEquals(0, testPlayer.getScore());
  }

  @Test
  void testPlayerNull() {
    NullPointerException nullThrow =
        assertThrows(NullPointerException.class, () -> action.execute(null));
    assertEquals("action needs a player to execute", nullThrow.getMessage());
  }
}