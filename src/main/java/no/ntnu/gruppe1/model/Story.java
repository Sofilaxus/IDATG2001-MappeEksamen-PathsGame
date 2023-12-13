package no.ntnu.gruppe1.model;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A story is an interactive, non-linear narrative consisting of a collection of passages.
 * Story has three attributes:
 * <ul>
 * <li><code>title</code> - The title of the story.
 * </li>
 * <li><code>passages</code> - A Map containing the story's passages.
 * The key to each passage is a link.
 * </li>
 * <li><code>openingPassage</code> - The first passage in the story.
 * The object must also be added in passages.
 * </li>
 * </ul>
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.02.21
 */
public class Story {

  //Fields
  private String title;
  private final Map<Link, Passage> passages;
  private Passage openingPassage;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * A Constructor for a Story with only title and opening passage as parameters.
   * Title can not be blank or null and passages can not be null, but can be empty.
   *
   * @param title          title of the story.
   * @param openingPassage The first passage in the Story.
   * @throws IllegalArgumentException if the title is blank or null.
   * @throws IllegalArgumentException if the opening passage is null.
   */
  public Story(String title, Passage openingPassage) {
    setTitle(title);
    setOpeningPassage(openingPassage);
    passages = new HashMap<>();
    passages.put(new Link.LinkBuilder()
            .setReference(openingPassage.getTitle())
            .build(),
        openingPassage);
  }

  /**
   * Method for setting or changing the title of a story.
   *
   * @param title of the story
   * @throws IllegalArgumentException if the title is blank
   */
  public void setTitle(String title) throws IllegalArgumentException {
    if (title == null || title.isBlank()) {
      logger.warning("The Stories tittle can not be changed or sett because the title was blank");
      throw new IllegalArgumentException("Title of story can not be blank or null");
    } else {
      this.title = title;
    }
  }

  /**
   * Method for setting or changing the opening passage of a story.
   *
   * @param openingPassage first passage of the story
   * @throws NullPointerException if the passage is null
   */
  public void setOpeningPassage(Passage openingPassage) throws NullPointerException {
    Objects.requireNonNull(openingPassage, "Opening passage cannot be null");
    this.openingPassage = openingPassage;
  }

  /**
   * Getter method for the title of the story.
   *
   * @return title of the story
   */
  public String getTitle() {
    return title;
  }

  /**
   * getter method for the first passage in the story.
   *
   * @return openingPassage of the story
   */
  public Passage getOpeningPassage() {
    return openingPassage;
  }

  /**
   * Add a passage to passages map based on passage.
   * A link is created with the title of the passage as the link text and reference.
   * The text of the link should be edited.
   * Can not add a passage which is null or already exists in the map.
   *
   * @param passage Passage - non-null passage to add to the story
   * @throws IllegalArgumentException if the passage already exists in the map
   */
  public void addPassage(Passage passage) throws IllegalArgumentException {
    Objects.requireNonNull(passage, "Passage can not be null.");
    if (passages.containsValue(passage)) {
      logger.warning("Passage not added do to an identical passage already existing");
      throw new IllegalArgumentException("Passage already exists in the story");
    } else {
      Link link = new Link.LinkBuilder()
          .setReference(passage.getTitle())
          .build();
      passages.put(link, passage);
    }
  }

  /**
   * Getter method for passage based on link.
   *
   * @param link The link of the passage that will be returned.
   * @return passage that corresponds to the link.
   * @throws IllegalArgumentException if the link is null.
   */
  public Passage getPassage(Link link) {
    Objects.requireNonNull(link, "Link can not be null.");
    if (passages.containsKey(link)) {
      return this.passages.get(link);
    } else {
      logger.warning("Story get Passage looked for passage: "
          + link.getReference() + " in story: " + getTitle() + ". But it was not found.");
      throw new NoSuchElementException(
          "Passage " + link.getReference() + " was not found in the story.");
    }
  }

  /**
   * Method for removing a passage based on link.
   *
   * @param link The link of the passage that will be deleted.
   * @throws IllegalArgumentException if the link is null.
   */
  public void removePassage(Link link) throws IllegalArgumentException, NoSuchElementException {
    Objects.requireNonNull(link, "link can not be null.");
    if (!passages.containsKey(link)) {
      logger.warning("Story remove Passage looked for passage: "
          + link.getReference() + " in story: " + getTitle() + ". But it was not found.");
      throw new NoSuchElementException(
          "Passade " + link.getReference() + " was not found in the story.");
    } else if (passages.get(link).equals(openingPassage)) {
      logger.warning("story remove passage tried to remove opening passage, "
          + "it was not allowed");
      throw new IllegalArgumentException("You cannot remove the opening passage");
    } else {
      passages.remove(link);
    }
  }

  /**
   * Method for getting the links that do not go anywhere.
   *
   * @return List of broken links
   */
  public List<String> getBrokenLinks() {
    List<String> titles = passages.values().stream().map(Passage::getTitle).toList();
    List<String> links = new ArrayList<>();
    for (Passage passage : passages.values()) {
      passage.getLinks().forEachRemaining(
          link -> links.add(link.getReference())
      );
    }

    return links.stream()
        .filter(e -> !titles.contains(e))
        .collect(Collectors.toList());
  }

  /**
   * Method for getting passages that do not have a link to it.
   *
   * @return List of passages you cant reach
   */
  public List<String> getUnreachablePassages() {
    List<String> titles =
        new ArrayList<>(passages.values().stream().map(Passage::getTitle).toList());
    List<String> links = new ArrayList<>();
    for (Passage passage : passages.values()) {
      passage.getLinks().forEachRemaining(
          link -> links.add(link.getReference())
      );
    }
    titles.removeIf(p -> p.equals(openingPassage.getTitle()));

    return titles.stream()
        .filter(e -> !links.contains(e))
        .collect(Collectors.toList());
  }

  /**
   * Getter method for the entire passages map.
   *
   * @return all passages in the story
   */
  public Iterator<Passage> getPassages() {
    return this.passages.values().iterator();
  }
}
