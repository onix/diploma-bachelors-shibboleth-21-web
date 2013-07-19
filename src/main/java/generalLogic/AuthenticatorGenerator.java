package generalLogic;

import imageStegoLib.common.BitBooleanArraysConverter;
import imageStegoLib.message.AuthenticatorContainer;

import java.util.Random;

public class AuthenticatorGenerator {
    public static byte[] authenticatorRandomGenerator(int size) { //TODO incremental generator
        byte[] b = new byte[size];
        new Random().nextBytes(b);
        boolean[] bits = BitBooleanArraysConverter.byteArray2BitArray(b);
        bits[AuthenticatorContainer.INDEX_OF_ALWAYS_ONE_BIT] = true;

        return BitBooleanArraysConverter.bitArray2ByteArray(bits);
    }
}
