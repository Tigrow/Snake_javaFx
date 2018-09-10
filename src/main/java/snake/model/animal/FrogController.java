package snake.model.animal;

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

  public FrogController(Properties properties, IWorldAnimal world) {
    this.world = world;
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
    frogs.get(element).resetPossition();
  }

  @Override
  public void run() {
    while (world.isRunned()) {
      move();
      world.update();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
