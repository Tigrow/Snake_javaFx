package snake.controller;

import snake.model.elements.Element;

import java.util.List;

public interface IControllerModel {
  public void setSceen(int width, int height);

  void updateAll(List<Element> addElements, List<Element> deleteElements, List<Element> moveElements);
}
