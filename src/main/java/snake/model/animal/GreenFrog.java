package snake.model.animal;

import snake.Properties;
import snake.model.IWorldAnimal;
import snake.model.animal.elements.FrogBody;

import java.awt.*;
import java.util.Random;

public class GreenFrog{
    private FrogBody frogBody;
    private IWorldAnimal world;
    private Properties properties;

    public GreenFrog(Properties properties, IWorldAnimal world) {
        this.world = world;
        this.properties = properties;
        Random random = new Random();
        frogBody = new FrogBody(new Point(random.nextInt(properties.widthSize),random.nextInt(properties.heightSize)));
        world.addElement(frogBody);
        /*
        * TODO: добавить проверку на позицию
        * */
    }

    public FrogBody getFrogBody() {
        return frogBody;
    }

    public void resetPossition(){
        Point newPosition = (Point) frogBody.getPosition().clone();
        Random random = new Random();
        newPosition.setLocation(random.nextInt(properties.widthSize),random.nextInt(properties.heightSize));
        world.moveElement(frogBody,newPosition);
    }

    public void move() {
        Random random = new Random();
        Point dir = new Point(1,0);
        /*
        * TODO что то не так с этим рандомом
        * */
        for(int i = 0; i<random.nextInt(4);i++){
            Point oldDir = (Point) dir.clone();
            dir.x = oldDir.y * -1;
            dir.y = oldDir.x * 1;
        }
        Point newFrogPosition = (Point) frogBody.getPosition().clone();
        newFrogPosition.translate(dir.x,dir.y);
        if(!world.moveElement(frogBody, newFrogPosition)){
            //move();
        }
    }
}
