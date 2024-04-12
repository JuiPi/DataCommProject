
import java.util.Arrays;
import java.util.Random;
public class Q1 {
	public static Random rand = new Random();
	public static int wordSize = 8;
	public static int numWord = 4;
	 public static int numDataWords = 10;
	 public static int[][] generatesdataword() {
	        int[][] dataWords = new int[numWord][wordSize];
	        for (int i = 0; i < numWord; i++) {
	            for (int j = 0; j < wordSize; j++) {
	                dataWords[i][j] = rand.nextInt(2);
	            }
	        }
	        return dataWords;
	    }
	
	public static int[][] generateevenparity(int[][] datawords, int wordSize, int numWord) {
	    int[][] codeword = new int[numWord + 1][wordSize + 1];

	    for (int i = 0; i < numWord; i++) {
	        System.arraycopy(datawords[i], 0, codeword[i], 0, wordSize);
	    }

	    for (int i = 0; i < numWord; i++) {
	        int parity = 0;
	        for (int j = 0; j < wordSize; j++) {
	            parity ^= datawords[i][j];
	        }
	        codeword[i][wordSize] = parity; 
	    }

	    
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
	    int[] rowsyndrome = new int[numWord + 1];
	    int[] colsyndrome = new int[wordSize + 1];
	    int[][] rowErrorloc = new int[numWord][wordSize];

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
	        if (rowsyndrome[i] == 1) {
	            rowerrorcount++;
	            System.out.println("Error occur at row: " + (i + 1));
	        }

	    }

	    int colerrorcount = 0;
	    for (int i = 0; i < colsyndrome.length; i++) {
	        if (colsyndrome[i] == 1) {
	            colerrorcount++;
	            System.out.println("Error occur at col: " + (i + 1));
	        }
	    }

	    if (rowerrorcount == 0 && colerrorcount == 0) {
	        System.out.println("No error occurred; data accepted.");
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
		int[][][] allDataWords = new int[numDataWords][numWord][wordSize];

        for (int l = 0; l < numDataWords; l++) {
        	System.out.println("Dataword " + (l + 1));
            int[][] currentData = generatesdataword();
            for (int i = 0; i < numWord; i++) {
            	
                for (int j = 0; j < wordSize; j++) {
                    allDataWords[l][i][j] = currentData[i][j];
                    
                    System.out.print(allDataWords[l][i][j]+" ");
                }
                System.out.println();
            }
            int[][] codeword = generateevenparity(currentData, wordSize, numWord);
            // Print codewords
            System.out.println("generate parity ");
            for (int i = 0; i < numWord + 1; i++) {
                for (int j = 0; j < wordSize + 1; j++) {
                    System.out.print(codeword[i][j] + " ");
                }
                System.out.println();
            }
            int[][] checkpirityerror = checkparitybit(codeword);
            System.out.println("-----------------");
        }
        
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
            int row = rand.nextInt(4);
            int col = rand.nextInt(8);
            System.out.println("errordata");
            codeword[row][col] = 1 - codeword[row][col];
            System.out.println("Random error generated at row: " + (row + 1) + ", col: " + (col + 1));
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
