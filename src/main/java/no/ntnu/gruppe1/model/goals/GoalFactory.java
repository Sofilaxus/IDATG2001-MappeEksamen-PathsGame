package no.ntnu.gruppe1.model.goals;

/**
 * Factory for goals.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.05.22
 */
public class GoalFactory {

  private static volatile GoalFactory actionFactory;

  /**
   * The constructor.
   */
  private GoalFactory() {
  }

  /**
   * Returns the GoalFactory object.
   * If no GoalFactory exists, the method creates one.
   *
   * @return the GoalFactory object
   */
  public static GoalFactory getGoalFactory() {
    if (actionFactory == null) {
      synchronized (GoalFactory.class) {
        actionFactory = new GoalFactory();
      }
    }
    return actionFactory;
  }

  /**
   * Constructor for Goal objects.
   *
   * @param goalType the type of object to make.
   * @param goalValue the value of the object.
   * @return Action object created.
   */
  public Goal<?> createGoal(String goalType, String goalValue) {
    switch (goalType) {
      case "GoldGoal" -> {
        try {
          return new GoldGoal(Integer.parseInt(goalValue));
        } catch (IllegalArgumentException nfe) {
          throw new IllegalArgumentException("value of gold Goal invalid " + nfe.getMessage());
        }
      }
      case "HealthGoal" -> {
        try {
          return new HealthGoal(Integer.parseInt(goalValue));
        } catch (IllegalArgumentException nfe) {
          throw new IllegalArgumentException("value of health Goal invalid " + nfe.getMessage());
        }
      }
      case "InventoryGoal" -> {
        try {
          return new InventoryGoal(goalValue);
        } catch (IllegalArgumentException nfe) {
          throw new IllegalArgumentException(
              "value of inventory Goal invalid " + nfe.getMessage());
        }
      }
      case "ScoreGoal" -> {
        try {
          return new ScoreGoal(Integer.parseInt(goalValue));
        } catch (IllegalArgumentException nfe) {
          throw new IllegalArgumentException("value of score Goal invalid " + nfe.getMessage());
        }
      }
      default -> throw new IllegalArgumentException("Goal type not recognised " + goalType);
    }
  }
}
