package snake.model.animal.elements.snake;

import java.awt.Point;
import snake.model.animal.elements.Element;

public abstract class SnakeDetails extends Element {
  SnakeDetails(Point position) {
    super(position);
  }

  SnakeDetails() {
    super();
  }
}
