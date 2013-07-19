package generalLogic;

import imageStegoLib.imageProcessor.ImageExtract;
import imageStegoLib.message.AuthenticatorContainer;
import imageStegoLib.message.MessageContainer;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;

public class PassImageChecker implements CheckPassImage {
    private static final Logger logger = Logger.getLogger(PassImageChecker.class);

    @Override
    public byte[] checkPassImage(BufferedImage passImage) {
        if (passImage != null) {
            try {
                final BufferedImage stegoContainer = passImage.getSubimage(620, 70, SecretStorage.STEGO_PHOTO_WIDTH, SecretStorage.STEGO_PHOTO_HEIGHT);

                ImageExtract ie = new ImageExtract();
                MessageContainer mc = new AuthenticatorContainer();
                SecretStorage storage = new SecretStorage();

                boolean isCorrect = mc.setMessageWithChecksumAndCheck(ie.extractFromImage(stegoContainer, storage, AuthenticatorContainer.MESSAGE_GENERAL_LENGTH));
                logger.info("Is checksum correct: " + isCorrect);
                if (isCorrect)
                    return mc.getOnlyMessageByteDump();
            } catch (Exception e) {  //TODO
                return null;
            }
        }
        return null;
    }
}
