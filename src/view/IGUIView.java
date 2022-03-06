package view;

import model.image.IImage;

/**
 * This interface represents operations that should be offered by a GUI view for IImage.
 */
public interface IGUIView extends IImageView, ViewEmitter {
  /**
   * This method shows the window produced by the view.
   *
   * @param show boolean value of whether to show or hide view window.
   */
  void showView(boolean show);


  public void updateView(IImage image);
}
