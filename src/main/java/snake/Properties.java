package snake;

public class Properties {
  private int heightSize = 10;
  private int widthSize = 10;
  private int snakeSize = 5;
  private int frogNumber = 95;
  private int snakeSleep = 500;

  public boolean parse(String[] args) {
    boolean problem = false;
    for (int i = 0; i < args.length; i++) {
      try {
        parseOne(args[i]);
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
    } else if (arg.contains("-frog_number=")) {
      String frog = arg.split("=")[1];
      frogNumber = Integer.parseInt(frog);
    } else if (arg.contains("-snake_sleep=")) {
      String sleep = arg.split("=")[1];
      snakeSleep = Integer.parseInt(sleep);
    }
  }

  private boolean checkParam() {
    boolean problem = false;
    if (snakeSize > widthSize) {
      snakeSize = widthSize;
      problem = true;
    } else if ((frogNumber + snakeSize) > (heightSize * widthSize)) {
      frogNumber = heightSize * widthSize / 10;
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

  public int getFrogNumber() {
    return frogNumber;
  }

}
