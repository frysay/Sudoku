package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import utils.SudokuUtils;

public class Sudoku {

	private static final Logger logger = LoggerFactory.getLogger(Sudoku.class);

	private int[][] table;

	@JsonIgnore
	private int valuesLeft;

	@JsonIgnore
	private int[] lastMove;

	public Sudoku() {
		this.table = new int[][]{
			{7,0,0,0,4,0,5,3,0},
			{0,0,5,0,0,8,0,1,0},
			{0,0,8,5,0,9,0,4,0},
			{5,3,9,0,6,0,0,0,1},
			{0,0,0,0,1,0,0,0,5},
			{8,0,0,7,2,0,9,0,0},
			{9,0,7,4,0,0,0,0,0},
			{0,0,0,0,5,7,0,0,0},
			{6,0,0,0,0,0,0,5,0}
		};

		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				this.valuesLeft = this.table[i][j] == 0 ? this.valuesLeft + 1 : this.valuesLeft;
			}
		}

		this.lastMove = new int[2];
		logger.debug("Sudoku table initialized");
	}

	public int[][] getTable() {
		return this.table;
	}

	public int getValuesLeft() {
		return this.valuesLeft;
	}

	public int getValue(int i, int j) {
		return this.table[i][j];
	}

	public boolean setValue(int value, int i, int j) {
		if(SudokuUtils.isPossibleMove(this.table, value, i, j)) {
			this.table[i][j] = value;
			this.valuesLeft--;
			this.lastMove[0] = i;
			this.lastMove[1] = j;
			logger.debug("Value set correctly");
			return true;
		} else {
			logger.debug("This value violate Sudoku rules and it has not been set");
			return false;
		}
	}

	public boolean isOver() {
		return this.valuesLeft == 0;
	}

	public void revertLastMove() {
		this.table[this.lastMove[0]][this.lastMove[1]] = 0;
		this.valuesLeft++;
		logger.debug("Last move has been reverted");
	}
}
