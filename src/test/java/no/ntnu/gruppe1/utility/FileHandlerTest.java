package no.ntnu.gruppe1.utility;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.gruppe1.model.Link;
import no.ntnu.gruppe1.model.Passage;
import no.ntnu.gruppe1.model.Story;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileHandlerTest {
  private final FileHandler fileHandler = FileHandler.getFileHandler();
  private Story testStory;

  @BeforeEach
  void setUp() {
    Passage openingPassage = new Passage.PassageBuilder()
        .setTitle("Beginnings")
        .setContent("You are in a small, dimly lit room. There is a door in front of you.")
        .setLink(new Link.LinkBuilder()
            .setText("Try to open the door")
            .setReference("Another room")
            .setAction(ActionFactory.getActionFactory()
                .createAction("health", "-2"))
            .build())
        .build();

    Passage passage = new Passage.PassageBuilder()
        .setTitle("Another room")
        .setContent("The door opens to another room. You see a desk with a large, dusty book.")
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
  void fileHandlerExist() {
    assertNotNull(fileHandler);
  }

  @Test
  void testStoryFromFileReader() {
    Link link = new Link.LinkBuilder().setReference("Another room").build();

    Story storyFromFile = fileHandler.readStoryFromFile("src/test/resources/test.paths");
    assertEquals(storyFromFile.getTitle(), testStory.getTitle());
    assertEquals(storyFromFile.getBrokenLinks(), testStory.getBrokenLinks());
    assertEquals(storyFromFile.getUnreachablePassages(), testStory.getUnreachablePassages());
    assertTrue(storyFromFile.getOpeningPassage().equals(testStory.getOpeningPassage()));

    assertEquals(storyFromFile.getPassage(link), testStory.getPassage(link));
    assertEquals(storyFromFile.getPassage(link).getLinks().next(), testStory.getPassage(link).getLinks().next());
    assertEquals(storyFromFile.getPassage(link).getLinks().next().getActions().next().getValue(),
        testStory.getPassage(link).getLinks().next().getActions().next().getValue());

    Story altStoryFromFile =
        fileHandler.readStoryFromFile("src/test/resources/testAlternative.paths");
    assertEquals(altStoryFromFile.getTitle(), testStory.getTitle());
    assertEquals(altStoryFromFile.getBrokenLinks(), testStory.getBrokenLinks());
    assertEquals(altStoryFromFile.getUnreachablePassages(), testStory.getUnreachablePassages());
    assertEquals(altStoryFromFile.getOpeningPassage(), testStory.getOpeningPassage());

    assertEquals(storyFromFile.getPassage(link), testStory.getPassage(link));
    assertEquals(storyFromFile.getPassage(link).getLinks().next(), testStory.getPassage(link).getLinks().next());
    assertEquals(storyFromFile.getPassage(link).getLinks().next().getActions().next().getValue(),
        testStory.getPassage(link).getLinks().next().getActions().next().getValue());

  }

  @Test
  void fileReaderNoFile() {
    FileException thrownNoFile =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/Title.paths");
        });
    assertEquals("Could not read file: src/test/resources/Title.paths", thrownNoFile.getMessage());
  }

  @Test
  void fileReaderEmptyFile() {
    FileException thrownEmptyFile =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testEmptyFile.paths");
        });
    assertEquals(
        "Could not creat a story out of file, check file: src/test/resources/testEmptyFile.paths",
        thrownEmptyFile.getMessage());
  }

  @Test
  void fileReaderIncompleteFile() {
    FileException thrownNoInfo =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testIncompleteFile.paths");
        });
    assertEquals("Could not creat a story out of file, " +
        "check file: src/test/resources/testIncompleteFile.paths", thrownNoInfo.getMessage());

    FileException thrownOneLine =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testIncompleteFile2.paths");
        });
    assertEquals(
        "Could not creat a story out of file, check file: src/test/resources/testIncompleteFile2.paths",
        thrownOneLine.getMessage());
  }

  @Test
  void fileReaderWrongFileType() {
    FileException wrongType =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("test.txt");
        });
    assertEquals("The file has to be .paths", wrongType.getMessage());
  }

  @Test
  void fileReaderNoStoryTitle() {
    FileException thrownTitle =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoStoryTitle.paths");
        });
    assertEquals(
        "Story Title not found, check first line of file: src/test/resources/testNoStoryTitle.paths",
        thrownTitle.getMessage());
  }

  @Test
  void fileReaderNoPassageTitle() {
    FileException thrownPassageTitle =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoPassageTitle.paths");
        });
    assertEquals("passage title not found, passage with content: " +
            "The door opens to another room. You see a desk with a large, dusty book. "
        , thrownPassageTitle.getMessage());

    FileException thrownOpeningPassageTitle =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoOpeningPassageTitle.paths");
        });
    assertEquals("passage title not found, passage with content: " +
            "You are in a small, dimly lit room. There is a door in front of you. ",
        thrownOpeningPassageTitle.getMessage());
  }

  @Test
  void fileReaderNoPassageContent() {
    FileException thrownPassageContent =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoPassageContent.paths");
        });
    assertEquals("passage content not found, passage with title: " +
            "Another room"
        , thrownPassageContent.getMessage());

    FileException thrownOpeningPassageContent =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoOpeningPassageContent.paths");
        });
    assertEquals("passage content not found, passage with title: " +
        "Beginnings", thrownOpeningPassageContent.getMessage());
  }

  @Test
  void fileReaderNoLinkRef() {
    FileException thrownLinkRef =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoPassageLinkRef.paths");
        });
    assertEquals("link reference not found for passage: " +
            "Another room"
        , thrownLinkRef.getMessage());

    FileException thrownOpeningLinkRef =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoOpeningPassageLinkRef.paths");
        });
    assertEquals("link reference not found for passage: " +
        "Beginnings", thrownOpeningLinkRef.getMessage());
  }

  @Test
  void fileReaderNoLinkText() {
    FileException thrownLinkText =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoPassageLinkText.paths");
        });
    assertEquals("link text not found for passage: " +
            "Another room"
        , thrownLinkText.getMessage());

    FileException thrownOpeningLinkText =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoOpeningPassageLinkText.paths");
        });
    assertEquals("link text not found for passage: " +
        "Beginnings", thrownOpeningLinkText.getMessage());
  }

  @Test
  void fileReaderWrongActionType() {
    FileException thrownLinkText =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoPassageLinkActionType.paths");
        });
    assertEquals("Could not make action in passage: Another room"
        , thrownLinkText.getMessage());

    FileException thrownOpeningLinkText =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile(
              "src/test/resources/testNoOpeningPassageLinkActionType.paths");
        });
    assertEquals("Could not make action in passage: Beginnings"
        , thrownOpeningLinkText.getMessage());
  }

  @Test
  void fileReaderWrongActionValue() {
    FileException thrownLinkText =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile("src/test/resources/testNoPassageLinkActionValue.paths");
        });
    assertEquals("Could not make action in passage: Another room"
        , thrownLinkText.getMessage());

    FileException thrownOpeningLinkText =
        assertThrows(FileException.class, () -> {
          fileHandler.readStoryFromFile(
              "src/test/resources/testNoOpeningPassageLinkActionValue.paths");
        });
    assertEquals("Could not make action in passage: Beginnings"
        , thrownOpeningLinkText.getMessage());
  }

  @Test
  void writeToFile() {
    Link link = new Link.LinkBuilder().setReference("Another room").build();

    fileHandler.writeStory("src/test/resources/", testStory);
    Story story = fileHandler.readStoryFromFile("src/test/resources/Haunted House.paths");
    assertEquals(story.getTitle(), testStory.getTitle());
    assertEquals(story.getBrokenLinks(), testStory.getBrokenLinks());
    assertEquals(story.getOpeningPassage(), testStory.getOpeningPassage());

    assertEquals(story.getPassage(link), testStory.getPassage(link));
    assertEquals(story.getPassage(link).getLinks().next(), testStory.getPassage(link).getLinks().next());
    assertEquals(story.getPassage(link).getLinks().next().getActions().next().getValue(),
        testStory.getPassage(link).getLinks().next().getActions().next().getValue());
  }

  /**
   * Checks if a file going tru read and right stays the same.
   */
  @Test
  void writeToFileDragon() {
    Link link = new Link.LinkBuilder().setReference("Mother dragon").build();

    Story story = fileHandler.readStoryFromFile("src/test/resources/aDragon'sPromise.paths");
    fileHandler.writeStory("src/test/resources/", story);
    Story storyWritten =
        fileHandler.readStoryFromFile("src/test/resources/A Dragon's Promise.paths");
    assertEquals(story.getTitle(), storyWritten.getTitle());
    assertEquals(story.getBrokenLinks(), storyWritten.getBrokenLinks());
    assertEquals(story.getOpeningPassage(), storyWritten.getOpeningPassage());
    assertEquals(story.getUnreachablePassages(), storyWritten.getUnreachablePassages());

    assertEquals(story.getPassage(link), storyWritten.getPassage(link));
    assertEquals(story.getPassage(link).getLinks().next(), storyWritten.getPassage(link).getLinks().next());
    assertEquals(story.getPassage(link).getLinks().next().getActions().hasNext(),
        storyWritten.getPassage(link).getLinks().next().getActions().hasNext());
  }
}