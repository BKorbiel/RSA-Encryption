package RSAemail.RSA;
import java.math.BigInteger;
import java.util.Random;

public class Keys {
    BigInteger publicKeyN;
    BigInteger publicKeyE;
    BigInteger privateKeyD;
    int numberOfBits;

    public Keys(int bits) {
        this.numberOfBits = bits;
    }

    void Generate() {
        //generating keys
        BigInteger p = GetRandomOddNumber();
        while (!isPrime(p)) {
            p = p.add(BigInteger.TWO); //p is not prime, so we take the next odd number to check it
        }
        BigInteger q = GetRandomOddNumber();
        while (!isPrime(q) || q.compareTo(p)==0) {
            q = q.add(BigInteger.TWO); //q is not prime or it is equal to p, so we take the next odd number to check it
        }
        BigInteger n = p.multiply(q); //n = p*q
        BigInteger euler = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); //euler = (p-1) * (q-1) 

        BigInteger e = new BigInteger("3");
        while (GCD(e, euler).compareTo(BigInteger.ONE)!=0) { //We are looking for number e that satisfies gcd(e, euler) = 1
            e = e.add(BigInteger.TWO); //euler is even, so e is odd for sure - that's why we are incrementing by 2
        }
        //we have public key now - n and e

        //for private key we need number d that satisfies (d*e)%euler = 1
        BigInteger d = ModularMltiplicativeInverse(e, euler);
        //private key: d

        this.publicKeyN = n;
        this.publicKeyE = e;
        this.privateKeyD = d;
    }

    //function returns random odd number with a certain number of bits (512)
    BigInteger GetRandomOddNumber() {
        Random random = new Random();

        String[] symbols = {"0", "1"};

        String result = "1"; //number has to start with 1
        for (int i=0; i<this.numberOfBits-2; i++) {
            result+=symbols[random.nextInt(2)];
        }
        result+="1"; //number has to be odd

        return new BigInteger(result, 2);
    }
  
  
    //function checks whether the number is prime with the Miller-Rabin algorithm
    boolean isPrime(BigInteger number) {
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
                base = new BigInteger(this.numberOfBits-1, random); //choosing random base
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
  
    //function finds gcd of two integers with Euclidean algorithm
    BigInteger GCD(BigInteger a, BigInteger b) {
        if (a.compareTo(BigInteger.ZERO)==0) {
            return b;
        } else {
            return GCD(b.mod(a), a);
        }
    }
  
    //this functions finds the modular multiplicative inverse by extended Euclidean algorithm
    BigInteger ModularMltiplicativeInverse(BigInteger a, BigInteger b) {

        BigInteger w = a.add(BigInteger.ZERO); //w=a;
        BigInteger z = b.add(BigInteger.ZERO); //z=b;
        BigInteger u = new BigInteger("1");
        BigInteger x = new BigInteger("0");
        BigInteger q;

        while (w.compareTo(BigInteger.ZERO)!=0){ //while w!=0
            if (w.compareTo(z)==-1) { // w < z

            //we are swaping x and u with xors
            u = u.xor(x);
            x = u.xor(x);
            u = u.xor(x);
            
            //swaping w and z
            w = w.xor(z);
            z = w.xor(z);
            w = w.xor(z);
            }

            q = w.divide(z);
            u = u.subtract(q.multiply(x)); //u = u-q*x 
            w = w.subtract(q.multiply(z)); //w = w-q*z
        }
        if (x.compareTo(BigInteger.ZERO)==-1) { // x < 0
            x = x.add(b);
        }
        return x;
    }
}
