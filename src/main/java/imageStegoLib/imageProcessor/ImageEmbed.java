package imageStegoLib.imageProcessor;

import imageStegoLib.common.ImageEmbedExtractGeneralOperations;
import imageStegoLib.common.KeyGenerator;
import generalLogic.SecretStorage;
import imageStegoLib.message.MessageContainer;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.TreeMap;

public class ImageEmbed {
    private static final Logger logger = Logger.getLogger(ImageEmbed.class);

    public void embedIntoImage(BufferedImage image, SecretStorage storage, MessageContainer messageContainer) {
        if (image != null && messageContainer != null) {
            boolean[] message = messageContainer.getMessageWithChecksumBitDump();
            TreeMap<Integer, Integer> keys = KeyGenerator.keyPairGenerator(storage.getSecretKey(), storage.getAmountOfEmbeddingRounds());

            for (int i = 0; i < storage.getAmountOfEmbeddingRepeats() * message.length; i++) {
                int x = (int) Math.floor(i / storage.getSizeOfImageY()) + 1;
                int y = (i % storage.getSizeOfImageY()) + 1;

                for (int s = 0; s < storage.getAmountOfEmbeddingRounds(); s++) {
                    x = ((x + (keys.get(2 * s) ^ y)) % storage.getSizeOfImageX()) + 1;
                    y = ((y + (keys.get(2 * s + 1) ^ x)) % storage.getSizeOfImageY()) + 1;
                }
                int j = (int) Math.ceil(i / storage.getAmountOfEmbeddingRepeats());

                logger.info("Traversing pixel with x: " + x + " y:" + y + " j:" + j);
                ImageEmbedExtractGeneralOperations.embedIntoPixel(image, x, y, message[j], storage.getEmbeddingEnergy());
            }
            logger.info("Embedding successfully completed!");
            logger.info("Embedded message dump: " + Arrays.toString(message));
        }
        else
            logger.info("Embedding failed!");
    }

}