package snake.model.animal;

import java.awt.Point;
import java.util.*;

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
    comparator = (o1, o2) -> {
      if (snakeHeadPosition.distance(o1) > snakeHeadPosition.distance(o2)) {
        return (int) snakeHeadPosition.distance(o1);
      } else {
        return (int) snakeHeadPosition.distance(o2);
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
      List<Point> removePointList = new ArrayList<>();
      pointList.sort(comparator);
      if (pointList.size() > 0) {
        for (Point point : pointList) {
          if (snakeHeadPosition.distance(pointList.get(0)) > snakeHeadPosition.distance(point)) {
            removePointList.add(point);
          }
        }
        pointList.removeAll(removePointList);
        world.moveElement(getFrogBody(), pointList.get(random.nextInt(pointList.size())));
      }
    }
  }
}
