package no.ntnu.gruppe1.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import no.ntnu.gruppe1.model.Link;
import no.ntnu.gruppe1.model.actions.Action;
import no.ntnu.gruppe1.model.actions.ActionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkTest {
  private Link trollAttack;
  private Link trollFriend;
  private Action<?> gold;

  @BeforeEach
  void setUp() {
    trollAttack = new Link.LinkBuilder()
        .setText("You attacked the Troll!")
        .setReference("Troll")
        .build();
    trollFriend = new Link.LinkBuilder()
        .setText("You decided to be friendly with the Troll.")
        .setReference("Troll Friend")
        .build();
    gold = ActionFactory.getActionFactory().createAction("gold", "10");
    trollFriend.addAction(gold);
  }

  @Test
  void linkCreationBlankTest() {
    IllegalArgumentException thrownTitle =
        assertThrows(IllegalArgumentException.class, () -> {
          new Link.LinkBuilder()
              .setText("")
              .build();
        });
    assertEquals("Text of link can not be blank", thrownTitle.getMessage());

    thrownTitle =
        assertThrows(IllegalArgumentException.class, () -> {
          new Link.LinkBuilder()
              .setText("  ")
              .build();
        });
    assertEquals("Text of link can not be blank", thrownTitle.getMessage());

    thrownTitle =
        assertThrows(IllegalArgumentException.class, () -> {
          new Link.LinkBuilder()
              .setText(null)
              .build();
        });
    assertEquals("Text of link can not be blank", thrownTitle.getMessage());

    IllegalArgumentException thrownContent =
        assertThrows(IllegalArgumentException.class, () -> {
          new Link.LinkBuilder()
              .setReference("")
              .build();
        });
    assertEquals("Reference of link can not be blank", thrownContent.getMessage());

    thrownContent =
        assertThrows(IllegalArgumentException.class, () -> {
          new Link.LinkBuilder()
              .setReference("  ")
              .build();
        });
    assertEquals("Reference of link can not be blank", thrownContent.getMessage());

    IllegalArgumentException thrownContent3 =
        assertThrows(IllegalArgumentException.class, () -> {
          new Link.LinkBuilder()
              .setReference(null)
              .build();
        });
    assertEquals("Reference of link can not be blank", thrownContent3.getMessage());
  }

  @Test
  void linkCreationNullTest() {
    IllegalArgumentException thrownText =
        assertThrows(IllegalArgumentException.class, () -> {
          new Link.LinkBuilder()
              .setText("ok")
              .setText(null);
        });
    assertEquals("Text of link can not be blank", thrownText.getMessage());

    IllegalArgumentException thrownReference =
        assertThrows(IllegalArgumentException.class, () -> {
          new Link.LinkBuilder()
              .setReference(null)
              .setText("ok");
        });
    assertEquals("Reference of link can not be blank", thrownReference.getMessage());
  }

  @Test
  void linkEqualTest() {
    Link link;

    link = new Link.LinkBuilder()
        .setText("You attacked the Troll!")
        .setReference("Troll")
        .build();
    assertEquals(trollAttack, link);

    link = new Link.LinkBuilder()
        .setText("No Troll")
        .setReference("Troll")
        .build();
    assertEquals(trollAttack, link);

    link = new Link.LinkBuilder()
        .setText("No Troll")
        .setReference("Troll")
        .setAction(ActionFactory.getActionFactory()
            .createAction("health", "4"))
        .build();
    assertEquals(trollAttack, link);

    link = new Link.LinkBuilder()
            .setText("You attacked the Troll!")
            .setReference("Troll")
            .setAction(ActionFactory.getActionFactory()
                .createAction("gold", "10"))
            .build();
    assertEquals(trollAttack, link);

    link = new Link.LinkBuilder()
        .setText("You decided to be friendly with the Troll.")
        .setReference("Troll Friend")
        .setAction(ActionFactory.getActionFactory()
            .createAction("gold", "10"))
        .build();
    assertEquals(trollFriend, link);

    link = new Link.LinkBuilder()
        .setText("No Troll")
        .setReference("No Troll")
        .build();
    assertNotEquals(trollAttack, link);
  }

  @Test
  void linkEqualsWithActionTest() {
    Link link = new Link.LinkBuilder()
        .setText("You decided to be friendly with the Troll.")
        .setReference("Troll Friend")
        .setAction(ActionFactory.getActionFactory()
            .createAction("gold", "10"))
        .build();
    assertEquals(trollFriend, link);
  }

  @Test
  void linkNotEqualTest() {
    Link link = new Link.LinkBuilder()
            .setText("You attacked the Troll!")
            .setReference("No Troll")
            .build();
    assertNotEquals(trollAttack, link);

    link = new Link.LinkBuilder().
        setText("No Troll")
        .setReference("No Troll")
        .setAction(ActionFactory.getActionFactory()
            .createAction("health", "4"))
        .build();
    assertNotEquals(trollAttack, link);
  }

  @Test
  void getTextTest() {
    assertEquals("You attacked the Troll!", trollAttack.getText());
  }

  @Test
  void getReferenceTest() {
    assertEquals("Troll", trollAttack.getReference());
  }

  @Test
  void addActionTest() {
    Link link = new Link.LinkBuilder()
        .setText("You decided to be friendly with the Troll.")
        .setReference("Troll Friend")
        .build();
    gold = ActionFactory.getActionFactory()
        .createAction("gold", "10");
    link.addAction(gold);

    ArrayList<Action<?>> golds = new ArrayList<>();
    golds.add(gold);

    assertEquals(golds.get(0), link.getActions().next());
  }

  @Test
  void addActionNullTest() {
    NullPointerException thrownNull =
        assertThrows(NullPointerException.class, () -> {
          trollFriend.addAction(null);
        });
    assertEquals("Action Not added do to being null", thrownNull.getMessage());
  }

  @Test
  void getActionTest() {
    assertEquals(gold, trollFriend.getActions().next());
  }

  @Test
  void testLinkToString() {
    assertEquals("Text: You attacked the Troll!. Ref: Troll. Actions: []", trollAttack.toString());
  }
}