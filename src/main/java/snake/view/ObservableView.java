package snake.view;

import java.util.Observable;

import snake.model.animal.elements.Element;

/**
 * Абстрактный класс основанный на Observable для реализации отображения.
 */
public abstract class ObservableView extends Observable {
  /**
   * Метод устанавлювающий значение счёта.
   *
   * @param score - счёт игры
   */
  public abstract void setScore(int score);

  /**
   * Метод устанавливающий размер игрового поля.
   *
   * @param width  - ширина игрового поля.
   * @param height - высота игрового поля.
   */
  public abstract void loadScreen(int width, int height);

  /**
   * Метод обновления карты элементов.
   *
   * @param elements - элементы на основании которых обновляется карта.
   */
  public abstract void updateMap(Element[][] elements);

  /**
   * Метод сообщает что создана новая игра.
   */
  public abstract void newGame();

  /**
   * Метод сообщает что игра остановлена.
   */
  public abstract void gameStopped();

  /**
   * Метод сообщает что игра началась.
   */
  public abstract void gameStarted();

  /**
   * Метод который выводит сообщение об ошибке.
   */
  public abstract void showError();
}
