package model.pixel;

import java.util.Objects;

/**
 * Implementation of IPixelMutable that represents an rgb pixel.
 */
public class Pixel implements IPixelMutable {
  private int red;
  private int green;
  private int blue;
  private int maxValue;

  /**
   * Constructs a Pixel with the given red, green, blue, and maxvalue.
   *
   * @param red      the red rgb value
   * @param green    the green rgb value
   * @param blue     the blue rgb value
   * @param maxValue the max rgb value.
   */
  public Pixel(int red, int green, int blue, int maxValue) {
    if ((red >= 0 && red <= maxValue) && (blue >= 0 && blue <= maxValue)
            && (green >= 0 && green <= maxValue)) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.maxValue = maxValue;
    } else {
      throw new IllegalArgumentException("Invalid component levels");
    }
  }

  @Override
  public int getValue() {
    return Math.max(Math.max(this.red, this.green), this.blue);
  }

  @Override
  public double getIntensity() {
    return (this.red + this.green + this.blue) / 3.0;
  }

  @Override
  public double getLuma() {
    return 0.2126 * this.red + 0.7152 * this.green + 0.0722 * this.blue;
  }

  @Override
  public IPixelMutable clonePixel() {
    return new Pixel(this.red, this.green, this.blue, this.maxValue);
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public void setRed(int value) {
    if (value < 0) {
      this.red = 0;
    } else if (value > this.maxValue) {
      this.red = this.maxValue;
    } else {
      this.red = value;
    }
  }

  @Override
  public void setGreen(int value) {
    if (value < 0) {
      this.green = 0;
    } else if (value > this.maxValue) {
      this.green = this.maxValue;
    } else {
      this.green = value;
    }
  }

  @Override
  public void setBlue(int value) {
    if (value < 0) {
      this.blue = 0;
    } else if (value > this.maxValue) {
      this.blue = this.maxValue;
    } else {
      this.blue = value;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    Pixel pixel = (Pixel) obj;
    return pixel.getRed() == this.getRed() && pixel.getGreen() == this.getGreen()
            && pixel.blue == this.getBlue() && pixel.maxValue == this.maxValue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getRed(), this.getGreen(), this.getBlue(),
            this.getMaxValue());
  }
}
