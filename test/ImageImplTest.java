import org.junit.Test;

import java.io.FileNotFoundException;

import controller.commands.reader.ContentPair;
import controller.commands.reader.ReadPPM;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;
import model.pixel.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for {@link ImageImpl}s.
 */
public class ImageImplTest {
  IPixel[][] imagePixels;

  /**
   * Test if it is impossible to create a ImageImpl with null ContentPair.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelImageImpl1() {
    ContentPair<IPixel[][], Integer> content = null;
    try {
      IImageMutable model = new ImageImpl(content);
    } catch (IllegalArgumentException e) {
      IImageMutable model = new ImageImpl(content);
      fail("Can't have null inputs");
    }
  }

  /**
   * Test if it is impossible to create a ImageImpl with null ContentPair.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelImageImpl2() {
    IPixel[][] pixels = null;
    try {
      IImageMutable model = new ImageImpl(pixels);
    } catch (IllegalArgumentException e) {
      IImageMutable model = new ImageImpl(pixels);
      fail("Can't have null inputs");
    }
  }

  /**
   * Test if it is impossible to create a ImageImpl with negative inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeModelImageImpl1() {
    try {
      IImageMutable model = new ImageImpl(1, 1, -1);
    } catch (IllegalArgumentException e) {
      IImageMutable model = new ImageImpl(1, 1, -1);
      fail("Can't have negative row, col, maxValue");
    }
  }

  /**
   * Test if it is impossible to create a ImageImpl with negative inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeModelImageImpl2() {
    try {
      IImageMutable model = new ImageImpl(1, -1, 1);
    } catch (IllegalArgumentException e) {
      IImageMutable model = new ImageImpl(1, -1, 1);
      fail("Can't have negative row, col, maxValue");
    }
  }

  /**
   * Test if it is impossible to create a ImageImpl with negative inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeModelImageImpl3() {
    try {
      IImageMutable model = new ImageImpl(-1, 1, 1);
    } catch (IllegalArgumentException e) {
      IImageMutable model = new ImageImpl(-1, 1, 1);
      fail("Can't have negative row, col, maxValue");
    }
  }

  /**
   * Test valid ImageImpl.
   */
  @Test
  public void testValidModelImageImpl1() {
    try {
      this.initImage();
      ContentPair<IPixel[][], Integer> content = new ReadPPM()
              .read("res/example/PPM/OGImage.ppm");
      IImageMutable model = new ImageImpl(content);
      assertEquals(model.getWidth(), 3);
      assertEquals(model.getHeight(), 3);
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getWidth(); col++) {
          assertEquals(model.getPixel(row, col), this.imagePixels[row][col]);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Test valid ImageImpl.
   */
  @Test
  public void testValidModelImageImpl2() {
    this.initImage();
    IImageMutable model = new ImageImpl(this.imagePixels);
    assertEquals(model.getWidth(), 3);
    assertEquals(model.getHeight(), 3);
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        assertEquals(model.getPixel(row, col), this.imagePixels[row][col]);
      }
    }
  }

  /**
   * Test setPixel.
   */
  @Test
  public void testSetPixel() {
    try {
      this.initImage();
      ContentPair<IPixel[][], Integer> content = new ReadPPM()
              .read("res/example/PPM/OGImage.ppm");
      IImageMutable model = new ImageImpl(content);
      assertEquals(model.getPixel(0, 0), this.imagePixels[0][0]);
      IPixel newPixel = new Pixel(0, 0, 0, 255);
      model.setPixel(0, 0, newPixel);
      assertEquals(model.getPixel(0, 0), newPixel);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Test getPixel.
   */
  @Test
  public void testGetPixel() {
    try {
      this.initImage();
      ContentPair<IPixel[][], Integer> content = new ReadPPM()
              .read("res/example/PPM/OGImage.ppm");
      IImageMutable model = new ImageImpl(content);
      assertEquals(model.getPixel(0, 0), this.imagePixels[0][0]);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Test getWidth.
   */
  @Test
  public void testGetWidth() {
    try {
      this.initImage();
      ContentPair<IPixel[][], Integer> content = new ReadPPM()
              .read("res/example/PPM/OGImage.ppm");
      IImageMutable model = new ImageImpl(content);
      assertEquals(model.getWidth(), 3);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Test getHeight.
   */
  @Test
  public void testGetHeight() {
    try {
      this.initImage();
      ContentPair<IPixel[][], Integer> content = new ReadPPM()
              .read("res/example/PPM/OGImage.ppm");
      IImageMutable model = new ImageImpl(content);
      assertEquals(model.getHeight(), 3);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Test getMaxValue.
   */
  @Test
  public void testGetMaxValue() {
    try {
      this.initImage();
      ContentPair<IPixel[][], Integer> content = new ReadPPM()
              .read("res/example/PPM/OGImage.ppm");
      IImageMutable model = new ImageImpl(content);
      assertEquals(model.getMaxValue(), 255);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Test clone.
   */
  @Test
  public void testGetClone() {
    try {
      this.initImage();
      ContentPair<IPixel[][], Integer> content = new ReadPPM()
              .read("res/example/PPM/OGImage.ppm");
      IImageMutable model = new ImageImpl(content);
      IImageMutable clone = model.getClone();
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getWidth(); col++) {
          assertEquals(model.getPixel(row, col), clone.getPixel(row, col));
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  private void initImage() {
    try {
      this.imagePixels = new ReadPPM().read("res/example/PPM/OGImage.ppm").getFirst();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
}