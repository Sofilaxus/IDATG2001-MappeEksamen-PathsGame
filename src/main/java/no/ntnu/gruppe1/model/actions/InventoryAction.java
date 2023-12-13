package no.ntnu.gruppe1.model.actions;

import java.util.Objects;
import java.util.logging.Logger;
import no.ntnu.gruppe1.model.player.Player;

/**
 * Responsible for adding an item to the player's inventory.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.02.22
 */
public class InventoryAction implements Action<String> {

  //Fields
  private final String item;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor that adds an item in the player's inventory.
   *
   * @param item the item to be added in the inventory.
   * @throws IllegalArgumentException if the item is null or empty, the action will do nothing.
   */
  protected InventoryAction(String item) throws IllegalArgumentException {
    if (item == null || item.isBlank()) {
      logger.info("Inventory Action not created do to having an empty String");
      throw new IllegalArgumentException("This action does nothing.");
    } else {
      this.item = item;
    }
  }

  /**
   * Executes an action on a player.
   * Calls the addToInventory method on the player with an item.
   *
   * @param player the player to execute the action on
   */
  @Override
  public void execute(final Player player) {
    Objects.requireNonNull(player, "action needs a player to execute");
    player.addToInventory(item);
  }

  /**
   * Get method for the action's value.
   *
   * @return String - the item of the action
   */
  @Override
  public String getValue() {
    return item;
  }

}
