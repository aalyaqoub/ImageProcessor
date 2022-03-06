import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import controller.IImageController;
import controller.ImageControllerImpl;
import controller.commands.reader.IReader;
import controller.commands.reader.ReadBMP;
import controller.commands.reader.ReadJPEG;
import controller.commands.reader.ReadPNG;
import controller.commands.reader.ReadPPM;
import model.image.IImageMutable;
import model.image.ImageImpl;
import model.pixel.IPixel;
import view.IImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Abstract tests for {@link ImageControllerImpl}s.
 */
public class AbstractControllerTest {
  private final String fileExtension;
  private Map<String, IReader> knownReadTypes;

  /**
   * Constructs an abstract test for the given file type.
   *
   * @param fileExtension the file extension of the file
   */
  public AbstractControllerTest(String fileExtension) {
    this.fileExtension = fileExtension;
    this.initReadTypes();
  }

  /**
   * Test if it is invalid sequence of commands.
   */
  public boolean testInvalidSeqCmd() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten aaaa 10 og " +
            "og-brighter\u001a");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() + " file at res/example/" +
            this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Invalid sequence of commands.\n" +
            "Unknown command: 10\n" +
            "Unknown command: og\n" +
            "Unknown command: og-brighter\u001A\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Test if it is invalid sequence of commands.
   */
  public boolean testInvalidSeqCmd1() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og darken aaaa 10 og " +
            "og-brighter\u001a");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() + " file at res/example/" +
            this.fileExtension.toUpperCase() + "/OGImage." + this.fileExtension.toLowerCase() +
            " as og\n" +
            "Invalid sequence of commands.\n" +
            "Unknown command: 10\n" +
            "Unknown command: og\n" +
            "Unknown command: og-brighter\u001A\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Test for invalid commands.
   */
  public boolean testInvalidCmd1() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og bigger 10 og " +
            "og-big");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() + " file at res/example/" +
            this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Unknown command: bigger\n" +
            "Unknown command: 10\n" +
            "Unknown command: og\n" +
            "Unknown command: og-big\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Test for invalid commands.
   */
  public boolean testInvalidImage() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og darken 10 og-random " +
            "og-darken");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + " as og\n" +
            "Unknown image: og-random\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Test for invalid commands.
   */
  public boolean testInvalidImage1() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten 100 og " +
            "og-brighter save res/testImages/output/brighter.ppm jjj");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied brighten to the og image\n" +
            "Unknown image: jjj\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Test overloading reference.
   */
  public boolean overLoad() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og darken 100 og " +
            "og-bright both-flip og-bright moddedImage save " +
            "res/testImages/output/moddedImage." +
            this.fileExtension.toLowerCase() + " moddedImage " +
            "load res/testImages/output/moddedImage." + this.fileExtension.toLowerCase() + " og " +
            "save res/testImages/output/ogOverride." + this.fileExtension.toLowerCase() + " og");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied darken to the og image\n" +
            "Applied flip on both axises to the og-bright image\n" +
            "moddedImage saved to res/testImages/output/moddedImage."
            + this.fileExtension.toLowerCase() + "\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/testImages/output/moddedImage."
            + this.fileExtension.toLowerCase() + " as og\n" +
            "og saved to res/testImages/output/ogOverride."
            + this.fileExtension.toLowerCase() + "\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    IReader reader = this.knownReadTypes.getOrDefault(this.fileExtension.toLowerCase(), null);
    if (reader == null) {
      System.out.println("Could not read " + this.fileExtension.toLowerCase() + " file type\n");
    } else {
      try {
        IPixel[][] ogOverride =
                reader.read("res/testImages/output/ogOverride."
                        + this.fileExtension.toLowerCase()).getFirst();
        IPixel[][] moddedImage =
                reader.read("res/testImages/output/moddedImage."
                        + this.fileExtension.toUpperCase()).getFirst();
        for (int row = 0; row < ogOverride.length; row++) {
          for (int col = 0; col < ogOverride[0].length; col++) {
            assertEquals(ogOverride[row][col], moddedImage[row][col]);
          }
        }
      } catch (FileNotFoundException e) {
        System.out.println("Couldn't read files");
      }
    }
    return true;
  }

  /**
   * Tests to see view of ending program.
   */
  public boolean testEnd() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + " as og\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of ending program with more commands in between.
   */
  public boolean testEnd2() {
    Readable in = new StringReader("load res/example/" +
            this.fileExtension.toUpperCase() + "/OGImage." + this.fileExtension.toLowerCase()
            + " og brighten 100 og og-brighter\u001a");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + " as og\nApplied brighten to the og image\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of ending program with more commands in between.
   */
  public boolean testEnd3() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten 100 og " +
            "og-brighter values-component og og-values");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + " as og\nApplied brighten " +
            "to the og image\nApplied pixel-values greyscale to the og image\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of ending program with more commands in between.
   */
  public boolean testEnd4() {
    Readable in = new StringReader("");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of what happens if file path is wrong.
   */
  public boolean testBadFilePath() {
    Readable in = new StringReader("load res/example1/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten\u001a");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Could not read " + this.fileExtension.toLowerCase() + " file at res/example1/"
            + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + "\nUnknown command: og\n" +
            "Unknown command: brighten\u001A\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of what happens if unsupported file type is given.
   */
  public boolean testBadFileType() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage.jjt og brighten\u001a");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Could not read jjt file type\n" +
            "Unknown command: og\n" +
            "Unknown command: brighten\u001A\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of what happens if unsupported file type is given.
   */
  public boolean testBadFileType2() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage.gif og brighten\u001a");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Could not read gif file type\n" +
            "Unknown command: og\n" +
            "Unknown command: brighten\u001A\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of what happens if reference name is wrong.
   */
  public boolean testBadReferenceName() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten 100 og12 " +
            "og-brighter");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Unknown image: og12\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of what happens if reference name is wrong.
   */
  public boolean testBadReferenceName2() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten 100 og " +
            "og-brighter values-component og11 og-values");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + " as og\nApplied brighten to the og image\n" +
            "Unknown image: og11\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of what happens if reference name is wrong.
   */
  public boolean testBadReferenceName3() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten 100 og " +
            "og-brighter values-component og-bright og-values");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied brighten to the og image\n" +
            "Unknown image: og-bright\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see view of what happens if reference name is good.
   */
  public boolean testGoodReferenceName() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten 100 og " +
            "og-brighter values-component og-brighter og-values");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied brighten to the og image\n" +
            "Applied pixel-values greyscale to the og-brighter image\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten 100 og " +
            "og-brighter save res/testImages/output/brighter." + this.fileExtension.toLowerCase() +
            " og-brighter values-component" +
            " og og-values\u001a");
    File tempFile = new File("res/testImages/output/brighter." +
            this.fileExtension.toUpperCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\nApplied brighten to the " +
            "og image\nog-brighter saved to res/testImages/output/brighter."
            + this.fileExtension.toLowerCase() + "\nApplied pixel-values greyscale to " +
            "the og image\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving2() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og brighten 100 og " +
            "og-brighter save res/testImages/output/brighter." + this.fileExtension.toLowerCase() +
            " og-brighter  save res/testImages/output/og." + this.fileExtension.toLowerCase() +
            " og values-component og og-values\u001a");
    File tempFile = new File("res/testImages/output/brighter." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + " as og\nApplied brighten to the og image\n" +
            "og-brighter saved to res/testImages/output/brighter."
            + this.fileExtension.toLowerCase() + "\n" +
            "og saved to res/testImages/output/og." + this.fileExtension.toLowerCase() + "\n" +
            "Applied pixel-values greyscale to the og image\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving3() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase()
            + "/OGImage." + this.fileExtension.toLowerCase() + " og darken 100 og " +
            "og-darker save res/testImages/output/darken."
            + this.fileExtension.toLowerCase() + " og-darker");
    File tempFile = new File("res/testImages/output/darken."
            + this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied darken to the og image\n" +
            "og-darker saved to res/testImages/output/darken." + this.fileExtension.toLowerCase()
            + "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving4() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og red-component " +
            "og og-red save res/testImages/output/red." + this.fileExtension.toLowerCase()
            + " og-red");
    File tempFile = new File("res/testImages/output/red." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied red-component greyscale to the og image\n" +
            "og-red saved to res/testImages/output/red." + this.fileExtension.toLowerCase() + "\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving5() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og green-component og" +
            " og-green save res/testImages/output/green." + this.fileExtension.toLowerCase()
            + " og-green");
    File tempFile = new File("res/testImages/output/green." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage."
            + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied green-component greyscale to the og image\n" +
            "og-green saved to res/testImages/output/green."
            + this.fileExtension.toLowerCase() + "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving6() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og blue-component og" +
            " og-blue save res/testImages/output/blue." + this.fileExtension.toLowerCase()
            + " og-blue");
    File tempFile = new File("res/testImages/output/blue." +
            this.fileExtension.toUpperCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase()
            + "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied blue-component greyscale to the og image\n" +
            "og-blue saved to res/testImages/output/blue." + this.fileExtension.toLowerCase() +
            "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving7() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og intensity-component og" +
            " og-intensity save res/testImages/output/intensity." +
            this.fileExtension.toLowerCase() + " og-intensity");
    File tempFile = new File("res/testImages/output/intensity." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied pixel-intensity greyscale to the og image\n" +
            "og-intensity saved to res/testImages/output/intensity." +
            this.fileExtension.toLowerCase() + "\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving8() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og luma-component og" +
            " og-luma save res/testImages/output/luma." +
            this.fileExtension.toLowerCase() + " og-luma");
    File tempFile = new File("res/testImages/output/luma." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied pixel-luma greyscale to the og image\n" +
            "og-luma saved to res/testImages/output/luma." + this.fileExtension.toLowerCase() +
            "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving9() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og values-component og" +
            " og-values save res/testImages/output/values." + this.fileExtension.toLowerCase() +
            " og-values");
    File tempFile = new File("res/testImages/output/values." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied pixel-values greyscale to the og image\n" +
            "og-values saved to res/testImages/output/values." + this.fileExtension.toLowerCase() +
            "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving10() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og vertical-flip og" +
            " og-vertical save res/testImages/output/vertical." + this.fileExtension.toLowerCase() +
            " og-vertical");
    File tempFile = new File("res/testImages/output/vertical." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage." +
            this.fileExtension.toLowerCase() + " as og\n" +
            "Applied vertically flipped to the og image\n" +
            "og-vertical saved to res/testImages/output/vertical." +
            this.fileExtension.toLowerCase() + "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving11() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og horizontal-flip og" +
            " og-horizontal save res/testImages/output/horizontal." +
            this.fileExtension.toLowerCase() + " og-horizontal");
    File tempFile = new File("res/testImages/output/horizontal." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied horizontally flipped to the og image\n" +
            "og-horizontal saved to res/testImages/output/horizontal." +
            this.fileExtension.toLowerCase() + "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaving12() {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og both-flip og" +
            " og-both save res/testImages/output/both." + this.fileExtension.toLowerCase() +
            " og-both");
    File tempFile = new File("res/testImages/output/both." +
            this.fileExtension.toLowerCase());
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() + "/OGImage." +
            this.fileExtension.toLowerCase() + " as og\n" +
            "Applied flip on both axises to the og image\n" +
            "og-both saved to res/testImages/output/both." + this.fileExtension.toLowerCase() +
            "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }

  /**
   * Tests to see saving works correctly.
   */
  public boolean testSaveAs(String newFileExtension) {
    Readable in = new StringReader("load res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " og horizontal-flip og" +
            " og-horizontal save res/testImages/output/horizontal." +
            newFileExtension + " og-horizontal");
    File tempFile = new File("res/testImages/output/horizontal." + newFileExtension);
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    assertTrue(tempFile.exists());
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded " + this.fileExtension.toLowerCase() +
            " file at res/example/" + this.fileExtension.toUpperCase() +
            "/OGImage." + this.fileExtension.toLowerCase() + " as og\n" +
            "Applied horizontally flipped to the og image\n" +
            "og-horizontal saved to res/testImages/output/horizontal." +
            newFileExtension + "\nGoodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    return true;
  }


  /**
   * Initializes the known read types.
   */
  private void initReadTypes() {
    this.knownReadTypes = new HashMap<>();
    this.knownReadTypes.put("ppm", new ReadPPM());
    this.knownReadTypes.put("png", new ReadPNG());
    this.knownReadTypes.put("jpeg", new ReadJPEG());
    this.knownReadTypes.put("bmp", new ReadBMP());
  }
}
