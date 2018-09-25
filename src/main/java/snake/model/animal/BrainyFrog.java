package snake.model.animal;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import snake.model.World;
import snake.model.animal.elements.frog.FrogBody;

public class BrainyFrog<T extends FrogBody> extends Frog {

  private static final int MAX_PERCENT = 100;
  private Point snakeHeadPosition;
  private Comparator<Point> comparator;
  private double frogIq;

  public BrainyFrog(T frogBody, int sleep, World world, final Point snakeHeadPosition, double iq) {
    super(frogBody, sleep, world);
    this.snakeHeadPosition = snakeHeadPosition;
    this.frogIq = iq;
    comparator = new Comparator<Point>() {
      @Override
      public int compare(Point o1, Point o2) {
        if (snakeHeadPosition.distance(o1) > snakeHeadPosition.distance(o2)) {
          return (int) snakeHeadPosition.distance(o1);
        } else {
          return (int) snakeHeadPosition.distance(o2);
        }
      }
    };
  }

  @Override
  protected void move() {
    Random random = new Random();
    if (random.nextInt(MAX_PERCENT) < frogIq * MAX_PERCENT) {
      brainyMove();
    } else {
      super.move();
    }
  }

  private void brainyMove() {
    Random random = new Random();
    synchronized (world) {
      List<Point> pointList = world.getFreePosition(getFrogBody().getPosition());
      Collections.sort(pointList, comparator);
      if (pointList.size() > 0) {
        for (int i = 0; i < pointList.size(); i++) {
          if (snakeHeadPosition.distance(pointList.get(0)) > snakeHeadPosition.distance(pointList.get(i))) {
            pointList.remove(i);
          }
        }
        world.moveElement(getFrogBody(), pointList.get(random.nextInt(pointList.size())));
      }
    }
  }
}
