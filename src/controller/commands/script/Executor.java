package controller.commands.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import controller.IImageController;
import controller.ImageControllerImpl;
import model.image.IImageMutable;
import view.IImageView;

/**
 * Class to execute scripts.
 */
public class Executor {
  /**
   * Runs the script and shows its output.
   *
   * @param filePath the path of the script
   * @param view     the view to show output of running script
   * @param model    the model to run the script on.
   */
  public void executeScript(String filePath, IImageView view, IImageMutable model) {
    File script = new File(filePath);
    StringBuilder scriptCmd = new StringBuilder();
    if (script.exists()) {
      try {
        Scanner scanner = new Scanner(script);
        while (scanner.hasNext()) {
          scriptCmd.append(scanner.nextLine()).append("\n");
        }
      } catch (FileNotFoundException e) {
        try {
          view.renderMessage("File not found");
        } catch (IOException ioException) {
          System.out.println("Can't render Message");
        }
      }
      Readable in = new StringReader(scriptCmd.toString());
      IImageController controller = new ImageControllerImpl(model, view, in);
      controller.runImageEditor();
      try {
        view.renderMessage("\nScript executed");
      } catch (IOException e) {
        System.out.println("Can't render Message");
      }
      System.exit(0);
    } else {
      try {
        view.renderMessage("Couldn't load script\n");
      } catch (IOException ioException) {
        System.out.println("Can't render Message");
      }
    }
  }
}
