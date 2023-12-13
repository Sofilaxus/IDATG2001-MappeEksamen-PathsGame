package no.ntnu.gruppe1.model.actions;

import java.util.Objects;
import java.util.logging.Logger;
import no.ntnu.gruppe1.model.player.Player;

/**
 * Responsible for the player's score.
 * Adds or removes points form the score.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.02.22
 */
public class ScoreAction implements Action<Integer> {

  //Fields
  private final int points;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor for the score action.
   *
   * @param points the points.
   * @throws IllegalArgumentException if the points is 0 and the action will do nothing.
   */
  protected ScoreAction(int points) throws IllegalArgumentException {
    if (points != 0) {
      this.points = points;
    } else {
      logger.info("Score Action not created do to having 0 as value, which does nothing.");
      throw new IllegalArgumentException("This action does nothing");
    }
  }

  /**
   * Method to add points to the player.
   *
   * @param player how gets the points.
   */
  @Override
  public void execute(Player player) {
    Objects.requireNonNull(player, "action needs a player to execute");
    player.addScore(points);
  }

  /**
   * Get method for the action's value.
   *
   * @return Integer - the points of the action
   */
  @Override
  public Integer getValue() {
    return points;
  }
}
