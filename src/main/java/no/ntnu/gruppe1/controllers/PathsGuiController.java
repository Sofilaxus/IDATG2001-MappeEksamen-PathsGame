package no.ntnu.gruppe1.controllers;

import java.io.File;
import no.ntnu.gruppe1.model.StoryArchive;
import no.ntnu.gruppe1.utility.FileHandler;
import no.ntnu.gruppe1.view.PathsGui;

/**
 * Controller for the PathsGUIScene.
 * Reads a folder in the program where the stories are stored,
 * adds the stories to the StoryArchive.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.05.22
 */
public class PathsGuiController {

  //Fields
  private final PathsGui scene;
  private final StoryArchive storyArchive;

  /**
   * Constructor for the controller.
   *
   * @param scene the scene
   */
  public PathsGuiController(PathsGui scene) {
    this.scene = scene;
    storyArchive = StoryArchive.getStoryArchive();
  }

  /**
   * Method for reading all the stories from the resource folder
   * and adding them to the StoryArchive.
   */
  public void readStoriesFromResource() {
    File directoryPath = new File("src/main/resources/stories/");

    File[] filesList = directoryPath.listFiles();
    assert filesList != null;
    for (File file : filesList) {
      try {
        storyArchive.addStoryToArchive(FileHandler.getFileHandler()
            .readStoryFromFile(file.getPath()));
      } catch (Exception ignore) {
        return;
      }
    }
  }
}
