package snake.model.animal.elements.snake;

import javafx.scene.paint.Color;

import java.awt.Point;

public class SnakeBody extends SnakeDetails {

  public SnakeBody(Point position, Color color) {
    super(position, color);
  }

  public SnakeBody(Point position) {
    super(position);
  }

  public SnakeBody() {
    super();
  }
}
