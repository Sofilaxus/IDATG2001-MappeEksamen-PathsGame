package no.ntnu.gruppe1.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import no.ntnu.gruppe1.model.Link;
import no.ntnu.gruppe1.model.Passage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassageTest {
  private Passage trollPassage;
  private Passage trollAttackPassage;
  private Link link;


  @BeforeEach
  void setUp() {
    trollPassage = new Passage.PassageBuilder()
        .setTitle("Troll")
        .setContent("You met a big and scary Troll on your travels. " +
            "Do you attack the Troll or try to make friends?")
        .build();
    link = new Link.LinkBuilder()
        .setText("You attacked the Troll!")
        .setReference("Troll")
        .build();
    trollAttackPassage = new Passage.PassageBuilder()
        .setTitle("TrollAttack")
        .setContent("You attacked the Troll by throwing " +
            "a rock at it! The Troll got very sad and started to cry and ran away. You are a terrible person.")
        .setLink(link)
        .build();
  }

  @Test
  void passageCreationBlankTest() {
    IllegalArgumentException thrownTitle =
        assertThrows(IllegalArgumentException.class, () -> {
          new Passage.PassageBuilder()
              .setTitle("")
              .setContent("ok")
              .build();
        });
    assertEquals("Title of passage can not be blank", thrownTitle.getMessage());

    IllegalArgumentException thrownTitle2 =
        assertThrows(IllegalArgumentException.class, () -> {
          new Passage.PassageBuilder()
              .setTitle("  ")
              .setContent("ok")
              .build();
        });
    assertEquals("Title of passage can not be blank", thrownTitle2.getMessage());

    IllegalArgumentException thrownTitle3 =
        assertThrows(IllegalArgumentException.class, () -> {
          new Passage.PassageBuilder()
              .setTitle(null)
              .setContent("ok")
              .build();
        });
    assertEquals("Title of passage can not be blank", thrownTitle3.getMessage());


    IllegalArgumentException thrownContent =
        assertThrows(IllegalArgumentException.class, () -> {
          new Passage.PassageBuilder()
              .setTitle("ok")
              .setContent("")
              .build();
        });
    assertEquals("Content of passage can not be blank", thrownContent.getMessage());

    IllegalArgumentException thrownContent2 =
        assertThrows(IllegalArgumentException.class, () -> {
          new Passage.PassageBuilder()
              .setTitle("ok")
              .setContent("  ")
              .build();
        });
    assertEquals("Content of passage can not be blank", thrownContent2.getMessage());

    IllegalArgumentException thrownContent3 =
        assertThrows(IllegalArgumentException.class, () -> {
          new Passage.PassageBuilder()
              .setTitle("ok")
              .setContent(null)
              .build();
        });
    assertEquals("Content of passage can not be blank", thrownContent3.getMessage());
  }

  @Test
  void passageCreationNullTest() {
    IllegalArgumentException thrownTitle =
        assertThrows(IllegalArgumentException.class, () -> {
          new Passage.PassageBuilder()
              .setTitle(null)
              .setContent("ok")
              .build();
        });
    assertEquals("Title of passage can not be blank", thrownTitle.getMessage());

    IllegalArgumentException thrownContent =
        assertThrows(IllegalArgumentException.class, () -> {
          new Passage.PassageBuilder()
              .setTitle("ok")
              .setContent(null)
              .build();
        });
    assertEquals("Content of passage can not be blank", thrownContent.getMessage());
  }

  @Test
  void passageEqualTest() {
    Passage testPassage = new Passage.PassageBuilder()
        .setTitle("Troll")
        .setContent("You met a big and scary Troll on your travels. " +
            "Do you attack the Troll or try to make friends?")
        .build();
    assertEquals(testPassage, trollPassage);
    testPassage.addLink(link);
    assertEquals(testPassage, trollPassage);
  }

  @Test
  void passageNotEqualTest() {
    Passage testTitlePassage = new Passage.PassageBuilder()
        .setTitle("Not A Troll")
        .setContent("You met a big and scary Troll on your travels. " +
            "Do you attack the Troll or try to make friends?")
        .build();
    assertNotEquals(testTitlePassage, trollPassage);

    Passage testContentPassage = new Passage.PassageBuilder()
        .setTitle("Troll")
        .setContent("Nothing Happens")
        .build();
    assertNotEquals(testContentPassage, trollPassage);
  }

  @Test
  void getTitleTest() {
    assertEquals("Troll", trollPassage.getTitle());
  }

  @Test
  void getContentTest() {
    assertEquals("You met a big and scary Troll on your travels. " +
        "Do you attack the Troll or try to make friends?", trollPassage.getContent());
  }

  @Test
  void getLinkTest() {
    assertEquals(link, trollAttackPassage.getLinks().next());
  }

  @Test
  void addLinkTest() {
    Link l = new Link.LinkBuilder()
        .setText("s")
        .setReference("v")
        .build();
    trollAttackPassage.addLink(l);
    Iterator<Link> LinkIt = trollAttackPassage.getLinks();
    assertEquals(link, LinkIt.next());
    assertEquals(l, LinkIt.next());
  }

  @Test
  void hasLinkTest() {
    assertTrue(trollAttackPassage.hasLinks(link));
    Link l = new Link.LinkBuilder()
        .setText("i")
        .setReference("l")
        .build();
    trollAttackPassage.addLink(l);
    assertTrue(trollAttackPassage.hasLinks(l));
  }

  @Test
  void hasLinkTestFalse() {
    assertTrue(trollAttackPassage.hasLinks(link));
    Link l = new Link.LinkBuilder()
        .setText("i")
        .setReference("l")
        .build();
    assertFalse(trollAttackPassage.hasLinks(l));
  }

  @Test
  void getLinkNullTest() {
    NullPointerException thrownAddLink =
        assertThrows(NullPointerException.class, () -> {
          trollAttackPassage.addLink(null);
        });
    assertEquals("Link not added to passage do to being null", thrownAddLink.getMessage());

    NullPointerException thrownHasLink =
        assertThrows(NullPointerException.class, () -> {
          trollAttackPassage.hasLinks(null);
        });
    assertEquals("Link not found in passage do to being null", thrownHasLink.getMessage());
  }

  @Test
  void testPassageToString() {
    assertEquals(
        "title: TrollAttack. text: You attacked the Troll by throwing a rock at it! The Troll got very sad and started to cry and ran away. You are a terrible person.. links[Text: You attacked the Troll!. Ref: Troll. Actions: []]",
        trollAttackPassage.toString());
  }
}