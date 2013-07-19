package imageStegoLib.imageProcessor;

import imageStegoLib.common.ImageEmbedExtractGeneralOperations;
import imageStegoLib.common.KeyGenerator;
import generalLogic.SecretStorage;
import imageStegoLib.message.AuthenticatorContainer;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.TreeMap;

public class ImageExtract {
    private static final Logger logger = Logger.getLogger(ImageExtract.class);

    public boolean[] extractFromImage(BufferedImage image, SecretStorage storage, int outputArraySize) {
        if (image != null) {
            TreeMap<Integer, Integer> keys = KeyGenerator.keyPairGenerator(storage.getSecretKey(), storage.getAmountOfEmbeddingRounds());
            boolean[] message = new boolean[outputArraySize];

            int[] bluePixelEstimate = new int[storage.getAmountOfEmbeddingRepeats() + 1]; //wtf TODO -- koctbl/\b
            int[] BluePixelAnalyzed = new int[storage.getAmountOfEmbeddingRepeats() + 1];

            int t = 0;
            for (int i = 0; i < storage.getSizeOfImageX()  * storage.getSizeOfImageY(); i++) {
                int x = (int) Math.floor(i / storage.getSizeOfImageY()) + 1;
                int y = (i % storage.getSizeOfImageY()) + 1;

                for (int s = 0; s < storage.getAmountOfEmbeddingRounds(); s++) {
                    x = ((x + (keys.get(2 * s) ^ y)) % storage.getSizeOfImageX()) + 1;
                    y = ((y + (keys.get(2 * s + 1) ^ x)) % storage.getSizeOfImageY()) + 1;
                }

                logger.info("Estimating pixel with x: " + x + " y:" + y);

                int sigma1 = 0;
                int sigma2 = 0;
                int sigma3 = 0;
                int sigma4 = 0;

                if (storage.getAmountOfHorizontalAndVerticalPixelsToEstimate() < x &&
                        x <= storage.getSizeOfImageX() - storage.getAmountOfHorizontalAndVerticalPixelsToEstimate()) {
                    sigma1 = -storage.getAmountOfHorizontalAndVerticalPixelsToEstimate();
                    sigma2 = storage.getAmountOfHorizontalAndVerticalPixelsToEstimate();
                }

                if (x <= storage.getAmountOfHorizontalAndVerticalPixelsToEstimate()) {
                    sigma1 = 1 - x;
                    sigma2 = storage.getAmountOfHorizontalAndVerticalPixelsToEstimate();
                }

                if (x > storage.getSizeOfImageX() - storage.getAmountOfHorizontalAndVerticalPixelsToEstimate()) {
                    sigma1 = -storage.getAmountOfHorizontalAndVerticalPixelsToEstimate();
                    sigma2 = storage.getSizeOfImageX() - x;
                }

                if (storage.getAmountOfHorizontalAndVerticalPixelsToEstimate() < y &&
                        y <= storage.getSizeOfImageY() - storage.getAmountOfHorizontalAndVerticalPixelsToEstimate()) {
                    sigma3 = -storage.getAmountOfHorizontalAndVerticalPixelsToEstimate();
                    sigma4 = storage.getAmountOfHorizontalAndVerticalPixelsToEstimate();
                }

                if (y <= storage.getAmountOfHorizontalAndVerticalPixelsToEstimate()) {
                    sigma3 = 1 - y;
                    sigma4 = storage.getAmountOfHorizontalAndVerticalPixelsToEstimate();
                }

                if (y > storage.getSizeOfImageY() - storage.getAmountOfHorizontalAndVerticalPixelsToEstimate()) {
                    sigma3 = -storage.getAmountOfHorizontalAndVerticalPixelsToEstimate();
                    sigma4 = storage.getSizeOfImageY() - y;
                }

                int sum1 = 0;
                for (int a = sigma1; a <= sigma2; a++) {
                    sum1 += ImageEmbedExtractGeneralOperations.getPixelBlueChannelBrightness(new Color(image.getRGB(x + a, y)));
                }

                int sum2 = 0;
                for (int a = sigma3; a <= sigma4; a++) {
                    sum1 += ImageEmbedExtractGeneralOperations.getPixelBlueChannelBrightness(new Color(image.getRGB(x, y + a)));
                }

                int bluePixelEstimateUpper = (sum1 + sum2) - (2 * ImageEmbedExtractGeneralOperations.getPixelBlueChannelBrightness(new Color(image.getRGB(x, y))));
                int bluePixelEstimateLower = sigma2 - sigma1 + sigma4 - sigma3;
                int est = bluePixelEstimateUpper / bluePixelEstimateLower;
                bluePixelEstimate[t] = est;

                BluePixelAnalyzed[t] = ImageEmbedExtractGeneralOperations.getPixelBlueChannelBrightness(new Color(image.getRGB(x, y)));

                t++;
                if (t >= storage.getAmountOfEmbeddingRepeats()) {
                    int sum = 0;
                    for (int T = 0; T < storage.getAmountOfEmbeddingRepeats(); T++) {
                        sum += (BluePixelAnalyzed[T] - bluePixelEstimate[T]);
                    }

                    double delta = (1.0 / storage.getAmountOfEmbeddingRepeats()) * sum;
                    int j = (int) Math.ceil(i / storage.getAmountOfEmbeddingRepeats());

                    if (j >= AuthenticatorContainer.MESSAGE_GENERAL_LENGTH) {
                        logger.info("Message length overflow. Terminating.");
                        break;
                    }

                    if (delta <= 0) {
                        message[j] = false;
                        logger.info("Extracted from x: " + x + " y:" + y + " a [" + j + "] element of message which equals: " + false);
                    }
                    else {
                        message[j] = true;
                        logger.info("Extracted from x: " + x + " y:" + y + " a [" + j + "] element of message which equals: " + true);
                    }
                    t = 0;
                }
            }

            logger.info("Extracting successfully completed!");
            logger.info("Extracted message dump: " + Arrays.toString(message));
            return message;
        }
        else {
            logger.info("Extracting failed!");
            return null;
        }
    }

}
