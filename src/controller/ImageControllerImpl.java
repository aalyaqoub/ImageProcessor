package controller;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.IProcess;
import controller.commands.PartialProcess;
import controller.commands.reader.IReader;
import controller.commands.saver.ISaver;
import controller.commands.script.Executor;
import model.image.IImageMutable;
import model.image.ImageImpl;
import view.IImageView;

/**
 * An implementation of IImageController that allows for control and viewership of Image file and
 * IImage by using Readables and IImageView. The interactivity of this controller is text based.
 */
public class ImageControllerImpl extends AbstractController implements IImageController {
  private IImageMutable model;
  private final IImageView view;
  private final Readable in;
  private final Map<String, IImageMutable> loadedImages = new HashMap<>();

  /**
   * Constructs an ImageControllerImpl with the given model, view, reader, saver, and readable.
   *
   * @param model an IImageMutable for which to control
   * @param view  an IImageView that's capable of visualize of the how the commands are processed
   * @param in    the Readable that holds the controller command inputs
   * @throws IllegalArgumentException if given null inputs
   */
  public ImageControllerImpl(IImageMutable model, IImageView view,
                             Readable in) throws IllegalArgumentException {
    super();
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("Can't have null inputs");
    }
    this.model = model;
    this.view = view;
    this.in = in;
  }

  @Override
  public void runImageEditor() throws IllegalStateException {
    Scanner scanner = new Scanner(this.in);
    this.renderMessage(this.view, "Welcome to the program. Load an Image to get started.\n");
    while (scanner.hasNext()) {
      IProcess command = null;
      String in = scanner.next();

      switch (in) {
        case "load": { // test for not being able to open file in controller
          String filepath = scanner.next();
          String fileType = filepath.substring(filepath.lastIndexOf(".") + 1);
          IReader reader = this.knownReadTypes.getOrDefault(fileType.toLowerCase(), null);
          if (reader == null) {
            this.renderErrorMessage("Could not read " + fileType + " file type\n");
          } else {
            try {
              this.model = new ImageImpl(reader.read(filepath));
              String name = scanner.next();
              this.renderMessage(this.view, "Loaded " + fileType + " file at "
                      + filepath + " as " + name + "\n");
              this.loadedImages.put(name, this.model.getClone());
            } catch (FileNotFoundException e) {
              this.renderErrorMessage("Could not read " + fileType + " file at " + filepath + "\n");
            }
          }
          break;
        }
        case "save": {
          String filepath = scanner.next();
          String fileType = filepath.substring(filepath.lastIndexOf(".") + 1);
          ISaver saver = this.knownSaveTypes.getOrDefault(fileType.toLowerCase(), null);
          String imageName = scanner.next();
          if (saver == null) {
            this.renderErrorMessage("Could not save " + fileType + " file type\n");
          } else {
            IImageMutable wantedImage = loadedImages.getOrDefault(imageName, null);
            if (wantedImage == null) {
              this.renderErrorMessage("Unknown image: " + imageName + "\n");
            } else {
              saver.save(filepath, wantedImage);
              this.renderMessage(this.view, imageName + " saved to " + filepath + "\n");
            }
          }
          break;
        }
        case "file":
          new Executor().executeScript(scanner.next(), this.view, this.model);
          break;
        case "partial":
          String imageMask = scanner.next();
          IImageMutable wantedMask = loadedImages.getOrDefault(imageMask, null);
          if (wantedMask == null) {
            this.renderErrorMessage("Unknown image: " + imageMask + "\n");
          } else {
            in = scanner.next();
            if (Arrays.asList(this.needIntCmd).contains(in) && !scanner.hasNextInt()) {
              this.renderErrorMessage("Invalid sequence of commands.\n");
              scanner.next();
            } else {
              Function<Scanner, IProcess> cmd =
                      knownCommands.getOrDefault(in, null);

              if (cmd == null) {
                this.renderErrorMessage("Unknown command: " + in + "\n");
              } else {
                try {
                  command = cmd.apply(scanner);
                } catch (Exception e) {
                  this.renderErrorMessage("Couldn't execute command");
                }
                String imageName = scanner.next();
                String newImageName = scanner.next();
                IImageMutable wantedImage = loadedImages.getOrDefault(imageName, null);
                if (wantedImage == null) {
                  this.renderErrorMessage("Unknown image: " + imageName + "\n");
                } else {
                  if (command != null) {
                    this.model = new PartialProcess(wantedMask.getClone(),
                            command).process(wantedImage.getClone());
                    this.loadedImages.put(newImageName, this.model);
                    this.renderMessage(this.view, "Applied partial " +
                            command.toString() + " to the " + imageName
                            + " image using mask " + imageMask + "\n");
                  }
                }
              }
            }
          }
          break;
        default:
          if (Arrays.asList(this.needIntCmd).contains(in) && !scanner.hasNextInt()) {
            this.renderErrorMessage("Invalid sequence of commands.\n");
            scanner.next();
          } else {
            Function<Scanner, IProcess> cmd =
                    knownCommands.getOrDefault(in, null);

            if (cmd == null) {
              this.renderErrorMessage("Unknown command: " + in + "\n");
            } else {
              try {
                command = cmd.apply(scanner);
              } catch (Exception e) {
                this.renderErrorMessage("Couldn't execute command");
              }
              String imageName = scanner.next();
              String newImageName = scanner.next();
              IImageMutable wantedImage = loadedImages.getOrDefault(imageName, null);
              if (wantedImage == null) {
                this.renderErrorMessage("Unknown image: " + imageName + "\n");
              } else {
                if (command != null) {
                  this.model = command.process(wantedImage.getClone());
                  this.loadedImages.put(newImageName, this.model);
                  this.renderMessage(this.view, "Applied " + command.toString() +
                          " to the " + imageName + " image\n");
                }
              }
            }
          }
          break;
      }
    }
    this.renderMessage(this.view, "Goodbye :)");
  }

  /**
   * Render error messages.
   *
   * @param message renders an error message.
   */
  protected void renderErrorMessage(String message) {
    this.renderMessage(this.view, message);
  }
}
