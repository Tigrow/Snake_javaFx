package snake.controller;

import com.sun.istack.internal.NotNull;

import java.util.Observable;
import java.util.Observer;

import snake.Main;
import snake.model.ObservableWorld;
import snake.model.World;
import snake.model.WorldChange;
import snake.model.animal.elements.Direction;
import snake.view.ObservableView;
import snake.view.ViewChange;

/**
 * Этот класс выполняет связующую функцию между моделью и представлением.
 */
public class Controller implements Observer {
  private ObservableWorld world;
  private ObservableView mainView;
  private boolean gameLoaded;

  /**
   *  Конструктов в который в качестве параметров поступает @param mainView, и инициализируется наблюдение за ним.
   * @param mainView - объект преставления.
   */
  public Controller(@NotNull ObservableView mainView) {
    this.mainView = mainView;
    mainView.addObserver(this);
    newGame();
  }

  @Override
  public void update(Observable observable, Object arg) {
    if (observable instanceof ObservableWorld) {
      WorldChange change = (WorldChange) arg;
      if (change == WorldChange.ELEMENT_MOVED) {
        mainView.updateMap(world.getElements());
      } else if (change == WorldChange.SCORE_CHANGED) {
        mainView.setScore(world.getScore());
      } else if (change == WorldChange.GAME_OVER) {
        mainView.gameStopped();
        gameLoaded = false;
      }
    } else if (observable instanceof ObservableView) {
      ViewChange change = (ViewChange) arg;
      if (change == ViewChange.START_GAME) {
        startGame();
      } else if (change == ViewChange.STOP_GAME) {
        stopGame();
      } else if (change == ViewChange.LEFT_PRESSED) {
        leftPressed();
      } else if (change == ViewChange.RIGHT_PRESSED) {
        rightPressed();
      }
    }
  }

  private void startGame() {
    if (gameLoaded) {
      world.startGame();
      mainView.gameStarted();
    } else {
      newGame();
      mainView.newGame();
    }
  }

  private void leftPressed() {
    world.changeDirection(Direction.LEFT);
  }

  private void rightPressed() {
    world.changeDirection(Direction.RIGHT);
  }

  private void stopGame() {
    world.stopGame();
    mainView.gameStopped();
    gameLoaded = false;
  }

  private void newGame() {
    if (world != null) {
      world.deleteObservers();
    }
    world = new World(Main.properties);
    world.addObserver(this);
    mainView.loadScreen(Main.properties.getWidthSize(), Main.properties.getHeightSize());
    world.loadGame();
    mainView.setScore(world.getScore());
    mainView.newGame();
    gameLoaded = true;
  }
}
