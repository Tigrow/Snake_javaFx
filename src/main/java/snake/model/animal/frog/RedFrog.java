package snake.model.animal.frog;

import snake.Properties;
import snake.model.IWorldAnimal;
import snake.model.animal.elements.RedFrogBody;

public class RedFrog extends Frog<RedFrogBody> {

  public RedFrog(Properties properties, IWorldAnimal world) {
    super(properties, world);
    body = new RedFrogBody();
    world.addElement(body);
    resetPossition();
  }
}
