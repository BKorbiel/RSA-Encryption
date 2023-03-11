import java.util.*;
import java.math.BigInteger;

public class RSA {
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
      BigInteger p = GetRandomNumber();
      BigInteger q = GetRandomNumber();
	  }


    //function returns random hexadecimal odd number with a certain number of bits (512)
    static BigInteger GetRandomNumber() {
      Random random = new Random();

      int numberOfBits = 512;
      String[] symbols = {"0", "1"};

      String result = "1"; //number has to start with 1
      for (int i=0; i<numberOfBits-2; i++) {
        result+=symbols[random.nextInt(2)];
      }
      result+="1"; //number has to be odd

      return new BigInteger(result, 2);
    }


    //function checks whether the number is prime with the Miller-Rabin algorithm
    static boolean isPrime(BigInteger number) {
      Random random = new Random();
      int numberOfTests = 30; //the chance that algorithm gives wrong answer is less than (1/4)^30 < 1/(10^18)

      BigInteger numberMinusOne = number.subtract(BigInteger.ONE);

      BigInteger d = number.subtract(BigInteger.ONE);
      int s = 0;
      while (d.mod(BigInteger.TWO).compareTo(BigInteger.ZERO)!=0) { //while d%2==0
        s+=1;
        d = d.divide(BigInteger.TWO);
      }
      
      for (int i=0; i<numberOfTests; i++) {
        BigInteger base;
        do {
            base = new BigInteger(511, random); //choosing the base
        } while (base.compareTo(BigInteger.ONE)!=1 || base.compareTo(numberMinusOne)!=-1); //base must be greater than 1 and less than number-1

        BigInteger x = base.modPow(d, number); // x = (a^d)%number, it is the first term of a sequence 

        if (x.compareTo(BigInteger.ONE)==0 || x.compareTo(numberMinusOne)==0) {
          continue;   //if (x==1 || x==number-1) then continue;
        }

        int j=1;
        while (j<s && x.compareTo(numberMinusOne)!=0) { //j<s && x!=number-1
          
          x = x.modPow(BigInteger.TWO, number); //x=(x^2)%number, the next term of a sequence

          if (x.compareTo(BigInteger.ONE)==0) { //if x==1 then it's not prime
            return false;
          }

          j++;
        }
        if (x.compareTo(numberMinusOne)!=0) { //if x!=p-1 then it is not prime
          return false;
        }
      }
      return true;
    }
}