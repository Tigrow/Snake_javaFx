package snake.model.animal.elements.frog;

import javafx.scene.paint.Color;

import java.awt.*;

public class RedFrogBody extends FrogBody {
  public RedFrogBody(Point position) {
    super(position);
    this.color = Color.RED;
  }

  public RedFrogBody() {
    this.color = Color.RED;
  }

  @Override
  public void setDefaultColor() {
    this.color = Color.RED;
  }
}
