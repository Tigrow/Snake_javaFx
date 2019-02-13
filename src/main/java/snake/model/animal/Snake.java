package snake.model.animal;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import snake.Properties;
import snake.model.Direction;
import snake.model.World;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeHead;
import snake.model.animal.elements.snake.SnakeTail;

/**
 * Класс характеризующий змею и её поведение в мире.
 */
public class Snake implements Runnable {
  private Properties properties;
  private final World world;
  private SnakeHead snakeHead;
  private SnakeTail snakeTail;
  private LinkedList<SnakeBody> snakeBodyList;
  private Point dir = new Point(1, 0);
  private Direction changeDirection;
  private int addBodyCount = 0;

  /**
   * Инициализация начального положения змеи.
   *
   * @param properties - настройки мира.
   * @param world      - экземпляр мира.
   */
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
      if (!world.isPaused()) {
        move();
      }
      try {
        Thread.sleep(properties.getSnakeSleep());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public Point getSnakeHeadPosition() {
    return snakeHead.getPosition();
  }

  /**
   * Метод добавляющий элемент к змее.
   */
  public void addBodySegment() {
    addBodyCount++;
    System.out.println("snakeSize = " + snakeBodyList.size());

  }

  /**
   * Установка изменения направления.
   *
   * @param direction - направление.
   */
  public void setChangeDirection(Direction direction) {
    changeDirection = direction;
  }

  /**
   * Метод удаляющий последний элемент змеи, и ставящий на это место хвост.
   */
  public void deleteBodySegment() {
    if (snakeBodyList.size() + 2 > properties.getSnakeSize()) {
      world.clearPosition(snakeBodyList.getLast().getPosition());
      snakeBodyList.removeLast();
      world.moveElement(snakeTail, snakeBodyList.getLast().getPosition());
      System.out.println("snakeSize = " + snakeBodyList.size());
    }
  }

  private void move() {
//    changeDirection();
    intellictualMove();
    moveHead();
  }

  private void intellictualMove() {
    Random random = new Random();
    List<Point> points = world.getFreePositionWithoutFrog(snakeHead.getPosition());
    if (points.size() != 0) {
      Point point = points.get(random.nextInt(points.size()));
      dir = new Point(point.x - snakeHead.getPosition().x, point.y - snakeHead.getPosition().y);
    } else {
      points = world.getFreePositionWithoutFrogAndBody(snakeHead.getPosition());
      Point point = points.get(random.nextInt(points.size()));
      dir = new Point(point.x - snakeHead.getPosition().x, point.y - snakeHead.getPosition().y);
    }
//    if (point.x == snakeHead.getPosition().x && point.y > snakeHead.getPosition().y ||
//        point.y == snakeHead.getPosition().y && point.x > snakeHead.getPosition().x) {
//
//    } else if (point.x == snakeHead.getPosition().x && point.y < snakeHead.getPosition().y ||
//               point.y == snakeHead.getPosition().y && point.x < snakeHead.getPosition().x) {
//      changeDirection = Direction.RIGHT;
//    } else {
//      changeDirection = Direction.NONE;
//    }
    //changeDirection();
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
