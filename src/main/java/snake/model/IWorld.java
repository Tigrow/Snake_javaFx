package snake.model;

import snake.model.elements.Element;

import java.awt.*;

interface IWorld {
    void moveElement(Element element, Point oldPoint);
    void addElement(Element element);
}
