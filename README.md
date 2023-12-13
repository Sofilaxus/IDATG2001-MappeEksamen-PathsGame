# Paths

## Description
Paths is an application for the semester exam in IDATG2001 Programming 2.
This application is made by Marie Skamsar Aasen and Sofia Serine Mikkelsen, group 1.

## Table of contents
- [How to run the application](#how-to-run-the-application)
- [Project structure](#project-structure)
- [License](#license)
- [Project status](#project-status)

## How to run the application
To run the application locally on a computer, Maven is needed.
If Maven is not already installed on the computer, you can download it [here](https://maven.apache.org/download.cgi).
Pack out  the Maven project and read the README file included in how to install Maven.

When Maven is installed and ready, the project can be run with the following command:

```text
mvn javafx:run
```

## Project structure
```text
mappe1
├── src
|   ├── main
|   |   ├── java
|   |   |   └── no.ntnu.gruppe1
|   |   |       ├── controllers
|   |   |       |   ├── LoadGameController.java
|   |   |       |   ├── PathsGuiController.java
|   |   |       |   ├── PlayGameController.java
|   |   |       |   ├── SceneController.java
|   |   |       |   └── StoryArchiveController.java
|   |   |       ├── model
|   |   |       |   ├── actions
|   |   |       |   |   ├── Action.java
|   |   |       |   |   ├── ActionFactory.java
|   |   |       |   |   ├── GoldAction.java
|   |   |       |   |   ├── HealthAction.java
|   |   |       |   |   ├── InventoryAction.java
|   |   |       |   |   └── ScoreAction.java
|   |   |       |   ├── goals
|   |   |       |   |   ├── Goal.java
|   |   |       |   |   ├── GoalFactory.java
|   |   |       |   |   ├── GoldGoal.java
|   |   |       |   |   ├── HealthGoal.java
|   |   |       |   |   ├── InventoryGoal.java
|   |   |       |   |   └── ScoreGoal.java
|   |   |       |   ├── player
|   |   |       |   |   ├── Player.java
|   |   |       |   |   └── PlayerTypes.java
|   |   |       |   ├── Game.java
|   |   |       |   ├── Link.java
|   |   |       |   ├── Passage.java
|   |   |       |   ├── Story.java
|   |   |       |   └── StoryArchive.java
|   |   |       ├── utility
|   |   |       |   ├── FileException.java
|   |   |       |   ├── FileHandler.java
|   |   |       |   └── LinkClicked.java
|   |   |       ├── view
|   |   |       |   ├── ChoiceBoxes
|   |   |       |   ├── LoadGameScene.java
|   |   |       |   ├── MainMenuScene.java
|   |   |       |   ├── PathsGui.java
|   |   |       |   ├── PlayGameScene.java
|   |   |       |   ├── StoryArchiveScene.java
|   |   |       |   └── TutorialScene.java
|   |   |       └── Main.java
|   |   └── resources
|   |       ├── images
|   |       |   ├── background.jpeg
|   |       |   ├── example.png
|   |       |   ├── gold.PNG
|   |       |   ├── health.PNG
|   |       |   ├── menuImage.PNG
|   |       |   ├── mute.PNG
|   |       |   └── unmute.PNG
|   |       ├── stories
|   |       |   ├── A Dragon's Promise.paths
|   |       |   ├── The Missing People Insident.paths
|   |       |   └── The Mystery of The Troll Attack.paths
|   |       ├── logo.PNG
|   |       ├── PathsMusic.wav
|   |       └── style.css
|   └── test
|       └── java
|           └── no.ntnu.gruppe1
|               ├── actions
|               |   ├── GoldActionTest.java
|               |   ├── HealthActionTest.java
|               |   ├── InventoryActionTest.java
|               |   └── ScoreActionTest.java
|               ├── goals
|               |   ├── GoldGoalTest.java
|               |   ├── HealthGoalTest.java
|               |   ├── InventoryGoalTest.java
|               |   └── ScoreGoalTest.java
|               ├── utility
|               |   └── FileHandlerTest.java
|               ├── GameTest.java
|               ├── LinkTest.java
|               ├── PassageTest.java
|               ├── PlayerTest.java
|               └── StoryTest.java
├── .gitignore
├── pom.xml
└── README.md
```

## License
For open source projects, say how it is licensed.

## Project status
This is a student project, and will not be further developed as of now. 
