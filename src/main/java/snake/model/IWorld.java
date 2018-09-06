package snake.model;

import snake.model.elements.Direction;

public interface IWorld {

  void startGame();

  void changeDirection(Direction direction);

  void StopGame();
}
