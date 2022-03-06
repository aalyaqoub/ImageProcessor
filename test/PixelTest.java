import org.junit.Test;

import model.pixel.IPixelMutable;
import model.pixel.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for {@link Pixel}s.
 */
public class PixelTest {
  IPixelMutable pixel1;
  IPixelMutable pixel2;
  IPixelMutable pixel3;


  @Test
  public void testCreateValidPixel() {
    this.pixel1 = new Pixel(1, 2, 3, 255);
    assertEquals(this.pixel1.getRed(), 1);
    assertEquals(this.pixel1.getGreen(), 2);
    assertEquals(this.pixel1.getBlue(), 3);
    this.pixel1.setRed(100000);
    this.pixel1.setGreen(1000000);
    this.pixel1.setBlue(10000);
    assertEquals(this.pixel1.getRed(), 255);
    assertEquals(this.pixel1.getGreen(), 255);
    assertEquals(this.pixel1.getBlue(), 255);

    this.pixel2 = new Pixel(4, 5, 7, 7);
    assertEquals(this.pixel2.getRed(), 4);
    assertEquals(this.pixel2.getGreen(), 5);
    assertEquals(this.pixel2.getBlue(), 7);
    this.pixel2.setRed(100700);
    this.pixel2.setGreen(1007000);
    this.pixel2.setBlue(17000);
    assertEquals(this.pixel2.getRed(), 7);
    assertEquals(this.pixel2.getGreen(), 7);
    assertEquals(this.pixel2.getBlue(), 7);

    this.pixel3 = new Pixel(8, 9, 10, 15);
    assertEquals(this.pixel3.getRed(), 8);
    assertEquals(this.pixel3.getGreen(), 9);
    assertEquals(this.pixel3.getBlue(), 10);
    this.pixel3.setRed(100730);
    this.pixel3.setGreen(1307000);
    this.pixel3.setBlue(17300);
    assertEquals(this.pixel3.getRed(), 15);
    assertEquals(this.pixel3.getGreen(), 15);
    assertEquals(this.pixel3.getBlue(), 15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPixel() {
    try {
      new Pixel(4, 3, 3, 3);
    } catch (IllegalArgumentException e) {
      new Pixel(4, 3, 3, 3);
      fail("Invalid component levels");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPixel1() {
    try {
      new Pixel(2, 3, 3, 2);
    } catch (IllegalArgumentException e) {
      new Pixel(2, 3, 3, 2);
      fail("Invalid component levels");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPixel2() {
    try {
      new Pixel(2, 3, 1, 1);
    } catch (IllegalArgumentException e) {
      new Pixel(2, 3, 1, 1);
      fail("Invalid component levels");
    }
  }

  @Test
  public void getValue() {
    this.initPixels();

    assertEquals(this.pixel1.getValue(), 3);
    assertEquals(this.pixel1.getValue(), 3);
    assertEquals(this.pixel1.getValue(), 3);

    assertEquals(this.pixel2.getValue(), 7);
    assertEquals(this.pixel2.getValue(), 7);
    assertEquals(this.pixel2.getValue(), 7);

    assertEquals(this.pixel3.getValue(), 10);
    assertEquals(this.pixel3.getValue(), 10);
    assertEquals(this.pixel3.getValue(), 10);
  }

  @Test
  public void getIntensity() {
    this.initPixels();

    assertEquals(String.valueOf(this.pixel1.getIntensity()), "2.0");

    assertEquals(String.valueOf(this.pixel2.getIntensity()), "5.333333333333333");

    assertEquals(String.valueOf(this.pixel3.getIntensity()), "9.0");
  }

  @Test
  public void getLuma() {
    this.initPixels();

    assertEquals(String.valueOf(this.pixel1.getLuma()), "1.8596");

    assertEquals(String.valueOf(this.pixel2.getLuma()), "6.2177999999999995");

    assertEquals(String.valueOf(this.pixel3.getLuma()), "9.1404");
  }

  @Test
  public void clonePixel() {
    this.initPixels();

    assertEquals(pixel1.clonePixel(), pixel1);
    assertEquals(pixel2.clonePixel(), pixel2);
    assertEquals(pixel3.clonePixel(), pixel3);
  }

  @Test
  public void getRed() {
    this.initPixels();

    assertEquals(this.pixel1.getRed(), 1);
    assertEquals(this.pixel2.getRed(), 4);
    assertEquals(this.pixel3.getRed(), 10);
  }

  @Test
  public void getGreen() {
    initPixels();

    assertEquals(this.pixel3.getGreen(), 9);
    assertEquals(this.pixel2.getGreen(), 7);
    assertEquals(this.pixel1.getGreen(), 2);
  }

  @Test
  public void getBlue() {
    initPixels();

    assertEquals(this.pixel1.getBlue(), 3);
    assertEquals(this.pixel2.getBlue(), 5);
    assertEquals(this.pixel3.getBlue(), 8);
  }

  @Test
  public void setRed() {
    this.initPixels();

    assertEquals(this.pixel1.getRed(), 1);
    assertEquals(this.pixel2.getRed(), 4);
    assertEquals(this.pixel3.getRed(), 10);

    this.pixel1.setRed(8);
    this.pixel2.setRed(100700);
    this.pixel3.setRed(3);

    assertEquals(this.pixel1.getRed(), 8);
    assertEquals(this.pixel2.getRed(), 7);
    assertEquals(this.pixel3.getRed(), 3);
  }

  @Test
  public void setGreen() {
    initPixels();

    assertEquals(this.pixel3.getGreen(), 9);
    assertEquals(this.pixel2.getGreen(), 7);
    assertEquals(this.pixel1.getGreen(), 2);

    this.pixel1.setGreen(100700);
    this.pixel2.setGreen(2);
    this.pixel3.setGreen(3);

    assertEquals(this.pixel1.getGreen(), 255);
    assertEquals(this.pixel2.getGreen(), 2);
    assertEquals(this.pixel3.getGreen(), 3);
  }

  @Test
  public void setBlue() {
    initPixels();

    assertEquals(this.pixel1.getBlue(), 3);
    assertEquals(this.pixel2.getBlue(), 5);
    assertEquals(this.pixel3.getBlue(), 8);

    this.pixel1.setBlue(67);
    this.pixel2.setBlue(1);
    this.pixel3.setBlue(100700);

    assertEquals(this.pixel1.getBlue(), 67);
    assertEquals(this.pixel2.getBlue(), 1);
    assertEquals(this.pixel3.getBlue(), 15);
  }

  private void initPixels() {
    this.pixel1 = new Pixel(1, 2, 3, 255);
    this.pixel2 = new Pixel(4, 7, 5, 7);
    this.pixel3 = new Pixel(10, 9, 8, 15);
  }
}