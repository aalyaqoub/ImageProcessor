package controller.commands.saver;

/**
 * Implementation of ISaver for PNG files. This class will be able to save IImages as png files.
 */
public class SaveAsPNG extends AbstractSaver {
  @Override
  protected String fileType() {
    return "png";
  }
}
