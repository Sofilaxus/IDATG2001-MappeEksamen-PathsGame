package no.ntnu.gruppe1.controllers;

import java.io.File;
import java.nio.file.Files;
import java.util.Iterator;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import no.ntnu.gruppe1.model.Story;
import no.ntnu.gruppe1.model.StoryArchive;
import no.ntnu.gruppe1.utility.FileHandler;
import no.ntnu.gruppe1.view.PathsGui;
import no.ntnu.gruppe1.view.StoryArchiveScene;

/**
 * Controller for StoryArchiveScene.
 * Has methods for importing a story and deleting a story.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.04.28
 */
public class StoryArchiveController {

  //Fields
  private final StoryArchiveScene storyArchiveScene;
  private final StoryArchive storyArchive;

  /**
   * Constructor for the controller.
   *
   * @param storyArchiveScene the view this controller is connected to
   */
  public StoryArchiveController(StoryArchiveScene storyArchiveScene) {
    this.storyArchiveScene = storyArchiveScene;
    storyArchive = StoryArchive.getStoryArchive();
  }

  /**
   * Gets the story archive.
   *
   * @return the story archive with stories
   */
  public Iterator<Story> getStoryArchive() {
    return storyArchive.getStoriesFromArchive();
  }

  /**
   * Method that imports a story. Updates the table with the imported story.
   * If a story is not formatted correctly, an alert box will inform the user
   * that the story cannot be imported.
   * If a story is successfully imported, an alert box will inform the user.
   * It is only possible to import one story at a time.
   */
  public void importStoryAndUpdateTable() {
    try {
      Story story = FileHandler.getFileHandler()
          .readStoryFromFile(getFilePath("OPEN"));
      storyArchive.addStoryToArchive(story);
      FileHandler.getFileHandler().writeStory("src/main/resources/stories/", story);
      storyArchiveScene.updateStoryList();
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText("The story was no accepted. Problems found:");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }

  }

  /**
   * Method that deletes a Story in the Story Archive.
   */
  public void deleteStory(Story story) {
    try {
      File storyFile = new File("src/main/resources/stories/" + story.getTitle() + ".paths");
      Files.delete(storyFile.toPath());
      storyArchive.deleteStoryFromArchive(story);

    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("The story could not be deleted. Problems found:");
      alert.setContentText("Check that file Title and Story Title match: " + e.getMessage());
      alert.showAndWait();
    }
    storyArchiveScene.updateStoryList();
  }

  /**
   * Uses the system default file explorer to get a file path of the user's choice.
   * If a user closes the file chooser, null is returned, nothing happens.
   *
   * @param fileChooserType OPEN or SAVE depending on whether the user wants to open or write a file
   * @return the file path of a paths file for a story
   */
  public String getFilePath(String fileChooserType) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PATHS", "*.paths"));
    File file = null;
    if (fileChooserType.equals("OPEN")) {
      fileChooser.setTitle("Select story from file");
      file = fileChooser.showOpenDialog(PathsGui.getStage());
    } else if (fileChooserType.equals("SAVE")) {
      fileChooser.setTitle("Select directory to save story");
      file = fileChooser.showSaveDialog(PathsGui.getStage());
    }

    if (file != null) {
      return file.getPath();
    }
    return null;
  }
}