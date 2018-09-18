package snake.view;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import snake.Main;
import snake.controller.Controller;
import snake.controller.IControllerView;
import snake.model.animal.elements.*;
import snake.model.animal.elements.frog.GreenFrogBody;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeHead;
import snake.model.animal.elements.snake.SnakeTail;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MainView implements Initializable, IMainView {

  private static final int CELL_SIZE = 30;
  private static final int HALF_CELL_SIZE = CELL_SIZE / 2;
  private static final int HEAD_SNAKE_RADIUS = CELL_SIZE / 2;
  private static final int BODY_SNAKE_RADIUS = CELL_SIZE / 3;
  private static final int TAIL_SNAKE_RADIUS = CELL_SIZE / 4;
  private static final int BODY_FROG_RADIUS = CELL_SIZE / 3;

  public Label labelScore;
  public Pane pane;
  public Button startButton;
  public Button stopButton;
  private HashMap<Element, Circle> map;
  private Circle[][] circles;
  private IControllerView controller;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    map = new HashMap<>();
    circles = new Circle[Main.properties.getWidthSize()][Main.properties.getHeightSize()];
    controller = new Controller();
    controller.init(this);
  }

  public void showError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error in params");
    alert.setHeaderText("you have a problem in the parameters");
    String errorMessage = "parameters corrected to \n"
            + "Snake size =" + Main.properties.getSnakeSize() + "\n"
            + "Frog number = " + Main.properties.getFrogNumber() + "\n"
            + "Snake sleep = " + Main.properties.getSnakeSleep() + "\n"
            + "Width size = " + Main.properties.getWidthSize() + "\n"
            + "Height size = " + Main.properties.getHeightSize() + "\n";
    alert.setContentText(errorMessage);
    alert.showAndWait();
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
  public void gameStarted() {
  }

  @Override
  public void gameStoped() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        startButton.setText("New game");
      }
    });
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
        circle.setLayoutX(element.getPosition().x * CELL_SIZE + HALF_CELL_SIZE);
        circle.setLayoutY(element.getPosition().y * CELL_SIZE + HALF_CELL_SIZE);
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

  private void addCircle(final Circle circle) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        pane.getChildren().add(circle);
      }
    });
  }

  public void updateMap(final Element[][] elements) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        for (int x = 0; x < Main.properties.getWidthSize(); x++) {
          for (int y = 0; y < Main.properties.getHeightSize(); y++) {
            updateCircle(circles[x][y],elements[x][y]);
          }
        }
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
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        circles[x][y] = new Circle(HEAD_SNAKE_RADIUS);
        circles[x][y].setLayoutX(x * CELL_SIZE + HALF_CELL_SIZE);
        circles[x][y].setLayoutY(y * CELL_SIZE + HALF_CELL_SIZE);
        pane.getChildren().add(circles[x][y]);
        circles[x][y].setVisible(false);
      }
    }
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
  public void disableStartButton() {
    startButton.setDisable(true);
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
  public void changeTextStartButtonToStart() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        startButton.setText("Start");
      }
    });
  }

  private void updateCircle(Circle circle, Element element) {
    if (element instanceof SnakeHead) {
      circle.setVisible(true);
      circle.setRadius(HEAD_SNAKE_RADIUS);
      circle.setFill(Color.YELLOW);
    } else if (element instanceof SnakeTail) {
      circle.setVisible(true);
      circle.setRadius(TAIL_SNAKE_RADIUS);
      circle.setFill(Color.YELLOW);
    } else if (element instanceof SnakeBody) {
      circle.setVisible(true);
      circle.setRadius(BODY_SNAKE_RADIUS);
      circle.setFill(Color.YELLOW);
    } else if (element instanceof GreenFrogBody) {
      circle.setVisible(true);
      circle.setRadius(BODY_FROG_RADIUS);
      circle.setFill(Color.GREEN);
    } else {
      circle.setVisible(false);
    }
  }

  private Circle getCircleByElements(Element element) {
    Circle circle = null;
    if (element instanceof SnakeHead) {
      circle = new Circle(HEAD_SNAKE_RADIUS);
      circle.setFill(Color.YELLOW);
    } else if (element instanceof SnakeTail) {
      circle = new Circle(TAIL_SNAKE_RADIUS);
      circle.setFill(Color.YELLOW);
    } else if (element instanceof SnakeBody) {
      circle = new Circle(BODY_SNAKE_RADIUS);
      circle.setFill(Color.YELLOW);
    } else if (element instanceof GreenFrogBody) {
      circle = new Circle(BODY_FROG_RADIUS);
      circle.setFill(Color.GREEN);
    }
    return circle;
  }
}
