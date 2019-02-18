package snake.model.animal.elements;

import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.Objects;

/**
 * Абстрактный класс характеризующий все возможные объекты в карте модели.
 */
public abstract class Element {
  private Point position;
  protected Color color;


  public Element(Point position, Color color) {
    this.position = position;
    this.color = color;
  }

  /**
   * Инициализация позиция объекта.
   *
   * @param position - позиция.
   */
  protected Element(Point position) {
    this.position = position;
  }

  /**
   * Инициализация позиция объекта.
   */
  protected Element() {
    this.position = new Point();
  }

  /**
   * Установка позиции.
   *
   * @param position - позиция.
   */
  public void setPosition(Point position) {
    this.position.x = position.x;
    this.position.y = position.y;
  }

  public Point getPosition() {
    return position;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else {
      return false;
    }
  }
}
