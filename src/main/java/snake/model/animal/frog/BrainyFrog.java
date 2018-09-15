package snake.model.animal.frog;

import snake.Properties;
import snake.model.animal.elements.FrogBody;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BrainyFrog<T extends FrogBody> extends Frog {

  private static final int MAX_PERCENT = 100;
  private Point snakeHeadPosition;

  BrainyFrog(Properties properties, T frogBody, Point snakeHeadPosition) {
    super(properties, frogBody);
    this.snakeHeadPosition = snakeHeadPosition;
  }

  @Override
  protected Point move(List freePositions) {
    List<Point> pointList = freePositions;
    Random random = new Random();
    if (random.nextInt(MAX_PERCENT) < 0.8 * MAX_PERCENT) {
      Collections.sort(pointList, new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
          if (snakeHeadPosition.distance(o1) > snakeHeadPosition.distance(o2))
            return (int) snakeHeadPosition.distance(o1);
          else
            return (int) snakeHeadPosition.distance(o2);
        }
      });
      for (int i = 0; i < pointList.size(); i++) {
        if (snakeHeadPosition.distance(pointList.get(0)) > snakeHeadPosition.distance(pointList.get(i))) {
          pointList.remove(i);
        }
      }
      return super.move(pointList);
    } else {
      return super.move(freePositions);
    }
  }
}
