package snake.model;

import com.sun.istack.internal.NotNull;
import javafx.scene.paint.Color;
import snake.Properties;
import snake.model.animal.BrainyFrog;
import snake.model.animal.Frog;
import snake.model.animal.Snake;
import snake.model.animal.elements.Element;
import snake.model.animal.elements.Wall;
import snake.model.animal.elements.frog.FrogBody;
import snake.model.animal.elements.frog.GreenFrogBody;
import snake.model.animal.elements.frog.RedFrogBody;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeHead;
import snake.model.animal.elements.snake.SnakeTail;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Основной класс описывающий весь мир игры змейка.
 */
public class World extends ObservableWorld {
  private final Element[][] elements;
  private List<Snake> snakes = new ArrayList<>();
  private boolean running = false;
  private boolean paused = false;
  private HashMap<FrogBody, Frog> frogs = new HashMap<>();
  private List<Thread> threads = new ArrayList<>();
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
  }

  @Override
  public void loadGame() {
    Random rand = new Random();
    for (int i = 0; i<=0; i++){
      Snake snake = new Snake(properties, this, 5*i,
              Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
      snakes.add(snake);
      addThread(snake);
    }
    addFrogs();
  }

  private void addFrogs() {
    for (int i = 0; i < properties.getGreenFrogNumber(); i++) {
      FrogBody frogBody = new GreenFrogBody();
      BrainyFrog<FrogBody> frog = new BrainyFrog<>(frogBody, properties.getSnakeSleep() * 2,
              this, snakes.get(0).getSnakeHead().getPosition(), properties.getFrogIq());
      frogs.put(frogBody, frog);
      addThread(frog);
    }
    for (int i = 0; i < properties.getRedFrogNumber(); i++) {
      FrogBody frogBody = new RedFrogBody();
      Frog<FrogBody> frog = new Frog<>(frogBody, properties.getSnakeSleep() * 2, this);
      frogs.put(frogBody, frog);
      addThread(frog);
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
    if (paused) {
      paused = false;
      return;
    }
    running = true;
    threads.forEach(Thread::start);
  }

  @Override
  public void changeDirection(Direction direction) {
    //snake.setChangeDirection(direction);
  }

  @Override
  public void pauseGame() {
    paused = true;
  }

  @Override
  public void stopGame() {
    running = false;
  }

  public boolean isPaused() {
    return paused;
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
  public boolean moveElement(@NotNull Element element, Point newPosition) {
//    synchronized (elements) {
      boolean move = collision(element, newPosition);
      if (move) {
        if (elements[element.getPosition().x][element.getPosition().y] == element) {
          clearPosition(element.getPosition());
          elements[element.getPosition().x][element.getPosition().y] = null;
        }
        element.setPosition(newPosition);
        elements[newPosition.x][newPosition.y] = element;
        setChanged();
        notifyObservers(WorldChange.ELEMENT_MOVED);
      }
      return move;
//    }
  }

  public void clearPosition(Point position) {
    elements[position.x][position.y] = null;
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

      if (elementByPosition instanceof Wall || elementByPosition instanceof SnakeBody) {
        alive = false;
        setChanged();
//        running = false;
//        notifyObservers(WorldChange.GAME_OVER);
        snakes.forEach(snake -> {
          if (snake.getSnakeHead().equals(element)) {
            snake.kill();
          }
        });
      } else if (elementByPosition instanceof GreenFrogBody) {
        if (frogs.containsKey(elementByPosition)) {
          frogs.get(elementByPosition).kill();
        }
        snakes.forEach(snake -> {
          if (snake.getSnakeHead().equals(element)) {
            snake.addBodySegment();
          }
        });
        scorePlus(1);
      } else if (elementByPosition instanceof RedFrogBody) {
        if (frogs.containsKey(elementByPosition)) {
          frogs.get(elementByPosition).kill();
        }
        snakes.forEach(snake -> {
          if (snake.getSnakeHead().equals(element)) {
            snake.deleteBodySegment();
          }
        });
        scorePlus(2);
      }
    }
    return alive;
  }

  private void scorePlus(int additional) {
    score = score + additional;
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

  public List<Point> getFreePositionWithoutFrog(Point position) {
    List<Point> freePosition = new ArrayList<>();
    if (getElementByPosition(position.x - 1, position.y) == null
        || getElementByPosition(position.x - 1, position.y) instanceof FrogBody
        || getElementByPosition(position.x - 1, position.y) instanceof SnakeTail) {
      freePosition.add(new Point(position.x - 1, position.y));
    }
    if (getElementByPosition(position.x + 1, position.y) == null
        || getElementByPosition(position.x + 1, position.y) instanceof FrogBody
        || getElementByPosition(position.x + 1, position.y) instanceof SnakeTail) {
      freePosition.add(new Point(position.x + 1, position.y));
    }
    if (getElementByPosition(position.x, position.y + 1) == null
        || getElementByPosition(position.x, position.y + 1) instanceof FrogBody
        || getElementByPosition(position.x, position.y + 1) instanceof SnakeTail) {
      freePosition.add(new Point(position.x, position.y + 1));
    }
    if (getElementByPosition(position.x, position.y - 1) == null
        || getElementByPosition(position.x, position.y - 1) instanceof FrogBody
        || getElementByPosition(position.x, position.y - 1) instanceof SnakeTail) {
      freePosition.add(new Point(position.x, position.y - 1));
    }
    return freePosition;
  }

  public FrogBody getNearFrog(Point point) {
    return frogs.values()
            .stream()
            .filter(Frog::isAlive)
            .min((f1, f2) -> (int) (f1.getFrogBody().getPosition().distance(point) - f2.getFrogBody().getPosition().distance(point)))
            .get()
            .getFrogBody();
  }

  public FrogBody getNearFrog2(Point point) {
    long time = System.currentTimeMillis();
    Queue<Point> queue = new LinkedList<>();
    List<Point> points = new ArrayList<>();
    queue.add(point);
    points.add(point);
    while (queue.size() != 0) {
      Point pos = queue.remove();
      int x = 1;
      int y = 0;
      for (int i = 0; i < 4; i++) {
        int x1 = y;
        int y1 = x * -1;
        x = x1;
        y = y1;
        Point newPoint = new Point(pos.x + x, pos.y + y);
        if (canMoveTo(newPoint.x, newPoint.y) && !points.contains(newPoint)) {
          if (elements[newPoint.x][newPoint.y] == null) {
            queue.add(newPoint);
            points.add(newPoint);
          } else if (elements[newPoint.x][newPoint.y] instanceof FrogBody) {
            System.out.println(System.currentTimeMillis() - time);
            return (FrogBody) elements[newPoint.x][newPoint.y];
          }
        }
      }
    }
    System.out.println(System.currentTimeMillis() - time);
    return null;
  }

  private void addThread(Runnable runnable){
    Thread thread = new Thread(runnable);
    thread.setDaemon(true);
    thread.setName("Thread_" + runnable.getClass().getSimpleName() + "_" + runnable.hashCode());
    threads.add(thread);

  }
}
