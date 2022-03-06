package controller.commands.flipping;

/**
 * Represents an implementation of IProcess that allows for flipping an images along the vertical
 * and horizontal axises by repositioning pixels.
 */
public class FlipBothAxis extends AbstractFlipping {
  @Override
  protected int[] getNewCoordinates(int height, int width, int rowPosition, int colPosition) {
    int newRow = (height - rowPosition) - 1;
    int newCol = (width - colPosition) - 1;
    return new int[]{newRow, newCol};
  }

  @Override
  public String toString() {
    return "flip on both axises";
  }
}
