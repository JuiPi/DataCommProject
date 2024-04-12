import java.util.Scanner;

public class Q4 {

    public static String convertToBinary(char alphabet) { //this method will generate any alphabet to binary
        int binary = (int) alphabet;
        String binString = Integer.toBinaryString(binary);
        while (binString.length() < 7) { //to make sure that the lenght of binary is 7 for be use when go to hammimg code
            binString = "0" + binString; // if less than 7 then add 0 at front
        }
        return binString;
    }

    public static String[] hammingGen(String binString) {
        String[] result = new String[11]; // set the result string to be 11bit
        char[] chars = binString.toCharArray(); //split to each character of the string that we collect
        for (int j = 0; j < chars.length; j++) { 
            for (int i = result.length - 1; i >= 0; i--) { // when hamming it will start from right to left
                if (i == 2 || i == 4 || i == 5 || i == 6 || i == 8 || i == 9 || i == 10) {
                     // from the code position that will add the data will be 3,5,6,7,9,10,11 but need to decrese 1 for it start from 0
                    result[i] = String.valueOf(chars[j]); //store in result array
                    j++;// increse to be detect result array use the same data
                }
            }
            // find r1 instead of xor we can convert them to be integer and sum them all and modulo to se the result, same concept will xor
            result[0] = String.valueOf((Integer.parseInt(result[2]) +
                    Integer.parseInt(result[4]) +
                    Integer.parseInt(result[6]) +
                    Integer.parseInt(result[8]) +
                    Integer.parseInt(result[10])) % 2);

            // find r2
            result[1] = String.valueOf((Integer.parseInt(result[2]) +
                    Integer.parseInt(result[5]) +
                    Integer.parseInt(result[6]) +
                    Integer.parseInt(result[9]) +
                    Integer.parseInt(result[10])) % 2);

            // find r4
            result[3] = String.valueOf((Integer.parseInt(result[4]) +
                    Integer.parseInt(result[5]) +
                    Integer.parseInt(result[6])) % 2);

            // find r8 
            result[7] = String.valueOf((Integer.parseInt(result[8]) +
                    Integer.parseInt(result[9]) +
                    Integer.parseInt(result[10])) % 2);
        }
        return result;
    }

    public static int hammingCheck(String codeword) {
        char[] chars = codeword.toCharArray();

        String[] result = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = String.valueOf(chars[i]);
        }
        

        //the recieve output this will check all position all see the error if error redundancy will be 1
        int receivedr1 = (Integer.parseInt(String.valueOf(chars[0])) +
                Integer.parseInt(String.valueOf(chars[2])) +
                Integer.parseInt(String.valueOf(chars[4])) +
                Integer.parseInt(String.valueOf(chars[6])) +
                Integer.parseInt(String.valueOf(chars[8])) +
                Integer.parseInt(String.valueOf(chars[10]))) % 2;

        int receivedr2 = (Integer.parseInt(String.valueOf(chars[1])) +
                Integer.parseInt(String.valueOf(chars[2])) +
                Integer.parseInt(String.valueOf(chars[5])) +
                Integer.parseInt(String.valueOf(chars[6])) +
                Integer.parseInt(String.valueOf(chars[9])) +
                Integer.parseInt(String.valueOf(chars[10]))) % 2;

        int receivedr4 = (Integer.parseInt(String.valueOf(chars[3])) +
                Integer.parseInt(String.valueOf(chars[4])) +
                Integer.parseInt(String.valueOf(chars[5])) +
                Integer.parseInt(String.valueOf(chars[6]))) % 2;

        int receivedr8 = (Integer.parseInt(String.valueOf(chars[7])) +
                Integer.parseInt(String.valueOf(chars[8])) +
                Integer.parseInt(String.valueOf(chars[9])) +
                Integer.parseInt(String.valueOf(chars[10]))) % 2;

        // Calculate the error position redundancy will return the string of binary then convert each position
        int errorPos = receivedr1 + 2 * receivedr2 + 4 * receivedr4 + 8 * receivedr8;
        if(errorPos == 0) {
        	return 0;
        }else {
        return 12-errorPos;
        //start from 0 to 10 but +1 for start from right
    }
        }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an alphabet");
        System.out.println("a-z or A-Z: ");
        char input = scanner.next().charAt(0);
        scanner.close();
        String bin = convertToBinary(input);
        System.out.println("Dataword: " + bin);
        String[] codeword = hammingGen(bin);
        System.out.print("Codeword: ");
        for (int i = codeword.length - 1; i >= 0; i--) {
            System.out.print(codeword[i]);
        }
        System.out.println();
// EX OF error of 'a'
        String incorrect = "11000010110";
        System.out.println("Incorrect codeword: " + incorrect);
        System.out.println("Error position: " + hammingCheck(incorrect));
    }
}

