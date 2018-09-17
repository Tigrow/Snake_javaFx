package snake.model;

import snake.Properties;
import snake.model.animal.Snake;
import snake.model.animal.elements.Direction;
import snake.model.animal.elements.Element;

import java.util.Observable;

public class World2 extends Observable implements IWorld {
    private Properties properties;
    private Element[][] elements;
    private Snake snake;

    public World2(Properties properties) {
        this.properties = properties;
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
