package controller.commands.flipping;

/**
 * Represents an implementation of IProcess that allows for flipping an images along the horizontal
 * axises by repositioning the current pixels.
 */
public class FlipHorizontal extends AbstractFlipping {
  @Override
  protected int[] getNewCoordinates(int height, int width, int rowPosition, int colPosition) {
    int newCol = (width - colPosition) - 1;
    return new int[]{rowPosition, newCol};
  }

  @Override
  public String toString() {
    return "horizontally flipped";
  }
}
