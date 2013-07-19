package imageStegoLib.message;

public interface MessageContainer {
    public boolean[] getMessageWithChecksumBitDump();
    public byte[] getMessageWithChecksumByteDump();

    public boolean[] getOnlyMessageBitDump();
    public byte[] getOnlyMessageByteDump();

    public boolean setMessageWithChecksumAndCheck(boolean[] messageWithChecksum);
    public boolean setMessageAndComputeChecksum(boolean[] messageWithoutChecksum);
}
