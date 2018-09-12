package snake.view;

import snake.model.animal.elements.Element;

public interface IMainView {

  void addElement(Element element);

  void moveElement(Element element);

  void deleteElement(Element element);

  void setSceen(int width, int height);

  void setScore(int score);

  void showGameOver();

  void disableStartButton();

  void enableStartButton();

  void disableStopButton();

  void enableStopButton();

  void changeTextStartButtonToNew();

  void changeTextStartButtonToStart();
}
