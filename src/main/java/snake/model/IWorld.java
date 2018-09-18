package snake.model;

import snake.model.animal.elements.Direction;

public interface IWorld {

  void loadGame();

  void startGame();

  void changeDirection(Direction direction);

  void stopGame();
}
