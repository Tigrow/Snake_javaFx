package snake.model.animal.elements;

import java.awt.Point;

public abstract class Element {
  private Point position;

  public Element(Point position) {
    this.position = position;
  }

  public Element() {
    this.position = new Point();
  }

  public void setPosition(Point position) {
    this.position.x = position.x;
    this.position.y = position.y;
  }

  public Point getPosition() {
    return position;
  }
}
