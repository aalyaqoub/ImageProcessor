import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import controller.commands.reader.ReadPPM;
import controller.commands.saver.SaveAsPPM;
import model.image.IImageMutable;
import model.image.ImageImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link SaveAsPPM}s.
 */
public class SaveAsPPMTest {
  @Test
  public void save() {
    try {
      IImageMutable model = new ImageImpl(new ReadPPM().read("res/example/PPM/OGImage.ppm"));
      File tempFile = new File("res/testImages/output/saving.ppm");
      if (tempFile.exists()) {
        tempFile.delete();
      }

      assertFalse(tempFile.exists());
      new SaveAsPPM().save("res/testImages/output/saving.ppm", model);
      assertTrue(tempFile.exists());
      IImageMutable savedModel = new ImageImpl(new ReadPPM()
              .read("res/example/PPM/OGImage.ppm"));
      for (int row = 0; row < model.getHeight(); row++) {
        for (int col = 0; col < model.getHeight(); col++) {
          assertEquals(model.getPixel(row, col), savedModel.getPixel(row, col));
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find the file at the given path.");
    }
  }
}