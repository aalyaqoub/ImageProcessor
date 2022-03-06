import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import controller.commands.IProcess;
import controller.commands.reader.ReadPPM;
import controller.commands.resize.Downsizing;
import model.image.IImageMutable;
import model.image.ImageImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link Downsizing}s.
 */
public class DownsizingTest {
  IImageMutable image;

  @Before
  public void initImage() {
    try {
      this.image = new ImageImpl(new ReadPPM().read("res/example/PPM/OGImage.ppm"));
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  @Test
  public void testToString() {
    assertEquals("downsizing", new Downsizing(100, 100).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadWidth() {
    IProcess downsize = new Downsizing(0, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadHeight() {
    IProcess downsize = new Downsizing(100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeWidth() {
    IProcess downsize = new Downsizing(-1, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    IProcess downsize = new Downsizing(100, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    IProcess downsize = new Downsizing(1, 1);
    downsize.process(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigWidth() {
    IProcess downsize = new Downsizing(100, 1);
    downsize.process(this.image);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigHeight() {
    IProcess downsize = new Downsizing(1, 100);
    downsize.process(this.image);
  }

  @Test
  public void testNewDimensions() {
    IProcess downsize = new Downsizing(1, 1);
    IImageMutable small = downsize.process(this.image);
    assertEquals(small.getHeight(), 1);
    assertEquals(small.getWidth(), 1);
  }

  @Test
  public void testNewDimensions2() {
    IProcess downsize = new Downsizing(2, 2);
    IImageMutable small = downsize.process(this.image);
    assertEquals(small.getHeight(), 2);
    assertEquals(small.getWidth(), 2);
  }

}
