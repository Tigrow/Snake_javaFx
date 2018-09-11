package snake.model;

import snake.model.animal.elements.Direction;
import snake.model.animal.elements.Element;

import java.awt.Point;

public interface IWorldAnimal {

  boolean moveElement(Element element, Point newPosition);

  void addElement(Element element);

  Direction getDirection();

  void update();

  boolean isRunned();

  void changeDirection(Direction direction);
}
