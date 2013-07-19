package imageStegoLib.common;

import org.apache.log4j.Logger;

import java.util.TreeMap;

public class KeyGenerator {
    private static final Logger logger = Logger.getLogger(KeyGenerator.class);

    public static TreeMap<Integer, Integer> keyPairGenerator(int key, int rounds) {
        if (key >= 2 && rounds > 0) {
            TreeMap<Integer, Integer> out = new TreeMap<Integer, Integer>();
            for (int i = 0; i < rounds * 2; i++) {
                if (i == 0)
                    out.put(i, key);
                else {
                    int k_s_1 = out.get(i - 1);
                    int operation = k_s_1 * k_s_1;
                    String operationStr = String.valueOf(operation).substring(0, 3);

                    out.put(i, Integer.parseInt(operationStr));
                }
                if (out.get(i) > 255) {
                    int k_s = out.get(i);
                    String operationStr = String.valueOf(k_s).substring(0, 2);

                    out.put(i, Integer.parseInt(operationStr));
                }
                logger.info("Key pair K = " + i + ", V = " + out.get(i) + " generated.");
            }
            return out;
        } else
            return null;
    }

}
