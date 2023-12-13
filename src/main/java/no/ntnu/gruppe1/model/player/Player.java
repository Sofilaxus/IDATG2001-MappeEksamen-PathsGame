package no.ntnu.gruppe1.model.player;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The Player class represents a player with various properties that can be influenced in a story.
 * The Player class has four attributes:
 * <ul>
 * <li><code>name</code> - The name of the player.</li>
 * <li><code>health</code> - The amount of health the player has, can not be less than zero.</li>
 * <li><code>score</code> - The amount of points the player has acquired.</li>
 * <li><code>gold</code> - The amount of gold the player has acquired.</li>
 * <li><code>inventory</code> - A list of things the player has in inventory.</li>
 * </ul>
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.02.08
 */
public class Player {

  // Fields
  private String name;
  private int health;
  private int score;
  private int gold;
  private List<String> inventory;
  private final Logger logger = Logger.getLogger("logger");
  private final PropertyChangeSupport playerSupport = new PropertyChangeSupport(this);

  /**
   * Private default constructor.
   */
  private Player() {
  }

  /**
   * Adds a listener to player.
   *
   * @param listener class that listens for changes
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    playerSupport.addPropertyChangeListener(listener);
  }

  /**
   * Remove listener from player.
   *
   * @param listener class that listens for changes
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    playerSupport.removePropertyChangeListener(listener);
  }

  /**
   * Internal builder class for building Player objects.
   */
  public static class PlayerBuilder {
    private final String name;
    private int health;
    private int score;
    private int gold;
    private final List<String> inventory;

    /**
     * Constructor for PlayerBuilder.
     * sets some standard values and initialises inventory.
     *
     * @param name the name of the player.
     * @throws IllegalArgumentException if name is blank.
     */
    public PlayerBuilder(String name) throws IllegalArgumentException {
      Objects.requireNonNull(name, "Name cannot be null.");
      if (!name.isBlank()) {
        this.name = name;
        this.score = 0;
        this.inventory = new ArrayList<>();
      } else {
        throw new IllegalArgumentException("Name cannot be empty.");
      }
    }

    /**
     * Set the health of the player.
     *
     * @param health the health of the player
     * @return the player builder object
     */
    public PlayerBuilder setHealth(int health) throws IllegalArgumentException {
      if (health > 0) {
        this.health = health;
        return this;
      } else {
        throw new IllegalArgumentException("Health can not be zero or lower.");
      }
    }

    /**
     * Set the gold of the player.
     *
     * @param gold the gold of the player
     * @return the player builder object
     */
    public PlayerBuilder setGold(int gold) throws IllegalArgumentException {
      if (gold >= 0) {
        this.gold = gold;
        return this;
      } else {
        throw new IllegalArgumentException("You cannot have negative gold.");
      }
    }

    /**
     * Set the score of the player.
     *
     * @param score the score of the player
     * @return the player builder object
     */
    public PlayerBuilder setScore(int score) throws IllegalArgumentException {
      if (score >= 0) {
        this.score = score;
        return this;
      } else {
        throw new IllegalArgumentException("You cannot have negative score.");
      }
    }

    /**
     * Set the inventory of the player.
     *
     * @param items the items of the player
     * @return the player builder object
     */
    public PlayerBuilder setInventory(List<String> items) {
      Objects.requireNonNull(items, "Inventory cannot be null.");
      inventory.addAll(items);
      return this;
    }

    /**
     * Set an item of the player.
     *
     * @param item the items of the player
     * @return the player builder object
     */
    public PlayerBuilder setItem(String item) throws IllegalArgumentException {
      Objects.requireNonNull(item, "item cannot be null.");
      if (!item.isBlank()) {
        inventory.add(item);
        return this;
      } else {
        throw new IllegalArgumentException("item cannot be empty.");
      }
    }

    /**
     * Method to build a player object.
     *
     * @return the player object
     */
    public Player build() {
      Player player = new Player();
      player.name = this.name;
      player.health = this.health;
      player.score = this.score;
      player.gold = this.gold;
      player.inventory = this.inventory;

      return player;
    }
  }

  /**
   * Getter method for the players name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Getter method for the player health.
   *
   * @return health
   */
  public int getHealth() {
    return health;
  }

  /**
   * Getter method for the players score.
   *
   * @return score
   */
  public int getScore() {
    return score;
  }

  /**
   * Getter method for the players gold.
   *
   * @return gold
   */
  public int getGold() {
    return gold;
  }

  /**
   * Getter method for the players inventory.
   * Returns list of strings of all the items.
   *
   * @return inventory
   */
  public Iterator<String> getInventory() {
    return inventory.iterator();
  }

  /**
   * Mutator method for adding health.
   * Adds a number to the player's health.
   * The added health number can be positive or negative.
   * If health becomes negative, it is set to 0 (player dies).
   *
   * @param health int - positive or negative number to add to the player's health
   */
  public void addHealth(int health) throws IllegalArgumentException {
    int oldValue = this.health;
    this.health += health;
    if (this.health <= 0) {
      this.health = 0;
      logger.info(
          "The player add health method wants to set the player health to a "
              + "negative number, the health is sett to 0 instead.");
      throw new IllegalStateException(name + " died and can't continue the game!");
    }
    playerSupport.firePropertyChange("HEALTH", oldValue, this.health);
  }

  /**
   * Mutator method for adding score.
   * The new score is the old score plus points.
   *
   * @param points the points that are added
   */
  public void addScore(int points) throws IllegalArgumentException {
    int oldValue = this.score;
    this.score += points;
    if (this.score < 0) {
      this.score = 0;
      logger.info(
          "The player add score method wants to set the player score to "
              + "a negative number, the score is sett to 0 instead.");
      throw new IllegalStateException(name + " does not have enough points to continue!");
    }
    playerSupport.firePropertyChange("SCORE", oldValue, this.score);
  }

  /**
   * Mutator method for adding gold.
   * The added gold number can be positive or negative.
   * If the amount of becomes negative, it is set to 0 (player is broke).
   *
   * @param gold int - positive or negative number to add to the player's gold
   */
  public void addGold(int gold) throws IllegalArgumentException {
    int oldValue = this.gold;
    this.gold += gold;
    if (this.gold < 0) {
      this.gold = 0;
      logger.info(
          "The player add gold method wants to set the player gold to "
              + "a negative number, the gold is sett to 0 instead.");
      throw new IllegalStateException(name + " does not have enough gold to continue!");
    }
    playerSupport.firePropertyChange("GOLD", oldValue, this.gold);
  }

  /**
   * Adds an item to the player's inventory.
   * The inventory is stored in lower case.
   *
   * @param item an item to add to the inventory
   * @throws IllegalArgumentException thrown if item is null or blank
   */
  public void addToInventory(String item) throws IllegalArgumentException {
    if (item == null || item.isBlank()) {
      logger.warning(
          "The player add to inventory method did not add anything to "
              + "inventory as it was nothing to add.");
      throw new IllegalArgumentException(name + " can not pick up a nonexistent item.");
    } else {
      inventory.add(item.trim().toLowerCase());
      playerSupport.firePropertyChange("INVENTORY", inventory, item);
    }
  }
}
