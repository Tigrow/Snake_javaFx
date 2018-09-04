package snake.model;

import snake.controller.IMainController;
import snake.model.elements.Element;

import java.awt.Point;

public class World implements IWorld {
  private Element[][] elements;
  private Snake snake;
  private boolean isRunned;
  private IMainController controller;
  private Thread snakeThread;

  public World(Element[][] elements, IMainController controller) {
    this.controller = controller;
    this.elements = elements;

    snake = new Snake(10, this);

    snakeThread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (isRunned) {
          snake.move();
          update();
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    snakeThread.setDaemon(true);
  }

  public void startGame() {
    isRunned = true;
    snakeThread.start();
  }

  private void update() {
    controller.updateMap();
  }

  private boolean isEndWorld(Point point) {
    return (elements.length > point.x && elements[0].length > point.y);
  }

  @Override
  public boolean moveElement(Element element, Point oldPoint) {
    boolean moved;
    if (moved = isEndWorld(element.getPosition())) {
      elements[oldPoint.x][oldPoint.y] = null;
      addElement(element);
    }
    return moved;
  }


  @Override
  public void addElement(Element element) {
    elements[element.getPosition().x][element.getPosition().y] = element;
  }

  @Override
  public boolean isEmptyPosition(Point point) {
    return elements[point.x][point.y] == null;
  }
}