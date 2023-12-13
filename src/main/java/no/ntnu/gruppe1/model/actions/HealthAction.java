package no.ntnu.gruppe1.model.actions;

import java.util.Objects;
import java.util.logging.Logger;
import no.ntnu.gruppe1.model.player.Player;

/**
 * Responsible for the player's health.
 * Adds or removes health.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.02.22
 */
public class HealthAction implements Action<Integer> {

  //Fields
  private final int health;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor for the health action.
   *
   * @param health the health.
   * @throws IllegalArgumentException if the health is 0 and the action will do nothing.
   */
  protected HealthAction(int health) throws IllegalArgumentException {
    if (health != 0) {
      this.health = health;
    } else {
      logger.info("Health Action not created do to having 0 as value, which does nothing.");
      throw new IllegalArgumentException("This action does nothing");
    }
  }

  /**
   * This method is used to change the player health.
   *
   * @param player to change Health
   */
  @Override
  public void execute(Player player) {
    Objects.requireNonNull(player, "action needs a player to execute");
    player.addHealth(health);
  }

  /**
   * Get method for the action's value.
   *
   * @return Integer - the health points of the action
   */
  @Override
  public Integer getValue() {
    return health;
  }
}
