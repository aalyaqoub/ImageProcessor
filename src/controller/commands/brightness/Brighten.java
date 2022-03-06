package controller.commands.brightness;

/**
 * Represents an implementation of IProcess that allows for brightening images by increasing the
 * value of rgd of each pixel by the specified amount.
 */
public class Brighten extends AbstractBrightness {
  private final int brightnessChange;

  /**
   * Constructs a Brighten with the desired increment amount to increase brightness by.
   *
   * @param brightnessChange the desired increment amount to increase brightness by
   */
  public Brighten(int brightnessChange) {
    this.brightnessChange = brightnessChange;
  }

  @Override
  public String toString() {
    return "brighten";
  }

  @Override
  protected int operator(int currentValue) {
    return currentValue + this.brightnessChange;
  }
}
