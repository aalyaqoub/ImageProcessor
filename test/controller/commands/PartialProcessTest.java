package controller.commands;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import controller.commands.color.BlueComponent;
import controller.commands.color.GreenComponent;
import controller.commands.color.PixelIntensity;
import controller.commands.color.PixelLuma;
import controller.commands.color.PixelValues;
import controller.commands.color.RedComponent;
import controller.commands.colortransformation.GreyscaleTrans;
import controller.commands.colortransformation.SepiaToneTrans;
import controller.commands.filter.ImageBlur;
import controller.commands.filter.ImageSharpen;
import controller.commands.reader.ReadPNG;
import controller.commands.reader.ReadPPM;
import model.image.IImageMutable;
import model.image.ImageImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link PartialProcess}s.
 */
public class PartialProcessTest {
  IImageMutable image;
  IImageMutable mask;

  @Before
  public void initImage() {
    try {
      this.image = new ImageImpl(new ReadPPM().read("res/example/PPM/OGImage.ppm"));
      this.mask = new ImageImpl(new ReadPNG().read("res/example/PNG/Mask.png"));
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullMask() {
    IProcess partial = new PartialProcess(null, new SepiaToneTrans());
    partial.process(this.image);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullProcess() {
    IProcess partial = new PartialProcess(this.mask, null);
    partial.process(this.image);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    IProcess partial = new PartialProcess(this.mask, new SepiaToneTrans());
    partial.process(null);
  }

  @Test
  public void testBlur() {
    assertTrue(this.abstractTest(new ImageBlur()));
  }

  @Test
  public void testSharpen() {
    assertTrue(this.abstractTest(new ImageSharpen()));
  }

  @Test
  public void testGreyscale() {
    assertTrue(this.abstractTest(new GreyscaleTrans()));
  }

  @Test
  public void testPartialSepia() {
    assertTrue(this.abstractTest(new SepiaToneTrans()));
  }

  @Test
  public void testRed() {
    assertTrue(this.abstractTest(new RedComponent()));
  }

  @Test
  public void testGreen() {
    assertTrue(this.abstractTest(new GreenComponent()));
  }

  @Test
  public void testBlue() {
    assertTrue(this.abstractTest(new BlueComponent()));
  }

  @Test
  public void testIntensity() {
    assertTrue(this.abstractTest(new PixelIntensity()));
  }

  @Test
  public void testLuma() {
    assertTrue(this.abstractTest(new PixelLuma()));
  }

  @Test
  public void testValues() {
    assertTrue(this.abstractTest(new PixelValues()));
  }

  private boolean abstractTest(IProcess process) {
    IProcess partial = new PartialProcess(this.mask, process);
    IImageMutable pImage = partial.process(this.image);
    assertEquals(this.image.getPixel(0, 0), pImage.getPixel(0, 0));
    assertEquals(this.image.getPixel(0, 1), pImage.getPixel(0, 1));
    assertEquals(this.image.getPixel(0, 2), pImage.getPixel(0, 2));
    assertEquals(this.image.getPixel(1, 2), pImage.getPixel(1, 2));
    assertEquals(this.image.getPixel(2, 2), pImage.getPixel(2, 2));
    assertNotEquals(this.image.getPixel(1, 0), pImage.getPixel(1, 0));
    assertNotEquals(this.image.getPixel(1, 1), pImage.getPixel(1, 1));
    assertNotEquals(this.image.getPixel(2, 0), pImage.getPixel(2, 0));
    assertNotEquals(this.image.getPixel(2, 1), pImage.getPixel(2, 1));
    return true;
  }

}
