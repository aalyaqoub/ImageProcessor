package controller.commands.color;

import model.pixel.IPixelMutable;

/**
 * Represents an implementation of IProcess that changes a pixel to its red greyscale.
 */
public class RedComponent extends AbstractColorComponent {
  @Override
  protected IPixelMutable getComponent(IPixelMutable currentPixel) {
    IPixelMutable newPixel = currentPixel.clonePixel();
    newPixel.setGreen(newPixel.getRed());
    newPixel.setBlue(newPixel.getRed());
    return newPixel;
  }

  @Override
  public String toString() {
    return "red-component greyscale";
  }
}
