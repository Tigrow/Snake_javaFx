package snake.controller;

import snake.view.IMainView;

public interface IControllerView {
  void init(IMainView mainView);

  void startGame();

  void leftPressed();

  void rightPressed();

  void stopGame();
}
