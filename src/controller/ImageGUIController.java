package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.IProcess;
import controller.commands.reader.IReader;
import controller.commands.saver.ISaver;
import model.image.ImageImpl;
import view.IGUIView;
import view.ViewListener;

/**
 * An implementation of IImageController that allows for control and viewership of Image file and
 * and the applying various manipulations to the image. This controller implementations subscribes a
 * GUIView which emits commands when a user interacts with the gui. The interactivity of this
 * controller is GUI based.
 */
public class ImageGUIController extends AbstractController implements IImageController,
        ViewListener {
  private final IGUIView GUIView;

  /**
   * Constructs an ImageGUIController with the given viewGUI and subscribes to the viewGUI's
   * emissions.
   *
   * @param viewGUI the viewGUI with which this controller can display and receive commands.
   */
  public ImageGUIController(IGUIView viewGUI) {
    super();
    if (viewGUI == null) {
      throw new IllegalArgumentException("Can't have null view");
    }
    this.GUIView = viewGUI;
    this.GUIView.addListener(this);
  }

  @Override
  public void runImageEditor() throws IllegalStateException {
    this.GUIView.showView(true);
  }

  @Override
  public void applyCommand(String path, String... command) {
    if (path == null || command == null || command.length == 0) {
      throw new IllegalArgumentException("Invalid command inputs");
    }
    switch (command[0]) {
      case "Open": {
        this.open(path);
        this.updateView();
//        if (this.model != null) {
//          new Histogram().showHistogram(this.model);
//        }
      }
      break;
      case "Save": {
        this.save(path, true);
      }
      break;
      default: {
        try {
          this.applyProcess(command);
          this.updateView();
//          if (this.model != null) {
//            new Histogram().showHistogram(this.model);
//          }
        } catch (Exception e) {
          this.renderErrorMessage(e.getMessage());
        }
      }
    }


  }

  /**
   * Render error messages.
   *
   * @param message renders an error message.
   */
  private void renderErrorMessage(String message) {
    try {
      this.GUIView.renderErrorMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Couldn't render error message");
    }
  }

  /**
   * Open the file path and updates the model to contain info from file.
   *
   * @param path the path of the file
   */
  private void open(String path) {
    String fileType = path.substring(path.lastIndexOf(".") + 1);
    IReader reader = super.knownReadTypes.getOrDefault(fileType.toLowerCase(), null);

    if (reader == null) {
      this.renderErrorMessage("Could not read " + fileType + " file type\n");
    } else {
      try {
        this.model = new ImageImpl(reader.read(path));
        this.renderMessage(this.GUIView, "Loaded " + fileType + " file at " + path);
      } catch (FileNotFoundException e) {
        this.renderErrorMessage("Could not read " + fileType + " file at " + path + "\n");
      }
    }
  }

  /**
   * Save the IImage model to the given path.
   *
   * @param path        the path to save the image
   * @param showMessage whether to show message or not
   */
  private void save(String path, boolean showMessage) {
    String fileType = path.substring(path.lastIndexOf(".") + 1);
    ISaver saver = this.knownSaveTypes.getOrDefault(fileType.toLowerCase(), null);
    if (saver == null) {
      this.renderErrorMessage("Could not save " + fileType + " file type\n");
    } else {
      try {
        saver.save(path, this.model);
      } catch (NullPointerException e) {
        this.renderErrorMessage("Can't save uninitialized images");
      }

      if (showMessage) {
        this.renderMessage(this.GUIView, "Saved image to " + path);
      }
    }
  }

  /**
   * Applies the given command to this model.
   *
   * @param commands the given commands
   */
  private void applyProcess(String... commands) {
    if (Arrays.asList(super.needIntCmd).contains(commands[0])) {
      StringBuilder commandInputs = new StringBuilder();
      try {
        for (int i = 1; i < commands.length; i++) {
          commandInputs.append(commands[i]).append(" ");
        }
        Scanner scanner = new Scanner(commandInputs.toString());
        Function<Scanner, IProcess> cmd =
                knownCommands.getOrDefault(commands[0], null);

        IProcess command = cmd.apply(scanner);
        this.model = command.process(this.model);
        this.renderMessage(this.GUIView, "Applied " + command.toString() + " to the image\n");
      } catch (NumberFormatException numberFormatException) {
        this.renderErrorMessage("Invalid command inputs: " + commandInputs.toString());
      }
    } else {
      Scanner scanner = new Scanner("");
      Function<Scanner, IProcess> cmd =
              knownCommands.getOrDefault(commands[0], null);
      if (cmd != null) {
        IProcess command = cmd.apply(scanner);
        this.model = command.process(this.model);
        this.renderMessage(this.GUIView, "Applied " + command.toString() + " to the image");
      } else {
        this.renderErrorMessage("Invalid command: " + commands[0]);
      }

    }
  }

  private void updateView() {
    try {
      this.GUIView.updateView(this.model);
    } catch (IllegalArgumentException e) {
      this.renderErrorMessage(e.getMessage());
    }
  }
}
