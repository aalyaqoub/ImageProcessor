package view;

import java.io.IOException;

import model.image.IImage;

/**
 * Represents an implementation of IImageView that allows for IImage related commands to to be
 * viewed in a text format.
 */
public class ImageViewImpl implements IImageView {
  protected final IImage image;
  protected final Appendable out;

  /**
   * Constructs an ImageViewImpl with the given IImage and appendable.
   *
   * @param image the given IImage
   * @param out   the given Appendable
   */
  public ImageViewImpl(IImage image, Appendable out) {
    this.image = image;
    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IOException("Appendable failed");
    }
  }

  @Override
  public void renderErrorMessage(String message) throws IOException {
    this.renderMessage(message);
  }
}
