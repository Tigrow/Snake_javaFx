package snake.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import snake.Properties;
import snake.model.animal.BrainyFrog;
import snake.model.animal.Frog;
import snake.model.animal.Snake;
import snake.model.animal.elements.Element;
import snake.model.animal.elements.Wall;
import snake.model.animal.elements.frog.FrogBody;
import snake.model.animal.elements.frog.GreenFrogBody;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeHead;

/**
 * Основной класс описывающий весь мир игры змейка.
 */
public class World extends ObservableWorld {
  private final Element[][] elements;
  private Snake snake;
  private boolean running = false;
  private HashMap<FrogBody, Frog> frogs;
  private Thread snakeThread;
  private List<Thread> frogThreads;
  private Properties properties;
  private int score = 0;

  /**
   * Инициализация модели с параметрами.
   *
   * @param properties - параметры для модели.
   */
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
    addFrogs();
  }

  private void addFrogs() {
    for (int i = 0; i < properties.getGreenFrogNumber(); i++) {
      FrogBody frogBody = new GreenFrogBody();
      BrainyFrog<FrogBody> frog = new BrainyFrog<>(frogBody, properties.getSnakeSleep() * 2,
              this, snake.getSnakeHeadPosition(), properties.getFrogIq());
      Thread frogThread = new Thread(frog);
      frogThread.setDaemon(true);
      frogThreads.add(frogThread);
      frogs.put(frogBody, frog);
    }
  }

  public Element[][] getElements() {
    return elements;
  }

  public int getScore() {
    return score;
  }

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
    snake.setChangeDirection(direction);
  }

  @Override
  public void stopGame() {
    running = false;
  }

  /**
   * Перемещение элемента мира на новую позицию.
   * Если в целевой клетке находиться какой либо объект отличный
   * от null, то перемещение не происходит а метод возвращает false.
   *
   * @param element     - Элемент который надо переместить.
   * @param newPosition - позиция на которую надо переместить элемент.
   * @return - если перемещение произошло, то возвращает true
   */
  public boolean moveElement(Element element, Point newPosition) {
    synchronized (elements) {
      boolean move = collision(element, newPosition);
      if (move) {
        if (elements[element.getPosition().x][element.getPosition().y] == element) {
          elements[element.getPosition().x][element.getPosition().y] = null;
        }
        element.setPosition(newPosition);
        elements[newPosition.x][newPosition.y] = element;
        setChanged();
        notifyObservers(WorldChange.ELEMENT_MOVED);
      }
      return move;
    }
  }

  private Element getElementByPosition(Point point) {
    return getElementByPosition(point.x, point.y);
  }

  private Element getElementByPosition(int x, int y) {
    if (canMoveTo(x, y)) {
      return elements[x][y];
    } else {
      return new Wall(new Point(x, y));
    }
  }

  private boolean canMoveTo(int x, int y) {
    return (x < properties.getWidthSize()
            && y < properties.getHeightSize()
            && x >= 0
            && y >= 0);
  }

  protected boolean collision(Element element, Point newPosition) {
    boolean alive = true;
    Element elementByPosition = getElementByPosition(newPosition);
    if (element instanceof SnakeHead) {
      if (elementByPosition instanceof Wall) {
        alive = false;
        running = false;
        setChanged();
        notifyObservers(WorldChange.GAME_OVER);
      } else if (elementByPosition instanceof GreenFrogBody) {
        frogs.get(elementByPosition).kill();
        snake.addBodySegment();
        scorePlus();
      } else if (elementByPosition instanceof SnakeBody) {
        alive = false;
        running = false;
        setChanged();
        notifyObservers(WorldChange.GAME_OVER);
      }
    }
    return alive;
  }

  private void scorePlus() {
    score++;
    setChanged();
    notifyObservers(WorldChange.SCORE_CHANGED);
  }

  /**
   * Метод вычисляет все пустые клетки на карте.
   *
   * @return - возвращает коллекцию позиций пустых клеток.
   */
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

  /**
   * Данный метод на основании входной позиции, возвращает
   * свободные позиции свободных клеток вокруг него.
   *
   * @param position - позиция вокруг которой проверяются точки
   * @return - возвращает коллекцию позиций на карте не занятых элементами
   */
  public List<Point> getFreePosition(Point position) {
    List<Point> freePosition = new ArrayList<>();
    if (getElementByPosition(position.x - 1, position.y) == null) {
      freePosition.add(new Point(position.x - 1, position.y));
    }
    if (getElementByPosition(position.x + 1, position.y) == null) {
      freePosition.add(new Point(position.x + 1, position.y));
    }
    if (getElementByPosition(position.x, position.y + 1) == null) {
      freePosition.add(new Point(position.x, position.y + 1));
    }
    if (getElementByPosition(position.x, position.y - 1) == null) {
      freePosition.add(new Point(position.x, position.y - 1));
    }
    return freePosition;
  }
}
