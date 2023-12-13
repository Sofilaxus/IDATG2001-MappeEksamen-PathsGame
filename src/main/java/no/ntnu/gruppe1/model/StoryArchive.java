package no.ntnu.gruppe1.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Represents an archive for stories.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.04.14
 */
public class StoryArchive {

  //Fields
  private final List<Story> storyList;
  private static volatile StoryArchive storyArchive;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * Constructor for StoryArchive.
   */
  private StoryArchive() {
    storyList = new ArrayList<>();
  }

  /**
   * Get the story archive. If there is no story archive, it is created.
   *
   * @return the story archive
   */
  public static StoryArchive getStoryArchive() {
    if (storyArchive == null) {
      synchronized (StoryArchive.class) {
        storyArchive = new StoryArchive();
      }
    }
    return storyArchive;
  }

  /**
   * Adds a Story to the list of stories.
   *
   * @param story the Story to be added to the list
   */
  public void addStoryToArchive(Story story) throws IllegalArgumentException {
    Objects.requireNonNull(story, "Story can not be null");
    if (storyList.stream().anyMatch(s -> s.getTitle().equals(story.getTitle()))) {
      logger.warning("StoryArchive tried to add an existing story, it was not added");
      throw new IllegalArgumentException("Story already exist");
    } else {
      storyList.add(story);
    }
  }

  /**
   * Deletes a story from the list.
   *
   * @param story the Story to be deleted from the list
   * @throws IllegalArgumentException if the story does not exist
   */
  public void deleteStoryFromArchive(Story story) throws IllegalArgumentException {
    Objects.requireNonNull(story, "Story can not be null");
    if (storyList.contains(story)) {
      storyList.remove(story);
    } else {
      logger.warning("StoryArchive could not delete story as it does not exist");
      throw new NoSuchElementException("The story " + story.getTitle() + " does not exist!");
    }
  }

  /**
   * Returns the story list.
   *
   * @return Iterator with stories
   */
  public Iterator<Story> getStoriesFromArchive() {
    return storyList.iterator();
  }
}