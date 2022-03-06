package controller.commands.reader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.pixel.IPixel;
import model.pixel.Pixel;

/**
 * Abstract implementation of IReader for varying files. This class will be able to open and read
 * files.
 */
public abstract class AbstractReader implements IReader {
  @Override
  public ContentPair<IPixel[][], Integer> read(String filepath) throws FileNotFoundException {
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(filepath));
    } catch (IOException e) {
      throw new FileNotFoundException("File not found");
    }
    IPixel[][] image = new Pixel[img.getHeight()][img.getWidth()];
    for (int row = 0; row < img.getHeight(); row++) {
      for (int col = 0; col < img.getWidth(); col++) {
        Color color = new Color(img.getRGB(col, row));
        int blue = color.getBlue();
        int green = color.getGreen();
        int red = color.getRed();
        image[row][col] = new Pixel(red, green, blue, 255);
      }
    }
    return new ContentPair<>(image, 255);
  }
}
