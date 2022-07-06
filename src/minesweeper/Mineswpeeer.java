/**
 * @author Harrison McDougall
 * took about 4 and a half hours to complete
 */
package minesweeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Mineswpeeer {
	private static ArrayList<char[][]> myFields = new ArrayList<char[][]>();

	//main that runs
	public static void main(String[] args) throws FileNotFoundException {
		int n, m;
		Scanner fieldIn;
		if(args[0].equalsIgnoreCase("file")) {
			File in = new File("minesweeper_input.txt");
			fieldIn = new Scanner(in);
		}
		else {
			fieldIn = new Scanner(System.in);
		}		
		String input = fieldIn.nextLine();
		Scanner inputReader = new Scanner(input);	
		n = inputReader.nextInt();
		m = inputReader.nextInt();
		inputReader.close();	//closes this scanner for now, so that it's not taking memory while other things are going on
		while(n != 0 && m != 0) {
			char[][] temp = createMinefield(n, m, fieldIn);
			myFields.add(createHints(n, m, temp));
			input = fieldIn.nextLine();
			inputReader = new Scanner(input);
			n = inputReader.nextInt();
			m = inputReader.nextInt();
			inputReader.close();
		}
		//using a standard for loop to go through each of the hint fields generated rather than a for each loop so we can use i to track the field number
		for(int i = 1; i <= myFields.size(); i++) {
			System.out.println("Field #" + i +":");
			for(int j = 0; j < myFields.get(i-1).length; j++) {
				String temp = Arrays.toString(myFields.get(i-1)[j]);
				temp = temp.replace("[","");
				temp = temp.replace("]","");
				temp = temp.replace(",","");
				temp = temp.replace(" ","");
				System.out.println(temp);
			}
		}
	}

	//method that creates the actual minefield which is then used to created the hints
	private static char[][] createMinefield(final int theN, final int theM, final Scanner theInput) {
		char[][] temp = new char[theN][theM];
		char[][] result = new char[theN+2][theM+2];
		String input;
		for(int i = 0; i < theN; i++) {
			input = theInput.nextLine();
			temp[i] = input.toCharArray();
		}
		//nested for loop that transfers the initially created minefield array to the new one,so that there is a buffer around the edge of the actual field
		//so the code for for generating hints
		for(int i = 1; i <= temp.length; i++) {
			for(int j = 1; j <= temp[i-1].length; j++){
				result[i][j] = temp[i-1][j-1];
			}
		}
		return result;
	}

	//methods that creates the minefield full of hints and the bombs.
	private static char[][] createHints(final int theN, final int theM, final char[][] theMine) {
		char[][] hints = new char[theN][theM];	//the final 2d array which is the hints and the bombs, does not a buffer itself since it's not need
		//nested for loop that's for going through every tile in the minefield, excluding the buffers around them
		for(int i = 1; i <= theN; i++) {
			for(int j = 1; j <= theM; j++) {
				int counter = 0;
				//condition so that this will generate a number for the hints if the tilt isn't a bomb since there would be no point in doing so if we're not going to use it
				if(theMine[i][j] == '*') {
					hints[i-1][j-1] = '*';
				}
				else {
					//nested for loop that goes and checks the 8 tiles around the current looked at tile in theMine 2d array, to count the number of mines there are
					for(int k = -1; k < 2; k++) {
						for(int l = -1; l < 2; l++) {
							if(theMine[i+k][j+l] == '*') {
								counter++;
							}
						}
					}
					hints[i-1][j-1] = (char) (counter + 48); //stores the value of counter in the hint field
				}
			}
		}
		return hints;
	}

}
