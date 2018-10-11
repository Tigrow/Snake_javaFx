package snake.model;

import java.util.Observable;

import snake.model.animal.elements.Element;

/**
 * Абстрактный класс основанный на Observable характиризующий
 * основные игровые действия которые можно вызвать.
 */
public abstract class ObservableWorld extends Observable {
  /**
   * Получить игровое поле.
   *
   * @return - игровое поле.
   */
  public abstract Element[][] getElements();

  /**
   * Получить счёт игры.
   *
   * @return - счёт игры.
   */
  public abstract int getScore();

  /**
   * Вызов старта игры или возобновление игры.
   */
  public abstract void startGame();

  /**
   * Остановка игры.
   */
  public abstract void stopGame();

  /**
   * Загрузка игровых объектов.
   */
  public abstract void loadGame();

  /**
   * Методо передающий изменение направения.
   *
   * @param direction - направление.
   */
  public abstract void changeDirection(Direction direction);

  /**
   *  ставит игру на пазу.
   */
  public abstract void pauseGame();

  public abstract boolean isPaused();
}
