import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Q3 {

    private static int dataword_size = 8;

    // read file using scanner
    public static String readFile(String file_path) throws FileNotFoundException {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(file_path);
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            System.out.println("Data: " + data);
            scanner.close();

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(file_path + " not found :(");
        }
        return data.toString();
    }

    // convert text to binary string
    public static ArrayList<String> convertDataToBinary(String data) {
        ArrayList<String> binaryString = new ArrayList<String>();
        char[] chars = data.toCharArray();  // split every characters and put one chracter in array of char

        for (char c : chars) {
            StringBuilder binaryChar = new StringBuilder(String.format(Integer.toBinaryString(c)).replace(" ", ""));    // use stringbuilder to convert char to binary
            if(binaryChar.length() < dataword_size) {   // add 0 in front of binary string in case of data is not 8 bits
                binaryChar.insert(0, "0".repeat(dataword_size - binaryChar.length()));
            }
            binaryString.add(binaryChar.toString());    // add a char string into arraylist
        }
        return binaryString;
    }

    // summation of binary in an arraylist
    public static String sumBinary(ArrayList<String> binaryData) {
        int sum = 0;
        for (String e : binaryData) {
            sum += Integer.parseInt(e, 2);  // convert binary to int and sum it all
        }
        String result = Integer.toBinaryString(sum);    // convert sum back to binary 

        // in case of result over 8 bits
        while(result.length() > dataword_size) {
            sum = 0;
            String carry = result.substring(0, result.length() - dataword_size);    // extract binary that is over 8 bits
            result = result.substring(result.length() - dataword_size);     // extract binary in 8 bits

            // sum carry and result
            sum += Integer.parseInt(carry, 2);
            sum += Integer.parseInt(result, 2);
            result = Integer.toBinaryString(sum);
        }

        // add 0 in front of binary string in case of data is not 8 bits
        if(result.length() < dataword_size) {
            String head = "0".repeat(dataword_size - result.length());
            result = head + result;
        }

        return result;
    }

    // complement binary
    public static String complementBinary(String binaryNumber) {
        StringBuilder complement = new StringBuilder();
        for (int i = 0; i < binaryNumber.length(); i++) {
            char bit = binaryNumber.charAt(i);
            if (bit == '0') {
                complement.append('1');
            } else if (bit == '1') {
                complement.append('0');
            } else {
                return "Invalid binary number";
            }
        }
        return complement.toString();
    }

    // check whether the data is correct or not
    public static void checkValidity(String validity) {
        if(validity.equals("11111111")) {
            System.out.println("    >>> PASS: No Error Detected\n");
        }
        else {
            System.out.println("    >>> FAIL: Error Detected\n");
        }
    }
    
    // generate codeword of the data in text file and add checksum
    public static ArrayList<String> Checksum_gen(String file_path) throws FileNotFoundException {
        System.out.println("-------------------- Sender --------------------");
        String data = readFile(file_path);

        ArrayList<String> binaryData = convertDataToBinary(data);
        System.out.println("BinaryData: " + binaryData);
        
        String sum = sumBinary(binaryData);
        System.out.println("Sum: " + sum);

        String checkSum = complementBinary(sum);
        System.out.println("CheckSum: " + checkSum + "\n");

        binaryData.add(checkSum);
        return binaryData;
    }

    // check whether the data (receiver) is the same as the one generate checkSum (sender)
    public static String Checksum_check(String file_path, String checkSum) throws FileNotFoundException {
        System.out.println("-------------------- Checker --------------------");
        String data = readFile(file_path);

        ArrayList<String> binaryData = convertDataToBinary(data);
        binaryData.add(checkSum);   // add checksum in to data arraylist
        System.out.println("BinaryData&Checksum(lastindex): " + binaryData);

        String validity = sumBinary(binaryData);
        System.out.println("Validity: " + validity + "\n");

        return validity;
    }

    public static void main(String[] args) throws FileNotFoundException{
        String file1_path = "./File1.txt";
        String file2_path = "./File2.txt";

        ArrayList<String> codeword = Checksum_gen(file1_path);
        String checkSum = codeword.get(codeword.size() - 1);

        String validity1 = Checksum_check(file1_path, checkSum);
        checkValidity(validity1);

        String validity2 = Checksum_check(file2_path, checkSum);
        checkValidity(validity2);
    }
}
