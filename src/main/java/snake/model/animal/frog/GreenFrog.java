package snake.model.animal.frog;

import snake.Properties;
import snake.model.IWorldAnimal;
import snake.model.animal.elements.FrogBody;

import java.awt.*;
import java.util.Random;

public class GreenFrog {
  private FrogBody frogBody;
  private IWorldAnimal world;
  private Properties properties;
  private int counter = 100;
  private static Point[] mass = {new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)};

  GreenFrog(Properties properties, IWorldAnimal world) {
    this.world = world;
    this.properties = properties;
    frogBody = new FrogBody();
    world.addElement(frogBody);
    resetPossition();
  }

  protected FrogBody getFrogBody() {
    return frogBody;
  }

  protected boolean resetPossition() {
    if (!world.moveElement(frogBody, randomPosition()) && counter > 0) {
      counter--;
      resetPossition();
      return false;
    }else {
      counter = 100;
      return true;
    }
  }

  protected void move() {
    Random random = new Random();
    Point dir = mass[random.nextInt(mass.length)];
    Point newFrogPosition = (Point) frogBody.getPosition().clone();
    newFrogPosition.translate(dir.x, dir.y);
    if (!world.moveElement(frogBody, newFrogPosition)) {
      //move();
    }
  }

  private Point randomPosition() {
    Random random = new Random();
    return new Point(random.nextInt(properties.getWidthSize()), random.nextInt(properties.getHeightSize()));
  }
}
