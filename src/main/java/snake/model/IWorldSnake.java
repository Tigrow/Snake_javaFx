package snake.model;

import snake.model.animal.elements.Direction;

public interface IWorldSnake extends IWorldAnimal {

  Direction getDirection();

  void changeDirection(Direction direction);
}
