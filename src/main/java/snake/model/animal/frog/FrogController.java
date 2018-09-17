/*package snake.model.animal.frog;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import snake.Properties;

import snake.model.IWorldFrog;
import snake.model.animal.Frog;
import snake.model.animal.elements.Element;
import snake.model.animal.elements.frog.FrogBody;
import snake.model.animal.elements.frog.GreenFrogBody;

public class FrogController implements Runnable {
  private HashMap<Element, Frog> frogs;
  private List<FrogBody> frogBodyes;
  private IWorldFrog world;
  private Properties properties;

  public FrogController(Properties properties, IWorldFrog world) {
    this.world = world;
    this.properties = properties;
    frogs = new HashMap<>();
    frogBodyes = new ArrayList<>();
    for (int i = 0; i < properties.getFrogNumber(); i++) {
      Frog<GreenFrogBody> greenFrog = new Frog<>(new GreenFrogBody());
      frogs.put(greenFrog.getFrogBody(), greenFrog);
      frogBodyes.add(greenFrog.getFrogBody());
      world.addElement(greenFrog.getFrogBody());
      resetPostion(greenFrog.getFrogBody());
    }
  }

  private void move() {
    for (int i = 0; i < frogBodyes.size(); i++) {
      FrogBody frogBody = frogBodyes.get(i);
      List<Point> freePosition = world.getFreePosition(frogBody.getPosition());
      if (freePosition.size() > 0) {
        //Point newPosition = frogs.get(frogBody).move(freePosition);
        //world.moveElement(frogBody, newPosition);
      }
    }
  }

  public void resetPostion(Element element) {
    List<Point> freePosition = world.getAllFreePosition();
    if (freePosition.size() > 0) {
      Frog frog = frogs.get(element);
      Point newPosition = frog.resetPosition(freePosition);
      world.moveElement(frog.getFrogBody(), newPosition);
    } else {
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
*/