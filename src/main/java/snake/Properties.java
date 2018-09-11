package snake;

public class Properties {
  private int heightSize = 40;
  private int widthSize = 40;
  private int snakeSize = 5;
  private int frogNumber = 40;
  private int snakeSleep = 200;

  public int getSnakeSleep() {
    return snakeSleep;
  }

  public void setSnakeSleep(int snakeSleep) {
    this.snakeSleep = snakeSleep;
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
