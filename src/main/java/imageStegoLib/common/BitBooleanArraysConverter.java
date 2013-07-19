package imageStegoLib.common;

import java.util.Arrays;

public class BitBooleanArraysConverter {
    /**
     * Convert a byte array to a boolean array. Bit 0 is represented with false,
     * Bit 1 is represented with 1
     *
     * @param bytes byte[]
     * @return boolean[]
     */
    public static boolean[] byteArray2BitArray(byte[] bytes) {
        if (bytes != null) {
            boolean[] bits = new boolean[bytes.length * 8];

            for (int i = 0; i < bytes.length * 8; i++) {
                if ((bytes[i / 8] & (1 << (7 - (i % 8)))) > 0)
                    bits[i] = true;
            }

            return bits;
        }
        return null;
    }

    /**
     * Convert a bit array to a byte array. Bit 0 is represented with false,
     * Bit 1 is represented with 1
     *
     * @param bits boolean[]
     * @return byte[]
     */
    public static byte[] bitArray2ByteArray(boolean[] bits) {
        if (bits != null) {
            byte[] bytes = new byte[(int)Math.ceil((float) bits.length / 8)];

            if (bits.length % 8 != 0) {
                boolean[] destination = new boolean[bytes.length * 8];
                Arrays.fill(destination, false);

                System.arraycopy(bits, 0, destination, 0, bits.length);
                bits = destination;
            }

            int index0 = 0;
            int index1 = 8;
            for (int i = 0; i < bytes.length; i++) {
                for (int t = index0; t < index1; t++) {
                    bytes[i] <<= 1;
                    if (bits[t])
                        bytes[i] |= 1;
                }

                index0 += 8;
                index1 += 8;
            }

            return bytes;
        }
        return null;
    }

    /**
     * http://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
     * @param s - input string in HEX encoding.
     * @return byte[]
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
