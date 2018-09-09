package snake.view;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import snake.controller.Controller;
import snake.controller.IControllerView;
import snake.model.animal.elements.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class MainView implements Initializable, IMainView {
  private static final int CELL_SIZE = 20;
  public Label labelScore;
  public Pane pane;
  public Button startButton;
  private HashMap<Element, Circle> map;
  private IControllerView controller;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    map = new HashMap<>();
    controller = new Controller();
    controller.init(this);
  }

  public void onActionStartButton() {
    controller.startGame();
  }

  public void onMouseClickedPane(MouseEvent mouseEvent) {
    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
      controller.leftPressed();
    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
      controller.rightPressed();
    }
  }

  @Override
  public void addElement(final List<Element> list) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < list.size(); i++) {
          Element element = list.get(i);
          Circle circle = getCircleByElements(element);
          circle.setLayoutX(element.getPosition().x * CELL_SIZE + CELL_SIZE / 2);
          circle.setLayoutY(element.getPosition().y * CELL_SIZE + CELL_SIZE / 2);
          map.put(element, circle);
          pane.getChildren().add(circle);
        }
      }
    });

  }

  @Override
  public void moveElement(final List<Element> list) {
    /*Platform.runLater(new Runnable() {
      @Override
      public void run() {

      }
    });*/
    for (int i = 0; i < list.size(); i++) {
      Element element = list.get(i);
      Circle circle = map.get(element);
      circle.setLayoutX(element.getPosition().x * CELL_SIZE + CELL_SIZE / 2);
      circle.setLayoutY(element.getPosition().y * CELL_SIZE + CELL_SIZE / 2);
    }
  }

  @Override
  public void deleteElement() {

  }

  @Override
  public void setSceen(int width, int height) {
    Rectangle rectangle = new Rectangle();
    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(CELL_SIZE * width);
    rectangle.setHeight(CELL_SIZE * height);
    rectangle.setFill(Color.BLACK);
    pane.getChildren().add(rectangle);
  }

  @Override
  public void setScore(int score) {
    labelScore.setText(Integer.toString(score));
  }

  @Override
  public void showGameOver() {

  }

  @Override
  public void DisableStartButton() {
    startButton.setDisable(true);
  }

  @Override
  public void EnableStartButtor() {
    startButton.setDisable(false);
  }

  private Circle getCircleByElements(Element element) {
    Circle circle = null;
    if (element instanceof Head) {
      circle = new Circle((double) CELL_SIZE / 2);
      circle.setFill(Color.YELLOW);
    } else if (element instanceof Tail) {
      circle = new Circle((double) CELL_SIZE / 4);
      circle.setFill(Color.YELLOW);
    } else if (element instanceof Body) {
      circle = new Circle((double) CELL_SIZE / 3);
      circle.setFill(Color.YELLOW);
    }
    return circle;
  }
}
