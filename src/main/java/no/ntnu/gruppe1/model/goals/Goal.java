package no.ntnu.gruppe1.model.goals;

import no.ntnu.gruppe1.model.player.Player;

/**
 * A Goal represents a target value or a desired result related to the player's condition.
 * While actions change the state of the player along the way,
 * goals make it possible to check whether the player has achieved the expected result.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 2023.02.20
 */
public interface Goal<T> {

  /**
   * Checks if a player has fulfilled a goal.
   * Does not modify the players attributes.
   *
   * @param player Player - the player to check the goal with.
   * @throws NullPointerException thrown if player is null
   */
  boolean isFulfilled(Player player) throws NullPointerException;

  /**
   * Get the goal's fulfillment criteria.
   *
   * @return T - the goal's fulfillment criteria
   */
  T getFulfillmentCriteria();

}
