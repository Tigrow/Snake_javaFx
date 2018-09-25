package snake.model.animal;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import snake.model.World;
import snake.model.animal.elements.frog.FrogBody;

/**
 * Класс характеризующий поведение лягушки, и реализующий
 * Runnable для запуска в отдельном потоке.
 *
 * @param <T> - Тип тела которым характеризуется лягушка.
 */
public class Frog<T extends FrogBody> implements Runnable {
  private T body;
  private int sleep;
  protected final World world;
  private boolean alive;

  /**
   * Получить тело данной лягушки.
   *
   * @return - возвращает тело.
   */
  public T getFrogBody() {
    return body;
  }

  /**
   * Инициализация лягушки.
   *
   * @param frogBody - тело которое присвоено лягушке.
   * @param sleep    - время засыпания лягушки.
   * @param world    - мир в котором находится лягушка.
   */
  public Frog(T frogBody, int sleep, World world) {
    this.world = world;
    this.sleep = sleep;
    body = frogBody;
    resetPosition();
  }

  /**
   * Устанавливает статус лягушки - убита. После чего, лягушка будет пытаться возродить себя.
   */
  public void kill() {
    alive = false;
  }

  private void resetPosition() {
    Random random = new Random();
    synchronized (world) {
      List<Point> positions = world.getAllFreePosition();
      if (positions.size() > 0) {
        Point point = positions.get(random.nextInt(positions.size()));
        world.moveElement(body, point);
        alive = true;
      }
    }
  }

  /**
   * Метод вызывающийся когда ляшушка должна быть перемещена.
   */
  protected void move() {
    Random random = new Random();
    synchronized (world) {
      List<Point> positions = world.getFreePosition(body.getPosition());
      if (positions.size() > 0) {
        Point point = positions.get(random.nextInt(positions.size()));
        world.moveElement(body, point);
      }
    }
  }

  @Override
  public void run() {
    while (world.isRunning()) {
      if (!world.isPaused()) {
        if (alive) {
          move();
        } else {
          resetPosition();
        }
      }
      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
