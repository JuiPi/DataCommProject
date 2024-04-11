import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class Q3 {

    private static int dataword_size = 8;
    
    public static String convertStringToBinary(String string) {
        StringBuilder binaryString = new StringBuilder();
        char[] chars = string.toCharArray();

        for (char c : chars) {
            binaryString.append(String.format("%8s", Integer.toBinaryString(c)).replace(" ", ""));
            binaryString.append(" ");
        }

        return binaryString.toString();
    }
    
    public static void Checksum_gen() {
        
    }



    public static void main(String[] args) throws FileNotFoundException {
        // String file_path = "./File1.txt";
        // try {
        //     File file = new File(file_path);
        //     Scanner scanner = new Scanner(file);
        //     String data = "";

        //     while(scanner.hasNextLine()) {
        //         data += scanner.nextLine();
        //     }
        //     System.out.println(data);
        //     scanner.close();

        // } catch (FileNotFoundException e) {
        //     // TODO: handle exception
        //     throw new FileNotFoundException(file_path + "not found");
        // }


        System.out.println(convertStringToBinary("Hello World"));
    }
}
