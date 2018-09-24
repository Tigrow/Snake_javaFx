package snake.view;

/**
 * Служит для общения view с controller через Observable.
 */
public enum ViewChange {
  START_GAME,
  STOP_GAME,
  LEFT_PRESSED,
  RIGHT_PRESSED,
  PAUSE_GAME
}
