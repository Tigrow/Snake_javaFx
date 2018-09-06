package snake.model.elements;

import java.awt.*;

public abstract class Element {
  protected Point position;

  public Element(Point position) {
    this.position = position;
  }

  public void setPosition(Point position) {
    this.position.x = position.x;
    this.position.y = position.y;
  }

  public Point getPosition() {
    return position;
  }
}
