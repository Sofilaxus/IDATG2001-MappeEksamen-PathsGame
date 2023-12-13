package no.ntnu.gruppe1.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.gruppe1.controllers.SceneController;

/**
 * The tutorial scene. This scene should show when you press "Tutorial" in the Main Menu.
 * You can come back to this scene.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.03.16
 */
public class TutorialScene extends ScrollPane {

  //Fields
  private final SceneController sceneController;

  /**
   * The constructor.
   *
   * @param main the stage the scene is set upon
   */
  public TutorialScene(PathsGui main) {
    sceneController = new SceneController(main);
    setContent(tutorialPane());
  }

  /**
   * A VBox containing the tutorials.
   *
   * @return the vbox
   */
  private VBox tutorialPane() {
    Text welcome = new Text("Welcome to Paths!");
    welcome.setId("title-text");
    Text info = new Text("Paths is a game engine for interactive, choice-based storytelling.");
    info.setId("normal-text");
    Text howToPlay = new Text("How to play:");
    howToPlay.setId("sub-title-text");
    Text howToPlayTut = new Text("1. Simply go to the main menu and press the Play button." + "\n"
        + "2. Choose a story you wish to play in the Story Archive, and double click it." + "\n"
        + "3. Create a player and add goals if you wish." + "\n"
        + "4. Press Play and enjoy the game!" + "\n");
    howToPlayTut.setId("normal-text");
    Text howToImport = new Text("How to import your own story:");
    howToImport.setId("sub-title-text");
    Text howToImportTut = new Text("1. Go to the main menu and press the Play button." + "\n"
        + "2. Press the Import Story button. " + "\n"
        + "3. Choose the file you wish to import. " + "\n"
        + "4. The story should now be in the table! " + "\n"
        + "Note: Only files that ends with .paths will be accepted in the program" + "\n"
        + "Also, when naming your file, keep the file name the same as the story title!" + "\n");
    howToImportTut.setId("normal-text");

    Text howToCreateStory = new Text("How to create your own story:");
    howToCreateStory.setId("sub-title-text");
    Text howToCreateTut = new Text("To create a story, you must write a story." + "\n"
        + "A story must be written in the following format:" + "\n" + "\n"
        + "--------------------------------------" + "\n"
        + "Title" + "\n" + "\n"
        + "::Opening passage title" + "\n"
        + "Opening passage content" + "\n"
        + "[Link](Reference)" + "\n"
        + "[Link](Reference) <Action:Value>" + "\n"
        + "--------------------------------------" + "\n"
        + "Note, the following actions can be added to a game:" + "\n"
        + " - Gold (represents the player's economy)" + "\n"
        + " - Health (represents the player's health)" + "\n"
        + " - Points (represents the player's score)" + "\n"
        + " - Item (represents an item to be added into the player's inventory)" + "\n"
        + "The picture below is an example of how a story can be written.");
    howToCreateTut.setId("normal-text");
    ImageView example = new ImageView("images/example.png");
    example.setFitHeight(500);
    example.setFitWidth(770);

    VBox vbox = new VBox(back(), welcome, info, howToPlay, howToPlayTut,
        howToImport, howToImportTut, howToCreateStory, howToCreateTut, example);
    vbox.setSpacing(10);
    vbox.setMaxHeight(450);
    vbox.setMaxWidth(600);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(5, 5, 5, 5));
    vbox.setId("box-style");

    return vbox;
  }

  /**
   * Creates a button that takes you back to the main menu.
   *
   * @return the button
   */
  private Button back() {
    Button backToMainMenu = new Button("Back to Main Menu");
    backToMainMenu.setOnAction(e -> sceneController.goBackToMainMenu());
    return backToMainMenu;
  }

}


