package snake.model.animal;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import snake.model.World;
import snake.model.animal.elements.frog.FrogBody;

public class Frog<T extends FrogBody> implements Runnable {
  private T body;
  private int sleep;
  private  final World world;

  public T getFrogBody() {
    return body;
  }

  public Frog(T frogBody, int sleep, World world) {
    this.world = world;
    this.sleep = sleep;
    body = frogBody;
    resetPosition();
  }

  public void resetPosition() {
    Random random = new Random();
    synchronized (world) {
      List<Point> positions = world.getAllFreePosition();
      if (positions.size() > 0) {
        Point point = positions.get(random.nextInt(positions.size()));
        world.moveElement(body, point);
      }
    }
  }


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
      move();
      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
