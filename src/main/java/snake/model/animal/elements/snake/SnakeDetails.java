package snake.model.animal.elements.snake;

import java.awt.Point;

import javafx.scene.paint.Color;
import snake.model.animal.elements.Element;

public abstract class SnakeDetails extends Element {
  SnakeDetails(Point position, Color color) {
    super(position, color);
  }

  SnakeDetails(Point position) {
    super(position);
    this.color = Color.YELLOW;
  }

  SnakeDetails() {
    super();
  }
}
