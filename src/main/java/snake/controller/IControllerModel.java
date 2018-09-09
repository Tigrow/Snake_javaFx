package snake.controller;

import snake.model.animal.elements.Element;

import java.util.List;

public interface IControllerModel {

  void setSceen(int width, int height);

  void updateAll(List<Element> addElements, List<Element> deleteElements, List<Element> moveElements);

  void gameOver();

  void updateScore(int score);
}
