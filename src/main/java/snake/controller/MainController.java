package snake.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import snake.Main;
import snake.model.World;
import snake.model.elements.Body;
import snake.model.elements.Element;
import snake.model.elements.Head;
import snake.model.elements.Tail;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MainController implements Initializable, IMainController {
  public Label labelScore;
  public Pane pane;
  private World world;
  private Element[][] elements;
  private HashMap<Element, Circle> map;
  private static final int CELL_SIZE = 20;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Rectangle rectangle = new Rectangle();
    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(CELL_SIZE * Main.WIDTH_SIZE);
    rectangle.setHeight(CELL_SIZE * Main.HEIGHT_SIZE);
    rectangle.setFill(Color.BLACK);
    pane.getChildren().add(rectangle);

    elements = new Element[Main.WIDTH_SIZE][Main.HEIGHT_SIZE];
    map = new HashMap<>();
    world = new World(elements,this);
    for (int x = 0; x < Main.WIDTH_SIZE; x++) {
      for (int y = 0; y < Main.HEIGHT_SIZE; y++) {
        if (elements[x][y] != null) {
          if (elements[x][y] instanceof Head) {
            Circle circle = new Circle((double) CELL_SIZE / 2);
            circle.setFill(Color.YELLOW);
            circle.setLayoutX(x * CELL_SIZE + CELL_SIZE / 2);
            circle.setLayoutY(y * CELL_SIZE + CELL_SIZE / 2);
            map.put(elements[x][y], circle);
            pane.getChildren().add(circle);
          } else if (elements[x][y] instanceof Tail) {
            Circle circle = new Circle((double) CELL_SIZE / 4);
            circle.setFill(Color.YELLOW);
            circle.setLayoutX(x * CELL_SIZE + CELL_SIZE / 2);
            circle.setLayoutY(y * CELL_SIZE + CELL_SIZE / 2);
            map.put(elements[x][y], circle);
            pane.getChildren().add(circle);
          } else if (elements[x][y] instanceof Body) {
            Circle circle = new Circle((double) CELL_SIZE / 3);
            circle.setFill(Color.YELLOW);
            circle.setLayoutX(x * CELL_SIZE + CELL_SIZE / 2);
            circle.setLayoutY(y * CELL_SIZE + CELL_SIZE / 2);
            map.put(elements[x][y], circle);
            pane.getChildren().add(circle);
          }
        }
      }
    }
  }

  public void onActionStartButton(ActionEvent actionEvent) {
    world.startGame();
  }

  public void onMouseClickedPane(MouseEvent mouseEvent) {
  }

  @Override
  public void setScore(int score) {
    labelScore.setText(Integer.toString(score));
  }

  @Override
  public void updateMap() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        for (int x = 0; x < Main.WIDTH_SIZE; x++) {
          for (int y = 0; y < Main.HEIGHT_SIZE; y++) {
            if (elements[x][y] != null) {
              Circle circle = map.get(elements[x][y]);
              circle.setLayoutX(x * CELL_SIZE + CELL_SIZE / 2);
              circle.setLayoutY(y * CELL_SIZE + CELL_SIZE / 2);
            }
          }
        }
      }
    });
  }
}
