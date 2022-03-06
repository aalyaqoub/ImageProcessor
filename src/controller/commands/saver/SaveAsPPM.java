package controller.commands.saver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.image.IImage;
import model.pixel.IPixel;

/**
 * Implementation of ISaver for PPM files. This class will be able to save IImages as ppm files.
 */
public class SaveAsPPM implements ISaver {
  @Override
  public void save(String filepath, IImage image) {
    try {
      File file = new File(filepath);
      FileWriter imageFile = new FileWriter(filepath);
      imageFile.write("P3\n");
      imageFile.write(image.getWidth() + " " + image.getHeight() + "\n");
      imageFile.write(image.getMaxValue() + "\n");

      for (int i = 0; i < image.getHeight(); i++) {
        for (int j = 0; j < image.getWidth(); j++) {
          IPixel currentPixel = image.getPixel(i, j);

          imageFile.write(currentPixel.getRed() + "\n");
          imageFile.write(currentPixel.getGreen() + "\n");
          imageFile.write(currentPixel.getBlue() + "\n");
        }
      }
      imageFile.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
