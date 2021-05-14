/**
 * 
 */
package view.viewHelper;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author ajdowney
 *
 */
public final class BrightnessUtility {
        
        //private constructor to prevent instantiation
        
        private BrightnessUtility() {
                throw new UnsupportedOperationException();
        }

        
        /**
         * @param source
         * @param brightnessPercentage
         * @return
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
}
