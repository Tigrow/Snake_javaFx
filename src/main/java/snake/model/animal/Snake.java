package snake.model.animal;

import java.awt.Point;
import java.util.LinkedList;

import snake.Properties;
import snake.model.World;
import snake.model.animal.elements.Direction;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeHead;
import snake.model.animal.elements.snake.SnakeTail;

public class Snake implements Runnable {
  private Properties properties;
  private final World world;
  private SnakeHead snakeHead;
  private SnakeTail snakeTail;
  private LinkedList<SnakeBody> snakeBodyList;
  private Point dir = new Point(1, 0);
  private Direction changeDirection;
  private int addBodyCount = 0;

  public Snake(Properties properties, World world) {
    this.properties = properties;
    this.world = world;
    this.snakeBodyList = new LinkedList<>();
    snakeTail = new SnakeTail(new Point(0, 0));
    world.moveElement(snakeTail, snakeTail.getPosition());
    for (int i = 1; i <= properties.getSnakeSize() - 2; i++) {
      SnakeBody snakeBody = new SnakeBody(new Point(i, 0));
      snakeBodyList.addFirst(snakeBody);
      world.moveElement(snakeBody, snakeBody.getPosition());
    }
    snakeHead = new SnakeHead(new Point(properties.getSnakeSize() - 1, 0));
    world.moveElement(snakeHead, snakeHead.getPosition());
  }

  @Override
  public void run() {
    while (world.isRunning()) {
      move();
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

  public void setChangeDirection(Direction direction) {
    changeDirection = direction;
  }

  private void move() {
    changeDirection();
    moveHead();
  }

  private void moveHead() {
    Point newHeadPosition = (Point) snakeHead.getPosition().clone();
    Point oldHeadPosition = (Point) snakeHead.getPosition().clone();
    newHeadPosition.translate(dir.x, dir.y);
    synchronized (world) {
      if (world.moveElement(snakeHead, newHeadPosition)) {
        moveBody(oldHeadPosition);
      }
    }

  }

  private void moveBody(Point position) {
    Point newTailPosition = (Point) snakeBodyList.getLast().getPosition().clone();
    if (addBodyCount > 0) {
      SnakeBody snakeBody = new SnakeBody(position);
      snakeBodyList.addFirst(snakeBody);
      world.moveElement(snakeBody, snakeBody.getPosition());
      addBodyCount--;
    } else {
      if (!snakeBodyList.isEmpty()) {
        snakeBodyList.addFirst(snakeBodyList.getLast());
        snakeBodyList.removeLast();
        world.moveElement(snakeBodyList.getFirst(), position);
      }
      world.moveElement(snakeTail, newTailPosition);
    }
  }

  private void changeDirection() {
    Point oldDir = (Point) dir.clone();
    if (changeDirection == Direction.LEFT) {
      dir.x = oldDir.y;
      dir.y = oldDir.x * -1;
      changeDirection = Direction.NONE;
    } else if (changeDirection == Direction.RIGHT) {
      dir.x = oldDir.y * -1;
      dir.y = oldDir.x;
      changeDirection = Direction.NONE;
    }
  }
}
