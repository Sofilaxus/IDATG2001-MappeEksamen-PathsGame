package no.ntnu.gruppe1.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * A passage is a smaller part of a story, a paragraph if you will.
 * It is possible to go from one passage to another via a link.
 * The Passage class has three attributes:
 * <ul>
 * <li><code>title</code> - An overall description that also acts as an identifier.
 * </li>
 * <li><code>content</code> - Textual content that typically represents a
 * paragraph or part of a dialogue.
 * </li>
 * <li><code>links</code> - Links that connect this passage to other passages. A passage with two or
 * multiple links make the story non-linear.
 * </li>
 * </ul>
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @author 2023.02.16
 */
public class Passage {

  // Fields
  private String title;
  private String content;
  private List<Link> links;
  private static final Logger logger = Logger.getLogger("logger");

  /**
   * The constructor for Passage.
   * Creates a title and content for tha passage.
   */
  private Passage() {
  }

  /**
   * Internal Passage Builder for crating Passage objects.
   */
  public static class PassageBuilder {
    private String title;
    private String content;
    private final List<Link> links;

    /**
     * Constructor for PassageBuilder.
     * Initialises the Links Arraylist.
     */
    public PassageBuilder() {
      links = new ArrayList<>();
    }

    /**
     * Set the title of the passage.
     *
     * @param title of the passage.
     * @return the passage builder object.
     */
    public PassageBuilder setTitle(String title) throws IllegalArgumentException {
      if (title == null || title.isBlank()) {
        logger.warning("Passage not created do to missing title");
        throw new IllegalArgumentException("Title of passage can not be blank");
      } else {
        this.title = title;
        return this;
      }
    }

    /**
     * Set the content of the passage.
     *
     * @param content the text in the passage.
     * @return the passage builder object.
     */
    public PassageBuilder setContent(String content) throws IllegalArgumentException {
      if (content == null || content.isBlank()) {
        logger.warning("Passage not created do to missing content");
        throw new IllegalArgumentException("Content of passage can not be blank");
      } else {
        this.content = content;
        return this;
      }
    }

    /**
     * Set a link in the passage.
     *
     * @param link a link in the passage
     * @return the passage builder object
     */
    public PassageBuilder setLink(Link link) throws IllegalArgumentException {
      Objects.requireNonNull(link, "Link not added to passage do to being null");
      links.add(link);
      return this;
    }

    /**
     * Method to build a passage object.
     *
     * @return the passage object
     */
    public Passage build() {
      Passage passage = new Passage();
      passage.title = this.title;
      passage.content = this.content;
      passage.links = this.links;

      return passage;
    }
  }

  /**
   * Getter method for title.
   * An overall description that also acts as an identifier.
   *
   * @return the title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Getter method for content.
   * Text that typically represents a paragraph or part of a dialogue.
   *
   * @return the content.
   */
  public String getContent() {
    return content;
  }

  /**
   * Getter method for the list of links.
   *
   * @return links list of all Link objects
   */
  public Iterator<Link> getLinks() {
    return links.iterator();
  }

  /**
   * Adds a Link to the links List.
   * Can be used to creat hidden rooms in passages.
   *
   * @param link to add
   * @return true is link is added
   */
  public boolean addLink(Link link) {
    Objects.requireNonNull(link, "Link not added to passage do to being null");
    return links.add(link);
  }

  /**
   * Method for finding if a specific Link object is in the links list.
   *
   * @param link to find
   * @return true is link is in the links List
   */
  public boolean hasLinks(Link link) {
    Objects.requireNonNull(link, "Link not found in passage do to being null");
    return links.contains(link);
  }

  /**
   * Override hashCode() method from Object class.
   * To not include sertan parts.
   *
   * @return int hashCode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(title, content);
  }

  /**
   * Override the equals() method from Object class.
   *
   * @param obj an object.
   * @return true if obj is equal.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Passage passage = (Passage) obj;
    return
        Objects.equals(title, passage.title)
            && Objects.equals(content, passage.content);
  }

  /**
   * Override toString() method form Object class.
   *
   * @return String from object.
   */
  @Override
  public String toString() {
    return "title: " + title + ". text: " + content + ". links" + links.toString();
  }
}