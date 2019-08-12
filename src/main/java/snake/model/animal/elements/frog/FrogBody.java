package snake.model.animal.elements.frog;

import snake.model.animal.elements.Element;

import java.awt.*;

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

  public abstract void setDefaultColor();
}
