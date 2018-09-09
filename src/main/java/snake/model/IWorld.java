package snake.model;

import snake.model.animal.elements.Direction;

public interface IWorld {

  void startGame();

  void changeDirection(Direction direction);

  void StopGame();
}
