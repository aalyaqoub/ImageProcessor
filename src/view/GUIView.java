package view;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.WindowConstants;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.histogram.Histogram;
import model.image.IImage;

/**
 * A GUI implementation of view. This view implements IView by using Java swing components. To make
 * use a GUIView a subscriber controller is need to listen to and execute commands emitted by the
 * view. The view is composed of the menubar and the main panel. The main panel is divided into a
 * portion for viewing the image being worked on and a control panel for manipulating the image.
 * Lastly, the control panel is made out of various components that allow for user interaction and
 * input.
 */
public class GUIView extends JFrame implements IGUIView, ActionListener {
  private ImageIcon image;
  private final JLabel imageLabel;
  private ImageIcon histogramImage;
  private final JLabel histogram;

  private final JTextField brightnessChange;

  private final JTextField mosaicSeed;

  private final JTextField downsizingWidth;
  private final JTextField downsizingHeight;

  private final JLabel imagePath;
  private final JLabel savePath;

  private final JLabel message;

  private final List<ViewListener> listenerList;

  /**
   * Constructs a GUIView with an Image preview and control panel to manipulate the image.
   */
  public GUIView() {
    super();
    this.listenerList = new ArrayList<>();
    // set the title
    this.setTitle("Image Editor");
    this.setLayout(new GridLayout(1, 0, 10, 10));
    this.addWindowListener(
            new WindowAdapter() {
              @Override
              public void windowClosing(WindowEvent e) {
                //clean app data.
                cleanAppData();
              }
            });
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    Dimension minDim = new Dimension(900, 700);
    setMinimumSize(minDim);

    //Create the menu bar.
    JMenuBar menuBar = new JMenuBar();
    //Build the file menu.
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_A);
    fileMenu.getAccessibleContext().setAccessibleDescription(
            "The file menu option for opening and saving files");
    this.imagePath = new JLabel();
    menuBar.add(fileMenu);

    //add file opening option
    JMenuItem open = new JMenuItem("Open");
    open.getAccessibleContext().setAccessibleDescription(
            "Open a file");
    open.addActionListener(this);
    fileMenu.add(open);

    // add a file saving option
    JMenuItem save = new JMenuItem("Save");
    save.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_S, ActionEvent.META_MASK));
    save.getAccessibleContext().setAccessibleDescription(
            "Save a file");
    save.addActionListener(this);
    fileMenu.add(save);

    // add a file saving as option
    JMenuItem saveAs = new JMenuItem("Save As");
    saveAs.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_S, ActionEvent.META_MASK + ActionEvent.SHIFT_MASK));
    saveAs.getAccessibleContext().setAccessibleDescription(
            "Save a file");
    saveAs.addActionListener(this);
    this.savePath = new JLabel();
    fileMenu.add(saveAs);

    //Creates the main Panel
    JPanel mainPanel = new JPanel();
    //for elements to be arranged horizontally within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);

    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Preview"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    imagePanel.setMaximumSize(null);

    this.image = new ImageIcon("");
    this.image.getImage().flush();
    this.imageLabel = new JLabel(this.image);
    JScrollPane imageScrollPane = new JScrollPane(this.imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(600, 600));
    imagePanel.add(imageScrollPane);

    //Control panel
    JPanel controlPanel = new JPanel();
    //a border around the panel with a caption
    controlPanel.setBorder(BorderFactory.createTitledBorder("Image Adjustments"));
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
    controlPanel.setMaximumSize(null);
    JScrollPane scrollableControl = new JScrollPane(controlPanel);

    //Brightness Control
    JPanel brightnessPanel = new JPanel();
    //a border around the panel with a caption
    brightnessPanel.setBorder(BorderFactory.createTitledBorder("Brightness"));
    brightnessPanel.setLayout(new GridLayout(1, 0, 0, 10));
    this.brightnessChange = new JTextField(10);
    brightnessPanel.add(this.brightnessChange);
    // button to emit the inputted number
    JButton changeBrightness = new JButton("Change Brightness");
    changeBrightness.setActionCommand("changeBrightness");
    changeBrightness.addActionListener(this);
    brightnessPanel.add(changeBrightness);

    //Mosaic Control
    JPanel mosaicPanel = new JPanel();
    //a border around the panel with a caption
    mosaicPanel.setBorder(BorderFactory.createTitledBorder("Mosaic"));
    mosaicPanel.setLayout(new GridLayout(1, 0, 0, 10));
    this.mosaicSeed = new JTextField(10);
    mosaicPanel.add(this.mosaicSeed);
    // button to emit the inputted number
    JButton mosaicSeedSubmit = new JButton("Seed");
    mosaicSeedSubmit.setActionCommand("mosaic");
    mosaicSeedSubmit.addActionListener(this);
    mosaicPanel.add(mosaicSeedSubmit);

    //Downsizing Control
    JPanel downsizingPanel = new JPanel();
    //a border around the panel with a caption
    downsizingPanel.setBorder(BorderFactory.createTitledBorder("Downsizing"));
    downsizingPanel.setLayout(new GridLayout(1, 0, 0, 10));
    this.downsizingWidth = new JTextField(10);
    downsizingPanel.add(this.downsizingWidth);
    this.downsizingHeight = new JTextField(10);
    downsizingPanel.add(this.downsizingHeight);
    // button to emit the inputted number
    JButton downsizingSubmit = new JButton("Downsize");
    downsizingSubmit.setActionCommand("downsizing");
    downsizingSubmit.addActionListener(this);
    downsizingPanel.add(downsizingSubmit);


    //Orientation Control
    JPanel orientationPanel = new JPanel();
    //a border around the panel with a caption
    orientationPanel.setBorder(BorderFactory.createTitledBorder("Orientation"));
    orientationPanel.setLayout(new GridLayout(0, 1, 0, 0));
    // Button to flip vertically
    JButton changeVertical = new JButton("Flip Vertically");
    changeVertical.setActionCommand("vertical-flip");
    changeVertical.addActionListener(this);
    // Button to flip horizontally
    JButton changeHorizontal = new JButton("Flip Horizontally");
    changeHorizontal.setActionCommand("horizontal-flip");
    changeHorizontal.addActionListener(this);
    orientationPanel.add(changeVertical);
    orientationPanel.add(changeHorizontal);

    // Histogram view
    JPanel histogramPanel = new JPanel();
    //a border around the panel with a caption
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setLayout(new GridLayout(0, 1, 0, 0));
    this.histogramImage = new ImageIcon("");
    this.histogram = new JLabel(histogramImage);
    JScrollPane histogramScrollPane = new JScrollPane(histogram);
    histogramScrollPane.setPreferredSize(new Dimension(20, 160));
    histogramPanel.add(histogramScrollPane);

    // Color components panel
    JPanel colorPanel = new JPanel();
    //a border around the panel with a caption
    colorPanel.setBorder(BorderFactory.createTitledBorder("Color Components"));
    colorPanel.setLayout(new GridLayout(0, 1, 0, 0));
    // Buttons that'll be added to the panel
    JButton redComp = new JButton("Red Component");
    redComp.setActionCommand("red-component");
    redComp.addActionListener(this);
    JButton greenComp = new JButton("Green Component");
    greenComp.setActionCommand("green-component");
    greenComp.addActionListener(this);
    JButton blueComp = new JButton("Blue Component");
    blueComp.setActionCommand("blue-component");
    blueComp.addActionListener(this);
    JButton intensityComp = new JButton("Intensity Component");
    intensityComp.setActionCommand("intensity-component");
    intensityComp.addActionListener(this);
    JButton lumaComp = new JButton("Luma Component");
    lumaComp.setActionCommand("luma-component");
    lumaComp.addActionListener(this);
    JButton valueComp = new JButton("Value Component");
    valueComp.setActionCommand("values-component");
    valueComp.addActionListener(this);
    colorPanel.add(redComp);
    colorPanel.add(greenComp);
    colorPanel.add(blueComp);
    colorPanel.add(intensityComp);
    colorPanel.add(lumaComp);
    colorPanel.add(valueComp);

    // Filters panel
    JPanel filterPanel = new JPanel();
    //a border around the panel with a caption
    filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
    filterPanel.setLayout(new GridLayout(0, 1, 0, 0));
    // Buttons that'll be added to the panel
    JButton blur = new JButton("Blur");
    blur.setActionCommand("blur");
    blur.addActionListener(this);
    JButton sharpen = new JButton("Sharpen");
    sharpen.setActionCommand("sharpen");
    sharpen.addActionListener(this);
    filterPanel.add(blur);
    filterPanel.add(sharpen);

    // Transformations panel
    JPanel transformationPanel = new JPanel();
    //a border around the panel with a caption
    transformationPanel.setBorder(BorderFactory.createTitledBorder("Transformations"));
    transformationPanel.setLayout(new GridLayout(0, 1, 0, 0));
    // Buttons that'll be added to the panel
    JButton grayScale = new JButton("Gray Scale");
    grayScale.setActionCommand("greyscale-trans");
    grayScale.addActionListener(this);
    JButton sepiaTone = new JButton("Sepia Tone");
    sepiaTone.setActionCommand("sepia-tone");
    sepiaTone.addActionListener(this);
    transformationPanel.add(grayScale);
    transformationPanel.add(sepiaTone);

    // Add a footer panel for messages
    JPanel footer = new JPanel();
    footer.setBorder(BorderFactory.createTitledBorder("Message output"));
    footer.setLayout(new GridLayout(1, 0, 10, 10));
    // Label to show messages
    this.message = new JLabel(" ");
    this.message.setPreferredSize(new Dimension(250, 25));
    JScrollPane messageScroll = new JScrollPane(this.message);
    footer.add(messageScroll);

    // Add elements to the page
    add(mainScrollPane);
    mainPanel.add(imagePanel);
    controlPanel.add(brightnessPanel);
    controlPanel.add(mosaicPanel);
    controlPanel.add(downsizingPanel);
    controlPanel.add(orientationPanel);
    controlPanel.add(histogramPanel);
    controlPanel.add(colorPanel);
    controlPanel.add(filterPanel);
    controlPanel.add(transformationPanel);
    controlPanel.add(footer);
    mainPanel.add(scrollableControl);
    setJMenuBar(menuBar);

    this.pack();
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * Removes temp files created by the program.
   */
  private void cleanAppData() {
    File appDataDir = new File("res/appData");
    File[] appDataFiles = appDataDir.listFiles();
    for (File file : appDataFiles) {
      if (!file.delete()) {
        try {
          this.renderErrorMessage("Couldn't clean up app data");
        } catch (IOException e) {
          throw new IllegalStateException("Couldn't render error message");
        }
      }
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.message.setText(" " + message);
    repaint();
    revalidate();
  }

  @Override
  public void renderErrorMessage(String message) throws IOException {
    JOptionPane.showMessageDialog(GUIView.this,
            message, "Error", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Open": {
        this.fileIO(e.getActionCommand());
        this.emitCommand(this.imagePath.getText(), e.getActionCommand());
        break;
      }
      case "Save As": {
        this.fileIO(e.getActionCommand());
        this.emitCommand(this.savePath.getText(), "Save");
        break;
      }
      case "Save": {
        this.emitCommand(this.imagePath.getText(), "Save");
        break;
      }
      case "changeBrightness": {
        if (!this.imagePath.getText().isEmpty()) {
          try {
            if (Integer.parseInt(this.brightnessChange.getText()) >= 0) {
              this.emitCommand("currentPath", "brighten",
                      this.brightnessChange.getText());
            } else {
              System.out.println("Darken");
              this.emitCommand("currentPath", "darken",
                      this.brightnessChange.getText());
            }
          } catch (NumberFormatException numberFormatException) {
            try {
              this.renderErrorMessage("Invalid brightness change value: " +
                      this.brightnessChange.getText());
            } catch (IOException ioException) {
              throw new IllegalStateException("Couldn't render error message");
            }
          }
        }
        break;
      }
      case "mosaic": {
        if (!this.imagePath.getText().isEmpty()) {
          try {
            this.emitCommand("currentPath", "mosaic",
                    this.mosaicSeed.getText());
          } catch (NumberFormatException numberFormatException) {
            try {
              this.renderErrorMessage("Invalid mosaic seed: " +
                      this.mosaicSeed.getText());
            } catch (IOException ioException) {
              throw new IllegalStateException("Couldn't render error message");
            }
          }
        }
        break;
      }
      case "downsizing": {
        if (!this.imagePath.getText().isEmpty()) {
          try {
            this.emitCommand("currentPath", "downsizing",
                    this.downsizingWidth.getText(), this.downsizingHeight.getText());
          } catch (NumberFormatException numberFormatException) {
            try {
              this.renderErrorMessage("Invalid resize dimensions: " +
                      this.downsizingWidth.getText() + ", " + this.downsizingHeight.getText());
            } catch (IOException ioException) {
              throw new IllegalStateException("Couldn't render error message");
            }
          }
        }
        break;
      }
      default: {
        if (!this.imagePath.getText().isEmpty()) {
          this.emitCommand("currentPath", e.getActionCommand());
        }
      }
    }
//    this.updateImage();
//    this.updateHistogram();
  }

  /**
   * Shows a file choosing menu for IO operations like Opening images and Saving as.
   *
   * @param operationIO the operation that needs to be done, Open or Save as.
   */
  private void fileIO(String operationIO) {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG", "jpg", "jpeg");
    fchooser.setFileFilter(filter);
    filter = new FileNameExtensionFilter(
            "PPM", "ppm");
    fchooser.setFileFilter(filter);
    filter = new FileNameExtensionFilter(
            "BMP", "bmp");
    fchooser.setFileFilter(filter);
    filter = new FileNameExtensionFilter(
            "PNG", "png");
    fchooser.setFileFilter(filter);
    int retvalue;
    if (operationIO.equals("Open")) {
      retvalue = fchooser.showOpenDialog(GUIView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        this.imagePath.setText(f.getAbsolutePath());
      }
    } else {
      retvalue = fchooser.showSaveDialog(GUIView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        this.savePath.setText(f.getAbsolutePath());
      }
    }


  }

  /**
   * Updates the preview of the image.
   */
  private void updateImage(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't give a null image");
    }
    BufferedImage bufImg = image.getBufferedImage();
    this.image = new ImageIcon(bufImg);
    this.imageLabel.setIcon(this.image);
    repaint();
    revalidate();
    this.updateHistogram(image);
  }

  /**
   * Updates the preview of the histogram.
   */
  private void updateHistogram(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Can't give a null image");
    }
    BufferedImage bufImg = new Histogram().showHistogram(image);
    this.histogramImage = new ImageIcon(bufImg);
    this.histogram.setIcon(this.histogramImage);
    repaint();
    revalidate();
  }

  @Override
  public void showView(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void updateView(IImage image) {
    this.updateImage(image);
  }

  @Override
  public void addListener(ViewListener viewListener) {
    this.listenerList.add(Objects.requireNonNull(viewListener));
  }

  /**
   * Emit a command to subscribers.
   *
   * @param path    the path of the image.
   * @param command the command to be executed
   */
  private void emitCommand(String path, String... command) {
    for (ViewListener listener : listenerList) {
      listener.applyCommand(path, command);
    }
  }
}