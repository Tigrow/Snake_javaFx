package snake.model;

import snake.Properties;
import snake.controller.IControllerModel;
import snake.model.animal.FrogController;
import snake.model.animal.Snake;
import snake.model.animal.elements.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World implements IWorld, IWorldAnimal {
  private Element[][] elements;
  private Snake snake;
  private boolean isRunned = false;
  private Thread snakeThread, frogThread;
  private IControllerModel controller;
  private List<Element> addElements, deleteElements, moveElements;
  private Direction dir = Direction.None;
  private FrogController frogController;
  private Properties properties;
  private int score = 0;

  public World(IControllerModel controller, Properties properties) {
    this.controller = controller;
    this.properties = properties;
    initLists();
    elements = new Element[properties.getWidthSize()][properties.getHeightSize()];
    snake = new Snake(properties, this);
    frogController = new FrogController(properties, this);
    frogThread = new Thread(frogController);
    frogThread.setDaemon(true);
    snakeThread = new Thread(snake);
    snakeThread.setDaemon(true);
    controller.setSceen(properties.getWidthSize(), properties.getHeightSize());
    update();
  }

  public synchronized void update() {
    controller.updateAll(addElements, deleteElements, moveElements);
    initLists();
  }

  @Override
  public boolean isRunned() {
    return isRunned;
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
  public void startGame() {
    isRunned = true;
    snakeThread.start();
    frogThread.start();
  }

  @Override
  public void changeDirection(Direction direction) {
    dir = direction;
  }

  @Override
  public Direction getDirection() {
    return dir;
  }

  @Override
  public void stopGame() {
    isRunned = false;
  }

  @Override
  public synchronized boolean moveElement(Element element, Point newPosition) {
    boolean move = collision(element, newPosition);
    if (move) {
      elements[element.getPosition().x][element.getPosition().y] = null;
      element.setPosition(newPosition);
      elements[newPosition.x][newPosition.y] = element;
      moveElements.add(element);
    }
    return move;
  }

  @Override
  public synchronized boolean addElement(Element element) {
    boolean move = collision(element, element.getPosition());
    if (move) {
      elements[element.getPosition().x][element.getPosition().y] = element;
      addElements.add(element);
    }
    return move;
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
    return (point.x < properties.getWidthSize()
            && point.y < properties.getHeightSize()
            && point.x >= 0
            && point.y >= 0);
  }

  private boolean collision(Element element, Point newPosition) {
    boolean isAlive = true;
    Element elementByPosition = getElementByPosition(newPosition);
    if (element instanceof FrogBody) {
      if (elementByPosition instanceof Death) {
        isAlive = false;
      } else if (elementByPosition != null) {
        isAlive = false;
      }
    }
    if (element instanceof Head) {
      if (elementByPosition instanceof Death) {
        isAlive = false;
        isRunned = false;
        controller.gameOver();
      } else if (elementByPosition instanceof FrogBody) {
        frogController.resetPostion(elementByPosition);
        snake.addBodySegment();
        scorePlus();
      } else if (elementByPosition instanceof Body) {
        isAlive = false;
        isRunned = false;
        controller.gameOver();
      }
    }
    return isAlive;
  }

  private void scorePlus() {
    score++;
    controller.updateScore(score);
  }
}