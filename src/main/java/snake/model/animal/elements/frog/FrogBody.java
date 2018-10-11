package snake.model.animal.elements.frog;

import java.awt.Point;

import snake.model.animal.elements.Element;

/**
 * Абстрактный класс характеризующий абстрактную сущность тело лягушки
 * с позицией в мире.
 */
public abstract class FrogBody extends Element {
  /**
   * Инициализация тела лягушки с определённой позицией.
   *
   * @param position - позиция.
   */
  public FrogBody(Point position) {
    super(position);
  }

  /**
   * Инициализация тела лягушки с позициейн (0,0).
   */
  FrogBody() {
    super();
  }
}
