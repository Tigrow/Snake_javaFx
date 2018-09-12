package snake.controller;

import snake.model.animal.elements.Element;

public interface IControllerModel {

  void setSceen(int width, int height);

  void updateElement(Element element, Changer changer);

  void gameOver();

  void updateScore(int score);
}
