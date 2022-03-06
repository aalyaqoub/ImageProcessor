package controller.commands.brightness;

/**
 * Represents an implementation of IProcess that allows darkening pixels by decreasing the value of
 * rgd by the specified amount.
 */
public class Darken extends AbstractBrightness {
  private final int brightnessChange;

  /**
   * Constructs a Darken with the desired decrement amount to decrease brightness by.
   *
   * @param brightnessChange the desired decrement amount to decrease brightness by
   */
  public Darken(int brightnessChange) {
    this.brightnessChange = brightnessChange;
  }

  @Override
  protected int operator(int currentValue) {
    return currentValue - brightnessChange;
  }

  @Override
  public String toString() {
    return "darken";
  }
}
