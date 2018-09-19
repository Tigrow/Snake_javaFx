package snake.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import snake.Properties;
import snake.model.animal.Frog;
import snake.model.animal.Snake;
import snake.model.animal.elements.Death;
import snake.model.animal.elements.Direction;
import snake.model.animal.elements.Element;
import snake.model.animal.elements.frog.FrogBody;
import snake.model.animal.elements.frog.GreenFrogBody;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeDetails;
import snake.model.animal.elements.snake.SnakeHead;


public class World extends ObservableWorld implements IWorld, IWorldAnimal, IWorldSnake, IWorldFrog {
  private final Element[][] elements;
  private Snake snake;
  private boolean running = false;
  private HashMap<FrogBody, Frog> frogs;
  private Thread snakeThread;
  private List<Thread> frogThreads;
  private Direction dir = Direction.NONE;
  private Properties properties;
  private int score = 0;

  public World(Properties properties) {
    this.properties = properties;
    elements = new Element[properties.getWidthSize()][properties.getHeightSize()];
    frogs = new HashMap<>();
    frogThreads = new ArrayList<>();
  }

  @Override
  public void loadGame() {
    snake = new Snake(properties, this);
    snakeThread = new Thread(snake);
    snakeThread.setDaemon(true);
    for (int i = 0; i < properties.getGreenFrogNumber(); i++) {
      addFrogs();
    }
  }

  private void addFrogs() {
    FrogBody frogBody = new GreenFrogBody();
    Frog<FrogBody> frog = new Frog<>(frogBody, properties.getSnakeSleep() * 2, this);
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
  public boolean isRunning() {
    return running;
  }


  @Override
  public void startGame() {
    running = true;
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
    running = false;
  }

  @Override
  public boolean moveElement(Element element, Point newPosition) {
    synchronized (elements) {
      boolean move = collision(element, newPosition);
      if (move) {
        elements[element.getPosition().x][element.getPosition().y] = null;
        element.setPosition(newPosition);
        elements[newPosition.x][newPosition.y] = element;
        setChanged();
        notifyObservers(WorldChange.ELEMENT_MOVED);
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
        running = false;
        setChanged();
        notifyObservers(WorldChange.GAME_OVER);
      } else if (elementByPosition instanceof GreenFrogBody) {
        frogs.get(elementByPosition).resetPosition();
        snake.addBodySegment();
        scorePlus();
      } else if (elementByPosition instanceof SnakeBody) {
        isAlive = false;
        running = false;
        setChanged();
        notifyObservers(WorldChange.GAME_OVER);
      }
    }
    return isAlive;
  }

  private void scorePlus() {
    score++;
    setChanged();
    notifyObservers(WorldChange.SCORE_CHANGED);
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