import java.util.*;
import java.math.BigInteger;

public class RSA {
  public static int numberOfBits = 1024;
  public static void main(String args[]) {
    Scanner sc=new Scanner(System.in);
    while (true) {
            System.out.println("\n\nChoose:");
            System.out.println("1. Generate public and private key");
            System.out.println("2. Encrypt message");
            System.out.println("3. Decrypt message");
            System.out.println("4. Exit");
            int selectedOption = sc.nextInt();
            System.out.println("============================");
            switch(selectedOption) {
                case 1:
                  GenerateKeys();
                  break;
                case 2:
                  EncryptMessage();
                  break;
                case 3:
                  // code block
                  break;
                case 4:
                  sc.close();
                  return;
                default:
                    System.out.println("\nIncorrect input");
                    System.out.println("\n========================================\n\n");
            }
        }
    }

    static void GenerateKeys() { //generates public and private keys

      Keys keys = new Keys(numberOfBits);
      keys.Generate();

      System.out.println("\n\nYour public key:");
      System.out.println("N: " + keys.publicKeyN.toString(16)); 
      System.out.println("E: " + keys.publicKeyE.toString(16)); 
      System.out.println("\nYour private key:");
      System.out.println("D: " + keys.privateKeyD.toString(16));
      System.out.println("\nSave this data. Do not show the private key to anyone.");
      System.out.println("\n\n============================"); 
	  }

    static void EncryptMessage() {
      Scanner sc=new Scanner(System.in);
      System.out.println("\nMessage to encrypt:");
      String messageToEncrypt = sc.nextLine();
      
      Keys keys = new Keys(numberOfBits);
      System.out.println("\nEnter public key:");
      System.out.print("N: ");
      String str = sc.next();
      keys.publicKeyN = new BigInteger(str);
      System.out.print("E: ");
      str = sc.next();
      keys.publicKeyE = new BigInteger(str);
      
      Message message = new Message(numberOfBits, messageToEncrypt, false, keys);

      message.EncryptMessage();
    }
}
