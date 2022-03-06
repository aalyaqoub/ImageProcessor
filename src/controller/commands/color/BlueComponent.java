package controller.commands.color;

import model.pixel.IPixelMutable;

/**
 * Represents an implementation of IProcess that changes a pixel to its blue greyscale.
 */
public class BlueComponent extends AbstractColorComponent {
  @Override
  protected IPixelMutable getComponent(IPixelMutable currentPixel) {
    IPixelMutable newPixel = currentPixel.clonePixel();
    newPixel.setRed(newPixel.getBlue());
    newPixel.setGreen(newPixel.getBlue());
    return newPixel;
  }

  @Override
  public String toString() {
    return "blue-component greyscale";
  }
}
