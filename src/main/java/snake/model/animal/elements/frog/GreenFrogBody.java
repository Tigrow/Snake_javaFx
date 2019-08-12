package snake.model.animal.elements.frog;

import javafx.scene.paint.Color;

import java.awt.*;

/**
 * Класс характеризующий тело зелёной лягушки.
 */
public class GreenFrogBody extends FrogBody{

  public GreenFrogBody() {
    this.color = Color.GREEN;
  }

  @Override
  public void setDefaultColor() {
    this.color = Color.GREEN;
  }

  public GreenFrogBody(Point position) {
    super(position);
    this.color = Color.GREEN;
  }
}
