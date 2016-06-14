package controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import model.Sudoku;
import utils.SudokuUtils;

@RestController
public class SudokuController {

	private Sudoku sudoku = new Sudoku();

	// Just for tests purposes
	public Sudoku getSudoku() {
		return sudoku;
	}

	// Just for tests purposes
	public void setSudoku(Sudoku sudoku) {
		this.sudoku = sudoku;
	}

	@RequestMapping("/sudoku")
	@ResponseBody
	public Sudoku sudoku(@RequestParam(value = "value", required = false) Integer value,
			@RequestParam(value = "row", required = false) Integer row,
			@RequestParam(value = "column", required = false) Integer column) {

		if(value !=null && row != null && column != null) {
			if(sudoku.setValue(value, row, column)) {
				if(SudokuUtils.isDeadLockGame(sudoku.getTable(), sudoku.getValuesLeft())) {
					throw new SudokuDeadLockGameException(value, row, column);
				}
			} else {
				throw new SudokuMoveNotAllowedException(value, row, column);
			}
		}
		return sudoku;
	}
	
	@RequestMapping("/sudoku/revert")
	@ResponseBody
	public Sudoku sudokuRevert() {
		sudoku.revertLastMove();
		return sudoku;
	}
	
	@RequestMapping("/sudoku/new")
	@ResponseBody
	public Sudoku sudokuNew() {
		sudoku = new Sudoku();
		return sudoku;
	}

	//Note: Just having a bit of fun with HttpStatus; of course this is not the proper use of it
	@ResponseStatus(HttpStatus.CONFLICT)
	class SudokuMoveNotAllowedException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public SudokuMoveNotAllowedException(int value, int row, int column) {
			super("This move is not allowed: value " + value + " - row " + row + " - clomun " + column);
		}
	}
	
	//Note: Just having a bit of fun with HttpStatus; of course this is not the proper use of it
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	class SudokuDeadLockGameException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public SudokuDeadLockGameException(int value, int row, int column) {
			super("This move will lead to a lost game: value - " + value + " row - " + row + " clomun -" + column + "\nWe suggest you to use the call /sudoku/revert to revert last move and keep playing!");
		}
	}
}
