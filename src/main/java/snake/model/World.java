package snake.model;

import snake.model.elements.Element;

public class World {
    private Element[][] elements;
    private Snake snake;
    public World(Element[][] elements){
        this.elements = elements;
        snake = new Snake(3,elements);
    }
    public void move(){
        snake.move();
    }
}
