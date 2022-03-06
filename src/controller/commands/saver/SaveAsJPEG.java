package controller.commands.saver;

/**
 * Implementation of ISaver for JPEG files. This class will be able to save IImages as jpeg files.
 */
public class SaveAsJPEG extends AbstractSaver {
  @Override
  protected String fileType() {
    return "jpeg";
  }
}
