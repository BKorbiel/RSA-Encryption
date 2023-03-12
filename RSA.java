import java.util.*;
import java.math.BigInteger;

public class RSA {
    public static int numberOfBits = 1024;
    public static void main(String args[]) {
        while (true) {
            Scanner sc=new Scanner(System.in);
            System.out.println("Choose:");
            System.out.println("1. Generate public and private key");
            System.out.println("2. Encrypt messege");
            System.out.println("3. Decrypt messege");
            System.out.println("4. Exit");
            int selectedOption = sc.nextInt();
            System.out.println("============================");
            switch(selectedOption) {
                case 1:
                  GenerateKeys();
                  break;
                case 2:
                  // code block
                  break;
                case 3:
                  // code block
                  break;
                case 4:
                  return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    static void GenerateKeys() { //generates public and private keys

      Keys keys = new Keys(numberOfBits);

      System.out.println("\n\nYour public key:");
      System.out.println("N: " + keys.publicKeyN.toString(16)); 
      System.out.println("E: " + keys.publicKeyE.toString(16)); 
      System.out.println("\nYour private key:");
      System.out.println("D: " + keys.privateKeyD.toString(16));
      System.out.println("\nSave this data. Do not show the private key to anyone.");
      System.out.println("\n\n============================");;
	  }
}
