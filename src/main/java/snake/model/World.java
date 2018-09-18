package snake.model;

import snake.Properties;
import snake.controller.IControllerModel;
import snake.model.animal.Frog;
import snake.model.animal.elements.*;
import snake.model.animal.Snake;

import snake.model.animal.elements.frog.FrogBody;
import snake.model.animal.elements.frog.GreenFrogBody;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeHead;
import snake.model.animal.elements.snake.SnakeTail;

import java.awt.Point;
import java.util.*;

public class World extends Observable implements IWorld, IWorldAnimal, IWorldSnake, IWorldFrog {
  private Element[][] elements;
  private Snake snake;
  private boolean isRunned = false;
  private HashMap<FrogBody, Frog> frogs;
  private Thread snakeThread;
  private List<Thread> frogThreads;
  private IControllerModel controller;
  private Direction dir = Direction.NONE;
  //private FrogController frogController;
  private Properties properties;
  private int score = 0;

  public World(Properties properties) {
    this.properties = properties;
    elements = new Element[properties.getWidthSize()][properties.getHeightSize()];
    frogs = new HashMap<>();
    frogThreads = new ArrayList<>();
    //frogController = new FrogController(properties, this);
    //    //frogThread = new Thread(frogController);
    //    //frogThread.setDaemon(true);
    //controller.setSceen(properties.getWidthSize(), properties.getHeightSize());
  }

  @Override
  public void loadGame() {
    snake = new Snake(properties, this);
    snakeThread = new Thread(snake);
    snakeThread.setDaemon(true);
    for (int i = 0; i < properties.getFrogNumber(); i++) {
      addFrogs();
    }
  }

  private void addFrogs() {
    FrogBody frogBody = new GreenFrogBody();
    Frog frog = new Frog(frogBody, properties.getSnakeSleep() * 2, this);
    Thread frogThread = new Thread(frog);
    frogThread.setDaemon(true);
    frogThreads.add(frogThread);
    frogs.put(frogBody, frog);
  }

  public Element[][] getElements() {
    return elements;
  }

  public int getScore() {
    return score;
  }

  @Override
  public boolean isRunned() {
    return isRunned;
  }


  @Override
  public void startGame() {
    isRunned = true;
    snakeThread.start();
    for (Thread frogThread : frogThreads) {
      frogThread.start();
    }
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

  public synchronized void moveElements(List<Element> elementList, List<Point> newPositions) {
    synchronized (elements) {
      for (int i = 0; i < elementList.size(); i++) {
        if (!moveElement(elementList.get(i), newPositions.get(i))) {
          return;
        }
      }
    }
  }

  @Override
  public synchronized boolean moveElement(Element element, Point newPosition) {
    synchronized (elements) {
      boolean move = collision(element, newPosition);
      if (move) {
        elements[element.getPosition().x][element.getPosition().y] = null;
        element.setPosition(newPosition);
        elements[newPosition.x][newPosition.y] = element;
        setChanged();
        notifyObservers();
      }
      return move;
    }
  }

  public synchronized boolean moveRandomPosition(Element element) {
    synchronized (elements) {
      List<Point> positions = getAllFreePosition();
      if (positions.size() > 0) {
        Random random = new Random();
        Point position = positions.get(random.nextInt(positions.size()));
        element.setPosition(position);
        elements[position.x][position.y] = element;
        setChanged();
        notifyObservers();
        return true;
      } else {
        return false;
      }
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
    if (element instanceof SnakeHead) {
      if (elementByPosition instanceof Death) {
        isAlive = false;
        isRunned = false;
        //controller.gameOver();
      } else if (elementByPosition instanceof GreenFrogBody) {
        frogs.get(elementByPosition).resetPosition();
        snake.addBodySegment();
        scorePlus();
      } else if (elementByPosition instanceof SnakeBody) {
        isAlive = false;
        isRunned = false;
        //controller.gameOver();
      }
    }
    return isAlive;
  }

  private void scorePlus() {
    score++;
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