package controller.commands.color;

import model.pixel.IPixelMutable;

/**
 * Represents an implementation of IProcess that changes a pixel to its luma greyscale.
 */
public class PixelLuma extends AbstractColorComponent {
  @Override
  protected IPixelMutable getComponent(IPixelMutable currentPixel) {
    IPixelMutable newPixel = currentPixel.clonePixel();
    int value = (int) newPixel.getLuma();
    newPixel.setRed(value);
    newPixel.setGreen(value);
    newPixel.setBlue(value);
    return newPixel;
  }

  @Override
  public String toString() {
    return "pixel-luma greyscale";
  }
}
