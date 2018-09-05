package snake.controller;

import snake.model.IWorld;
import snake.model.World;
import snake.view.IMainView;

public class Controller implements IControllerModel, IControllerView {
  private IWorld world;
  private IMainView mainView;

  @Override
  public void init(IMainView mainView) {
    this.mainView = mainView;
    world = new World(this);
  }
}
