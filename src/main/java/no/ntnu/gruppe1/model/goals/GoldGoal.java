package no.ntnu.gruppe1.model.goals;

import java.util.logging.Logger;
import no.ntnu.gruppe1.model.player.Player;

/**
 * A Gold Goal represents a target value related to the player's Gold.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 2023.02.21
 */
public class GoldGoal implements Goal<Integer> {

  //Fields
  private final int minimumGold; // the lowest amount of gold needed to complete the goal.
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor for GoldGoal.
   *
   * @param minimumGold the minimum gold.
   * @throws IllegalArgumentException when goal value is negative, a state the player cannot be in.
   */
  protected GoldGoal(int minimumGold) throws IllegalArgumentException {
    if (minimumGold >= 0) {
      this.minimumGold = minimumGold;
    } else {
      logger.warning("Gold Goal not created do to being lower than 0 and unreachable");
      throw new IllegalArgumentException(
          "The goal value can not be negative, the player can not reach it");
    }
  }

  /**
   * Method to check if the desired gold amount has been reach.
   *
   * @param player the players gold to be tested.
   * @return true if the player has enough gold.
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getGold() >= minimumGold;
  }


  /**
   * Get the gold goal.
   *
   * @return the gold goal
   */
  @Override
  public Integer getFulfillmentCriteria() {
    return minimumGold;
  }
}
