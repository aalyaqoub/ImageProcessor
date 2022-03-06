import org.junit.Test;

import controller.ImageControllerImpl;

import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link ImageControllerImpl}s BMP implementation.
 */
public class BMPControllerTest {
  AbstractControllerTest test = new AbstractControllerTest("bmp");

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
    assertTrue(this.test.overLoad());
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
  public void testSaveAsJPEG() {
    assertTrue(this.test.testSaveAs("jpeg"));
  }

  @Test
  public void testSaveAsPNG() {
    assertTrue(this.test.testSaveAs("png"));
  }
}
