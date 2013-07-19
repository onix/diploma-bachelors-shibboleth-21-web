package imageStegoLib.common;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageEmbedExtractGeneralOperations {
    private static final Logger logger = Logger.getLogger(ImageEmbedExtractGeneralOperations.class);

    public static int getPixelBlueChannelBrightness(Color pixel) {
        return pixel.getBlue();
    }

    public static int embedIntoPixel(BufferedImage image, int x, int y, boolean bit, double energy) {
        Color pixel = new Color(image.getRGB(x, y));
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();

        int pixelBrightness = (int) (0.29890 * red + 0.58662 * green + 0.11448 * blue);

        int modifiedBlueComponent;
        if (bit)
            modifiedBlueComponent = (int) (blue + energy * pixelBrightness);
        else
            modifiedBlueComponent = (int) (blue - energy * pixelBrightness);

        if (modifiedBlueComponent > 255)
            modifiedBlueComponent = 255;
        if (modifiedBlueComponent < 0)
            modifiedBlueComponent = 0;

        Color pixelModified = new Color(red, green, modifiedBlueComponent);

        image.setRGB(x, y, pixelModified.getRGB());
        logger.info("Modified pixel with x = " + x + " y = " + y + ", embedded bit: " + bit);
        return modifiedBlueComponent;
    }

}
