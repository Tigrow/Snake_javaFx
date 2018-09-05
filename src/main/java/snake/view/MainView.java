package snake.view;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import snake.controller.Controller;
import snake.controller.IControllerView;
import snake.model.elements.Element;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MainView implements Initializable, IMainView {
  private static final int CELL_SIZE = 20;
  public Label labelScore;
  public Pane pane;
  private HashMap<Element, Circle> map;
  private IControllerView controller;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    controller = new Controller();
    controller.init(this);
    /*Rectangle rectangle = new Rectangle();
    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(CELL_SIZE * Main.WIDTH_SIZE);
    rectangle.setHeight(CELL_SIZE * Main.HEIGHT_SIZE);
    rectangle.setFill(Color.BLACK);
    pane.getChildren().add(rectangle);

    map = new HashMap<>();
    world = new World(this);
    elements = world.getElements();
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
    }*/
  }

  public void onActionStartButton(ActionEvent actionEvent) {
    //world.startGame();
  }

  public void onMouseClickedPane(MouseEvent mouseEvent) {
  }

  @Override
  public void setScore(int score) {
    labelScore.setText(Integer.toString(score));
  }

  @Override
  public void showGameOver() {

  }

  @Override
  public void updateMap() {
    /*Platform.runLater(new Runnable() {
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
    });*/
  }
}
