package no.ntnu.gruppe1.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import no.ntnu.gruppe1.model.actions.Action;

/**
 * A link makes it possible to go from one passage to another.
 * Links bind together the different parts of a story.
 * There are three attributes in Link:
 * <ul>
 * <li><code>text</code> - A descriptive text that indicates a choice or an action in a story.
 * The text is the part of the link that will be visible to the player.
 * </li>
 * <li><code>reference</code> - A string that uniquely identifies a passage (part of a story).
 * In practice this will be the title of the passage you wish to refer to.
 * </li>
 * <li><code>actions</code> - A list of special objects that make it possible
 * to influence the characteristics of a player.
 * </li>
 * </ul>
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.02.16
 */
public class Link {

  // Fields
  private String text;
  private String reference;
  private List<Action<?>> actions;
  private static final Logger logger = Logger.getLogger("logger");

  /**
   * The constructor of a Link.
   * Is created with text for the player and a reference that connects to a passage.
   */
  private Link() {
  }

  /**
   * Internal LinkBuilder for constructing link objects.
   */
  public static class LinkBuilder {
    private String text;
    private String reference;
    private final List<Action<?>> actions;

    /**
     * Constructor for linkBuilder.
     * Initialises the actions arraylist
     */
    public LinkBuilder() {
      actions = new ArrayList<>();
    }

    /**
     * Set the text of the link.
     *
     * @param text of the link.
     * @return the link builder object.
     */
    public LinkBuilder setText(String text) throws IllegalArgumentException {
      if (text == null || text.isBlank()) {
        logger.warning("Link not created do to missing text");
        throw new IllegalArgumentException("Text of link can not be blank");
      } else {
        this.text = text;
        return this;
      }
    }

    /**
     * Set the reference of the link.
     *
     * @param reference what passage the link references to.
     * @return the link builder object.
     */
    public LinkBuilder setReference(String reference) throws IllegalArgumentException {
      if (reference == null || reference.isBlank()) {
        logger.warning("Link not created do to missing reference");
        throw new IllegalArgumentException("Reference of link can not be blank");
      } else {
        this.reference = reference;
        return this;
      }
    }

    /**
     * Set an action in the link.
     *
     * @param action an action in the link
     * @return the link builder object
     */
    public LinkBuilder setAction(Action<?> action) {
      Objects.requireNonNull(action, "Action Not added do to being null");
      actions.add(action);
      return this;
    }

    /**
     * Method to build a link object.
     *
     * @return the link object
     */
    public Link build() {
      Link link = new Link();
      link.reference = this.reference;
      link.text = this.text;
      link.actions = this.actions;

      return link;
    }
  }

  /**
   * Getter method for text.
   * A descriptive text that indicates a choice or an action in a story.
   * The text is the part of the link that will be visible to the player
   *
   * @return the text.
   */
  public String getText() {
    return text;
  }

  /**
   * Getter method for reference.
   * A string that uniquely identifies a passage (part of a story).
   *
   * @return the reference.
   */
  public String getReference() {
    return reference;
  }

  /**
   * Getter method for actions.
   * A list of special objects that make it possible to influence the characteristics
   * of a player.
   * The actions that can be done.
   *
   * @return actions.
   */
  public Iterator<Action<?>> getActions() {
    return actions.iterator();
  }

  /**
   * Adds an Action to the end of the actions list.
   *
   * @param action The action to add
   */
  public boolean addAction(Action<?> action) throws NullPointerException {
    Objects.requireNonNull(action, "Action Not added do to being null");
    return this.actions.add(action);
  }


  /**
   * Override the equals() method from Object class.
   *
   * @param o an object.
   * @return true if obj i equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    Link link = (Link) o;
    return Objects.equals(this.reference, link.reference);
  }

  /**
   * Override hashCode() method from Object class.
   *
   * @return int hashCode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(reference);
  }

  /**
   * Override toString() method form Object class.
   *
   * @return String from object.
   */
  @Override
  public String toString() {
    return "Text: " + text + ". Ref: " + reference + ". Actions: " + actions;
  }
}

