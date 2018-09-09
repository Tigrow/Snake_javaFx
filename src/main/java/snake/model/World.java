package snake.model;

import snake.Main;
import snake.controller.IControllerModel;
import snake.model.animal.FrogController;
import snake.model.animal.Snake;
import snake.model.animal.elements.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class World implements IWorld, IWorldAnimal {
  private Element[][] elements;
  private Snake snake;
  private boolean isRunned;
  private Thread snakeThread;
  private IControllerModel controller;
  private List<Element> addElements;
  private List<Element> deleteElements;
  private List<Element> moveElements;
  private Direction dir = Direction.None;

  public World(IControllerModel controller) {
    this.controller = controller;
    initLists();
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
    controller.setSceen(Main.properties.widthSize, Main.properties.heightSize);
    update();
  }

  void update() {
    controller.updateAll(addElements, deleteElements, moveElements);
    initLists();
  }

  private void initLists() {
    addElements = new ArrayList<>();
    deleteElements = new ArrayList<>();
    moveElements = new ArrayList<>();
  }

  @Override
  public void snakeDeath() {
    isRunned = false;
  }

  @Override
  public Direction getDirection() {
    return dir;
  }

  @Override
  public void startGame() {
    isRunned = true;
    snakeThread.start();
  }

  @Override
  public void changeDirection(Direction direction) {
    dir = direction;
  }

  @Override
  public void StopGame() {

  }

  @Override
  public void moveElement(Element element, Point oldPoint) {
    if (canMoveTo(element.getPosition())) {
      elements[oldPoint.x][oldPoint.y] = null;
      elements[element.getPosition().x][element.getPosition().y] = element;
      moveElements.add(element);
    }
  }


  @Override
  public void addElement(Element element) {
    elements[element.getPosition().x][element.getPosition().y] = element;
    addElements.add(element);
  }

  @Override
  public Element getElementByPosition(Point point) {
    if (canMoveTo(point)) {
      return elements[point.x][point.y];
    } else {
      return new Death(point);
    }
  }

  private boolean canMoveTo(Point point) {
    return (point.x < Main.properties.widthSize && point.y < Main.properties.heightSize && point.x >= 0 && point.y >= 0);
  }
}