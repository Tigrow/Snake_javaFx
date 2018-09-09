package snake;

public class Properties {
  public int heightSize = 40;
  public int widthSize = 40;
  public int snakeSize = 5;
  private int frogNumber = 500;

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

  public void setHeightSize(int heightSize) {
    this.heightSize = heightSize;
  }

  public void setWidthSize(int widthSize) {
    this.widthSize = widthSize;
  }

  public void setSnakeSize(int snakeSize) {
    this.snakeSize = snakeSize;
  }

  public void setFrogNumber(int frogNumber) {
    this.frogNumber = frogNumber;
  }
}
