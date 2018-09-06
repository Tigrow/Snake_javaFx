package snake.model;

import snake.model.elements.Direction;
import snake.model.elements.Element;

import java.awt.Point;

public interface IWorldAnimal {

  void moveElement(Element element, Point oldPoint);

  Element getElementByPosition(Point point);

  void addElement(Element element);

  void snakeDeath();

  Direction getDirection();

  void changeDirection(Direction direction);
}
