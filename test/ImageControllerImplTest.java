import org.junit.Test;

import java.io.StringReader;

import controller.IImageController;
import controller.ImageControllerImpl;
import model.image.IImageMutable;
import model.image.ImageImpl;
import view.IImageView;
import view.ImageViewImpl;

import static org.junit.Assert.fail;

/**
 * General tests for {@link ImageControllerImpl}s.
 */
public class ImageControllerImplTest {
  /**
   * Test if it is impossible to create a IImageController with null model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModelControllerImpl() {
    Readable in = new StringReader("load images/example/OGImage.ppm og");
    Appendable ap = System.out;
    IImageMutable model = null;
    IImageView view = new ImageViewImpl(model, ap);
    try {
      IImageController controller = new ImageControllerImpl(model, view, in);
    } catch (IllegalArgumentException e) {
      IImageController controller = new ImageControllerImpl(model, view, in);
      fail("Can't have null inputs");
    }
  }

  /**
   * Test if it is impossible to create a IImageController with null view.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullViewControllerImpl() {
    Readable in = new StringReader("load images/example/OGImage.ppm og");
    Appendable ap = System.out;
    IImageMutable model = new ImageImpl();
    IImageView view = null;
    try {
      IImageController controller = new ImageControllerImpl(model, view, in);
    } catch (IllegalArgumentException e) {
      IImageController controller = new ImageControllerImpl(model, view, in);
      fail("Can't have null inputs");
    }
  }
}