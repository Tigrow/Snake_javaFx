package snake.model.animal.frog;

import snake.Properties;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import snake.model.animal.elements.FrogBody;

class Frog<T extends FrogBody> {
  protected T body;
  protected Properties properties;

  protected T getFrogBody() {
    return body;
  }

  Frog(Properties properties, T frogBody) {
    this.properties = properties;
    body = frogBody;
  }

  protected Point move(List<Point> freePositions) {
    Random random = new Random();
    return freePositions.get(random.nextInt(freePositions.size()));
  }

  protected Point resetPosition(List<Point> freePositions) {
    return move(freePositions);
  }

}