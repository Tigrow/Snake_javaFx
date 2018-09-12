package snake.model.animal.frog;

import snake.Properties;
import snake.model.IWorldAnimal;
import snake.model.animal.elements.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrogController implements Runnable {
  private HashMap<Element, GreenFrog> frogs;
  private List<Element> frogBodyes;
  private IWorldAnimal world;
  private Properties properties;

  public FrogController(Properties properties, IWorldAnimal world) {
    this.world = world;
    this.properties = properties;
    frogs = new HashMap<>();
    frogBodyes = new ArrayList<>();
    for (int i = 0; i < properties.getFrogNumber(); i++) {
      GreenFrog greenFrog = new GreenFrog(properties, world);
      frogs.put(greenFrog.getFrogBody(), greenFrog);
      frogBodyes.add(greenFrog.getFrogBody());
    }
  }

  private void move() {
    for (int i = 0; i < frogBodyes.size(); i++) {
      frogs.get(frogBodyes.get(i)).move();
    }
  }

  public void resetPostion(Element element) {
    if (!frogs.get(element).resetPossition()) {
      frogs.remove(element);
      world.deleteElement(element);
      frogBodyes.remove(element);
    }
  }

  @Override
  public void run() {
    while (world.isRunned()) {
      move();
      try {
        Thread.sleep(properties.getSnakeSleep() * 2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
