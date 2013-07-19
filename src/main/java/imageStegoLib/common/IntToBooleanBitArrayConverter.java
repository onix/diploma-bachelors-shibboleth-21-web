package imageStegoLib.common;

public class IntToBooleanBitArrayConverter {

    public static boolean[] convertIntToBitBooleanArray(int n, int outputBitCount) {
        if ((n >= 0) && (n <= 255)) {

            String binStr = String.format("%8s", Integer.toString(n,2)).replace(' ', '0');
            String s = Integer.toBinaryString(n);
            boolean[] bits = new boolean[outputBitCount];

            for (int i = 0; i < outputBitCount; i++) {
                bits[i] = binStr.charAt(i) == '1';
            }
            return bits;
        } else
            return null;
    }


    /*
    public static boolean[] int2bit(int n, int outputBitCount) {
        boolean[] output = new boolean[outputBitCount];
        for(boolean bit:output) {
            if (n & 1)
            n << 1 |
        }

            int x = 0;
            for (boolean bit : bits) {
                x = x << 1 | (bit ? 1 : 0);
            }

            return x;


        if ((n >= 0) && (n <= 255)) {

            String binStr = String.format("%8s", Integer.toBinaryString(n)).replace(' ', '0');
            String s = Integer.toBinaryString(n);
            boolean[] bits = new boolean[outputBitCount];

            for (int i = 0; i < outputBitCount; i++) {
                bits[i] = binStr.charAt(i) == '1';
            }
            return bits;
        } else
            return null;
    }
    */
}
