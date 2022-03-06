import java.io.InputStreamReader;

import controller.IImageController;
import controller.ImageControllerImpl;
import controller.ImageGUIController;
import controller.commands.script.Executor;
import model.image.IImageMutable;
import model.image.ImageImpl;
import view.GUIView;
import view.IGUIView;
import view.IImageView;
import view.ImageViewImpl;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {
  /**
   * Main method that allows the user to use the image editor program. There are 3 ways of running
   * the program. The program can be run with script by including the program tag -file followed by
   * script path. The program can also be run with interactive text by including the tag -text,
   * lastly the program can be run with GUI interactivity by including no tags.
   *
   * @param args string arguments inputted in the program arguments.
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      if (args[0].equals("-file")) {
        Appendable ap = System.out;
        IImageMutable model = new ImageImpl();
        IImageView view = new ImageViewImpl(model, ap);
        new Executor().executeScript(args[1], view, model);
      } else if (args[0].equals("-text")) {
        Readable in = new InputStreamReader(System.in);
        Appendable ap = System.out;
        IImageMutable model = new ImageImpl();
        IImageView view = new ImageViewImpl(model, ap);
        IImageController controller = new ImageControllerImpl(model, view, in);
        controller.runImageEditor();
      }
    } else {
      IGUIView view = new GUIView();
      IImageController gui = new ImageGUIController(view);
      gui.runImageEditor();
    }
  }
}

