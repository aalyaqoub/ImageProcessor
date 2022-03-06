package view;

/**
 * This interface represents operations that should be offered by view listeners to apply commands
 * emitted by the implementations subscribed view.
 */
public interface ViewListener {
  /**
   * Applies a command emitted by the view.
   *
   * @param path    the path of the image
   * @param command the command inputs
   */
  void applyCommand(String path, String... command);
}
