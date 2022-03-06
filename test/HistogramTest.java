import junit.framework.TestCase;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import controller.commands.reader.ReadPNG;
import controller.commands.reader.ReadPPM;
import model.image.IImage;
import model.image.ImageImpl;
import model.pixel.IPixel;
import view.histogram.Histogram;
import view.histogram.IHistogram;

/**
 * Tests if {@link Histogram}s reads in and counts pixel values  correctly.
 */
public class HistogramTest extends TestCase {
  private Map<Integer, Integer> redValues;
  private Map<Integer, Integer> greenValues;
  private Map<Integer, Integer> blueValues;
  private Map<Integer, Integer> intensityValues;
  private Integer maxCount;

  /**
   * Creates an histogram and saves it as a png.
   *
   * @throws FileNotFoundException if read can't file
   */
  @Test
  public void testShowHistogram() throws FileNotFoundException {
    File tempFile = new File("res/appData/histogram.png");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImage actualImage = new ImageImpl(new ReadPNG().read("res/appData/temp.png"));

    new Histogram().showHistogram(actualImage);
    assertTrue(tempFile.exists());
  }

  /**
   * Tests if the histogram correctly counts the values of the red channel.
   *
   * @throws FileNotFoundException if the read can't find file
   */
  @Test
  public void testRedCount() throws FileNotFoundException {
    IImage actualImage = new ImageImpl(new ReadPPM().read("res/example/PPM/OGImage.ppm"));
    this.initMaps(actualImage.getMaxValue());
    this.setCounts(actualImage);
    IHistogram histogram = new Histogram();
    histogram.showHistogram(actualImage);
    for (Map.Entry<Integer, Integer> entry : this.redValues.entrySet()) {
      int currentCount = entry.getValue();
      int actualCount = histogram.redCount(entry.getKey());
      assertEquals(currentCount, actualCount);
    }
  }

  /**
   * Tests if the histogram correctly counts the values of the green channel.
   *
   * @throws FileNotFoundException if the read can't find file
   */
  @Test
  public void testGreenCount() throws FileNotFoundException {
    IImage actualImage = new ImageImpl(new ReadPPM().read("res/example/PPM/OGImage.ppm"));
    this.initMaps(actualImage.getMaxValue());
    this.setCounts(actualImage);
    IHistogram histogram = new Histogram();
    histogram.showHistogram(actualImage);
    for (Map.Entry<Integer, Integer> entry : this.greenValues.entrySet()) {
      int currentCount = entry.getValue();
      int actualCount = histogram.greenCount(entry.getKey());
      assertEquals(currentCount, actualCount);
    }
  }

  /**
   * Tests if the histogram correctly counts the values of the blue channel.
   *
   * @throws FileNotFoundException if the read can't find file
   */
  @Test
  public void testBlueCount() throws FileNotFoundException {
    IImage actualImage = new ImageImpl(new ReadPPM().read("res/example/PPM/OGImage.ppm"));
    this.initMaps(actualImage.getMaxValue());
    this.setCounts(actualImage);
    IHistogram histogram = new Histogram();
    histogram.showHistogram(actualImage);
    for (Map.Entry<Integer, Integer> entry : this.blueValues.entrySet()) {
      int currentCount = entry.getValue();
      int actualCount = histogram.blueCount(entry.getKey());
      assertEquals(currentCount, actualCount);
    }
  }

  /**
   * Tests if the histogram correctly counts the values of the blue channel.
   *
   * @throws FileNotFoundException if the read can't find file
   */
  @Test
  public void testIntensityCount() throws FileNotFoundException {
    IImage actualImage = new ImageImpl(new ReadPPM().read("res/example/PPM/OGImage.ppm"));
    this.initMaps(actualImage.getMaxValue());
    this.setCounts(actualImage);
    IHistogram histogram = new Histogram();
    histogram.showHistogram(actualImage);
    for (Map.Entry<Integer, Integer> entry : this.intensityValues.entrySet()) {
      int currentCount = entry.getValue();
      int actualCount = histogram.intensityCount(entry.getKey());
      assertEquals(currentCount, actualCount);
    }
  }

  /**
   * Sets the counts of the channels.
   */
  private void setCounts(IImage image) {
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        IPixel currentPixel = image.getPixel(row, col);
        Integer currentRed = currentPixel.getRed();
        Integer currentGreen = currentPixel.getGreen();
        Integer currentBlue = currentPixel.getBlue();
        Integer currentIntensity = (int) currentPixel.getIntensity();

        Integer redCount = this.redValues.get(currentRed);
        Integer greenCount = this.greenValues.get(currentGreen);
        Integer blueCount = this.blueValues.get(currentBlue);
        Integer intensityCount = this.intensityValues.get(currentIntensity);

        this.redValues.put(currentRed, redCount + 1);
        if (redCount + 1 > this.maxCount) {
          this.maxCount = redCount + 1;
        }
        this.greenValues.put(currentGreen, greenCount + 1);
        if (greenCount + 1 > this.maxCount) {
          this.maxCount = greenCount + 1;
        }
        this.blueValues.put(currentBlue, blueCount + 1);
        if (blueCount + 1 > this.maxCount) {
          this.maxCount = blueCount + 1;
        }

        this.intensityValues.put(currentIntensity, intensityCount + 1);
        if (intensityCount + 1 > this.maxCount) {
          this.maxCount = intensityCount + 1;
        }
      }
    }
  }

  /**
   * Initializes the maps with all possible channel values.
   *
   * @param maxValue the max value of the channel
   */
  private void initMaps(int maxValue) {
    this.redValues = new HashMap<>();
    this.greenValues = new HashMap<>();
    this.blueValues = new HashMap<>();
    this.intensityValues = new HashMap<>();
    this.maxCount = 0;
    for (int value = 0; value <= maxValue; value++) {
      this.redValues.put(value, 0);
      this.greenValues.put(value, 0);
      this.blueValues.put(value, 0);
      this.intensityValues.put(value, 0);
    }
  }
}