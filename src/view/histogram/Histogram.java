package view.histogram;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import controller.commands.flipping.FlipBothAxis;
import controller.commands.flipping.FlipHorizontal;
import model.image.IImage;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;
import model.pixel.Pixel;

/**
 * A class that is able to create an image of a histogram of RGB and intensity channels from a given
 * IImage. This is done by counting the occurrence of each value in a channel and then creating a
 * histogram with that.
 */
public class Histogram implements IHistogram {
  private Map<Integer, Integer> redValues; // pixelValue, count
  private Map<Integer, Integer> greenValues;
  private Map<Integer, Integer> blueValues;
  private Map<Integer, Integer> intensityValues;
  private Integer maxCount;

  /**
   * Constructor for the histogram. It initializes the fields in the class.
   */
  public Histogram() {
    this.redValues = new HashMap<>();
    this.greenValues = new HashMap<>();
    this.blueValues = new HashMap<>();
    this.intensityValues = new HashMap<>();
    this.maxCount = 0;
  }

//  @Override
//  public void showHistogram(IImage image) {
//    this.showHistogram(image, "res/appData/histogram.png");
//  }

  @Override
  public BufferedImage showHistogram(IImage image) {
    this.setChannelValues(image);
    IPixel whitePixel = new Pixel(image.getMaxValue(), image.getMaxValue(), image.getMaxValue(),
            image.getMaxValue());

    IPixel redPixel = new Pixel(image.getMaxValue(), 0, 0, image.getMaxValue());
    IPixel greenPixel = new Pixel(0, image.getMaxValue(), 0, image.getMaxValue());
    IPixel bluePixel = new Pixel(0, 0, image.getMaxValue(), image.getMaxValue());
    IPixel blackPixel = new Pixel(0, 0, 0, image.getMaxValue());

    int groupDivision = this.maxCount / image.getMaxValue() * 2;

    IPixel[][] histogramPixels;
    if (groupDivision != 0) {
      histogramPixels = new IPixel[this.maxCount / groupDivision + 21][image.getMaxValue() + 11];
      for (IPixel[] histogramPixel : histogramPixels) {
        Arrays.fill(histogramPixel, whitePixel);
      }
      this.setChannelLines(groupDivision, histogramPixels, this.redValues, redPixel);
      this.setChannelLines(groupDivision, histogramPixels, this.greenValues, greenPixel);
      this.setChannelLines(groupDivision, histogramPixels, this.blueValues, bluePixel);
      this.setChannelLines(groupDivision, histogramPixels, this.intensityValues, blackPixel);
    } else {
      histogramPixels = new IPixel[this.maxCount + 11][image.getMaxValue() + 11];
      for (IPixel[] histogramPixel : histogramPixels) {
        Arrays.fill(histogramPixel, whitePixel);
      }
      this.setChannelLines(1, histogramPixels, this.redValues, redPixel);
      this.setChannelLines(1, histogramPixels, this.greenValues, greenPixel);
      this.setChannelLines(1, histogramPixels, this.blueValues, bluePixel);
      this.setChannelLines(1, histogramPixels, this.intensityValues, blackPixel);
    }
    IImageMutable histogram = new FlipHorizontal()
            .process(new FlipBothAxis().process(new ImageImpl(histogramPixels)));
//    new SaveAsPNG().save(path, histogram);
    return histogram.getBufferedImage();
  }

  @Override
  public int redCount(int value) {
    return this.redValues.getOrDefault(value, 0);
  }

  @Override
  public int greenCount(int value) {
    return this.greenValues.getOrDefault(value, 0);
  }

  @Override
  public int blueCount(int value) {
    return this.blueValues.getOrDefault(value, 0);
  }

  @Override
  public int intensityCount(int value) {
    return this.intensityValues.getOrDefault(value, 0);
  }

  /**
   * Sets the count of each pixel value for the image.
   *
   * @param image the image being worked on.
   */
  private void setChannelValues(IImage image) {
    this.initMaps(image.getMaxValue());

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
   * Initializes the map for color values and sets count to 0.
   *
   * @param maxValue the max value a pixels in the image
   */
  private void initMaps(int maxValue) {
    for (int value = 0; value <= maxValue; value++) {
      this.redValues.put(value, 0);
      this.greenValues.put(value, 0);
      this.blueValues.put(value, 0);
      this.intensityValues.put(value, 0);
    }
  }

  /**
   * Create line representing the distribution of pixel value counts for each channel.
   *
   * @param groupDivision   an int to divide the counts into groups
   * @param histogramPixels the pixels of the image
   * @param valuesMap       the count of the values
   * @param pixel           the pixel to compose the line of
   */
  private void setChannelLines(int groupDivision, IPixel[][] histogramPixels,
                               Map<Integer, Integer> valuesMap, IPixel pixel) {
    for (Map.Entry<Integer, Integer> entry : valuesMap.entrySet()) {
      if (entry.getKey() > 0) {
        int oldColumn = entry.getKey() - 1;
        int oldCount = valuesMap.get(oldColumn) / groupDivision + 10;

        int difference = entry.getValue() / groupDivision + 10 - oldCount;
        int startPosnY = entry.getValue() / groupDivision + 10;
        if (difference < 0) {
          while (difference != 0) {
            histogramPixels[startPosnY][oldColumn + 5] = pixel;
            startPosnY++;
            difference++;
          }
        } else {
          while (difference != 0) {
            histogramPixels[startPosnY][oldColumn + 5] = pixel;
            startPosnY--;
            difference--;
          }
        }
      }
      histogramPixels[entry.getValue() / groupDivision + 10][entry.getKey() + 5] = pixel;
    }
  }

}
