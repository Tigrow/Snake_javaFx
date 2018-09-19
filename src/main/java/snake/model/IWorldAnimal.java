package snake.model;

import java.awt.Point;

import snake.model.animal.elements.Element;


public interface IWorldAnimal {

  boolean moveElement(Element element, Point newPosition);

  boolean isRunning();
}
