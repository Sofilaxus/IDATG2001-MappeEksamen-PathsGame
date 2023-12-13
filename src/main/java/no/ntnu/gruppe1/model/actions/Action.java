package no.ntnu.gruppe1.model.actions;

import no.ntnu.gruppe1.model.player.Player;

/**
 * An Action represents something the player can do.
 * When an action is done the state of the player changes.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 2023.02.20
 */
public interface Action<T> {
  /**
   * Executes an action on a player.
   * Modifies the player's attributes.
   *
   * @param player Player - the player to execute the action on
   * @throws NullPointerException thrown if player is null
   * @throws IllegalArgumentException if the acton results in an illegal state for the player.
   */
  void execute(Player player) throws NullPointerException, IllegalArgumentException;

  /**
   * Get method for the action's value.
   *
   * @return T - the value of the action
   */
  T getValue();

}
