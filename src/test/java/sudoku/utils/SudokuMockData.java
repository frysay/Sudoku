package sudoku.utils;

import model.Sudoku;

public class SudokuMockData {

	public static Sudoku createSudokuMockData() {
		
		Sudoku sudoku = new Sudoku();
		sudoku.setTable(new int[][]{
			{7,9,2,1,4,6,5,3,8},
			{4,6,5,2,3,8,7,1,9},
			{3,1,8,5,7,9,0,0,2},//6,4
			{5,3,9,8,6,4,2,7,1},
			{2,7,6,0,1,3,4,8,5},//9
			{8,4,1,7,2,5,9,6,3},
			{9,5,7,4,8,1,3,2,0},//6
			{1,2,3,6,5,7,8,9,4},
			{6,8,4,3,9,2,1,5,7}
		});
		sudoku.setValuesLeft(4);		
		sudoku.setLastMove(new int[]{1,1});
		
		return sudoku;
	}

	public static Sudoku createSudokuMockDataOver() {
		Sudoku sudoku = new Sudoku();
		sudoku.setTable(new int[][]{
			{7,9,2,1,4,6,5,3,8},
			{4,6,5,2,3,8,7,1,9},
			{3,1,8,5,7,9,6,4,2},
			{5,3,9,8,6,4,2,7,1},
			{2,7,6,9,1,3,4,8,5},
			{8,4,1,7,2,5,9,6,3},
			{9,5,7,4,8,1,3,2,6},
			{1,2,3,6,5,7,8,9,4},
			{6,8,4,3,9,2,1,0,7}//5
		});
		sudoku.setValuesLeft(1);		
		sudoku.setLastMove(new int[]{1,1});
		
		return sudoku;
	}
}
