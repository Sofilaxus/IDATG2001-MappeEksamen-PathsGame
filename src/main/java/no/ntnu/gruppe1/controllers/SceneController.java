package no.ntnu.gruppe1.controllers;

import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import no.ntnu.gruppe1.model.Game;
import no.ntnu.gruppe1.model.Story;
import no.ntnu.gruppe1.view.*;
import no.ntnu.gruppe1.view.PlayGameScene;

/**
 * Responsible for changing between the different scenes.
 * We call them scenes, but in reality they are mainly BorderPanes
 * that is set on the PathsGUI stage as a scene.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.03.11
 */
public class SceneController {

  //Fields
  private final PathsGui pathsgui;
  private final Stage stage;

  /**
   * Constructor for the SceneController.
   *
   * @param pathsgui the stage the scene will be set upon
   */
  public SceneController(PathsGui pathsgui) {
    this.pathsgui = pathsgui;
    stage = PathsGui.getStage();
  }

  /**
   * Creates the StoryArchiveScene and puts the scene on the stage.
   */
  public void goToStoryArchive() {
    StoryArchiveScene storyArchiveScene = new StoryArchiveScene(pathsgui);
    Scene sceneStoryArchive = new Scene(storyArchiveScene, 1120, 660);
    sceneStoryArchive.getStylesheets().add("style.css");
    stage.setScene(sceneStoryArchive);
    stage.setResizable(false);
  }

  /**
   * This scene change will only happen when the user is in a game.
   * Informs the user that all progress in the game will be lost if they wish to exit.
   */
  public void goBackToStoryArchive() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Go back to Story Archive");
    alert.setHeaderText("Leave the game?");
    alert.setContentText("Are you sure you want to leave the game? All progress will be lost.");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent()) {
      if (result.get() == ButtonType.OK) {
        goToStoryArchive();
      }
    }
  }

  /**
   * Creates the TutorialScene and puts the scene on the stage.
   */
  public void goToTutorial() {
    TutorialScene tutorialScene = new TutorialScene(pathsgui);
    Scene sceneTutorial = new Scene(tutorialScene, 1120, 660);
    sceneTutorial.getStylesheets().add("style.css");
    stage.setScene(sceneTutorial);
    stage.setResizable(false);
  }

  /**
   * Creates the LoadGameScene and puts the scene on the stage.
   */
  public void goToLoadGame(Story story) {
    LoadGameScene loadGameScene = new LoadGameScene(story);
    Scene sceneLoadGame = new Scene(loadGameScene, 1120, 660);
    sceneLoadGame.getStylesheets().add("style.css");
    stage.setScene(sceneLoadGame);
    stage.setResizable(false);
  }

  /**
   * Creates the PlayGameScene and puts the scene on the stage.
   */
  public void goToPlayGame(Game game) {
    PlayGameScene playGameScene = new PlayGameScene(game);
    Scene scenePlayGame = new Scene(playGameScene, 1120, 660);
    scenePlayGame.getStylesheets().add("style.css");
    stage.setScene(scenePlayGame);
    stage.setResizable(false);
  }

  /**
   * Creates the MainMenuScene and puts the scene on the stage.
   */
  public void goBackToMainMenu() {
    MainMenuScene mainMenuScene = new MainMenuScene(pathsgui);
    Scene sceneMainMenu = new Scene(mainMenuScene, 1120, 660);
    sceneMainMenu.getStylesheets().add("style.css");
    stage.setScene(sceneMainMenu);
    stage.setResizable(false);
  }
}

