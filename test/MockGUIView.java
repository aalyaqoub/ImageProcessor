import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.image.IImage;
import model.image.IImageMutable;
import view.IGUIView;
import view.ViewListener;

/**
 * Mock implementation of GUIView that allows for direct emission of commands without the need to
 * click on a button.
 */
public class MockGUIView implements IGUIView {
  protected final Appendable out;
  private List<ViewListener> listenerList;
  private boolean startProgram = false;

  /**
   * Constructs a MockGUIView and sets the Appendable and initializes the lister list.
   *
   * @param out the the Appendable with which to show messages
   */
  public MockGUIView(Appendable out) {
    this.out = out;
    this.listenerList = new ArrayList<>();
  }

  @Override
  public void showView(boolean show) {
    this.startProgram = show;
  }

  @Override
  public void updateView(IImage image) {
///////////////////////////////////////////////
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (this.startProgram) {
      try {
        this.out.append(message + "\n");
      } catch (IOException e) {
        throw new IOException("Appendable failed");
      }
    }
  }

  @Override
  public void renderErrorMessage(String message) throws IOException {
    if (this.startProgram) {
      this.renderMessage(message.replace("\n", ""));
    }
  }

  @Override
  public void addListener(ViewListener viewListener) {
    this.listenerList.add(Objects.requireNonNull(viewListener));
  }

  /**
   * Emit a command to subscribers.
   *
   * @param path the path of the image.
   * @param command the command to be executed
   */
  public void emitCommand(String path, String... command) {
    for (ViewListener listener : listenerList) {
      listener.applyCommand(path, command);
    }
  }

  public int getListenersCount() {
    return this.listenerList.size();
  }

}
