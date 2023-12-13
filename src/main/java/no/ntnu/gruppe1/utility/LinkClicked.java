package no.ntnu.gruppe1.utility;

import javafx.event.ActionEvent;
import no.ntnu.gruppe1.model.Link;

/**
 * Event that happens when a link is clicked in a story.
 *
 * @author Sofia Serine Mikkelsen and Marie Skamsar Aasen
 * @version 2023.05.05
 */
public class LinkClicked extends ActionEvent {

  //Fields
  private final Link link;

  /**
   * Creates a new event when link is clicked.
   *
   * @param link the clicked link
   */
  public LinkClicked(Link link) {
    super();
    this.link = link;
  }

  /**
   * Get the clicked link.
   *
   * @return the clicked link
   */
  public Link getLink() {
    return link;
  }
}
