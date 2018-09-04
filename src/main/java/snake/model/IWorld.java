package snake.model;

import snake.model.elements.Element;

import java.awt.*;

interface IWorld {
  boolean moveElement(Element element, Point oldPoint);

  void addElement(Element element);

  boolean isEmptyPosition(Point point);
}
