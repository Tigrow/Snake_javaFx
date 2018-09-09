package snake.view;

import snake.model.animal.elements.Element;

import java.util.List;

public interface IMainView {
  void addElement(List<Element> list);

  void moveElement(List<Element> list);

  void deleteElement();

  void setSceen(int width, int height);

  void setScore(int score);

  void showGameOver();

  void DisableStartButton();

  void EnableStartButtor();
}
