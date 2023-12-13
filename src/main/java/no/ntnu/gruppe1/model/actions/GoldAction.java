package no.ntnu.gruppe1.model.actions;

import java.util.Objects;
import java.util.logging.Logger;
import no.ntnu.gruppe1.model.player.Player;

/**
 * Responsible for the player's finances.
 * Adds or removes gold from the player's wallet.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.02.22
 */
public class GoldAction implements Action<Integer> {

  //Fields
  private final int gold;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor for the gold action.
   *
   * @param gold the gold.
   * @throws IllegalArgumentException if the gold is 0 and the action will do nothing.
   */
  protected GoldAction(int gold) throws IllegalArgumentException {
    if (gold != 0) {
      this.gold = gold;
    } else {
      logger.info("Gold Action not created do to having 0 as value, which does nothing.");
      throw new IllegalArgumentException("This action does nothing");
    }
  }

  /**
   * This method is used to change the players gold amount.
   *
   * @param player the player to change gold for.
   */
  @Override
  public void execute(Player player) {
    Objects.requireNonNull(player, "action needs a player to execute");
    player.addGold(gold);
  }

  /**
   * Get method for the action's value.
   *
   * @return Integer - the gold of the action
   */
  @Override
  public Integer getValue() {
    return gold;
  }
}
