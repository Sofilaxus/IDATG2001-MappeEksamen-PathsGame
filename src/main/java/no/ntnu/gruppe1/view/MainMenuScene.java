package no.ntnu.gruppe1.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import no.ntnu.gruppe1.controllers.SceneController;


/**
 * The main menu scene. When opening the application, this should be the first scene you see.
 * You can come back to this scene. Extends BorderPane, for aligning contents.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.03.11
 */
public class MainMenuScene extends BorderPane {

  //Fields
  private final SceneController sceneController;
  private final PathsGui main;

  /**
   * The main method, and the BorderPane which contents are placed in.
   * Contains the Paths title text and buttons that takes the user to the other scenes in the
   * application.
   *
   * @param pathsGui the stage which the scene is set upon
   */
  public MainMenuScene(PathsGui pathsGui) {
    main = new PathsGui();
    sceneController = new SceneController(pathsGui);

    final Label paths = new Label("Paths");
    paths.setId("paths-title");

    Button play = new Button("Play");
    play.setOnAction(e -> sceneController.goToStoryArchive());
    Button tutorial = new Button("Tutorial");
    tutorial.setOnAction(e -> sceneController.goToTutorial());
    Button quit = new Button("Quit");
    quit.setOnAction(e -> main.quitConfirmation());
    VBox gameMenu = new VBox(paths, play, tutorial, quit);
    gameMenu.setSpacing(5);
    gameMenu.setAlignment(Pos.CENTER);

    setId("main-menu");
    setCenter(gameMenu);
  }

}
