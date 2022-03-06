package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.IProcess;
import controller.commands.brightness.Brighten;
import controller.commands.brightness.Darken;
import controller.commands.color.BlueComponent;
import controller.commands.color.GreenComponent;
import controller.commands.color.PixelIntensity;
import controller.commands.color.PixelLuma;
import controller.commands.color.PixelValues;
import controller.commands.color.RedComponent;
import controller.commands.colortransformation.GreyscaleTrans;
import controller.commands.colortransformation.SepiaToneTrans;
import controller.commands.filter.ImageBlur;
import controller.commands.filter.ImageSharpen;
import controller.commands.flipping.FlipBothAxis;
import controller.commands.flipping.FlipHorizontal;
import controller.commands.flipping.FlipVertical;
import controller.commands.mosaic.Mosaic;
import controller.commands.reader.IReader;
import controller.commands.reader.ReadBMP;
import controller.commands.reader.ReadJPEG;
import controller.commands.reader.ReadPNG;
import controller.commands.reader.ReadPPM;
import controller.commands.resize.Downsizing;
import controller.commands.saver.ISaver;
import controller.commands.saver.SaveAsBMP;
import controller.commands.saver.SaveAsJPEG;
import controller.commands.saver.SaveAsPNG;
import controller.commands.saver.SaveAsPPM;
import model.image.IImageMutable;
import view.IImageView;

/**
 * Abstract implementation of controller that allows for the control and modification of the model.
 */
public abstract class AbstractController implements IImageController {
  protected String[] needIntCmd;
  protected Map<String, Function<Scanner, IProcess>> knownCommands;
  protected Map<String, IReader> knownReadTypes;
  protected Map<String, ISaver> knownSaveTypes;
  protected IImageMutable model;

  /**
   * Constructor that initializes the fields.
   */
  public AbstractController() {
    this.initCommands();
    this.initReadTypes();
    this.initSaveTypes();
  }

  /**
   * Initializes the known commands.
   */
  protected void initCommands() {
    this.knownCommands = new HashMap<>();
    this.knownCommands.put("brighten", s -> new Brighten(s.nextInt()));
    this.knownCommands.put("darken", s -> new Darken(Math.abs(s.nextInt())));
    this.knownCommands.put("red-component", s -> new RedComponent());
    this.knownCommands.put("green-component", s -> new GreenComponent());
    this.knownCommands.put("blue-component", s -> new BlueComponent());
    this.knownCommands.put("intensity-component", s -> new PixelIntensity());
    this.knownCommands.put("luma-component", s -> new PixelLuma());
    this.knownCommands.put("values-component", s -> new PixelValues());
    this.knownCommands.put("vertical-flip", s -> new FlipVertical());
    this.knownCommands.put("horizontal-flip", s -> new FlipHorizontal());
    this.knownCommands.put("sepia-tone", s -> new SepiaToneTrans());
    this.knownCommands.put("greyscale-trans", s -> new GreyscaleTrans());
    this.knownCommands.put("blur", s -> new ImageBlur());
    this.knownCommands.put("sharpen", s -> new ImageSharpen());
    this.knownCommands.put("both-flip", s -> new FlipBothAxis());
    this.knownCommands.put("mosaic", s -> new Mosaic(s.nextInt()));
    this.knownCommands.put("downsizing", s -> {
      int width = s.nextInt();
      if (s.hasNextInt()) {
        return new Downsizing(width, s.nextInt());
      } else {
        throw new IllegalArgumentException("Invalid sequence of commands.\n");
      }
    });
    this.needIntCmd = new String[]{"brighten", "darken", "mosaic", "downsizing"};
  }


  /**
   * Initializes the known read types.
   */
  protected void initReadTypes() {
    this.knownReadTypes = new HashMap<>();
    this.knownReadTypes.put("ppm", new ReadPPM());
    this.knownReadTypes.put("png", new ReadPNG());
    this.knownReadTypes.put("jpeg", new ReadJPEG());
    this.knownReadTypes.put("bmp", new ReadBMP());
  }

  /**
   * Initializes the known save types.
   */
  protected void initSaveTypes() {
    this.knownSaveTypes = new HashMap<>();
    this.knownSaveTypes.put("ppm", new SaveAsPPM());
    this.knownSaveTypes.put("png", new SaveAsPNG());
    this.knownSaveTypes.put("jpeg", new SaveAsJPEG());
    this.knownSaveTypes.put("bmp", new SaveAsBMP());
  }

  /**
   * Takes in a messages and renders it.
   *
   * @param message the String of the message.
   */
  protected void renderMessage(IImageView view, String message) {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Couldn't render the message");
    }
  }
}
