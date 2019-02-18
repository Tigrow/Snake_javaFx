package snake.model.animal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.scene.paint.Color;
import snake.Properties;
import snake.model.Direction;
import snake.model.World;
import snake.model.animal.elements.frog.FrogBody;
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
  private FrogBody frogBody;
  private Color color;

  /**
   * Инициализация начального положения змеи.
   *
   * @param properties - настройки мира.
   * @param world      - экземпляр мира.
   */

  public Snake(Properties properties, World world, int y, Color color) {
    this.properties = properties;
    this.world = world;
    this.snakeBodyList = new LinkedList<>();
    this.color = color;
    snakeTail = new SnakeTail(new Point(0, y), color);
    world.moveElement(snakeTail, snakeTail.getPosition());
    for (int i = 1; i <= properties.getSnakeSize() - 2; i++) {
      SnakeBody snakeBody = new SnakeBody(new Point(i, y), color);
      snakeBodyList.addFirst(snakeBody);
      world.moveElement(snakeBody, snakeBody.getPosition());
    }
    snakeHead = new SnakeHead(new Point(properties.getSnakeSize() - 1, y), color);
    world.moveElement(snakeHead, snakeHead.getPosition());
  }

  @Override
  public void run() {
    while (world.isRunning()) {
      long delay = System.currentTimeMillis();
      if (!world.isPaused()) {
        move();
      }
      delay = System.currentTimeMillis() - delay;
      try {
        Thread.sleep(properties.getSnakeSleep() - delay > 0 ? properties.getSnakeSleep() - delay : 0);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Метод добавляющий элемент к змее.
   */
  public void addBodySegment() {
    frogBody = world.getRandomFrog();
    addBodyCount++;
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
    frogBody = world.getRandomFrog();
    if (snakeBodyList.size() + 2 > properties.getSnakeSize()) {
      world.clearPosition(snakeBodyList.getLast().getPosition());
      snakeBodyList.removeLast();
      world.moveElement(snakeTail, snakeBodyList.getLast().getPosition());
    }
  }

  private void move() {
//    changeDirection();
    intellectualMove();
    moveHead();
  }

  private void intellectualMove() {
    List<Point> points = world.getFreePositionWithoutFrog(snakeHead.getPosition());
    if (points.size() != 0) {
      points.sort((Point o1, Point o2) ->
              (int) (frogBody.getPosition().distance(o1) - frogBody.getPosition().distance(o2)));
      points.sort(Comparator.comparingInt(this::square).reversed());
      Point point;
      point = points.get(0);
      dir = new Point(point.x - snakeHead.getPosition().x, point.y - snakeHead.getPosition().y);
    }
  }

  private int square(Point point) {
    HashSet<Point> pointList = new HashSet<>();
    pointList.add(point);
    numberOfCall = 0;
    long start = System.currentTimeMillis();
    pointList = square3(new HashSet<>(),pointList);
//    pointList = square2(pointList);
    System.out.println(numberOfCall + " вызовов за: " + (System.currentTimeMillis() - start) + " мс");
    return pointList.size();
  }

  private int numberOfCall = 0;

  private HashSet<Point> square2(HashSet<Point> points) {
    numberOfCall++;
    HashSet<Point> pointList12 = new HashSet<>(points);
    int size = points.size();
    pointList12 = (HashSet<Point>) pointList12
            .stream()
            .flatMap(point -> {
              List<Point> pointList = world.getFreePositionWithoutFrog(point);
              pointList.removeAll(points);
              return pointList.stream();
            }).collect(Collectors.toSet());
    if (pointList12.size() > 0) {
      pointList12.addAll(points);
      return square2(pointList12);
    } else {
      pointList12.addAll(points);
      return pointList12;
    }
  }

  private HashSet<Point> square3(HashSet<Point> memory, HashSet<Point> points) {
    numberOfCall++;
    int size = points.size();
    points = (HashSet<Point>) points
            .stream()
            .flatMap(point -> {
              List<Point> pointList = world.getFreePositionWithoutFrog(point);
              return pointList.stream();
            }).collect(Collectors.toSet());
    points.removeAll(memory);
    if (points.size() > 0) {
      memory.addAll(points);
      return square3(memory, points);
    } else {
      memory.addAll(points);
      return memory;
    }
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
      SnakeBody snakeBody = new SnakeBody(position, color);
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

  public SnakeHead getSnakeHead() {
    return snakeHead;
  }
}
