import java.util.HashMap;
import java.util.Scanner;

public class Q2 {
    
    // hashmap of the crc polynomial
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

    // generate codeword with crc
    public static String CRC_gen(String dataword, int word_size, String CRC_type) {
        String divisor = crcDivisors.get(CRC_type); // get the divisor of crc type from the hashmap
        if(divisor == null) throw new IllegalArgumentException("Unsupported CRC Type");

        String paddedDataword = dataword + ("0".repeat(divisor.length() - 1));  // add extra 0 at the end of dataword

        String remainder = Mod2Div(paddedDataword, divisor);    // divide using xor and get the remainder

        String codeWord = dataword + remainder; // add the remainder at the end of the codeword

        return codeWord;
    }

    //
    public static String CRC_check(String codeword, String CRC_type) {
        String divisor = crcDivisors.get(CRC_type);
        String syndrome = Mod2Div(codeword, divisor);
        return syndrome;
    }


    public static void main(String[] args) {

        String samples[][] = {
            {"1011", "CRC-4"},
            {"0110" ,"CRC-4"},
            {"10101100", "CRC-8"},
            {"11010111", "CRC-8"},
            {"1010101010101010", "CRC-16"},
            {"1111000011110000", "CRC-16"},
            {"0101010110101010", "RCRC-16"},
            {"0000111100001111", "RCRC-16"},
            {"110011001100110011001100", "CRC-24"},
            {"101010101010101010101010", "CRC-24"},
            {"11110000111100001111000011110000", "CRC-32"},
            {"00110011001100110011001100110011", "CRC-32"}
        };

        int i = 1;
        for (String[] data : samples) {
            System.out.println("-------------------- Sample[" + i + "]--------------------");
            String dataword = data[0];
            String CRC_type = data[1];
            int word_size = data.length - 1;
            String codeword = CRC_gen(dataword, word_size, CRC_type);
            String CRC = codeword.substring(codeword.length() - dataword.length());
            String syndrome = CRC_check(codeword, CRC_type);
            System.out.println("Dataword: " + dataword + 
                                "\nCRC-Type: " + CRC_type +
                                "\nCRC: " + CRC +
                                "\nCodeword: " + codeword +
                                "\nSyndrome: " + syndrome + "\n");
            i++;
        }

        String erroneous_samples[][] = {
            {"0101010101010101", "CRC-4"},
            {"1010110011001100", "CRC-4"}
        };

        String erroneous_samples_error[][] = {
            {"0101010101001010", "CRC-4"},
            {"1010001101001100", "CRC-4"}
        };

    for (i = 0; i < erroneous_samples.length; i++) {
        System.out.println("\n-------------------- Erroneous Sample[" + (i + 1) + "]--------------------");
        String dataword = erroneous_samples[i][0];
        String CRC_type = erroneous_samples[i][1];
        int word_size = erroneous_samples[i].length - 1;
        String codeword = CRC_gen(dataword, word_size, CRC_type);
        String CRC = codeword.substring(codeword.length() - dataword.length());

        // Erroneous: 5 bits change
        dataword = erroneous_samples_error[i][0];

        String syndrome = CRC_check(codeword, CRC_type);
        System.out.println("Dataword: " + dataword + 
                            "\nCRC-Type: " + CRC_type +
                            "\nCRC: " + CRC +
                            "\nCodeword: " + codeword +
                            "\nSyndrome: " + syndrome + "\n");



    }






        
    }
}
