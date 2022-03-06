package controller.commands.colortransformation;

/**
 * SepiaTone command that can be used to make an image Sepia Tone.
 */
public class SepiaToneTrans extends AbstractColorTrans {

  /**
   * creates SepiaToneTrans command object.
   */
  public SepiaToneTrans() {
    super(new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}});
  }

  @Override
  public String toString() {
    return "sepia tone";
  }
}
