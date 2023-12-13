package no.ntnu.gruppe1.view;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.ntnu.gruppe1.controllers.PathsGuiController;

/**
 * The Graphic User Interface of the application.
 * Responsible for making the application look cool.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.03.04
 */
public class PathsGui extends Application {

  //Fields
  private static Stage stage;

  /**
   * The constructor for the stage.
   *
   * @param primaryStage the stage which the scenes are set upon.
   */
  @Override
  public void start(Stage primaryStage) {
    PathsGuiController controller = new PathsGuiController(this);
    controller.readStoriesFromResource();
    primaryStage.setTitle("Paths Game");
    Image icon = new Image("logo.PNG");
    primaryStage.getIcons().add(icon);
    stage = primaryStage;

    MainMenuScene mainMenu = new MainMenuScene(this);
    Scene scMainMenu = new Scene(mainMenu, 1120, 660);
    scMainMenu.getStylesheets().add("style.css");
    primaryStage.setScene(scMainMenu);
    stage.setResizable(false);
    primaryStage.setOnCloseRequest(evt -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Exit");
      alert.setHeaderText("Exit Paths?");
      alert.setContentText("Are you sure you want to exit Paths?");
      alert.showAndWait().filter(r -> r != ButtonType.OK).ifPresent(r -> evt.consume());
    });
    primaryStage.show();
  }

  /**
   * A quit confirmation alert box that will show when pressing the button "Quit".
   */
  public void quitConfirmation() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Exit");
    alert.setHeaderText("Exit Paths?");
    alert.setContentText("Are you sure you want to exit Paths?");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent()) {
      if (result.get() == ButtonType.OK) {
        Platform.exit();
      }
    }
  }

  /**
   * Gets the main stage for the program.
   *
   * @return stage - Stage - the starting program stage
   */
  public static Stage getStage() {
    return stage;
  }

  /**
   * The main method that starts the application. Will be called upon in Main.class
   *
   * @param args arguments
   */
  public static void appMain(String[] args) {
    launch(args);
  }

  /**
   * Stops/exits the application.
   */
  @Override
  public void stop() {
    Platform.exit();
  }
}
