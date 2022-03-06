package controller.commands.saver;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.image.IImage;
import model.pixel.IPixel;

/**
 * Abstract implementation of ISaver for varying files types. This class will be able to save
 * IImages as varying files types.
 */
public abstract class AbstractSaver implements ISaver {
  @Override
  public void save(String filepath, IImage image) {
    File outPutFile = new File(filepath);
    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_RGB);
    Color color;
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        IPixel pixel = image.getPixel(row, col);
        color = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        bufferedImage.setRGB(col, row, color.getRGB());
      }
    }
    try {
      ImageIO.write(bufferedImage, fileType(), outPutFile);
    } catch (IOException e) {
      System.out.println("Couldn't save image");
    }
  }

  protected abstract String fileType();
}
