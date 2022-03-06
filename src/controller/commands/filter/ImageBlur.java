package controller.commands.filter;

/**
 * ImageBlur command objects can be used to add a blur effect to images.
 */
public class ImageBlur extends AbstractFiltering {


  /**
   * creates imageBlur command Object.
   */
  public ImageBlur() {
    super(new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}});

  }

  @Override
  public String toString() {
    return "image blur";
  }
}

