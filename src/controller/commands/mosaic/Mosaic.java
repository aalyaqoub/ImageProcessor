package controller.commands.mosaic;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import controller.commands.IProcess;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixelMutable;
import model.pixel.Pixel;

/**
 * Creates a mosaic of the image with the given seed amount.
 */
public class Mosaic implements IProcess {
  private final int seed;

  /**
   * Constructs a mosaic with the given seed.
   * @param seed the given seed
   */
  public Mosaic(int seed) {
    if (seed < 1) {
      throw new IllegalArgumentException("Can't have seed less than 1");
    }
    this.seed = seed;
  }

  @Override
  public IImageMutable process(IImageMutable image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't have null or empty inputs");
    }

    if (this.seed > image.getWidth() * image.getWidth()) {
      throw new IllegalArgumentException("Can't have a seed greater than the number of pixels");
    }
    if (this.seed > image.getWidth() * image.getWidth()) {
      throw new IllegalArgumentException("Can't have a seed greater than the number of pixels");
    }

    Map<Point2D, Set<Map<Point2D, IPixelMutable>>> pixelMapping = new HashMap<>();

    Random rand = new Random();
    int addedSeeds = 0;

    //creates the seed pixels and adds them to map
    while (addedSeeds < seed) {
      int selectedWidth = rand.nextInt(image.getWidth());
      int selectHeight = rand.nextInt(image.getHeight());
      Point2D point = new Point2D.Double(selectHeight, selectedWidth);
      if (!pixelMapping.containsKey(point)) {
        pixelMapping.put(point, new HashSet<>());
        ++addedSeeds;
      }
    }

    // groups pixels
    double shortestDistance;
    int iteration;
    Point2D closest;
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        IPixelMutable currentPixel = image.getPixel(row, col);
        shortestDistance = 0;
        iteration = 0;
        closest = null;
        for (Map.Entry<Point2D, Set<Map<Point2D, IPixelMutable>>> entry : pixelMapping.entrySet()) {
          Point2D point = entry.getKey();
          double currentDistance = distanceCalc(row, col, point.getX(), point.getY());
          if (currentDistance < shortestDistance || iteration == 0) {
            shortestDistance = currentDistance;
            closest = point;
          }
          ++iteration;
        }

        Set<Map<Point2D, IPixelMutable>> oldSet = pixelMapping.get(closest);
        Point2D pixelPoint = new Point2D.Double(row, col);
        Map<Point2D, IPixelMutable> currentPointInfo = new HashMap<>();
        currentPointInfo.put(pixelPoint, currentPixel);
        oldSet.add(currentPointInfo);
        pixelMapping.put(closest, oldSet);
      }
    }

    //set the average for pixels
    int redTotal;
    int greenTotal;
    int blueTotal;
    int count;
    for (Map.Entry<Point2D, Set<Map<Point2D, IPixelMutable>>> entry : pixelMapping.entrySet()) {
      redTotal = 0;
      greenTotal = 0;
      blueTotal = 0;
      count = 0;

      //get the values in each group
      for (Map<Point2D, IPixelMutable> setEntry : entry.getValue()) {
        for (Map.Entry<Point2D, IPixelMutable> pixelInfo : setEntry.entrySet()) {
          redTotal += pixelInfo.getValue().getRed();
          greenTotal += pixelInfo.getValue().getGreen();
          blueTotal += pixelInfo.getValue().getBlue();
          ++count;
        }
      }

      // sets each pixel to thr average
      for (Map<Point2D, IPixelMutable> setEntry : entry.getValue()) {
        for (Map.Entry<Point2D, IPixelMutable> pixelInfo : setEntry.entrySet()) {
          IPixelMutable averagedPixel = new Pixel(redTotal / count, greenTotal / count,
                  blueTotal / count, image.getMaxValue());
          setEntry.put(pixelInfo.getKey(), averagedPixel);
        }
      }
    }

    IImageMutable imageOutput = new ImageImpl(image.getHeight(), image.getWidth(),
            image.getMaxValue());

    for (Map.Entry<Point2D, Set<Map<Point2D, IPixelMutable>>> entry : pixelMapping.entrySet()) {
      for (Map<Point2D, IPixelMutable> setEntry : entry.getValue()) {
        for (Map.Entry<Point2D, IPixelMutable> pixelInfo : setEntry.entrySet()) {
          imageOutput.setPixel((int) pixelInfo.getKey().getX(), (int) pixelInfo.getKey().getY(),
                  pixelInfo.getValue());
        }
      }
    }

    return imageOutput;
  }

  private double distanceCalc(int fromX, int fromY, double toX, double toY) {
    double verticalDis = Math.abs(fromY - toY);
    double horizontalDis = Math.abs(fromX - toX);
    return Math.sqrt((Math.pow(verticalDis, 2) + Math.pow(horizontalDis, 2)));
  }

  @Override
  public String toString() {
    return "mosaic";
  }
}
