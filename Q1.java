
import java.util.Arrays;
import java.util.Random;
public class Q1 {
	public static Random rand = new Random();
	public static int wordSize = 8;
	public static int numWord = 4;
	 public static int numDataWords = 10;
	 //make generate randomdataword
	 public static int[][] generatesdataword() {
	        int[][] dataWords = new int[numWord][wordSize];
	        for (int i = 0; i < numWord; i++) {
	            for (int j = 0; j < wordSize; j++) {
	                dataWords[i][j] = rand.nextInt(2); //2 mean random data will have only [0-1] 
	            }
	        }
	        return dataWords;
	    }
	
	public static int[][] generateevenparity(int[][] datawords, int wordSize, int numWord) {
	    int[][] codeword = new int[numWord + 1][wordSize + 1]; // add size for paritybit row and column

	    for (int i = 0; i < numWord; i++) {
	        System.arraycopy(datawords[i], 0, codeword[i], 0, wordSize); 
			// copy data from input to be store in new array that want to store parity
	    }
// do parity for row
	    for (int i = 0; i < numWord; i++) {
	        int parity = 0;
	        for (int j = 0; j < wordSize; j++) {
	            parity ^= datawords[i][j];
				// use xor to check whether it 0 or 1 should be parity if out put is 1 mean 1 is odd then add 1
	        }
	        codeword[i][wordSize] = parity; 
	    }

	// do parity for col    
	    for (int i = 0; i < wordSize; i++) {
	        int parity = 0; 
	        for (int j = 0; j < numWord; j++) {
	            parity ^= datawords[j][i];
	        }
	        codeword[numWord][i] = parity; 
	    }

	    
	    return codeword;
	}

	public static int[][] checkparitybit(int[][] codeword) {
		// makesyndrome to check each row and column
	    int[] rowsyndrome = new int[numWord + 1];
	    int[] colsyndrome = new int[wordSize + 1];


	    for (int i = 0; i < numWord; i++) {
	        int syndrome = 0;
	        for (int j = 0; j < wordSize + 1; j++) {
	            syndrome ^= codeword[i][j];
	        }
	        rowsyndrome[i] = syndrome;
	    }

	    for (int i = 0; i < wordSize; i++) {
	        int syndrome = 0;
	        for (int j = 0; j < numWord + 1; j++) {
	            syndrome ^= codeword[j][i];
	        }
	        colsyndrome[i] = syndrome;
	    }
	    int rowerrorcount = 0;
	    for (int i = 0; i < rowsyndrome.length; i++) {
	        if (rowsyndrome[i] == 1) { //check if any row have error because for error check if no error ot will return 0
	            rowerrorcount++; 
	            System.out.println("Error occur at row: " + (i + 1)); // tell that which row have error occur
	        }

	    }

	    int colerrorcount = 0;
	    for (int i = 0; i < colsyndrome.length; i++) {
	        if (colsyndrome[i] == 1) {
	            colerrorcount++;
	            System.out.println("Error occur at col: " + (i + 1));// tell that which col have error occur
	        }
	    }

	    if (rowerrorcount == 0 && colerrorcount == 0) {
	        System.out.println("No error occurred; data accepted.");
			//if no error will print this message
	    } else if (rowerrorcount == 0 && colerrorcount != 0) {
	        System.out.println("Error(s) occurred in column(s) only.");
	    } else if (rowerrorcount != 0 && colerrorcount == 0) {
	        System.out.println("Error(s) occurred in row(s) only.");
	    } else {
	        System.out.println("Error(s) occurred in both row(s) and column(s).");
	    }
	    return codeword;
	}
	
	public static void main(String[]args) {
		System.out.println("Correct Data");
		//make 3D data to be store all of generate 2D dataword
		int[][][] allDataWords = new int[numDataWords][numWord][wordSize];

        for (int l = 0; l < numDataWords; l++) {
        	System.out.println("Dataword " + (l + 1));
            int[][] currentData = generatesdataword(); //set to know the current dataword
            for (int i = 0; i < numWord; i++) {
            	
                for (int j = 0; j < wordSize; j++) {
                    allDataWords[l][i][j] = currentData[i][j];
                    
                    System.out.print(allDataWords[l][i][j]+" ");
                }
                System.out.println();
            }
            int[][] codeword = generateevenparity(currentData, wordSize, numWord); // use currentdata to generate parity and store in codeword
            // Print codewords
            System.out.println("generate parity ");
            for (int i = 0; i < numWord + 1; i++) {
                for (int j = 0; j < wordSize + 1; j++) {
                    System.out.print(codeword[i][j] + " ");
                }
                System.out.println();
            }
            int[][] checkpirityerror = checkparitybit(codeword);//check error for codeword that already generated 
            System.out.println("-----------------");
        }
        //make 3D data to be store all of generate 2D dataword which is incorrect
        System.out.println("Incorrect data");
        int[][][] allincorrectDataWords = new int[numDataWords][numWord][wordSize];

        for (int l = 0; l < numDataWords; l++) {
        	System.out.println("Dataword " + (l + 1)+" :");
            int[][] currentData = generatesdataword();
            for (int i = 0; i < numWord; i++) {
            	
                for (int j = 0; j < wordSize; j++) {
                	allincorrectDataWords[l][i][j] = currentData[i][j];
                    
                    System.out.print(allincorrectDataWords[l][i][j]+" ");
                }
                System.out.println();
            }
            int[][] codeword = generateevenparity(currentData, wordSize, numWord);
            // Print codewords
            System.out.println("generate parity: ");
            for (int i = 0; i < numWord + 1; i++) {
                for (int j = 0; j < wordSize + 1; j++) {
                    System.out.print(codeword[i][j] + " ");
                }
                System.out.println();
            }
			// adjust the scenario that error occur when transmin if some dataword have error
            int row = rand.nextInt(4);
            int col = rand.nextInt(8);
			// the length that will occur will be the size of 4*8 or dataword
            System.out.println("errordata");
            codeword[row][col] = 1 - codeword[row][col]; // this line set the number to be only 0-1
            System.out.println("Random error generated at row: " + (row + 1) + ", col: " + (col + 1)); 
			// +1 for that the row start at 0 and column start at 0
            for (int i = 0; i < numWord + 1; i++) {
                for (int j = 0; j < wordSize + 1; j++) {
                    System.out.print(codeword[i][j] + " ");
                }
                System.out.println();
            }
            int[][] checkpirityerror = checkparitybit(codeword);
            System.out.println("-----------------");
        
        
        
    }}
}
