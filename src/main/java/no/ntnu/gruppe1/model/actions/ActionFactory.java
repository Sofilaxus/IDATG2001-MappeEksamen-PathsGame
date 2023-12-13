package no.ntnu.gruppe1.model.actions;

/**
 * Class for creating an action object.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 2023.03.13
 */

public class ActionFactory {

  private static volatile ActionFactory actionFactory;

  /**
   * The constructor.
   */
  private ActionFactory() {
  }

  /**
   * Returns the ActionFactory object.
   * If no ActionFactory exists, the method creates one.
   *
   * @return the GoalFactory object
   */
  public static ActionFactory getActionFactory() {
    if (actionFactory == null) {
      synchronized (ActionFactory.class) {
        actionFactory = new ActionFactory();
      }
    }
    return actionFactory;
  }

  /**
   * Constructor for Action objects.
   *
   * @param actionType the type of object to make.
   * @param actionValue the value of the object.
   * @return Action object created.
   */
  public Action<?> createAction(String actionType, String actionValue) {
    switch (actionType) {
      case "gold" -> {
        try {
          return new GoldAction(Integer.parseInt(actionValue));
        } catch (IllegalArgumentException nfe) {
          throw new IllegalArgumentException("value of gold Action invalid " + nfe.getMessage());
        }
      }
      case "health" -> {
        try {
          return new HealthAction(Integer.parseInt(actionValue));
        } catch (IllegalArgumentException nfe) {
          throw new IllegalArgumentException("value of health Action invalid " + nfe.getMessage());
        }
      }
      case "item" -> {
        try {
          return new InventoryAction(actionValue);
        } catch (IllegalArgumentException nfe) {
          throw new IllegalArgumentException(
              "value of inventory Action invalid " + nfe.getMessage());
        }
      }
      case "points" -> {
        try {
          return new ScoreAction(Integer.parseInt(actionValue));
        } catch (IllegalArgumentException nfe) {
          throw new IllegalArgumentException("value of score Action invalid " + nfe.getMessage());
        }
      }
      default -> {
        throw new IllegalArgumentException("action type not recognised");
      }
    }
  }
}
