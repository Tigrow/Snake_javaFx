package snake.model;

import snake.Main;
import snake.Properties;
import snake.controller.IControllerModel;
import snake.model.elements.Death;
import snake.model.elements.Element;

import java.awt.*;

public class World implements IWorld {
  private Element[][] elements;
  private Snake snake;
  private boolean isRunned;
  private Thread snakeThread;
  private IControllerModel controller;

  public World(IControllerModel controller) {
    this.controller = controller;
    //this.controller = controller;
    elements = new Element[Main.properties.widthSize][Main.properties.heightSize];
    snake = new Snake(Main.properties.snakeSize, this);

    snakeThread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (isRunned) {
          snake.move();
          update();
          try {
            Thread.sleep(200);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    snakeThread.setDaemon(true);
  }

  public Element[][] getElements() {
    return elements;
  }

  public void startGame() {
    isRunned = true;
    snakeThread.start();
  }

  private void update() {
    controller.updateMap();
  }

  @Override
  public Element moveElement(Element element, Point oldPoint) {
    if (canMoveTo(element.getPosition())) {
      elements[oldPoint.x][oldPoint.y] = null;
      addElement(element);
    }
    return new Death(element.getPosition());
   /* if (!isEndWorld(element.getPosition())) {
      elements[oldPoint.x][oldPoint.y] = null;
      addElement(element);
    } else {
      isRunned = false;
      view.showGameOver();
    }
    return !isEndWorld(element.getPosition());*/
  }


  @Override
  public void addElement(Element element) {
    elements[element.getPosition().x][element.getPosition().y] = element;
  }

  Element getElementByPosition(Point point) {
    if (canMoveTo(point)) {
      return elements[point.x][point.y];
    } else {
      return new Death(point);
    }
  }

  private boolean canMoveTo(Point point) {
    return (point.x < Main.properties.widthSize && point.y < Main.properties.heightSize);
  }
}