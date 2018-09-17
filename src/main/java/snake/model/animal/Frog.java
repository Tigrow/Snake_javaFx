package snake.model.animal;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import snake.model.animal.elements.frog.FrogBody;

class Frog<T extends FrogBody> implements Runnable {
    private T body;
    private int sleep;

    public T getFrogBody() {
        return body;
    }

    Frog(T frogBody, int sleep) {
        this.sleep = sleep;
        body = frogBody;
    }

    private Point move(List<Point> freePositions) {
        Random random = new Random();
        return freePositions.get(random.nextInt(freePositions.size()));
    }

    public Point resetPosition(List<Point> freePositions) {
        return move(freePositions);
    }

    @Override
    public void run() {
            while (world.isRunned()) {
                move();
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}