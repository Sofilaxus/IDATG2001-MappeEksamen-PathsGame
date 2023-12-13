package no.ntnu.gruppe1.model.goals;

import java.util.logging.Logger;
import no.ntnu.gruppe1.model.player.Player;

/**
 * A Health Goal represents a target value related to the player's health.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 2023.02.21
 */
public class HealthGoal implements Goal<Integer> {

  //Fields
  private final int minimumHealth;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor for HealthGoal.
   *
   * @param minimumHealth the minimum health the player should have.
   * @throws IllegalArgumentException when goal value is negative, a state the player cannot be in.
   */
  protected HealthGoal(int minimumHealth) throws IllegalArgumentException {
    if (minimumHealth >= 0) {
      this.minimumHealth = minimumHealth;
    } else {
      logger.warning("Health Goal not created do to being lower than 0 and unreachable");
      throw new IllegalArgumentException(
          "The goal value can not be negative, the player can not reach it");
    }
  }

  /**
   * Method to check if the player has the desired health amount.
   *
   * @param player the players health to be tested.
   * @return true if the player has enough health.
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getHealth() >= minimumHealth;
  }

  /**
   * Get the goal.
   *
   * @return the minimum health goal
   */
  @Override
  public Integer getFulfillmentCriteria() {
    return minimumHealth;
  }
}
