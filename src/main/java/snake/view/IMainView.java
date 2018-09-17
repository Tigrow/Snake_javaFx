package snake.view;

import snake.model.animal.elements.Element;

public interface IMainView {

  void gameStarted();

  void gameStoped();

  void addElement(Element element);

  void moveElement(Element element);

  void deleteElement(Element element);

  void setSceen(int width, int height);

  void setScore(int score);

  void disableStartButton();

  void disableStopButton();

  void enableStopButton();

  void changeTextStartButtonToStart();
}
