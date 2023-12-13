package no.ntnu.gruppe1.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoryArchiveTest {
  private final StoryArchive archive = StoryArchive.getStoryArchive();
  private Story testStory;
  private Passage openingPassage;

  @BeforeEach
  void setUp() {
    openingPassage = new Passage.PassageBuilder()
        .setTitle("Beginnings")
        .setContent(
            "You are in a small, dimly lit room. There is a door in front of you.")
        .setLink(new Link.LinkBuilder()
            .setText("Try to open the door")
            .setReference("Another room")
            .setAction(ActionFactory.getActionFactory()
                .createAction("health", "-2"))
            .build())
        .build();

    Passage passage = new Passage.PassageBuilder()
        .setTitle("Another room")
        .setContent(
            "The door opens to another room.You see a desk with a large, dusty book.")
        .setLink(new Link.LinkBuilder()
            .setText("Open the book")
            .setReference("The book of spells")
            .setAction(ActionFactory.getActionFactory().createAction("item", "book"))
            .setAction(ActionFactory.getActionFactory().createAction("points", "5"))
            .setAction(ActionFactory.getActionFactory().createAction("gold", "2"))
            .build())
        .setLink(new Link.LinkBuilder()
            .setText("Go back")
            .setReference("Beginnings")
            .build())
        .build();

    testStory = new Story("Haunted House", openingPassage);
    testStory.addPassage(passage);
    archive.addStoryToArchive(testStory);
  }

  @AfterEach
  void TearDown() {
    if (findStoryInArchive(testStory)) {
      archive.deleteStoryFromArchive(testStory);
    }
  }

  @Test
  void getStoryArchive() {
    assertEquals(archive, StoryArchive.getStoryArchive());
  }

  @Test
  void getStoriesFromArchive() {
    assertTrue(findStoryInArchive(testStory));
  }

  @Test
  void addStoryToArchive() {
    Story newStory = new Story("New Story", openingPassage);
    archive.addStoryToArchive(newStory);
    assertTrue(findStoryInArchive(newStory));
    archive.deleteStoryFromArchive(newStory);

  }
  /**
   * method for finding story in archive
   *
   * @param story  to test
   * @return if it is found
   */
  boolean findStoryInArchive(Story story) {
    Iterator<Story> storyIterator = archive.getStoriesFromArchive();
    while (storyIterator.hasNext()) {
      if (storyIterator.next().equals(story)) {
        return true;
      }
    }
    return false;
  }

  @Test
  void addStoryToArchiveThrows() {
    NullPointerException addNull =
        assertThrows(NullPointerException.class, () -> {
          archive.addStoryToArchive(null);
        });
    assertEquals("Story can not be null", addNull.getMessage());

    IllegalArgumentException exist =
        assertThrows(IllegalArgumentException.class, () -> {
          archive.addStoryToArchive(new Story("Haunted House", openingPassage));
        });
    assertEquals("Story already exist", exist.getMessage());
  }

  @Test
  void deleteStoryFromArchive() {
    archive.deleteStoryFromArchive(testStory);
    assertFalse(findStoryInArchive(testStory));

  }

  @Test
  void deleteStoryInArchiveThrows() {
    NullPointerException addNull =
        assertThrows(NullPointerException.class, () -> {
          archive.deleteStoryFromArchive(null);
        });
    assertEquals("Story can not be null", addNull.getMessage());

    NoSuchElementException dontExist =
        assertThrows(NoSuchElementException.class, () -> {
          archive.deleteStoryFromArchive(new Story("Haunted House", openingPassage));
        });
    assertEquals("The story Haunted House does not exist!", dontExist.getMessage());
  }

}