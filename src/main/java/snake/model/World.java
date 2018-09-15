package snake.model;

import snake.Properties;
import snake.controller.Changer;
import snake.controller.IControllerModel;
import snake.model.animal.elements.*;
import snake.model.animal.Snake;

import snake.model.animal.frog.FrogController;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class World implements IWorld, IWorldAnimal, IWorldSnake, IWorldFrog {
  private Element[][] elements;
  private Snake snake;
  private boolean isRunned = false;
  private Thread snakeThread, frogThread;
  private IControllerModel controller;
  private Direction dir = Direction.None;
  private FrogController frogController;
  private Properties properties;
  private int score = 0;

  public World(IControllerModel controller, Properties properties) {
    this.controller = controller;
    this.properties = properties;
    elements = new Element[properties.getWidthSize()][properties.getHeightSize()];
    snake = new Snake(properties, this);
    frogController = new FrogController(properties, this);
    frogThread = new Thread(frogController);
    frogThread.setDaemon(true);
    snakeThread = new Thread(snake);
    snakeThread.setDaemon(true);
    controller.setSceen(properties.getWidthSize(), properties.getHeightSize());
  }


  @Override
  public boolean isRunned() {
    return isRunned;
  }

  @Override
  public void startGame() {
    isRunned = true;
    snakeThread.start();
    frogThread.start();
  }

  @Override
  public void changeDirection(Direction direction) {
    dir = direction;
  }

  @Override
  public Direction getDirection() {
    return dir;
  }

  @Override
  public void stopGame() {
    isRunned = false;
  }

  @Override
  public synchronized boolean moveElement(Element element, Point newPosition) {
    boolean move = collision(element, newPosition);
    if (move) {
      elements[element.getPosition().x][element.getPosition().y] = null;
      element.setPosition(newPosition);
      elements[newPosition.x][newPosition.y] = element;
      controller.updateElement(element, Changer.move);
    }
    return move;
  }

  @Override
  public synchronized void addElement(Element element) {
    controller.updateElement(element, Changer.add);
  }

  @Override
  public void deleteElement(Element element) {
    if (elements[element.getPosition().x][element.getPosition().y] == element) {
      elements[element.getPosition().x][element.getPosition().y] = null;
      controller.updateElement(element, Changer.delete);
    }
  }

  private Element getElementByPosition(Point point) {
    return getElementByPosition(point.x, point.y);
  }

    private Element getElementByPosition(int x, int y) {
        if (canMoveTo(x, y)) {
            return elements[x][y];
        } else {
            return new Death(new Point(x, y));
        }
    }

  protected boolean canMoveTo(int x, int y) {
    return (x < properties.getWidthSize()
            && y < properties.getHeightSize()
            && x >= 0
            && y >= 0);
  }

  private boolean collision(Element element, Point newPosition) {
    boolean isAlive = true;
    Element elementByPosition = getElementByPosition(newPosition);
    if (element instanceof Head) {
      if (elementByPosition instanceof Death) {
        isAlive = false;
        isRunned = false;
        controller.gameOver();
      } else if (elementByPosition instanceof GreenFrogBody) {
        frogController.resetPostion(elementByPosition);
        snake.addBodySegment();
        scorePlus();
      } else if (elementByPosition instanceof Body) {
        isAlive = false;
        isRunned = false;
        controller.gameOver();
      }
    }
    return isAlive;
  }

  private void scorePlus() {
    score++;
    controller.updateScore(score);
  }

  @Override
  public List<Point> getAllFreePosition() {
    List<Point> positionList = new ArrayList<>();
    for (int x = 0; x < properties.getWidthSize(); x++) {
      for (int y = 0; y < properties.getHeightSize(); y++) {
        if (getElementByPosition(x, y) == null) {
          positionList.add(new Point(x, y));
        }
      }
    }
    return positionList;
  }


  @Override
  public List<Point> getFreePosition(Point position) {
    Point positionLeft = (Point) position.clone();
    positionLeft.translate(-1, 0);
    Point positionRight = (Point) position.clone();
    positionRight.translate(1, 0);
    Point positionUp = (Point) position.clone();
    positionUp.translate(0, 1);
    Point positionDown = (Point) position.clone();
    positionDown.translate(0, -1);

    List<Point> freePosition = new ArrayList<>(4);
    if (getElementByPosition(positionLeft) == null) {
      freePosition.add(positionLeft);
    }
    if (getElementByPosition(positionRight) == null) {
      freePosition.add(positionRight);
    }
    if (getElementByPosition(positionUp) == null) {
      freePosition.add(positionUp);
    }
    if (getElementByPosition(positionDown) == null) {
      freePosition.add(positionDown);
    }
    return freePosition;
  }
}