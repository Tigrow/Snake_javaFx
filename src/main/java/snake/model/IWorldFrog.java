package snake.model;

import java.awt.Point;
import java.util.List;

public interface IWorldFrog extends IWorldAnimal {

    List<Point> getAllFreePosition();

    List<Point> getFreePosition(Point position);
}
