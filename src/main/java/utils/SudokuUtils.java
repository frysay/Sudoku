package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuUtils {
	private static final Logger logger = LoggerFactory.getLogger(SudokuUtils.class);

	public static boolean isPossibleMove(int[][] table, int value, int i, int j) {

		boolean valid = true;

		// check if it's a value contained in sudoku domain
		if(value > 9 || value < 1) {
			return false;
		}

		//check row and column
		for (int rowColumn = 0; rowColumn < 9; rowColumn++) {
			if(valid) {
				valid = table[i][rowColumn] != value && table[rowColumn][j] != value;
			} else {
				logger.debug("This move violete the row/column constraints");
				return false;
			}
		}
		logger.debug("This move is allowed by the row/column constraints");

		//check 3*3 square
		for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++) {
			for (int column = (j / 3) * 3; column < (j / 3) * 3 + 3; column++) {
				if(valid) {
					valid = table[row][column] != value;
				} else {
					logger.debug("This move violete the 3*3 constraints");
					return false;
				}
			}
		}
		logger.debug("This move is allowed by the 3*3 constraints as well and can be set");
		return true;		
	}

	public static boolean isDeadLockGame(int[][] table, int valuesLeft) {
		int[][] tmpTable = new int[9][9];
		for(int i = 0; i< 9; i++) {
			for(int j = 0; j< 9; j++) {
				tmpTable[i][j] = table[i][j];				
			}
		}
		int counter = valuesLeft;
		while(counter > 0) {
			int value = 1;
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) {
					if(tmpTable[i][j] == 0) {
						while(value < 10 && !isPossibleMove(tmpTable, value, i, j)) {
							value++;
						}
						if(value < 10) {
							tmpTable[i][j] = value;
							counter--;
							value = 1;
						} else {
							logger.debug("There aren't any possible solutions left after this move");
							return true;
						}
					}
				}

			}
		}
		logger.debug("This move doesn't lead to a lost game");
		return false;
	}
}
