package no.ntnu.gruppe1.utility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import no.ntnu.gruppe1.model.Link;
import no.ntnu.gruppe1.model.Passage;
import no.ntnu.gruppe1.model.Story;
import no.ntnu.gruppe1.model.actions.Action;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * The file handler.
 * Responsible for handling files, such as reading and writing files.
 *
 * @author Marie Skamsar Aasen and Sofia Serine Mikkelsen
 * @version 2023.03.13
 */
public class FileHandler {

  //Fields
  private static volatile FileHandler fileHandler;
  private final Logger logger = Logger.getLogger("logger");

  /**
   * The constructor.
   */
  private FileHandler() {
  }

  /**
   * Returns the FileHandler object.
   * If no FileHandler exists, the method creates one.
   *
   * @return the FileHandler object
   */
  public static FileHandler getFileHandler() {
    if (fileHandler == null) {
      synchronized (FileHandler.class) {
        fileHandler = new FileHandler();
      }
    }
    return fileHandler;
  }

  /**
   * Responsible for reading a story from a file.
   *
   * @param filePath the file to read from
   * @return the story as it has been read
   * @throws FileException for an invalid file type
   */
  public Story readStoryFromFile(String filePath) throws FileException {

    if (!filePath.endsWith(".paths")) {
      logger.severe("Story not read do to wrong file format.");
      throw new FileException("The file has to be .paths");
    }

    Story story;

    try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
      story = null;
      String passageTitle = "";
      StringBuilder passageContent = new StringBuilder();
      List<Link> links = new ArrayList<>();

      //the line currently read.
      String readLine = reader.readLine();
      checkFile(filePath, readLine);

      //store title in story title.
      String storyTitle = readLine.trim();

      //throws exception if the title is blank
      if (storyTitle.isBlank()) {
        logger.severe(
            "File Reader read file but first line is empty and no story object was created");
        throw new FileException("Story Title not found, check first line of file: " + filePath);
      }

      //read next line
      readLine = reader.readLine();
      checkFile(filePath, readLine);

      //read empty space.
      if (readLine.isBlank()) {
        readLine = reader.readLine();
      }

      //Read the next passage
      while (readLine != null) {

        // passage title.
        if (readLine.contains("::")) {
          passageTitle = readLine.replace(":", "").trim();

          // passage link
        } else if (!StringUtils.containsNone(readLine, "[]()")) {
          links.add(makeLink(passageTitle, readLine));

          // if blank make passage item.
        } else if (readLine.isBlank()) {
          Passage passage = makePassage(passageTitle, passageContent.substring(0), links);

          //check if story exist and add passage
          story = makeStory(story, storyTitle, passage);

          //reset values
          passageContent.setLength(0);
          passageTitle = "";
          links.clear();

          // passage content.
        } else if (!readLine.isBlank()) {

          passageContent.append(readLine).append(" ");

        } else {
          logger.warning("File reader encountered unreadable line: ");
          logger.warning(readLine);
          throw new FileException("Unreadable line in story file");

        }
        readLine = reader.readLine();
      }

      checkFile(filePath, story);

      // if the file does not have a blank space at the end,
      // try putting together a final passage
      if (!passageTitle.isBlank() || !passageContent.isEmpty()) {
        makeStory(story, storyTitle, makePassage(passageTitle, passageContent.substring(0), links));
      }

    } catch (IOException e) {
      logger.severe("File reader failed, file:" + filePath + "whit exception: " + e);
      throw new FileException("Could not read file: " + filePath);
    }

    return story;
  }

  /**
   * Method for building the story.
   * creates story if it does not exist.
   * add passage to story if it does.
   *
   * @param story      story object to check or add to
   * @param storyTitle title of story
   * @param passage    passage to add to story
   * @return story created/changed.
   */
  private Story makeStory(Story story, String storyTitle, Passage passage) {
    if (story == null) {
      story = new Story(storyTitle, passage);
    } else {
      story.addPassage(passage);
    }
    return story;
  }

  /**
   * Method for checking if the file is incapable of creating a story
   * by checking fist lines and final story.
   *
   * @param filePath the path of the file
   * @param o        the object to test if is created.
   */
  private void checkFile(String filePath, Object o) {
    if (o == null) {
      logger.severe("File Reader tried to read file, "
          + "but it does not contain the correct information "
          + "to creat a story object");
      throw new FileException("Could not creat a story out of file, check file: " + filePath);
    }
  }

  /**
   * Creates a link based on the file data and add it to the links list.
   *
   * @param passageTitle Name of the passage for exception handling.
   * @param linkLine     the line holding the Link object information.
   * @throws FileException if text or reference is blank
   */
  private Link makeLink(String passageTitle, String linkLine) throws FileException {
    //Divide the line into text and reference and remove indicators.
    String linkText = StringUtils.substringBetween(linkLine, "[", "]");
    String linkReference = StringUtils.substringBetween(linkLine, "(", ")");

    //test if text or reference is blank
    if (linkText == null || linkText.isBlank()) {
      logger.warning("Link not created do to missing text filed.");
      throw new FileException("link text not found for passage: " + passageTitle);
    }
    if (linkReference == null || linkReference.isBlank()) {
      logger.warning("Link not created do to missing reference.");
      throw new FileException("link reference not found for passage: " + passageTitle);
    }

    //create link
    Link.LinkBuilder linkBuilder = new Link.LinkBuilder()
        .setText(linkText.trim())
        .setReference(linkReference.trim());

    //Add actions if applicable
    String[] linkStrings = StringUtils.substringsBetween(linkLine, "<", ">");

    if (linkStrings != null) {
      for (String actionStrings : linkStrings) {
        linkBuilder.setAction(makeAction(passageTitle, actionStrings));
      }
    }

    return linkBuilder.build();
  }

  /**
   * Creates an action of the written type and return it.
   *
   * @param passageTitle the tittle of the passage holding the action.
   * @param actionString the String that wil be made into an action.
   * @return The action created
   */
  private Action<?> makeAction(String passageTitle, String actionString) {
    ActionFactory factory = ActionFactory.getActionFactory();
    String[] action = StringUtils.split(actionString, ":", 2);

    try {
      return factory.createAction(action[0].trim().toLowerCase(), action[1].trim());
    } catch (RuntimeException e) {
      logger.warning("File handler could not make Action, value or type is wrong" + e.getMessage());
      throw new FileException("Could not make action in passage: " + passageTitle);
    }
  }


  /**
   * Creates a Passage based on information from the file.
   *
   * @param passageTitle   Title of the passage
   * @param passageContent The content of the passage
   * @param links          a list of links from the passage.
   * @return the constructed passage
   */
  private Passage makePassage(String passageTitle, String passageContent, List<Link> links)
      throws FileException {
    if (passageTitle.isBlank()) {
      logger.severe("File handler could not creat passage do to missing content");
      throw new FileException("passage title not found, passage with content: " + passageContent);
    }
    if (passageContent.isBlank()) {
      logger.severe("File handler could not creat passage do to missing title");
      throw new FileException("passage content not found, passage with title: " + passageTitle);
    }

    Passage.PassageBuilder passageBuilder = new Passage.PassageBuilder()
        .setTitle(passageTitle.trim())
        .setContent(passageContent.trim());

    for (Link l : links) {
      passageBuilder.setLink(l);
    }
    links.clear();
    return passageBuilder.build();
  }

  /**
   * Method for writing a story to a file.
   * If something goes wrong, it wil not write the file.
   *
   * @param story the story to write to file
   * @throws FileException if something goes wrong during writing
   */
  public void writeStory(String path, Story story) throws FileException {
    try (BufferedWriter writer = Files.newBufferedWriter(
        Path.of(path + story.getTitle() + ".paths"))) {

      // write title for story.
      writer.write(story.getTitle() + "\n");
      writer.write("\n");

      // Write the Opening passage title and content.
      writer.write("::" + story.getOpeningPassage().getTitle() + "\n");
      writer.write(story.getOpeningPassage().getContent() + "\n");

      // for all the links connected to opening passage, write [link text](link reference)<action>.
      story.getOpeningPassage().getLinks().forEachRemaining(link -> {
        try {
          writeLink(writer, link);
        } catch (IOException e) {
          logger.severe("could not write link in opening passage");
          throw new FileException("could not write link to file in opening passage :"
              + e.getMessage());
        }
      });

      writer.write("\n");

      // for all the passages in the story, write the passage title and content.
      story.getPassages().forEachRemaining(passage -> {
        try {
          writePassage(story, writer, passage);
        } catch (Exception e) {
          logger.severe("could not write passage in opening passage");
          throw new FileException("could not write passage to file in passage :"
              + e.getMessage());
        }

      });
    } catch (Exception e) {
      logger.severe("File-handler failed write to read file. " + path);
      throw new FileException("failed to write story "
          + story.getTitle() + " to file: " + e.getMessage());
    }
  }

  private void writePassage(Story story, BufferedWriter writer, Passage passage)
      throws IOException, FileException {
    if (!passage.equals(story.getOpeningPassage())) {
      writer.write("::" + passage.getTitle() + "\n");
      writer.write(passage.getContent() + "\n");

      // for all the links connected to that passage, write [link text](link reference).
      passage.getLinks().forEachRemaining(link -> {
        try {
          writeLink(writer, link);
        } catch (IOException e) {
          logger.severe("could not write link in passage");
          throw new FileException("could not write link to file in passage"
              + passage.getTitle() + " :" + e.getMessage());
        }
      });

      writer.write("\n");
    }
  }

  /**
   * Method for writing links to a file.
   *
   * @param writer writes to the file
   * @param link   the link to be written
   * @throws IOException   if the writer fails to write.
   * @throws FileException if an unrecognizable action is found.
   */
  private void writeLink(BufferedWriter writer, Link link)
      throws IOException, FileException {
    writer.write("[" + link.getText() + "]" + "(" + link.getReference() + ")");

    //for all actions in the link, write <actionType:actionValue>
    for (Iterator<Action<?>> it = link.getActions(); it.hasNext(); ) {
      Action<?> action = it.next();
      switch (action.getClass().getSimpleName()) {

        case "GoldAction" -> writer.write("<Gold:" + action.getValue() + ">");
        case "HealthAction" -> writer.write("<Health:" + action.getValue() + ">");
        case "InventoryAction" -> writer.write("<Item:" + action.getValue() + ">");
        case "ScoreAction" -> writer.write("<Points:" + action.getValue() + ">");

        default -> {
          logger.severe(
              "Could not write unrecognizable action " + action.getClass().getSimpleName()
                  + " in link " + link.getText());
          throw new FileException(
              "Action not recognised: " + action.getClass().getSimpleName());
        }
      }
    }
    writer.write("\n");
  }

}
