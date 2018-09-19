package snake.view;

import javafx.application.Platform;

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
import snake.model.animal.elements.Element;
import snake.model.animal.elements.frog.GreenFrogBody;
import snake.model.animal.elements.snake.SnakeBody;
import snake.model.animal.elements.snake.SnakeHead;
import snake.model.animal.elements.snake.SnakeTail;


public class MainView extends ObservableView {

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
  private Circle[][] circles;


  /**
   * Метод для отображения ошибки и корректированных параметров.
   */
  @Override
  public void showError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error in params");
    alert.setHeaderText("you have a problem in the parameters");
    String errorMessage = "parameters corrected to \n"
            + "Snake size =" + Main.properties.getSnakeSize() + "\n"
            + "Green frog number = " + Main.properties.getGreenFrogNumber() + "\n"
            + "Snake sleep = " + Main.properties.getSnakeSleep() + "\n"
            + "Width size = " + Main.properties.getWidthSize() + "\n"
            + "Height size = " + Main.properties.getHeightSize() + "\n";
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }

  /**
   * метод который отлавливает событие нажатия на кномпку "Start".
   */
  public void onActionStartButton() {
    this.setChanged();
    this.notifyObservers(ViewChange.START_GAME);
  }

  /**
   * метод который отлавливает событие нажатия на кномпку "Stop".
   */
  public void onActionStopButton() {
    this.setChanged();
    this.notifyObservers(ViewChange.STOP_GAME);
  }

  /**
   * Метод обрабатывающий нажатие на Pane.
   *
   * @param mouseEvent - обеъект для распознания нажатой клавиши мыши
   */
  public void onMouseClickedPane(MouseEvent mouseEvent) {
    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
      this.setChanged();
      this.notifyObservers(ViewChange.LEFT_PRESSED);
    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
      this.setChanged();
      this.notifyObservers(ViewChange.RIGHT_PRESSED);
    }
  }

  /**
   * Включает кнопку "Stop", и отключает кнопку "Start" в FX потоке.
   */
  @Override
  public void gameStarted() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        stopButton.setDisable(false);
        startButton.setDisable(true);
      }
    });
  }

  /**
   * Отключает кнопку "Stop", включает кнопку "Start" и устанавливает на неё текс "New game" .
   */
  @Override
  public void gameStopped() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        startButton.setText("New game");
      }
    });
  }

  /**
   * Включает кнопку "Stop", выключает кнопку "Start" и устанавливает на неё текс "Start"
   * в потоке FX.
   */
  @Override
  public void newGame() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        startButton.setText("Start");
      }
    });
  }

  /**
   * Метод обнавляющий состояние карты во вью.
   *
   * @param elements - элементы карты на основание которых делается обновление представления.
   */
  @Override
  public void updateMap(final Element[][] elements) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        for (int x = 0; x < Main.properties.getWidthSize(); x++) {
          for (int y = 0; y < Main.properties.getHeightSize(); y++) {
            updateCircle(circles[x][y], elements[x][y]);
          }
        }
      }
    });
  }

  /**
   * Load game screen.
   *
   * @param width  - game screen width.
   * @param height - game screen height.
   */
  @Override
  public void loadScreen(int width, int height) {
    pane.getChildren().removeAll();
    pane.getChildren().clear();
    Rectangle rectangle = new Rectangle();
    pane.getChildren().add(rectangle);
    rectangle.setX(0);
    rectangle.setY(0);
    rectangle.setWidth(CELL_SIZE * width);
    rectangle.setHeight(CELL_SIZE * height);
    rectangle.setFill(Color.BLACK);
    circles = new Circle[Main.properties.getWidthSize()][Main.properties.getHeightSize()];
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

  /**
   * Метод устанавливающий счёч игры.
   *
   * @param score - счёт который будет установлен в labelScore.
   */
  @Override
  public void setScore(final int score) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        labelScore.setText(Integer.toString(score));
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
}
