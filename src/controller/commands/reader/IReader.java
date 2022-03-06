package controller.commands.reader;

import java.io.FileNotFoundException;

import model.pixel.IPixel;


/**
 * This interface represents operations that should be offered by an Readers to open and read Image
 * files.
 */
public interface IReader {
  /**
   * Read in an Image file and express it as an array of pixels.
   *
   * @param filepath the filepath of the file to be read
   * @return the image expressed as an array of pixels
   * @throws FileNotFoundException if the image does not exist at the given file path
   */
  ContentPair<IPixel[][], Integer> read(String filepath) throws FileNotFoundException;
}
