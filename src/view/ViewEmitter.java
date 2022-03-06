package view;

/**
 * This interface represents operations that should be offered by view emitters. View emitter emit
 * data to their subscribers the ViewListeners.
 */
public interface ViewEmitter {
  /**
   * Adds the listener to this ViewEmitter.
   *
   * @param viewListener the viewListener to keep track of
   */
  void addListener(ViewListener viewListener);
}
