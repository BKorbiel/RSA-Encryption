import java.math.BigInteger;

public class Message {
    String decryptedMessage;
    String encryptedMessage;
    int numberOfBits;
    Keys keys;

    public Message(int bits, String message, boolean isEncrypted, Keys keys) {
        numberOfBits = bits;
        this.keys = keys;
        if (isEncrypted) {
            encryptedMessage = message;
        } else {
            decryptedMessage = message;
        }
    }

    public void EncryptMessage() {
        /* 
         * The idea is to convert each character to its ASCII code and then concatenate them into a single hexadecimal number.
         * 
         * The number cannot be greater than n, so we need to divide the message into blocks with a maximum length of 
         * numberOfBits*2/8-1  (because length of n is numberOfBits*2 bits and each character in message takes 8 bits, 
         * so the message that is (numberOfBits*2/8-1) characters long, when converted to a number, 
         * will definitely not be greater than n.)
         * 
         * Each block will be encrypted separately, and then we will combine them into a single encrypted message. 
         * Blocks in the encrypted message will be separated by spaces to be able to decrypt them later.
        */

        String result=""; 
        int index=0;
        while (index<decryptedMessage.length()) {

            //Creating single block as a hexadecimal number
            String block = "";
            for (int i=0; i<numberOfBits*2/8-1 && index<decryptedMessage.length(); i++) {
                String hex = Integer.toHexString((int) decryptedMessage.charAt(index));
                if (hex.length()==1) {
                    hex="0"+hex; //Each character code must have the same length (2) in order to be able to decode the message later.
                }
                block+=hex;
                index++;
            }
            result+=EncryptBlock(block);
            result+=" ";

        }
        encryptedMessage = result;
    }

    String EncryptBlock(String block) {
        // result = (block^e)%n

        BigInteger result = new BigInteger(block, 16);
        result = result.modPow(keys.publicKeyE, keys.publicKeyN);
        return result.toString(16);
    }
}