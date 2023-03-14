import java.math.BigInteger;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

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
         * Each block will be encrypted separately, and then they will be combined into a single encrypted message. 
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

    public void DecryptMessage() {
        String result=""; 
        int index=0;
        while (index<encryptedMessage.length()) {
            String block="";
            while (index<encryptedMessage.length() && encryptedMessage.charAt(index)!=' ') {
                block+=encryptedMessage.charAt(index);
                index++;
            }
            String decryptedBlockHex = DecryptBlock(block);
            result += ConvertHexToMessage(decryptedBlockHex);

            index++;
        }
        decryptedMessage = result;
    }

    String DecryptBlock(String block) {
        // result = (block^d)%n

        BigInteger result = new BigInteger(block, 16);
        result = result.modPow(keys.privateKeyD, keys.publicKeyN);
        return result.toString(16);
    }

    //function converts a block from a string of hexadecimal ascii codes to string of characters
    String ConvertHexToMessage(String block) {
        String result="";
        int index = block.length()-1;
        while (index>0) {
            int characterCode = Integer.valueOf(block.charAt(index-1) + "" + block.charAt(index), 16);
            char character = (char)characterCode;
            result = character + result;
            index-=2;
        }
        if (index == 0) {
            int characterCode = Integer.valueOf(block.charAt(index)+"", 16);
            char character = (char)characterCode;
            result = character + result;
        }
        return result;
    }
}