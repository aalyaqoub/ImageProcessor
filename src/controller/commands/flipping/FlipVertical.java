package controller.commands.flipping;

/**
 * Represents an implementation of IProcess that allows for flipping an images along the vertical
 * axises by repositioning the current pixels.
 */
public class FlipVertical extends AbstractFlipping {
  @Override
  protected int[] getNewCoordinates(int height, int width, int rowPosition, int colPosition) {
    int newRow = (height - rowPosition) - 1;
    return new int[]{newRow, colPosition};
  }

  @Override
  public String toString() {
    return "vertically flipped";
  }
}
