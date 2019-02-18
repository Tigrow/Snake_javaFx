package snake.model.animal.elements.snake;

import javafx.scene.paint.Color;

import java.awt.Point;

public class SnakeHead extends SnakeDetails {

  public SnakeHead(Point position, Color color) {
    super(position, color);
  }

  public SnakeHead(Point position) {
    super(position);
  }


  public SnakeHead() {
    super();
  }
}
