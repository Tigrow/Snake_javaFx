package snake.model;

import snake.model.elements.Body;
import snake.model.elements.Head;
import snake.model.elements.Tail;

import java.awt.*;
import java.util.LinkedList;

class Snake {
  private Head head;
  private Tail tail;
  private LinkedList<Body> bodyList;
  private IWorld world;

  Snake(int lenght, IWorld world) {
    this.world = world;
    bodyList = new LinkedList<>();
    tail = new Tail(new Point(0, 0));
    world.addElement(tail);
    for (int i = 1; i <= lenght - 2; i++) {
      Body body = new Body(new Point(i, 0));
      bodyList.addFirst(body);
      world.addElement(body);
    }
    head = new Head(new Point(lenght - 1, 0));
    world.addElement(head);
  }

  void move() {
    Point oldHeadPoint = (Point) head.getPosition().clone();
    Point oldTailPoint = (Point) tail.getPosition().clone();
    Point oldBodyPoint = (Point) bodyList.getLast().getPosition().clone();
    head.getPosition().translate(0, 1);
    world.moveElement(head, oldHeadPoint );
      if (!bodyList.isEmpty()) {
        bodyList.addFirst(bodyList.getLast());
        bodyList.getFirst().setPosition(oldHeadPoint);
        bodyList.removeLast();
        world.moveElement(bodyList.getFirst(), oldBodyPoint);
      }
      tail.setPosition(oldBodyPoint);
      world.moveElement(tail, oldTailPoint);

  }
}
