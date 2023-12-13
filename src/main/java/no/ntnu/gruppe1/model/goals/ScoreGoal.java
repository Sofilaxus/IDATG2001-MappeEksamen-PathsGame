package no.ntnu.gruppe1.model.goals;

import java.util.logging.Logger;
import no.ntnu.gruppe1.model.player.Player;

/**
 * A Score Goal represents a target value related to the player's Points.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 2023.04.19
 */
public class ScoreGoal implements Goal<Integer> {

  //Fields
  private final int minimumPoints;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor for ScoreGoal.
   *
   * @param minimumPoints the minimum score the player should have.
   * @throws IllegalArgumentException when goal value is negative, a state the player cannot be in.
   */
  protected ScoreGoal(int minimumPoints) throws IllegalArgumentException {
    if (minimumPoints >= 0) {
      this.minimumPoints = minimumPoints;
    } else {
      logger.warning("Score Goal not created do to being lower than 0 and unreachable");
      throw new IllegalArgumentException(
          "The goal value can not be negative, the player can not reach it");
    }
  }

  /**
   * Method to check if the desired score has been reach.
   *
   * @param player the players score to be tested.
   * @return true if the player has enough points.
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getScore() >= minimumPoints;
  }

  /**
   * Get the score goal's minimum score criteria.
   *
   * @return Integer - the minimum score criteria
   */
  @Override
  public Integer getFulfillmentCriteria() {
    return minimumPoints;
  }
}
