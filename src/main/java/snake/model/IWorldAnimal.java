package snake.model;

import snake.model.animal.elements.Element;

import java.awt.Point;

public interface IWorldAnimal {

  boolean moveElement(Element element, Point newPosition);

  boolean isRunned();
}
