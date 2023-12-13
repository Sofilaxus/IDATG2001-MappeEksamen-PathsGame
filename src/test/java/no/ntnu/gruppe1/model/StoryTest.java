package no.ntnu.gruppe1.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import no.ntnu.gruppe1.model.Link;
import no.ntnu.gruppe1.model.Passage;
import no.ntnu.gruppe1.model.Story;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoryTest {
  private Story testStory;
  private Passage openingPassage;
  private Passage passage;

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

    passage = new Passage.PassageBuilder()
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
  }

  @Test
  void TestStoryCreationBlank() {
    IllegalArgumentException thrownTitle =
        assertThrows(IllegalArgumentException.class, () -> {
          new Story("", openingPassage);
        });
    assertEquals("Title of story can not be blank or null", thrownTitle.getMessage());

    thrownTitle =
        assertThrows(IllegalArgumentException.class, () -> {
          new Story("  ", openingPassage);
        });
    assertEquals("Title of story can not be blank or null", thrownTitle.getMessage());

    thrownTitle =
        assertThrows(IllegalArgumentException.class, () -> {
          new Story(null, openingPassage);
        });
    assertEquals("Title of story can not be blank or null", thrownTitle.getMessage());

    NullPointerException thrownPassage =
        assertThrows(NullPointerException.class, () -> {
          new Story("Story", null);
        });
    assertEquals("Opening passage cannot be null", thrownPassage.getMessage());

    IllegalArgumentException thrownPassage2 =
        assertThrows(IllegalArgumentException.class, () -> {
          new Story("Story", new Passage.PassageBuilder()
              .setTitle(null)
              .build());
        });
    assertEquals("Title of passage can not be blank", thrownPassage2.getMessage());
  }

  @Test
  void testGetTitle() {
    assertEquals("Haunted House", testStory.getTitle());
  }

  @Test
  void testGetOpeningPassage() {
    assertEquals(openingPassage, testStory.getOpeningPassage());
  }

  @Test
  void testGetPassage() {
    assertEquals(passage, testStory.getPassage(new Link.LinkBuilder()
        .setReference("Another room")
        .build()));
  }

  @Test
  void testAddPassage() {
    testStory.addPassage(new Passage.PassageBuilder()
        .setTitle("TestTitle")
        .setContent("TestRef")
        .build());

    assertEquals(new Passage.PassageBuilder()
            .setTitle("TestTitle")
            .setContent("TestRef")
            .build(),
        testStory.getPassage(new Link.LinkBuilder()
            .setReference("TestTitle")
            .build()));
  }

  @Test
  void testAddPassageThrows() {
    NullPointerException thrownNull =
        assertThrows(NullPointerException.class, () -> {
          testStory.addPassage(null);
        });
    assertEquals("Passage can not be null.", thrownNull.getMessage());

    IllegalArgumentException passageExist =
        assertThrows(IllegalArgumentException.class, () -> {
          testStory.addPassage(new Passage.PassageBuilder()
              .setTitle("Beginnings")
              .setContent(
                  "You are in a small, dimly lit room. There is a door in front of you.")
              .build());
        });
    assertEquals("Passage already exists in the story", passageExist.getMessage());
  }

  @Test
  void testRemovePassage() {
    testStory.removePassage(new Link.LinkBuilder()
        .setReference("Another room")
        .build());

    assertNotEquals(testStory.getPassages().next(),
        (new Passage.PassageBuilder()
            .setTitle("Another room")
            .setContent(
                "The door opens to another room.You see a desk with a large, dusty book.")
            .build()));
  }

  @Test
  void testRemovePassageThrows() {
    NullPointerException passageNull =
        assertThrows(NullPointerException.class, () -> {
          testStory.removePassage(null);
        });
    assertEquals("link can not be null.", passageNull.getMessage());

    IllegalArgumentException passageIsOpener =
        assertThrows(IllegalArgumentException.class, () -> {
          testStory.removePassage(new Link.LinkBuilder()
              .setReference("Beginnings")
              .build());
        });
    assertEquals("You cannot remove the opening passage", passageIsOpener.getMessage());

    RuntimeException passageIsNotInStory =
        assertThrows(RuntimeException.class, () -> {
          testStory.removePassage(new Link.LinkBuilder()
              .setReference("Wrong")
              .build());
        });
    assertEquals("Passade Wrong was not found in the story.", passageIsNotInStory.getMessage());
  }

  @Test
  void testGetPassages() {
    assertTrue(findPassageInStory(passage));
    assertTrue(findPassageInStory(openingPassage));
    assertFalse(findPassageInStory(new Passage.PassageBuilder()
        .setTitle("title")
        .setContent("content")
        .build())
    );

  }

  /**
   * method for finding Passage in story
   *
   * @param passage passage to test
   * @return if it is found
   */
  boolean findPassageInStory(Passage passage) {
    Iterator<Passage> passageIterator = testStory.getPassages();
    while (passageIterator.hasNext()) {
      if (passageIterator.next().equals(passage)) {
        return true;
      }
    }
    return false;
  }

  @Test
  void testGetPassageThrows() {
    NullPointerException passageExist =
        assertThrows(NullPointerException.class, () -> {
          testStory.getPassage(null);
        });
    assertEquals("Link can not be null.", passageExist.getMessage());

    RuntimeException passageIsNotInStory =
        assertThrows(RuntimeException.class, () -> {
          testStory.getPassage(new Link.LinkBuilder()
              .setReference("Wrong")
              .build());
        });
    assertEquals("Passage Wrong was not found in the story.", passageIsNotInStory.getMessage());
  }

  @Test
  void testBrokenLinks() {
    assertEquals("The book of spells", testStory.getBrokenLinks().get(0));
  }

  @Test
  void testUnreachablePassage() {
    Passage noLinkPassage = new Passage.PassageBuilder()
        .setTitle("There Is no Link")
        .setContent("Some Text")
        .build();
    testStory.addPassage(noLinkPassage);
    assertEquals("There Is no Link", testStory.getUnreachablePassages().get(0));

    assertFalse(testStory.getBrokenLinks().removeAll(testStory.getUnreachablePassages()));
  }
}