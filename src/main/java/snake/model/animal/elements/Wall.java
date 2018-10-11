package snake.model.animal.elements;

import java.awt.Point;

/**
 * Элемент характеризующий стену в мире.
 */
public class Wall extends Element {
  /**
   * Инициализация позиция объекта.
   * @param position - позиция.
   */
  public Wall(Point position) {
    super(position);
  }
}
