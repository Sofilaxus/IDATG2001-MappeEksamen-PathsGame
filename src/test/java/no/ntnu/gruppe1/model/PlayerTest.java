package no.ntnu.gruppe1.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.ntnu.gruppe1.model.player.Player;
import no.ntnu.gruppe1.model.player.PlayerTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  private Player testPlayer;

  @BeforeEach
  void setUp() {
    testPlayer = new Player.PlayerBuilder("Marie")
        .setHealth(21)
        .setGold(10)
        .setScore(17)
        .build();
  }

  @Test
  void testNewPlayerInvalidName() {
    NullPointerException thrownNameNull =
        assertThrows(NullPointerException.class, () -> {
          new Player.PlayerBuilder(null).build();
        });
    assertEquals("Name cannot be null.", thrownNameNull.getMessage());

    IllegalArgumentException thrownName =
        assertThrows(IllegalArgumentException.class, () -> {
          new Player.PlayerBuilder("").build();
        });
    assertEquals("Name cannot be empty.", thrownName.getMessage());

    thrownName =
        assertThrows(IllegalArgumentException.class, () -> {
          new Player.PlayerBuilder("  ").build();
        });
    assertEquals("Name cannot be empty.", thrownName.getMessage());
  }

  @Test
  void testNewPlayerInvalidHealth() {
    IllegalArgumentException thrownHealth =
        assertThrows(IllegalArgumentException.class, () -> {
          new Player.PlayerBuilder("Marie").setHealth(0);
        });
    assertEquals("Health can not be zero or lower.", thrownHealth.getMessage());

    thrownHealth =
        assertThrows(IllegalArgumentException.class, () -> {
          new Player.PlayerBuilder("Marie").setHealth(-1);
        });
    assertEquals("Health can not be zero or lower.", thrownHealth.getMessage());
  }

  @Test
  void testNewPlayerInvalidGold() {
    IllegalArgumentException thrownGold =
        assertThrows(IllegalArgumentException.class, () -> {
          new Player.PlayerBuilder("Marie").setGold(-1);
        });
    assertEquals("You cannot have negative gold.", thrownGold.getMessage());
  }

  @Test
  void testNewPlayerInvalidScore() {
    IllegalArgumentException thrownScore =
        assertThrows(IllegalArgumentException.class, () -> {
          new Player.PlayerBuilder("Marie").setScore(-1);
        });
    assertEquals("You cannot have negative score.", thrownScore.getMessage());
  }

  @Test
  void testNewPlayerInvalidInventory() {
    NullPointerException thrownInventory =
        assertThrows(NullPointerException.class, () -> {
          new Player.PlayerBuilder("Marie").setInventory(null);
        });
    assertEquals("Inventory cannot be null.", thrownInventory.getMessage());

    assertNotNull(testPlayer.getInventory());
  }

  @Test
  void testNewPlayerInvalidItem() {
    NullPointerException thrownItemNull =
        assertThrows(NullPointerException.class, () -> {
          new Player.PlayerBuilder("Marie").setItem(null);
        });
    assertEquals("item cannot be null.", thrownItemNull.getMessage());

    IllegalArgumentException thrownItem =
        assertThrows(IllegalArgumentException.class, () -> {
          new Player.PlayerBuilder("Marie").setItem("");
        });
    assertEquals("item cannot be empty.", thrownItem.getMessage());

    thrownItem =
        assertThrows(IllegalArgumentException.class, () -> {
          new Player.PlayerBuilder("Marie").setItem("  ");
        });
    assertEquals("item cannot be empty.", thrownItem.getMessage());
  }

  @Test
  void testPlayerLowValues() {
    assertDoesNotThrow(() -> new Player.PlayerBuilder("M")
        .setHealth(1)
        .setGold(0)
        .setScore(0)
        .setInventory(new ArrayList<>())
        .build());
  }

  @Test
  void checkPlayerName() {
    assertEquals("Marie", testPlayer.getName());
  }

  @Test
  void checkPlayerHealth() {
    assertEquals(21, testPlayer.getHealth());
  }

  @Test
  void checkPlayerScore() {
    assertEquals(17, testPlayer.getScore());
  }

  @Test
  void checkPlayerGold() {
    assertEquals(10, testPlayer.getGold());
  }

  @Test
  void checkPlayerInventory() {
    assertNotEquals(null, testPlayer.getInventory());

    assertEquals(new ArrayList<String>().iterator().hasNext(), testPlayer.getInventory().hasNext());
  }

  @Test
  void checkPlayerInventoryItem() {
    testPlayer.addToInventory("rusty sword");
    assertEquals("rusty sword", testPlayer.getInventory().next());
  }

  @Test
  void testChangePlayerHealth() {
    testPlayer.addHealth(9);
    assertEquals(30, testPlayer.getHealth());

    testPlayer.addHealth(-1);
    assertEquals(29, testPlayer.getHealth());
  }

  @Test
  void testChangePlayerHealthIllegalState() {

    IllegalStateException thrownHealth =
        assertThrows(IllegalStateException.class, () ->
            testPlayer.addHealth(-29));
    assertEquals("Marie died and can't continue the game!", thrownHealth.getMessage());
    assertEquals(0, testPlayer.getHealth());

    thrownHealth =
        assertThrows(IllegalStateException.class, () ->
            testPlayer.addHealth(-12));
    assertEquals("Marie died and can't continue the game!", thrownHealth.getMessage());
    assertEquals(0, testPlayer.getHealth());
  }

  @Test
  void testChangePlayerScore() {
    testPlayer.addScore(5);
    assertEquals(22, testPlayer.getScore());

    testPlayer.addScore(-2);
    assertEquals(20, testPlayer.getScore());

    testPlayer.addScore(-20);
    assertEquals(0, testPlayer.getScore());
  }

  @Test
  void testChangePlayerScoreIllegalState() {
    IllegalStateException thrownScore =
        assertThrows(IllegalStateException.class, () ->
            testPlayer.addScore(-18));
    assertEquals("Marie does not have enough points to continue!", thrownScore.getMessage());
    assertEquals(0, testPlayer.getScore());
  }

  @Test
  void testChangePlayerGold() {
    testPlayer.addGold(3);
    assertEquals(13, testPlayer.getGold());

    testPlayer.addGold(-3);
    assertEquals(10, testPlayer.getGold());

    testPlayer.addGold(-10);
    assertEquals(0, testPlayer.getGold());
  }

  @Test
  void testChangePlayerGoldIllegalState() {
    IllegalStateException thrownGold =
        assertThrows(IllegalStateException.class, () ->
            testPlayer.addGold(-11));
    assertEquals("Marie does not have enough gold to continue!", thrownGold.getMessage());
    assertEquals(0, testPlayer.getGold());
  }

  @Test
  void testChangePlayerInventory() {
    testPlayer.addToInventory("shiny pendant");
    Iterator<String> inventoryIterator = testPlayer.getInventory();

    assertEquals("shiny pendant", inventoryIterator.next());

    testPlayer.addToInventory("rusty sword");
    inventoryIterator = testPlayer.getInventory();
    assertEquals("shiny pendant", inventoryIterator.next());
    assertEquals("rusty sword", inventoryIterator.next());
  }

  @Test
  void testChangePlayerInventoryNoItem() {

    IllegalArgumentException thrownInventory =
        assertThrows(IllegalArgumentException.class, () ->
            testPlayer.addToInventory(null));
    assertEquals("Marie can not pick up a nonexistent item.", thrownInventory.getMessage());

    thrownInventory =
        assertThrows(IllegalArgumentException.class, () ->
            testPlayer.addToInventory(""));
    assertEquals("Marie can not pick up a nonexistent item.", thrownInventory.getMessage());

    thrownInventory =
        assertThrows(IllegalArgumentException.class, () ->
            testPlayer.addToInventory("  "));
    assertEquals("Marie can not pick up a nonexistent item.", thrownInventory.getMessage());
  }
}