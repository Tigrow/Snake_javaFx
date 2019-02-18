package snake.model.animal.elements.snake;

import javafx.scene.paint.Color;

import java.awt.Point;

public class SnakeTail extends SnakeDetails {
  public SnakeTail(Point position, Color color) {
    super(position, color);
  }

  public SnakeTail(Point position) {
    super(position);
  }

  public SnakeTail() {
    super();
  }
}
