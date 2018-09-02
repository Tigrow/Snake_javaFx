package snake.model.elements;

import javafx.geometry.Point2D;

import java.awt.*;

public abstract class Element {
    protected Point position;

    public Element(Point position){
        this.position = position;
    }
    public void setPosition(Point position){
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }
}
