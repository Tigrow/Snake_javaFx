package snake.view;

import java.util.Observable;

import snake.model.animal.elements.Element;

public abstract class ObservableView extends Observable {

  public abstract void setScore(int score);

  public abstract void loadScreen(int width, int height);

  public abstract void updateMap(Element[][] elements);

  public abstract void newGame();

  public abstract void gameStopped();

  public abstract void gameStarted();

  public abstract void showError();
}
