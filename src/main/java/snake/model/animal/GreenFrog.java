package snake.model.animal;

import snake.Properties;
import snake.model.IWorldAnimal;
import snake.model.animal.elements.FrogBody;

import java.awt.*;
import java.util.Random;

public class GreenFrog {
  private FrogBody frogBody;
  private IWorldAnimal world;
  private Properties properties;
  private static Point[] mass = {new Point(1,0),new Point(0,1),new Point(-1,0),new Point(0,-1)};

  public GreenFrog(Properties properties, IWorldAnimal world) {
    this.world = world;
    this.properties = properties;
    frogBody = new FrogBody();
    initFrog();
    /*
     * TODO: добавить проверку на позицию
     * */
  }

  public FrogBody getFrogBody() {
    return frogBody;
  }

  public void resetPossition() {
    Point newPosition = (Point) frogBody.getPosition().clone();
    Random random = new Random();
    newPosition.setLocation(random.nextInt(properties.getWidthSize()), random.nextInt(properties.getHeightSize()));
    world.moveElement(frogBody, newPosition);
  }

  public void move() {
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

  private boolean initFrog() {
    frogBody.setPosition(randomPosition());
    if (!world.addElement(frogBody)) {
      initFrog();
    }
    return true;
  }
}
