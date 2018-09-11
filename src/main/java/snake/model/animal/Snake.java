package snake.model.animal;

import snake.Properties;
import snake.model.IWorldAnimal;
import snake.model.animal.elements.*;

import java.awt.Point;
import java.util.LinkedList;

public class Snake implements Runnable {
  private Properties properties;
  private IWorldAnimal world;
  private Head head;
  private Tail tail;
  private LinkedList<Body> bodyList;
  private Point dir = new Point(0, 1);
  private int addBodyCount = 0;

  public Snake(Properties properties, IWorldAnimal world) {
    this.properties = properties;
    this.world = world;
    this.bodyList = new LinkedList<>();
    tail = new Tail(new Point(0, 0));
    world.addElement(tail);
    world.moveElement(tail,tail.getPosition());
    for (int i = 1; i <= properties.getSnakeSize() - 2; i++) {
      Body body = new Body(new Point(i, 0));
      bodyList.addFirst(body);
      world.addElement(body);
      world.moveElement(body,body.getPosition());
    }
    head = new Head(new Point(properties.getSnakeSize() - 1, 0));
    world.addElement(head);
    world.moveElement(head,head.getPosition());
  }

  @Override
  public void run() {
    while (world.isRunned()) {
      move();
      world.update();
      try {
        Thread.sleep(properties.getSnakeSleep());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void addBodySegment() {
    addBodyCount++;
  }

  private void move() {
    Point newHeadPosition = (Point) head.getPosition().clone();
    Point newTailPosition = (Point) bodyList.getLast().getPosition().clone();
    Point newBodyPosition = (Point) head.getPosition().clone();
    changeDirection();
    newHeadPosition.translate(dir.x, dir.y);
    if (world.moveElement(head, newHeadPosition)) {
      if (addBodyCount != 0) {
        Body body = new Body(newBodyPosition);
        bodyList.addFirst(body);
        world.addElement(body);
        world.moveElement(body,body.getPosition());
        addBodyCount--;
      } else {
        if (!bodyList.isEmpty()) {
          bodyList.addFirst(bodyList.getLast());
          bodyList.removeLast();
          world.moveElement(bodyList.getFirst(), newBodyPosition);
        }
        world.moveElement(tail, newTailPosition);
      }
    }
  }

  private void changeDirection() {
    Point oldDir = (Point) dir.clone();
    if (world.getDirection() == Direction.Left) {
      dir.x = oldDir.y * 1;
      dir.y = oldDir.x * -1;
      world.changeDirection(Direction.None);
    } else if (world.getDirection() == Direction.Right) {
      dir.x = oldDir.y * -1;
      dir.y = oldDir.x * 1;
      world.changeDirection(Direction.None);
    }
  }
}
