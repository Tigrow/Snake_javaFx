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
import java.util.ResourceBundle;


public class MainView implements Initializable, IMainView {
  private static final int CELL_SIZE = 15;
  public Label labelScore;
  public Pane pane;
  public Button startButton;
  public Button stopButton;
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

  public void onActionStopButton() {
    controller.stopGame();
  }

  public void onMouseClickedPane(MouseEvent mouseEvent) {
    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
      controller.leftPressed();
    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
      controller.rightPressed();
    }
  }

  @Override
  public void addElement(final Element element) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Circle circle = getCircleByElements(element);
        map.put(element, circle);
        pane.getChildren().add(circle);
      }
    });
  }

  @Override
  public void moveElement(final Element element) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Circle circle = map.get(element);
        if (circle == null) {
          circle = getCircleByElements(element);
        }
        circle.setLayoutX(element.getPosition().x * CELL_SIZE + CELL_SIZE / 2);
        circle.setLayoutY(element.getPosition().y * CELL_SIZE + CELL_SIZE / 2);
      }
    });
  }

  @Override
  public void deleteElement(final Element element) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Circle circle = map.get(element);
        pane.getChildren().remove(circle);
        map.remove(element);
      }
    });
  }

  @Override
  public void setSceen(int width, int height) {
    pane.getChildren().removeAll();
    pane.getChildren().clear();
    Rectangle rectangle = new Rectangle();
    map = new HashMap<>();
    pane.getChildren().add(rectangle);
    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(CELL_SIZE * width);
    rectangle.setHeight(CELL_SIZE * height);
    rectangle.setFill(Color.BLACK);
  }

  @Override
  public void setScore(final int score) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        labelScore.setText(Integer.toString(score));
      }
    });
  }

  @Override
  public void showGameOver() {

  }

  @Override
  public void disableStartButton() {
    startButton.setDisable(true);
  }

  @Override
  public void enableStartButton() {
    startButton.setDisable(false);
  }

  @Override
  public void disableStopButton() {
    stopButton.setDisable(true);
  }

  @Override
  public void enableStopButton() {
    stopButton.setDisable(false);
  }

  @Override
  public void changeTextStartButtonToNew() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        startButton.setText("New game");
      }
    });
  }

  @Override
  public void changeTextStartButtonToStart() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        startButton.setText("Start");
      }
    });
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
    } else if (element instanceof FrogBody) {
      circle = new Circle((double) CELL_SIZE / 3);
      circle.setFill(Color.GREEN);
    }
    return circle;
  }
}
