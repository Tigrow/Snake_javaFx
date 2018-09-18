package snake.model.animal;

import snake.Properties;
import snake.model.IWorldSnake;
import snake.model.World;
import snake.model.animal.elements.Element;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.Direction;
import snake.model.animal.elements.snake.SnakeHead;
import snake.model.animal.elements.snake.SnakeTail;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake implements Runnable {
  private Properties properties;
  private World world;
  private SnakeHead snakeHead;
  private SnakeTail snakeTail;
  private LinkedList<SnakeBody> snakeBodyList;
  private Point dir = new Point(1, 0);
  private int addBodyCount = 0;
  private List<Element> elements;
  private List<Point> pointList;

  public Snake(Properties properties, World world) {
    this.properties = properties;
    this.world = world;
    this.snakeBodyList = new LinkedList<>();
    elements = new ArrayList<>();
    pointList = new ArrayList<>();

    snakeTail = new SnakeTail(new Point(0, 0));
    addChange(snakeTail, snakeTail.getPosition());
    for (int i = 1; i <= properties.getSnakeSize() - 2; i++) {
      SnakeBody snakeBody = new SnakeBody(new Point(i, 0));
      snakeBodyList.addFirst(snakeBody);
      addChange(snakeBody, snakeBody.getPosition());
    }
    snakeHead = new SnakeHead(new Point(properties.getSnakeSize() - 1, 0));
    addChange(snakeHead, snakeHead.getPosition());
    pushChange();
  }

  @Override
  public void run() {
    while (world.isRunned()) {
      move();
      try {
        Thread.sleep(properties.getSnakeSleep());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void pushChange() {
    world.moveElements(elements, pointList);
    elements = new ArrayList<>();
    pointList = new ArrayList<>();
  }

  private void addChange(Element element, Point newPosition) {
    elements.add(element);
    pointList.add(newPosition);
  }

  public void addBodySegment() {
    addBodyCount++;
  }

  private void move() {
    changeDirection();
    moveHead();
    pushChange();
  }

  private void moveHead() {
    Point newHeadPosition = (Point) snakeHead.getPosition().clone();
    Point oldHeadPosition = (Point) snakeHead.getPosition().clone();
    newHeadPosition.translate(dir.x, dir.y);
    addChange(snakeHead,newHeadPosition);
    //if (world.moveElement(snakeHead, newHeadPosition)) {
      moveBody(oldHeadPosition);
    //}
  }

  private void moveBody(Point position) {
    Point newTailPosition = (Point) snakeBodyList.getLast().getPosition().clone();
    if (addBodyCount > 0) {
      SnakeBody snakeBody = new SnakeBody(position);
      snakeBodyList.addFirst(snakeBody);
      addChange(snakeBody,snakeBody.getPosition());
      //world.moveElement(snakeBody, snakeBody.getPosition());
      addBodyCount--;
    } else {
      if (!snakeBodyList.isEmpty()) {
        snakeBodyList.addFirst(snakeBodyList.getLast());
        snakeBodyList.removeLast();
        addChange(snakeBodyList.getFirst(),position);
        //world.moveElement(snakeBodyList.getFirst(), position);
      }
      addChange(snakeTail,newTailPosition);
      world.moveElement(snakeTail, newTailPosition);
    }
  }

  private void changeDirection() {
    Point oldDir = (Point) dir.clone();
    if (world.getDirection() == Direction.LEFT) {
      dir.x = oldDir.y;
      dir.y = oldDir.x * -1;
      world.changeDirection(Direction.NONE);
    } else if (world.getDirection() == Direction.RIGHT) {
      dir.x = oldDir.y * -1;
      dir.y = oldDir.x;
      world.changeDirection(Direction.NONE);
    }
  }
}
