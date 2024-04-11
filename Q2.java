import java.util.HashMap;
import java.util.Scanner;

public class Q2 {
    
    static HashMap<String, String> crcDivisors = new HashMap<String, String>();
    
    static {
        crcDivisors.put("CRC-4", "11111");
        crcDivisors.put("CRC-8", "111010101");
        crcDivisors.put("RCRC-16", "10100000000000011");
        crcDivisors.put("CRC-16", "11000000000000101");
        crcDivisors.put("CRC-24", "1100000000101000100000001");
        crcDivisors.put("CRC-32", "100000100110000010001110110110111");
    }

    // Returns XOR of 'a' and 'b'
    // (both of same length)
    // Reference from GeeksforGeeks
    static String Xor(String a, String b) { 
 
        // Initialize result
        String result = "";
        int n = b.length();
 
        // Traverse all bits, if bits are
        // same, then XOR is 0, else 1
        for (int i = 1; i < n; i++) {
            if (a.charAt(i) == b.charAt(i))
                result += "0";
            else
                result += "1";
        }
        return result;
    }
 
    // Performs Modulo-2 division
    // Reference from GeeksforGeeks
    static String Mod2Div(String dividend, String divisor) {
 
        // Number of bits to be XORed at a time.
        int pick = divisor.length();
 
        // Slicing the dividend to appropriate
        // length for particular step
        String tmp = dividend.substring(0, pick);
 
        int n = dividend.length();
 
        while (pick < n) {
            if (tmp.charAt(0) == '1')
 
                // Replace the dividend by the result
                // of XOR and pull 1 bit down
                tmp = Xor(divisor, tmp)
                      + dividend.charAt(pick);
            else
 
                // If leftmost bit is '0'.
                // If the leftmost bit of the dividend (or
                // the part used in each step) is 0, the
                // step cannot use the regular divisor; we
                // need to use an all-0s divisor.
                tmp = Xor(new String(new char[pick])
                              .replace("\0", "0"),
                          tmp)
                      + dividend.charAt(pick);
 
            // Increment pick to move further
            pick += 1;
        }
 
        // For the last n bits, we have to carry it out
        // normally as increased value of pick will cause
        // Index Out of Bounds.
        if (tmp.charAt(0) == '1')
            tmp = Xor(divisor, tmp);
        else
            tmp = Xor(new String(new char[pick])
                          .replace("\0", "0"),
                      tmp);
 
        return tmp;
    }


    public static String CRC_gen(String dataword, int word_size, String CRC_type) {
        String divisor = crcDivisors.get(CRC_type);
        if(divisor == null) throw new IllegalArgumentException("Unsupported CRC Type");

        String paddedDataword = dataword + ("0".repeat(divisor.length() - 1));

        String remainder = Mod2Div(paddedDataword, divisor);

        String codeWord = dataword + remainder;

        return codeWord;
    }

    public static String CRC_check(String codeword, String CRC_type) {
        //TO-DO
        String divisor = crcDivisors.get(CRC_type);
        String syndrome = Mod2Div(codeword, divisor);
        return syndrome;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // System.out.println("Enter Dataword:");
        // String dataword = scanner.nextLine();
        // int word_size = dataword.length() - 1;
        // System.out.print("Enter CRC-type:");
        // String CRC_type = scanner.nextLine();

        String dataword = "101";
        int word_size = dataword.length() - 1;
        String CRC_type = "CRC-4";
        
        String codeword = CRC_gen(dataword, word_size, CRC_type);

        System.out.println("Codedword: " + codeword + 
                           "\nSyndrome: " + CRC_check(codeword, CRC_type));
    }
}
