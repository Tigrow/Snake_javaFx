package snake.model;

import snake.model.elements.Element;

import java.awt.*;

public interface IWorld {
  /*
  * TODO переписать данный интерфейс
  * */
  Element moveElement(Element element, Point oldPoint);

  void addElement(Element element);
}
