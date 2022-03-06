package controller.commands;

import model.image.IImageMutable;

/**
 * This interface represents operations that should be offered by processes that manipulate
 * images in certain ways.
 */
public interface IProcess {
  /**
   * Applies a process on an IImageMutable and gives back the result.
   *
   * @param image the IImageMutable that needs to be modified
   * @return the modified IImageMutable
   */
  IImageMutable process(IImageMutable image);

}
