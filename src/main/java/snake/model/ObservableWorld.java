package snake.model;

import java.util.Observable;

import snake.model.animal.elements.Element;

public abstract class ObservableWorld extends Observable {
  public abstract Element[][] getElements();

  public abstract int getScore();

  public abstract void startGame();

  public abstract void stopGame();

  public abstract void loadGame();

  public abstract void changeDirection(Direction direction);
}
