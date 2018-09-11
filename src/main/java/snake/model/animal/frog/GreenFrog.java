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

  protected void resetPossition() {
    Point newPosition = (Point) frogBody.getPosition().clone();
    newPosition.setLocation(randomPosition());
    if (!world.moveElement(frogBody, newPosition)) {
      resetPossition();
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
