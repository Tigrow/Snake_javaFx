package snake.model;

import snake.Properties;
import snake.controller.Changer;
import snake.controller.IControllerModel;
import snake.model.animal.Snake;
import snake.model.animal.elements.*;
import snake.model.animal.frog.FrogController;

import java.awt.*;

public class World implements IWorld, IWorldAnimal,IWorldSnake {
  private Element[][] elements;
  private Snake snake;
  private boolean isRunned = false;
  private Thread snakeThread, frogThread;
  private IControllerModel controller;
  private Direction dir = Direction.None;
  private FrogController frogController;
  private Properties properties;
  private int score = 0;

  public World(IControllerModel controller, Properties properties) {
    this.controller = controller;
    this.properties = properties;
    elements = new Element[properties.getWidthSize()][properties.getHeightSize()];
    snake = new Snake(properties, this);
    frogController = new FrogController(properties, this);
    frogThread = new Thread(frogController);
    frogThread.setDaemon(true);
    snakeThread = new Thread(snake);
    snakeThread.setDaemon(true);
    controller.setSceen(properties.getWidthSize(), properties.getHeightSize());
  }


  @Override
  public boolean isRunned() {
    return isRunned;
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
      controller.updateElement(element,Changer.move);
    }
    return move;
  }

  @Override
  public synchronized void addElement(Element element) {
    controller.updateElement(element, Changer.add);
  }

  @Override
  public void deleteElement(Element element) {
    if (elements[element.getPosition().x][element.getPosition().y] == element) {
      elements[element.getPosition().x][element.getPosition().y] = null;
      controller.updateElement(element,Changer.delete);
    }
  }

  private Element getElementByPosition(Point point) {
    if (canMoveTo(point)) {
      return elements[point.x][point.y];
    } else {
      return new Death(point);
    }
  }

  protected boolean canMoveTo(Point point) {
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