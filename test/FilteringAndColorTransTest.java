import org.junit.Test;

import java.io.FileNotFoundException;

import controller.commands.colortransformation.GreyscaleTrans;
import controller.commands.colortransformation.SepiaToneTrans;
import controller.commands.filter.ImageBlur;
import controller.commands.filter.ImageSharpen;
import controller.commands.reader.ReadPNG;
import model.image.IImageMutable;
import model.image.ImageImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for using Filter command objects on images.
 */
public class FilteringAndColorTransTest {

  @Test
  public void testBlur() throws FileNotFoundException {
    IImageMutable image = new ImageImpl(new ReadPNG()
            .read("res/example/PNG/OGImage.png"));
    IImageMutable imageBlur = new ImageImpl(new ReadPNG()
            .read("res/example/PNG/blur.png"));
    IImageMutable blurredImage = new ImageBlur().process(image);
    for (int row = 2; row < image.getHeight() - 2; row++) {
      for (int col = 2; col < image.getWidth() - 2; col++) {

        assertEquals(imageBlur.getPixel(row, col).getRed(),
                blurredImage.getPixel(row, col).getRed(), 2);
        assertEquals(imageBlur.getPixel(row, col).getGreen(),
                blurredImage.getPixel(row, col).getGreen(), 2);
        assertEquals(imageBlur.getPixel(row, col).getBlue(),
                blurredImage.getPixel(row, col).getBlue(), 2);
      }
    }

  }

  @Test
  public void testSharpen() throws FileNotFoundException {
    IImageMutable image = new ImageImpl(new ReadPNG()
            .read("res/example/PNG/OGImage.png"));
    IImageMutable imageSharpened = new ImageImpl(new ReadPNG()
            .read("res/example/PNG/sharpen.png"));
    IImageMutable sharpenedImage = new ImageSharpen().process(image);
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {

        assertEquals(imageSharpened.getPixel(row, col).getRed(),
                sharpenedImage.getPixel(row, col).getRed(), 2);
        assertEquals(imageSharpened.getPixel(row, col).getGreen(),
                sharpenedImage.getPixel(row, col).getGreen(), 2);
        assertEquals(imageSharpened.getPixel(row, col).getBlue(),
                sharpenedImage.getPixel(row, col).getBlue(), 2);
      }
    }
  }

  @Test
  public void testGreyscale() throws FileNotFoundException {
    IImageMutable image = new ImageImpl(new ReadPNG()
            .read("res/example/PNG/OGImage.png"));
    IImageMutable givenImage = new ImageImpl(new ReadPNG()
            .read("res/example/PNG/greyscaleTrans.png"));
    IImageMutable processImage = new GreyscaleTrans().process(image);
    for (int row = 2; row < image.getHeight() - 2; row++) {
      for (int col = 2; col < image.getWidth() - 2; col++) {

        assertEquals(givenImage.getPixel(row, col).getRed(),
                processImage.getPixel(row, col).getRed(), 2);
        assertEquals(givenImage.getPixel(row, col).getGreen(),
                processImage.getPixel(row, col).getGreen(), 2);
        assertEquals(givenImage.getPixel(row, col).getBlue(),
                processImage.getPixel(row, col).getBlue(), 2);
      }
    }

  }

  @Test
  public void testSepia() throws FileNotFoundException {
    IImageMutable image = new ImageImpl(new ReadPNG()
            .read("res/example/PNG/OGImage.png"));
    IImageMutable givenImage = new ImageImpl(new ReadPNG()
            .read("res/example/PNG/sepia.png"));
    IImageMutable processImage = new SepiaToneTrans().process(image);
    for (int row = 2; row < image.getHeight() - 2; row++) {
      for (int col = 2; col < image.getWidth() - 2; col++) {

        assertEquals(givenImage.getPixel(row, col).getRed(),
                processImage.getPixel(row, col).getRed(), 2);
        assertEquals(givenImage.getPixel(row, col).getGreen(),
                processImage.getPixel(row, col).getGreen(), 2);
        assertEquals(givenImage.getPixel(row, col).getBlue(),
                processImage.getPixel(row, col).getBlue(), 2);
      }
    }

  }

  @Test
  public void testToString() {
    assertEquals("image blur", new ImageBlur().toString());
    assertEquals("image sharpen", new ImageSharpen().toString());
    assertEquals("greyscale transformation", new GreyscaleTrans().toString());
    assertEquals("sepia tone", new SepiaToneTrans().toString());
  }


}
