package snake.model;

import snake.model.elements.Element;

import java.awt.*;

public class World implements IWorld{
  private Element[][] elements;
  private Snake snake;

  public World(Element[][] elements){
      this.elements = elements;
      snake = new Snake(10,this);
  }
    public void move(){
        snake.move();
    }
    @Override
    public void moveElement(Element element, Point oldPoint){
      elements[oldPoint.x][oldPoint.y] = null;
      addElement(element);
    }

    @Override
    public void addElement(Element element) {
      elements[element.getPosition().x][element.getPosition().y] = element;
    }
}