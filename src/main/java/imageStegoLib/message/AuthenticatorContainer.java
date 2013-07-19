package imageStegoLib.message;

import imageStegoLib.common.BitBooleanArraysConverter;
import imageStegoLib.common.IntToBooleanBitArrayConverter;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * +--------------------------------------------------------------------------------------------------------+
 * |Bits| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |...|248|249|250|251|252|253|254|255|256|257|258|259|260|261|262|263| -- 264
 * +--------------------------------------------------------------------|---|-------------------------------+
 * |Bin | 0 | 1 | 0 | 1 | 1 | 0 | 1 | 1 |...| 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 1 | -- 255th is always "1"
 * +--------------------------------------------------------------------|---|-------------------------------+
 * |HEX |       5       |       B       |...|       0       |       1   |   |       0       |       5       | -- 256 - 263 is checksum
 * +--------------------------------------------------------------------|---|-------------------------------+
 * |Mean|                          Information field                    |CON|Checksum of "1" without 255-bit|
 * +--------------------------------------------------------------------------------------------------------+
 */
public class AuthenticatorContainer implements MessageContainer {
    public static final int MESSAGE_GENERAL_LENGTH = 264;
    //public static final int INFORMATION_FIELD_LENGTH = 255;
    public static final int INFORMATION_FIELD_LENGTH = 256;
    public static final int CHECKSUM_FIELD_LENGTH = 8;
    public static final int INDEX_OF_ALWAYS_ONE_BIT = 255;

    private static final Logger logger = Logger.getLogger(AuthenticatorContainer.class);

    private final boolean bitArray[] = new boolean[MESSAGE_GENERAL_LENGTH];

    public AuthenticatorContainer() {
        setAlwaysOneBitField();
        logger.info("Created empty MessageContainer.");
    }

    public AuthenticatorContainer(boolean[] inputInformationField) {
        setMessageAndComputeChecksum(inputInformationField);
        logger.info("Created MessageContainer with not empty information field.");
    }

    private void setAlwaysOneBitField() {
        bitArray[INDEX_OF_ALWAYS_ONE_BIT] = true;
    }

    public void recomputeAndSaveChecksum() {
        final int checksum = computeAmountOfTrues(Arrays.copyOfRange(bitArray, 0, INDEX_OF_ALWAYS_ONE_BIT));//0...255 // -1?
        final boolean[] bitChecksum = IntToBooleanBitArrayConverter.convertIntToBitBooleanArray(checksum, CHECKSUM_FIELD_LENGTH);

        for (int i = INDEX_OF_ALWAYS_ONE_BIT + 1, a = 0; i < MESSAGE_GENERAL_LENGTH; i++, a++) {
            bitArray[i] = bitChecksum[a];
        }
        logger.info("MessageContainer checksum recomputed.");
    }

    public int computeAmountOfTrues(boolean[] inputData) {
        if (inputData != null) {
            int checksum = 0;

            for (boolean anInputData : inputData) {
                if (anInputData)
                    checksum++;
            }
            return checksum;
        } else
            return 0;
    }

    private boolean validateChecksum() {
        int amountOfTrues = computeAmountOfTrues(Arrays.copyOfRange(bitArray, 0, INDEX_OF_ALWAYS_ONE_BIT));
        boolean[] amountOfTruesBool = IntToBooleanBitArrayConverter.convertIntToBitBooleanArray(amountOfTrues, CHECKSUM_FIELD_LENGTH);
        return Arrays.equals(amountOfTruesBool, Arrays.copyOfRange(bitArray, INFORMATION_FIELD_LENGTH, MESSAGE_GENERAL_LENGTH));
    }

    @Override
    public boolean[] getMessageWithChecksumBitDump() {
        return bitArray;
    }

    @Override
    public byte[] getMessageWithChecksumByteDump() {
        return BitBooleanArraysConverter.bitArray2ByteArray(getMessageWithChecksumBitDump());
    }

    @Override
    public boolean[] getOnlyMessageBitDump() {
        boolean[] message = new boolean[INFORMATION_FIELD_LENGTH];
        System.arraycopy(bitArray, 0, message, 0, INFORMATION_FIELD_LENGTH);
        return message;
    }

    @Override
    public byte[] getOnlyMessageByteDump() {
        return BitBooleanArraysConverter.bitArray2ByteArray(getOnlyMessageBitDump());
    }

    @Override
    public boolean setMessageWithChecksumAndCheck(boolean[] messageWithChecksum) {
        if (messageWithChecksum != null) {
            Arrays.fill(bitArray, false);
            System.arraycopy(messageWithChecksum, 0, bitArray, 0, MESSAGE_GENERAL_LENGTH);
            if (validateChecksum()) {
                logger.info("MessageContainer information field filled with data: " + Arrays.toString(messageWithChecksum));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setMessageAndComputeChecksum(boolean[] messageWithoutChecksum) {
        if (messageWithoutChecksum != null) {
            Arrays.fill(bitArray, false);
            System.arraycopy(messageWithoutChecksum, 0, bitArray, 0, messageWithoutChecksum.length);
            setAlwaysOneBitField();
            recomputeAndSaveChecksum();
            logger.info("MessageContainer information field filled with data: " + Arrays.toString(messageWithoutChecksum));
            return true;
        } else
            return false;
    }

}

