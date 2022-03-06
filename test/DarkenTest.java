import org.junit.Test;

import java.io.FileNotFoundException;

import controller.commands.brightness.Darken;
import controller.commands.reader.ReadPPM;
import model.image.IImage;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;
import model.pixel.IPixelMutable;
import model.pixel.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link Darken}s.
 */
public class DarkenTest {
  IPixelMutable pixel1;
  IPixelMutable pixel2;
  IPixelMutable pixel3;
  IPixelMutable pixel4;
  IPixel[][] imagePixels;
  IImageMutable image;

  @Test
  public void brightnessEffect() {
    initPixels();
    for (int row = 0; row < this.image.getHeight(); row++) {
      for (int col = 0; col < this.image.getWidth(); col++) {
        assertEquals(this.image.getPixel(row, col), this.imagePixels[row][col]);
      }
    }

    IImageMutable darkenedImage = new Darken(100).process(this.image);
    for (int row = 0; row < darkenedImage.getHeight(); row++) {
      for (int col = 0; col < darkenedImage.getWidth(); col++) {
        assertEquals(darkenedImage.getPixel(row, col).getRed(),
                Math.max(this.imagePixels[row][col].getRed() - 100, 0));
        assertEquals(darkenedImage.getPixel(row, col).getGreen(),
                Math.max(this.imagePixels[row][col].getGreen() - 100, 0));
        assertEquals(darkenedImage.getPixel(row, col).getBlue(),
                Math.max(this.imagePixels[row][col].getBlue() - 100, 0));
      }
    }
  }

  @Test
  public void brightnessEffect1() {
    initSavedImage();

    IImage darkenedImage = new Darken(120).process(this.image);

    for (int row = 0; row < darkenedImage.getHeight(); row++) {
      for (int col = 0; col < darkenedImage.getWidth(); col++) {
        assertEquals(darkenedImage.getPixel(row, col).getRed(),
                Math.max(this.image.getPixel(row, col).getRed() - 120, 0));
        assertEquals(darkenedImage.getPixel(row, col).getGreen(),
                Math.max(this.image.getPixel(row, col).getGreen() - 120, 0));
        assertEquals(darkenedImage.getPixel(row, col).getBlue(),
                Math.max(this.image.getPixel(row, col).getBlue() - 120, 0));
      }
    }
  }

  @Test
  public void testToString() {
    assertEquals("darken", new Darken(100).toString());
  }

  private void initPixels() {
    this.pixel1 = new Pixel(1, 2, 3, 255);
    this.pixel2 = new Pixel(4, 7, 5, 7);
    this.pixel3 = new Pixel(10, 9, 8, 15);
    this.pixel4 = new Pixel(121, 3, 42, 255);
    IPixel[] row1 = new IPixel[]{this.pixel1, this.pixel2};
    IPixel[] row2 = new IPixel[]{this.pixel3, this.pixel4};
    this.imagePixels = new IPixel[][]{row1, row2};
    this.image = new ImageImpl(imagePixels);
  }

  /**
   * Initializes the Image using the test image stores in the testImages file.
   */
  private void initSavedImage() {
    try {
      this.image = new ImageImpl(new ReadPPM().read("res/example/PPM/OGImage.ppm"));
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
}