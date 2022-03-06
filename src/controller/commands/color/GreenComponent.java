package controller.commands.color;

import model.pixel.IPixelMutable;

/**
 * Represents an implementation of IProcess that changes a pixel to its green greyscale.
 */
public class GreenComponent extends AbstractColorComponent {
  @Override
  protected IPixelMutable getComponent(IPixelMutable currentPixel) {
    IPixelMutable newPixel = currentPixel.clonePixel();
    newPixel.setRed(newPixel.getGreen());
    newPixel.setBlue(newPixel.getGreen());
    return newPixel;
  }

  @Override
  public String toString() {
    return "green-component greyscale";
  }
}
