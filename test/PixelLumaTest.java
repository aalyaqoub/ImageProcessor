import org.junit.Test;

import java.io.FileNotFoundException;

import controller.commands.color.PixelIntensity;
import controller.commands.color.PixelLuma;
import controller.commands.reader.ReadPPM;
import model.image.IImage;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;
import model.pixel.IPixelMutable;
import model.pixel.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link PixelLuma}s.
 */
public class PixelLumaTest {
  IPixelMutable pixel1;
  IPixelMutable pixel2;
  IPixelMutable pixel3;
  IPixelMutable pixel4;
  IPixel[][] imagePixels;
  IImageMutable image;

  @Test
  public void getComponent() {
    initPixels();
    for (int row = 0; row < this.image.getHeight(); row++) {
      for (int col = 0; col < this.image.getWidth(); col++) {
        assertEquals(this.image.getPixel(row, col), this.imagePixels[row][col]);
      }
    }

    IImage pixelLumaTest = new PixelLuma().process(this.image);
    for (int row = 0; row < pixelLumaTest.getHeight(); row++) {
      for (int col = 0; col < pixelLumaTest.getWidth(); col++) {
        int luma = (int) pixelLumaTest.getPixel(row, col).getLuma();
        assertEquals(pixelLumaTest.getPixel(row, col).getRed(), luma);
        assertEquals(pixelLumaTest.getPixel(row, col).getGreen(), luma);
        assertEquals(pixelLumaTest.getPixel(row, col).getBlue(), luma);
      }
    }
  }

  @Test
  public void getComponent1() {
    initSavedImage();

    IImage pixelIntensityImage = new PixelIntensity().process(this.image);
    for (int row = 0; row < pixelIntensityImage.getHeight(); row++) {
      for (int col = 0; col < pixelIntensityImage.getWidth(); col++) {
        int intensity = (int) pixelIntensityImage.getPixel(row, col).getIntensity();
        assertEquals(pixelIntensityImage.getPixel(row, col).getRed(), intensity);
        assertEquals(pixelIntensityImage.getPixel(row, col).getGreen(), intensity);
        assertEquals(pixelIntensityImage.getPixel(row, col).getBlue(), intensity);
      }
    }
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

  @Test
  public void testToString() {
    assertEquals("pixel-luma greyscale", new PixelLuma().toString());
  }
}