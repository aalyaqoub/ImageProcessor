import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import controller.ImageGUIController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the GUI View {@link view.GUIView}s and Controller functionality {@link
 * ImageGUIController}s. Uses a mock to imitate the view's emitter functionality.
 */
public class GUIViewTest {
  private ImageGUIController guiController;
  private MockGUIView gui;
  private ByteArrayOutputStream byteArrayOutputStream;

  /**
   * Tests the need to start the program for anything to show up.
   */
  @Test
  public void renderMessageNoStart() {
    this.initController();
    this.gui.emitCommand("res/example/PPM/blue.ppm", "Open");
    String stringStream = "";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test bad file path.
   */
  @Test
  public void badFile() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("ss", "Open");
    String stringStream = "Could not read ss file type\n" +
            "Can't save uninitialized images\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to flip a image.
   */
  @Test
  public void wrongCommand() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "flip");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Invalid command: flip\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Tests the showing up of messages also test if ppm can be opened and read.
   */
  @Test
  public void renderMessageStart() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/PPM/blue.ppm", "Open");
    String stringStream = "Loaded ppm file at res/example/PPM/blue.ppm\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to open a BMP file.
   */
  @Test
  public void openBMP() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/BMP/OGImage.bmp", "Open");
    String stringStream = "Loaded bmp file at res/example/BMP/OGImage.bmp\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to open a PNG file.
   */
  @Test
  public void openPNG() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/PNG/OGImage.png", "Open");
    String stringStream = "Loaded png file at res/example/PNG/OGImage.png\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to open a jpeg file.
   */
  @Test
  public void openJPEG() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to flip a image.
   */
  @Test
  public void flipVertically() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "vertical-flip");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied vertically flipped to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to flip horizontally a image.
   */
  @Test
  public void flipHorizontally() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "horizontal-flip");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied horizontally flipped to the image" + "\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to flip both axis of an image.
   */
  @Test
  public void bothFlip() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "both-flip");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied flip on both axises to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to sepiaTone a image.
   */
  @Test
  public void sepiaTone() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "sepia-tone");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied sepia tone to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to greyscale-trans a image.
   */
  @Test
  public void greyscaleTrans() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "greyscale-trans");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied greyscale transformation to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to blur an image.
   */
  @Test
  public void blur() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "blur");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied image blur to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to sharpen an image.
   */
  @Test
  public void sharpen() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "sharpen");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied image sharpen to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to get values-component of an image.
   */
  @Test
  public void valuesComponent() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "values-component");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied pixel-values greyscale to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to get luma-component of an image.
   */
  @Test
  public void lumaComponent() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "luma-component");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied pixel-luma greyscale to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to get intensity-component of an image.
   */
  @Test
  public void intensityComponent() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "intensity-component");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied pixel-intensity greyscale to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to get blue-component of an image.
   */
  @Test
  public void blueComponent() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "blue-component");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied blue-component greyscale to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to get green-component of an image.
   */
  @Test
  public void greenComponent() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "green-component");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied green-component greyscale to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to get red-component of an image.
   */
  @Test
  public void redComponent() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "red-component");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied red-component greyscale to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to darken of an image.
   */
  @Test
  public void darken() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "darken", "100");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied darken to the image\n" +
            "\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to darken of an image.
   */
  @Test
  public void brighten() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "brighten", "100");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied brighten to the image\n" +
            "\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Test if its possible to apply all commands.
   */
  @Test
  public void allCommands() {
    this.initController();
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "brighten", "100");
    this.gui.emitCommand("res/appData/temp.png", "darken", "100");
    this.gui.emitCommand("res/appData/temp.png", "red-component");
    this.gui.emitCommand("res/appData/temp.png", "green-component");
    this.gui.emitCommand("res/appData/temp.png", "blue-component");
    this.gui.emitCommand("res/appData/temp.png", "intensity-component");
    this.gui.emitCommand("res/appData/temp.png", "luma-component");
    this.gui.emitCommand("res/appData/temp.png", "values-component");
    this.gui.emitCommand("res/appData/temp.png", "sharpen");
    this.gui.emitCommand("res/appData/temp.png", "blur");
    this.gui.emitCommand("res/appData/temp.png", "greyscale-trans");
    this.gui.emitCommand("res/appData/temp.png", "sepia-tone");
    this.gui.emitCommand("res/appData/temp.png", "both-flip");
    this.gui.emitCommand("res/appData/temp.png", "horizontal-flip");
    this.gui.emitCommand("res/appData/temp.png", "vertical-flip");
    String stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied brighten to the image\n\n" +
            "Applied darken to the image\n\n" +
            "Applied red-component greyscale to the image\n" +
            "Applied green-component greyscale to the image\n" +
            "Applied blue-component greyscale to the image\n" +
            "Applied pixel-intensity greyscale to the image\n" +
            "Applied pixel-luma greyscale to the image\n" +
            "Applied pixel-values greyscale to the image\n" +
            "Applied image sharpen to the image\n" +
            "Applied image blur to the image\n" +
            "Applied greyscale transformation to the image\n" +
            "Applied sepia tone to the image\n" +
            "Applied flip on both axises to the image\n" +
            "Applied horizontally flipped to the image\n" +
            "Applied vertically flipped to the image\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
  }

  /**
   * Tests if temp image file is saved in appData.
   */
  @Test
  public void testImageTempSave() {
    this.initController();
    File tempFile = new File("res/appData/temp.png");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/PNG/OGImage.png", "Open");
    assertTrue(tempFile.exists());
  }

  /**
   * Tests if temp histogram file is saved in appData.
   */
  @Test
  public void testHistogramTempSave() {
    this.initController();
    File tempFile = new File("res/appData/histogram.png");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/PNG/OGImage.png", "Open");
    assertTrue(tempFile.exists());
  }

  /**
   * Tests Save.
   */
  @Test
  public void testSavePNG() {
    this.initController();
    File tempFile = new File("res/testImages/output/save1.png");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/PNG/OGImage.png", "Open");
    this.gui.emitCommand("res/testImages/output/save1.png", "Save");
    assertTrue(tempFile.exists());
  }

  /**
   * Tests Save.
   */
  @Test
  public void testSavePPM() {
    this.initController();
    File tempFile = new File("res/testImages/output/save1.ppm");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/PPM/OGImage.ppm", "Open");
    this.gui.emitCommand("res/testImages/output/save1.ppm", "Save");
    assertTrue(tempFile.exists());
  }

  /**
   * Tests Save.
   */
  @Test
  public void testSaveJPEG() {
    this.initController();
    File tempFile = new File("res/testImages/output/save1.jpeg");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/testImages/output/save1.jpeg", "Save");
    assertTrue(tempFile.exists());
  }

  /**
   * Tests Save.
   */
  @Test
  public void testSaveBMP() {
    this.initController();
    File tempFile = new File("res/testImages/output/save1.bmp");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    assertFalse(tempFile.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/BMP/OGImage.bmp", "Open");
    this.gui.emitCommand("res/testImages/output/save1.bmp", "Save");
    assertTrue(tempFile.exists());
  }

  /**
   * Tests Save As.
   */
  @Test
  public void testSaveASBMP() {
    this.initController();
    File tempFile = new File("res/testImages/output/save1.png");
    File tempFile1 = new File("res/testImages/output/save1.jpeg");
    File tempFile2 = new File("res/testImages/output/save1.ppm");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    if (tempFile1.exists()) {
      tempFile1.delete();
    }
    if (tempFile2.exists()) {
      tempFile2.delete();
    }

    assertFalse(tempFile.exists() || tempFile1.exists() || tempFile2.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/BMP/OGImage.bmp", "Open");
    this.gui.emitCommand("res/testImages/output/save1.png", "Save");
    this.gui.emitCommand("res/testImages/output/save1.jpeg", "Save");
    this.gui.emitCommand("res/testImages/output/save1.ppm", "Save");
    assertTrue(tempFile.exists() || tempFile1.exists() || tempFile2.exists());
  }

  /**
   * Tests Save As.
   */
  @Test
  public void testSaveASPNG() {
    this.initController();
    File tempFile = new File("res/testImages/output/save1.bmp");
    File tempFile1 = new File("res/testImages/output/save1.jpeg");
    File tempFile2 = new File("res/testImages/output/save1.ppm");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    if (tempFile1.exists()) {
      tempFile1.delete();
    }
    if (tempFile2.exists()) {
      tempFile2.delete();
    }

    assertFalse(tempFile.exists() || tempFile1.exists() || tempFile2.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/PNG/OGImage.png", "Open");
    this.gui.emitCommand("res/testImages/output/save1.bmp", "Save");
    this.gui.emitCommand("res/testImages/output/save1.jpeg", "Save");
    this.gui.emitCommand("res/testImages/output/save1.ppm", "Save");
    assertTrue(tempFile.exists() || tempFile1.exists() || tempFile2.exists());
  }

  /**
   * Tests Save As.
   */
  @Test
  public void testSaveASPPM() {
    this.initController();
    File tempFile = new File("res/testImages/output/save1.bmp");
    File tempFile1 = new File("res/testImages/output/save1.jpeg");
    File tempFile2 = new File("res/testImages/output/save1.png");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    if (tempFile1.exists()) {
      tempFile1.delete();
    }
    if (tempFile2.exists()) {
      tempFile2.delete();
    }

    assertFalse(tempFile.exists() || tempFile1.exists() || tempFile2.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/PPM/OGImage.ppm", "Open");
    this.gui.emitCommand("res/testImages/output/save1.bmp", "Save");
    this.gui.emitCommand("res/testImages/output/save1.jpeg", "Save");
    this.gui.emitCommand("res/testImages/output/save1.png", "Save");
    assertTrue(tempFile.exists() || tempFile1.exists() || tempFile2.exists());
  }

  /**
   * Tests Save As.
   */
  @Test
  public void testSaveASJPEG() {
    this.initController();
    File tempFile = new File("res/testImages/output/save1.bmp");
    File tempFile1 = new File("res/testImages/output/save1.png");
    File tempFile2 = new File("res/testImages/output/save1.ppm");
    if (tempFile.exists()) {
      tempFile.delete();
    }
    if (tempFile1.exists()) {
      tempFile1.delete();
    }
    if (tempFile2.exists()) {
      tempFile2.delete();
    }

    assertFalse(tempFile.exists() || tempFile1.exists() || tempFile2.exists());
    this.guiController.runImageEditor();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/testImages/output/save1.bmp", "Save");
    this.gui.emitCommand("res/testImages/output/save1.png", "Save");
    this.gui.emitCommand("res/testImages/output/save1.ppm", "Save");
    assertTrue(tempFile.exists() || tempFile1.exists() || tempFile2.exists());
  }

  /**
   * Tests showing error messages.
   */
  @Test
  public void renderErrorMessage() {
    this.initController();
    try {
      this.guiController.runImageEditor();
      this.gui.renderErrorMessage("Testing the error output");
      String stringStream = "Testing the error output\n";
      assertEquals(stringStream, this.byteArrayOutputStream.toString());
    } catch (IOException e) {
      throw new IllegalArgumentException("Can't render error");
    }
  }

  /**
   * Tests if the view doesn't show anything if not enabled.
   */
  @Test
  public void showView() {
    this.initController();
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "darken", "100");
    String stringStream = "";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());
    this.gui.showView(true);
    this.gui.emitCommand("res/example/JPEG/OGImage.jpeg", "Open");
    this.gui.emitCommand("res/appData/temp.png", "darken", "100");
    stringStream = "Loaded jpeg file at res/example/JPEG/OGImage.jpeg\n" +
            "Applied darken to the image\n" +
            "\n";
    assertEquals(stringStream, this.byteArrayOutputStream.toString());

  }

  /**
   * Tests adding listeners to the view that'll receive the view's emits.
   */
  @Test
  public void addListener() {
    this.gui = new MockGUIView(System.out);
    assertEquals(0, this.gui.getListenersCount());
    this.initController();
    assertEquals(1, this.gui.getListenersCount());
    new ImageGUIController(this.gui);
    assertEquals(2, this.gui.getListenersCount());
  }

  /**
   * Initializes this tests fields.
   */
  private void initController() {
    this.byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    this.gui = new MockGUIView(stream);
    this.guiController = new ImageGUIController(this.gui);
  }
}