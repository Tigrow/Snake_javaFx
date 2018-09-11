package snake;

public class Properties {
  private int heightSize = 40;
  private int widthSize = 40;
  private int snakeSize = 5;
  private int frogNumber = 20;
  private int snakeSleep = 200;

  public void parse(String[] args) {
    for (int i = 0; i < args.length; i++) {
      try {
        parseOne(args[i]);
      } catch (NumberFormatException ex) {
        //
      } catch (NullPointerException ex) {
        //
      }
    }
    checkParam();
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

  private void checkParam() {
    if (snakeSize > widthSize) {
      snakeSize = widthSize;
    } else if ((frogNumber + snakeSize) >= (heightSize * widthSize)) {
      frogNumber = heightSize * widthSize / 10;
    }
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
