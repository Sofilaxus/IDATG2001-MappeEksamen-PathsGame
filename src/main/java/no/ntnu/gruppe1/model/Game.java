package no.ntnu.gruppe1.model;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import no.ntnu.gruppe1.model.goals.Goal;
import no.ntnu.gruppe1.model.player.Player;

/**
 * Game is a facade for a Paths game.
 * The class connects a player to a story, and has handy methods for starting and
 * maneuvering in the game.
 * The class has three attributes:
 * <ul>
 * <li><code>player</code> - The player of the game.
 * </li>
 * <li><code>story</code> - The story of the game.
 * </li>
 * <li><code>goals</code> - Refers to a list of special objects that indicate
 * desired results in a game.
 * </li>
 * </ul>
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.02.08
 */
public class Game {

  // Fields
  private final Player player;
  private final Story story;
  private final List<Goal<?>> goals;

  /**
   * The constructor of the game.
   * Is created with a player, a story and the goals for the game.
   *
   * @param player the player of the game.
   * @param story the story of the game.
   * @param goals the goals of the game.
   */
  public Game(Player player, Story story, List<Goal<?>> goals) throws IllegalArgumentException {
    Objects.requireNonNull(player, "You need a player to create a game");
    Objects.requireNonNull(story, "You need a story to create a game");
    Objects.requireNonNull(goals, "Goals have not been initialised correctly");
    this.player = player;
    this.story = story;
    this.goals = goals;
  }

  /**
   * Getter method for the player in the game.
   *
   * @return player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Getter method for the story in the game.
   *
   * @return story
   */
  public Story getStory() {
    return story;
  }

  /**
   * Getter method for the goals in the game.
   * A list of objectives for the player to complete.
   *
   * @return goals
   */
  public Iterator<Goal<?>> getGoals() {
    return goals.iterator();
  }

  /**
   * Getter method for first passage.
   *
   * @return The first passage in story.
   */
  public Passage begin() {
    return story.getOpeningPassage();
  }

  /**
   * Method for going to a passage based on the link in Story passages map.
   *
   * @param link for the passage.
   * @return the passage with selected link.
   */

  public Passage go(Link link) {
    Objects.requireNonNull(link, "Link can not be null.");
    return story.getPassage(link);
  }
}

