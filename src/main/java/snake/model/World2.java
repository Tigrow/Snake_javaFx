/*package snake.model;

import snake.Properties;
import snake.model.animal.Snake;
import snake.model.animal.Snake2;
import snake.model.animal.elements.Direction;
import snake.model.animal.elements.Element;

import java.util.Observable;

public class World2 extends Observable implements IWorld,IWorldSnake {
  private Properties properties;
  private Element[][] elements;
  private Snake2 snake;
  private Direction dir = Direction.NONE;

  public World2(Properties properties) {
    this.properties = properties;
    elements = new Element[properties.getWidthSize()][properties.getHeightSize()];
    snake = new Snake2(properties, this);
  }

  @Override
  public void startGame() {

  }

  @Override
  public void changeDirection(Direction direction) {

  }

  @Override
  public void stopGame() {

  }

  public Element[][] getElements() {
    return elements;
  }
}
*/