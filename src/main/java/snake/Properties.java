package snake;

/**
 * Класс считывающий и корректирующий настройки программы.
 */
public class Properties {
  private static final int HEIGHT = 10;
  private static final int WIDTH = 10;
  private static final int SNAKE_SIZE = 5;
  private static final int GREEN_FROG_NUMBER = 10;
  private static final int RED_FROG_NUMBER = 10;
  private static final int SNAKE_SLEEP = 500;

  private int heightSize = HEIGHT;
  private int widthSize = WIDTH;
  private int snakeSize = SNAKE_SIZE;
  private int greenFrogNumber = GREEN_FROG_NUMBER;
  private int redFrogNumber = RED_FROG_NUMBER;
  private int snakeSleep = SNAKE_SLEEP;

  /**
   * Метод выполняющий проход по массиву аргументов и проверяет их на валидность.
   *
   * @param args - аргументы приходящие в метод.
   * @return возвращает true если были обнаруженны проблеммы при парсинге
   * массива, и значения были корретированы. В противном случае возвращает false.
   */
  public boolean parse(String[] args) {
    boolean problem = false;
    for (String arg : args) {
      try {
        parseOne(arg);
      } catch (NumberFormatException | NullPointerException ex) {
        problem = true;
      }
    }
    if (checkParam()) {
      problem = true;
    }
    return problem;
  }

  private void parseOne(String arg) throws NumberFormatException, NullPointerException {
    if (arg.contains("-width=")) {
      String width = arg.split("=")[1];
      widthSize = Integer.parseInt(width);
    } else if (arg.contains("-height=")) {
      String height = arg.split("=")[1];
      heightSize = Integer.parseInt(height);
    } else if (arg.contains("-snake_size=")) {
      String snake = arg.split("=")[1];
      snakeSize = Integer.parseInt(snake);
    } else if (arg.contains("-green_frog_number=")) {
      String frog = arg.split("=")[1];
      greenFrogNumber = Integer.parseInt(frog);
    } else if (arg.contains("-snake_sleep=")) {
      String sleep = arg.split("=")[1];
      snakeSleep = Integer.parseInt(sleep);
    } else if (arg.contains("-red_frog_number=")) {
      String frog = arg.split("=")[1];
      redFrogNumber = Integer.parseInt(frog);
    }
  }

  private boolean checkParam() {
    boolean problem = false;
    if (snakeSize > widthSize) {
      snakeSize = widthSize;
      problem = true;
    } else if ((greenFrogNumber + redFrogNumber + snakeSize) > (heightSize * widthSize)) {
      greenFrogNumber = heightSize * widthSize / 10;
      redFrogNumber = heightSize * widthSize / 10;
      problem = true;
    }
    return problem;
  }

  public int getSnakeSleep() {
    return snakeSleep;
  }

  public int getHeightSize() {
    return heightSize;
  }

  public int getWidthSize() {
    return widthSize;
  }

  public int getSnakeSize() {
    return snakeSize;
  }

  public int getGreenFrogNumber() {
    return greenFrogNumber;
  }

  public int getRedFrogNumber() {
    return redFrogNumber;
  }
}
