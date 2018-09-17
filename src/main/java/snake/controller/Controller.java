package snake.controller;

import snake.Main;
import snake.model.IWorld;
import snake.model.World;
import snake.model.World2;
import snake.model.animal.elements.Direction;
import snake.model.animal.elements.Element;
import snake.view.IMainView;

public class Controller implements IControllerModel, IControllerView {
  private IWorld world;
  private IMainView mainView;
  private boolean statusGame;

  private void newGame() {
    world = new World2(Main.properties);
    mainView.disableStopButton();
    mainView.setScore(0);
    statusGame = true;
  }

  @Override
  public void init(IMainView mainView) {
    this.mainView = mainView;
    newGame();
  }

  @Override
  public void startGame() {
    if (statusGame) {
      world.startGame();
      mainView.disableStartButton();
      mainView.enableStopButton();
    } else {
      newGame();
      mainView.changeTextStartButtonToStart();
    }
  }

  @Override
  public void leftPressed() {
    world.changeDirection(Direction.LEFT);
  }

  @Override
  public void rightPressed() {
    world.changeDirection(Direction.RIGHT);
  }

  @Override
  public void stopGame() {
    world.stopGame();
    mainView.gameStoped();
    statusGame = false;
  }

  @Override
  public void setSceen(int width, int height) {
    mainView.setSceen(width, height);
  }

  @Override
  public void updateElement(Element element, Changer changer) {
    if (changer == Changer.add) {
      mainView.addElement(element);
    } else if (changer == Changer.move) {
      mainView.moveElement(element);
    } else if (changer == Changer.delete) {
      mainView.deleteElement(element);
    }
  }

  @Override
  public void gameOver() {
    mainView.gameStoped();
    statusGame = false;
  }

  @Override
  public void updateScore(int score) {
    mainView.setScore(score);
  }
}
