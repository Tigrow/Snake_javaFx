package snake.model;

import snake.model.elements.Body;
import snake.model.elements.Element;
import snake.model.elements.Head;
import snake.model.elements.Tail;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

class Snake{
    private Head head;
    private Tail tail;
    private LinkedList<Body> bodyList;
    private Element[][] elements;

    Snake(int lenght, Element[][] elements){
        this.elements = elements;
        tail = new Tail(new Point(0,0));
        elements[0][0] = tail;
        bodyList = new LinkedList<>();
        for(int i = 1; i<=lenght - 2;i++){
            Body body = new Body(new Point(i,0));
            bodyList.addFirst(body);
            elements[i][0] = body;
        }
        head = new Head(new Point(lenght-1,0));
        elements[lenght-1][0] = head;
    }
    void move(){
        elements[tail.getPosition().x][tail.getPosition().y] = null;
        tail.setPosition(bodyList.getLast().getPosition());
        elements[bodyList.getLast().getPosition().x][bodyList.getLast().getPosition().y] = tail;
        elements[head.getPosition().x][head.getPosition().y] = bodyList.getLast();
        bodyList.addFirst(bodyList.getLast());
        bodyList.removeLast();
        bodyList.getFirst().setPosition(head.getPosition());
        head.getPosition().translate(1,0);
        elements[head.getPosition().x][head.getPosition().y] = head;
    }

    Head getHead() {
        return head;
    }

    Tail getTail() {
        return tail;
    }

    List<Body> getBodyList() {
        return bodyList;
    }
}
