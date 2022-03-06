package controller.commands.filter;

/**
 * ImageSharpen command object can be used to sharpen images.
 */
public class ImageSharpen extends AbstractFiltering {


  /**
   * creates a ImageSharpen command object.
   */
  public ImageSharpen() {
    super(new double[][]{
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8,},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8,}});

  }

  @Override
  public String toString() {
    return "image sharpen";
  }
}