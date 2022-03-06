package controller.commands.saver;

import model.image.IImage;

/**
 * This interface represents operations that should be offered by an Savers to save IImages into
 * permanent files.
 */
public interface ISaver {
  /**
   * Saves the IImage to the desired filepath.
   *
   * @param filepath the filepath
   * @param image    the IImage that needs to be saved
   */
  void save(String filepath, IImage image);
}
