package controller.commands.saver;

/**
 * Implementation of ISaver for JPEG files. This class will be able to save IImages as bmp files.
 */
public class SaveAsBMP extends AbstractSaver {
  @Override
  protected String fileType() {
    return "bmp";
  }
}
