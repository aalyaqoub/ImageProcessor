import org.junit.Test;

import java.io.FileNotFoundException;

import controller.commands.reader.ContentPair;
import controller.commands.reader.ReadPPM;
import model.pixel.IPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for {@link ReadPPM}s.
 */
public class ReadPPMTest {
  IPixel[][] imagePixels;

  @Test(expected = FileNotFoundException.class)
  public void readInvalid() throws FileNotFoundException {
    try {
      new ReadPPM().read("res/testImage/Test.ppm");
    } catch (FileNotFoundException e) {
      new ReadPPM().read("res/testImage/Test.ppm");
      fail("File images/testImage/Test.ppm not found!");
    }
  }

  @Test
  public void read() throws FileNotFoundException {
    initImage();
    ContentPair<IPixel[][], Integer> image = new ReadPPM()
            .read("res/example/PPM/OGImage.ppm");
    for (int row = 0; row < image.getFirst().length; row++) {
      for (int col = 0; col < image.getFirst()[0].length; col++) {
        assertEquals(image.getFirst()[row][col], this.imagePixels[row][col]);
      }
    }
    assertEquals(255, image.getSecond().intValue());
  }

  private void initImage() {
    try {
      this.imagePixels = new ReadPPM().read("res/example/PPM/OGImage.ppm").getFirst();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
}