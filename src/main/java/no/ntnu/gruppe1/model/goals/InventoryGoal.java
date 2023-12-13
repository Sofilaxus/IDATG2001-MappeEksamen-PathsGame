package no.ntnu.gruppe1.model.goals;

import java.util.Iterator;
import java.util.logging.Logger;
import no.ntnu.gruppe1.model.player.Player;

/**
 * An Inventory Goal represents items the player need to complete the quest.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 2023.02.21
 */
public class InventoryGoal implements Goal<String> {

  //Fields
  private final String mandatoryItem;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor for InventoryGoal.
   *
   * @param mandatoryItem the mandatory items.
   */
  protected InventoryGoal(String mandatoryItem) {
    if (mandatoryItem == null || mandatoryItem.isBlank()) {
      logger.warning("Inventory Goal not created do to being null or blank");
      throw new IllegalArgumentException("inventory goal can not be blank");
    } else {
      this.mandatoryItem = mandatoryItem;
    }
  }

  /**
   * Method to check if the desired items have been gathered.
   *
   * @param player the players items to be tested.
   * @return true if the player has the right items.
   */
  @Override
  public boolean isFulfilled(Player player) {
    Iterator<String> inventoryIterator = player.getInventory();
    while (inventoryIterator.hasNext()) {
      if (inventoryIterator.next().equalsIgnoreCase(mandatoryItem)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the inventory goal.
   *
   * @return the mandatory items to have for goal fulfillment
   */
  @Override
  public String getFulfillmentCriteria() {
    return mandatoryItem;
  }
}
