package snake.model;

import snake.Properties;
import snake.controller.Changer;
import snake.model.animal.elements.Death;
import snake.model.animal.elements.Element;
import snake.model.animal.elements.frog.GreenFrogBody;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeHead;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private Element[][] elements;

    private int width;
    private int height;

    public Map(Properties properties) {
        this.width = properties.getWidthSize();
        this.height = properties.getHeightSize();
        elements = new Element[width][height];
    }

    public synchronized boolean moveElement(Element element, Point newPosition) {
        boolean move = collision(element, newPosition);
        if (move) {
            elements[element.getPosition().x][element.getPosition().y] = null;
            element.setPosition(newPosition);
            elements[newPosition.x][newPosition.y] = element;
            //controller.updateElement(element, Changer.move);
        }
        return move;
    }

    public synchronized void addElement(Element element) {
        //controller.updateElement(element, Changer.add);
    }

    public void deleteElement(Element element) {
        if (elements[element.getPosition().x][element.getPosition().y] == element) {
            elements[element.getPosition().x][element.getPosition().y] = null;
            //controller.updateElement(element, Changer.delete);
        }
    }


    private Element getElementByPosition(Point point) {
        return getElementByPosition(point.x, point.y);
    }

    private Element getElementByPosition(int x, int y) {
        if (canMoveTo(x, y)) {
            return elements[x][y];
        } else {
            return new Death(new Point(x, y));
        }
    }

    protected boolean canMoveTo(int x, int y) {
        return (x < width
                && y < height
                && x >= 0
                && y >= 0);
    }

    public java.util.List<Point> getAllFreePosition() {
        List<Point> positionList = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (getElementByPosition(x, y) == null) {
                    positionList.add(new Point(x, y));
                }
            }
        }
        return positionList;
    }

    public List<Point> getFreePosition(Point position) {
        Point positionLeft = (Point) position.clone();
        positionLeft.translate(-1, 0);
        Point positionRight = (Point) position.clone();
        positionRight.translate(1, 0);
        Point positionUp = (Point) position.clone();
        positionUp.translate(0, 1);
        Point positionDown = (Point) position.clone();
        positionDown.translate(0, -1);

        List<Point> freePosition = new ArrayList<>(4);
        if (getElementByPosition(positionLeft) == null) {
            freePosition.add(positionLeft);
        }
        if (getElementByPosition(positionRight) == null) {
            freePosition.add(positionRight);
        }
        if (getElementByPosition(positionUp) == null) {
            freePosition.add(positionUp);
        }
        if (getElementByPosition(positionDown) == null) {
            freePosition.add(positionDown);
        }
        return freePosition;
    }

    private boolean collision(Element element, Point newPosition) {
        boolean isAlive = true;
        Element elementByPosition = getElementByPosition(newPosition);
        if (element instanceof SnakeHead) {
            if (elementByPosition instanceof Death) {
                isAlive = false;
                isRunned = false;
                //controller.gameOver();
            } else if (elementByPosition instanceof GreenFrogBody) {
                //frogController.resetPostion(elementByPosition);
                snake.addBodySegment();
                //scorePlus();
            } else if (elementByPosition instanceof SnakeBody) {
                isAlive = false;
                isRunned = false;
                //controller.gameOver();
            }
        }
        return isAlive;
    }
}
