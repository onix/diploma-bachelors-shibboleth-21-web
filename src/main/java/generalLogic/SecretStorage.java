package generalLogic;

public class SecretStorage {
    public static final int secretKey = 125;
    public static final int amountOfEmbeddingRounds = 10; //R
    public static final int STEGO_PHOTO_WIDTH = 354; // 0 ... 637
    public static final int STEGO_PHOTO_HEIGHT = 472; // 0 ... 1010
    public static final int PASS_WIDTH = 637;
    public static final int PASS_HEIGHT = 1010;
    public static final int amountOfEmbeddingRepeats = 20; //tau
    public static final double embeddingEnergy = 0.2; // lambda
    public static final int amountOfHorizontalAndVerticalPixelsToEstimate = 3; //sigma

    //*** Needed for library
    public static int getSecretKey() {
        return secretKey;
    }

    public static int getAmountOfEmbeddingRounds() {
        return amountOfEmbeddingRounds;
    }

    public static int getSizeOfImageX() {
        return STEGO_PHOTO_WIDTH - 1;
    }

    public static int getSizeOfImageY() {
        return STEGO_PHOTO_HEIGHT - 1;
    }

    public static int getPassWidth() {
        return PASS_WIDTH;
    }

    public static int getPassHeight() {
        return PASS_HEIGHT;
    }

    public static int getAmountOfEmbeddingRepeats() {
        return amountOfEmbeddingRepeats;
    }

    public static double getEmbeddingEnergy() {
        return embeddingEnergy;
    }

    public static int getAmountOfHorizontalAndVerticalPixelsToEstimate() {
        return amountOfHorizontalAndVerticalPixelsToEstimate;
    }
}
