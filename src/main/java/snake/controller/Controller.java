package snake.controller;

import snake.model.IWorld;
import snake.model.World;
import snake.model.elements.Direction;
import snake.model.elements.Element;
import snake.view.IMainView;

import java.util.List;

public class Controller implements IControllerModel, IControllerView {
  private IWorld world;
  private IMainView mainView;

  @Override
  public void init(IMainView mainView) {
    this.mainView = mainView;
    world = new World(this);
  }

  @Override
  public void startGame() {
    world.startGame();
    mainView.DisableStartButton();
  }

  @Override
  public void leftPressed() {
    world.changeDirection(Direction.Left);
  }

  @Override
  public void rightPressed() {
    world.changeDirection(Direction.Right);
  }

  @Override
  public void setSceen(int width, int height) {
    mainView.setSceen(width, height);
  }

  @Override
  public void updateAll(List<Element> addElements, List<Element> deleteElements, List<Element> moveElements) {
    mainView.addElement(addElements);
    mainView.moveElement(moveElements);
  }
}
