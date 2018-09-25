package snake.model.animal.elements;

import java.awt.Point;

/**
 * Абстрактный класс характеризующий все возможные объекты в карте модели.
 */
public abstract class Element {
  private Point position;

  /**
   * Инициализация позиция объекта.
   *
   * @param position - позиция.
   */
  public Element(Point position) {
    this.position = position;
  }

  /**
   * Инициализация позиция объекта.
   */
  public Element() {
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
}
