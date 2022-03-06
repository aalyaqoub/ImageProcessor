import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.StringReader;

import controller.IImageController;
import controller.ImageControllerImpl;
import controller.commands.reader.IReader;
import controller.commands.reader.ReadJPEG;
import model.image.IImageMutable;
import model.image.ImageImpl;
import view.IImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link ImageControllerImpl}s JPEG implementation.
 */
public class JPEGControllerTest {
  AbstractControllerTest test = new AbstractControllerTest("jpeg");

  @Test
  public void testInvalidSeqCmd() {
    assertTrue(this.test.testInvalidSeqCmd());
  }

  @Test
  public void testInvalidSeqCmd1() {
    assertTrue(this.test.testInvalidSeqCmd1());
  }

  @Test
  public void testInvalidCmd1() {
    assertTrue(this.test.testInvalidCmd1());
  }

  @Test
  public void testInvalidImage() {
    assertTrue(this.test.testInvalidImage());
  }

  @Test
  public void testInvalidImage1() {
    assertTrue(this.test.testInvalidImage1());
  }

  @Test
  public void overLoad() {
    Readable in = new StringReader("load res/example/JPEG/OGImage.jpeg og darken 100 og " +
            "og-bright both-flip og-bright moddedImage save " +
            "res/testImages/output/moddedImage.jpeg moddedImage " +
            "load res/testImages/output/moddedImage.jpeg og " +
            "save res/testImages/output/ogOverride.jpeg og");
    IImageMutable model = new ImageImpl();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream stream = new PrintStream(byteArrayOutputStream);
    IImageView view = new ImageViewImpl(model, stream);
    IImageController controller = new ImageControllerImpl(model, view, in);
    controller.runImageEditor();
    String stringStream = "Welcome to the program. Load an Image to get started.\n" +
            "Loaded jpeg file at res/example/JPEG/OGImage.jpeg as og\n" +
            "Applied darken to the og image\n" +
            "Applied flip on both axises to the og-bright image\n" +
            "moddedImage saved to res/testImages/output/moddedImage.jpeg\n" +
            "Loaded jpeg file at res/testImages/output/moddedImage.jpeg as og\n" +
            "og saved to res/testImages/output/ogOverride.jpeg\n" +
            "Goodbye :)";
    assertEquals(stringStream, byteArrayOutputStream.toString());
    IReader reader = new ReadJPEG();
    File ogOveride = new File("res/testImages/output/ogOverride.jpeg");
    File moddedImage = new File("res/testImages/output/moddedImage.jpeg");
    assertTrue(ogOveride.exists() && moddedImage.exists());
  }

  @Test
  public void testEnd() {
    assertTrue(this.test.testEnd());
  }

  @Test
  public void testEnd2() {
    assertTrue(this.test.testEnd2());
  }

  @Test
  public void testEnd3() {
    assertTrue(this.test.testEnd3());
  }

  @Test
  public void testEnd4() {
    assertTrue(this.test.testEnd4());
  }

  @Test
  public void testBadFilePath() {
    assertTrue(this.test.testBadFilePath());
  }

  @Test
  public void testBadFileType() {
    assertTrue(this.test.testBadFileType());
  }

  @Test
  public void testBadFileType2() {
    assertTrue(this.test.testBadFileType2());
  }

  @Test
  public void testBadReferenceName() {
    assertTrue(this.test.testBadReferenceName());
  }

  @Test
  public void testBadReferenceName2() {
    assertTrue(this.test.testBadReferenceName2());
  }

  @Test
  public void testBadReferenceName3() {
    assertTrue(this.test.testBadReferenceName3());
  }

  @Test
  public void testGoodReferenceName() {
    assertTrue(this.test.testGoodReferenceName());
  }

  @Test
  public void testSaving() {
    assertTrue(this.test.testSaving());
  }

  @Test
  public void testSaving2() {
    assertTrue(this.test.testSaving2());
  }

  @Test
  public void testSaving3() {
    assertTrue(this.test.testSaving3());
  }

  @Test
  public void testSaving4() {
    assertTrue(this.test.testSaving4());
  }

  @Test
  public void testSaving5() {
    assertTrue(this.test.testSaving5());
  }

  @Test
  public void testSaving6() {
    assertTrue(this.test.testSaving6());
  }

  @Test
  public void testSaving7() {
    assertTrue(this.test.testSaving7());
  }

  @Test
  public void testSaving8() {
    assertTrue(this.test.testSaving8());
  }

  @Test
  public void testSaving9() {
    assertTrue(this.test.testSaving9());
  }

  @Test
  public void testSaving10() {
    assertTrue(this.test.testSaving10());
  }

  @Test
  public void testSaving11() {
    assertTrue(this.test.testSaving11());
  }

  @Test
  public void testSaving12() {
    assertTrue(this.test.testSaving12());
  }

  @Test
  public void testSaveAsPPM() {
    assertTrue(this.test.testSaveAs("ppm"));
  }

  @Test
  public void testSaveAsPNG() {
    assertTrue(this.test.testSaveAs("png"));
  }

  @Test
  public void testSaveAsBPM() {
    assertTrue(this.test.testSaveAs("bmp"));
  }
}
