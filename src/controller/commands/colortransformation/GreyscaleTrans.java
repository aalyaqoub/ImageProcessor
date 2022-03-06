package controller.commands.colortransformation;

/**
 * Greyscale command which can be used to make an image greyscale.
 */
public class GreyscaleTrans extends AbstractColorTrans {

  /**
   * Creates GreyscaleTrans command object.
   */
  public GreyscaleTrans() {
    super(new double[][]{
            {0.21260, 0.71520, 0.0722},
            {0.21260, 0.71520, 0.0722},
            {0.21260, 0.71520, 0.0722}});
  }

  @Override
  public String toString() {
    return "greyscale transformation";
  }
}


