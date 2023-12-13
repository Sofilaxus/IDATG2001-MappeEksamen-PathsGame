package no.ntnu.gruppe1.view;

/**
 * A class that represents an option in a ChoiceBox.
 *
 * @param <T> the value type that the choice box should contain
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.05.05
 */
public class ChoiceBoxes<T> {

  //Fields
  private final String display;
  private final T value;

  /**
   * Creates a new ChoiceBoxOption with the given display string and value.
   *
   * @param display the display of the option
   * @param value the value of the option
   */
  public ChoiceBoxes(String display, T value) {
    this.display = display;
    this.value = value;
  }

  /**
   * Returns the display string of the option.
   * Shown in the choice box GUI.
   *
   * @return the display string
   */
  public String getDisplay() {
    return display;
  }

  /**
   * Returns the value of the option.
   *
   * @return the value
   */
  public T getValue() {
    return value;
  }

  /**
   * Returns the display string of the option.
   *
   * @return the display string
   */
  @Override
  public String toString() {
    return display;
  }
}
