package snake.model;

import snake.model.animal.elements.Element;

import java.awt.*;

public interface IWorldAnimal {

  boolean moveElement(Element element, Point newPosition);

  void addElement(Element element);

  void deleteElement(Element element);

  boolean isRunned();
}
