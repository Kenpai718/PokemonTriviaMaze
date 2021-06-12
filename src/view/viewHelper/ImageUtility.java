/**
 * 
 */
package view.viewHelper;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Utility to change the brightness of an image or read an image
 * 
 * Source:
 * https://stackoverflow.com/questions/46797579/how-can-i-control-the-brightness-of-an-image
 * 
 * @author ajdowney
 * @author kenneth ahrens
 *
 */
public final class ImageUtility {


	/**
	 * Darkens image to black
	 */
	private final static float MIN_BRIGHT = 0.0f;

	/**
	 * private constructor to prevent instantiation
	 */
	private ImageUtility() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Adjusts the brightness of the image to Darken it.
	 * 
	 * @param Image the iamge to change
	 * @param brightnessPercentage the alpha value to change to
	 * @return the Image darkened
	 */
	public static Image adjustBrighness(final Image theSource,
			final float theBrightnessPercentage) {

		final BufferedImage bi = new BufferedImage(theSource.getWidth(null),
				theSource.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		final int[] pixel = { 0, 0, 0, 0 };
		final float[] hsbvals = { 0, 0, 0 };

		bi.getGraphics().drawImage(theSource, 0, 0, null);

		// recalculare every pixel, changing the brightness
		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {

				// get the pixel data
				bi.getRaster().getPixel(j, i, pixel);

				// converts its data to hsb to change brightness
				Color.RGBtoHSB(pixel[0], pixel[1], pixel[2], hsbvals);

				// create a new color with the changed brightness
				final Color c = new Color(Color.HSBtoRGB(hsbvals[0], hsbvals[1],
						hsbvals[2] * theBrightnessPercentage));

				// set the new pixel
				bi.getRaster().setPixel(j, i, new int[] { c.getRed(),
						c.getGreen(), c.getBlue(), pixel[3] });

			}

		}

		return bi;

	}

	/**
	 * Helper method that make all pixels of an image pure black
	 * 
	 * @param theSource
	 * @return black image
	 */
	public static Image setToBlack(final Image theSource) {
		return adjustBrighness(theSource, MIN_BRIGHT);
	}
	
	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage. It
	 * is scaled this way to prevent the picture from looking blurry.
	 * 
	 * SOURCE:
	 * https://riptutorial.com/java/example/28299/how-to-scale-a-bufferedimage
	 * 
	 * @param srcImg - source image to scale
	 * @param w      - desired width
	 * @param h      - desired height
	 * @return - the new resized image
	 */
	public static BufferedImage getScaledImage(final Image srcImg, final int w,
			final int h) {

		// Create a new image with good size that contains or might contain
		// arbitrary alpha values between and including 0.0 and 1.0.
		final BufferedImage resizedImg = new BufferedImage(w, h,
				BufferedImage.TRANSLUCENT);

		// Create a device-independant object to draw the resized image
		final Graphics2D g2 = resizedImg.createGraphics();

		// improve quality of rendering
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// Finally draw the source image in the Graphics2D with the desired
		// size.
		g2.drawImage(srcImg, 0, 0, w, h, null);

		// Disposes of this graphics context and releases any system resources
		// that it is using
		g2.dispose();

		// Return the image used to create the Graphics2D
		return resizedImg;
	}

	/**
	 * Helper method to read an Image given a filepath
	 * 
	 * @param String theLocation filepath
	 * @return BufferedImage the new image
	 */
	public static BufferedImage readImage(final String theLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(theLocation));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return img;
	}

}
