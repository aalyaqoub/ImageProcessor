package controller.commands.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.pixel.IPixel;
import model.pixel.Pixel;

/**
 * Implementation of IReader for PPM files. This class will be able to open and read files.
 */
public class ReadPPM implements IReader {
  @Override
  public ContentPair<IPixel[][], Integer> read(String filepath) throws FileNotFoundException {
    IPixel[][] image;
    Scanner sc;


    try {
      sc = new Scanner(new FileInputStream(filepath));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filepath + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    image = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel currentPixel = new Pixel(r, g, b, maxValue);
        image[i][j] = currentPixel;
      }
    }

    return new ContentPair<>(image, maxValue);
  }
}
