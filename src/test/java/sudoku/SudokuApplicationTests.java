package sudoku;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import controller.SudokuController;
import model.Sudoku;
import sudoku.utils.SudokuMockData;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SudokuApplication.class)
@WebAppConfiguration
public class SudokuApplicationTests {

	private MockMvc mockMvc;

	private SudokuController sudokuController = new SudokuController();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(sudokuController).build();
	}

	@Test
	public void sudokuTest() throws Exception {

		mockMvc.perform(get("/sudoku"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,0,0,0,4,0,5,3,0],[0,0,5,0,0,8,0,1,0],[0,0,8,5,0,9,0,4,0],[5,3,9,0,6,0,0,0,1],[0,0,0,0,1,0,0,0,5],[8,0,0,7,2,0,9,0,0],[9,0,7,4,0,0,0,0,0],[0,0,0,0,5,7,0,0,0],[6,0,0,0,0,0,0,5,0]],\"over\":false}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == 52);
		assertTrue(!sudokuController.getSudoku().isOver());
	}

	@Test
	public void sudokuMoveTest() throws Exception {

		Sudoku sudoku = SudokuMockData.createSudokuMockData();		
		sudokuController.setSudoku(sudoku);

		int startingValuesLeft = sudoku.getValuesLeft();

		mockMvc.perform(get("/sudoku")
				.param("value", "6")
				.param("row", "2")
				.param("column", "6"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,9,2,1,4,6,5,3,8],[4,6,5,2,3,8,7,1,9],[3,1,8,5,7,9,6,0,2],[5,3,9,8,6,4,2,7,1],[2,7,6,0,1,3,4,8,5],[8,4,1,7,2,5,9,6,3],[9,5,7,4,8,1,3,2,0],[1,2,3,6,5,7,8,9,4],[6,8,4,3,9,2,1,5,7]],\"over\":false}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == startingValuesLeft - 1);
		assertTrue(sudokuController.getSudoku().getTable()[2][5] == 9);
		assertTrue(!sudokuController.getSudoku().isOver());

		mockMvc.perform(get("/sudoku")
				.param("value", "6")
				.param("row", "2")
				.param("column", "5"))
		.andExpect(status().isConflict());
		assertTrue(sudokuController.getSudoku().getValuesLeft() == startingValuesLeft - 1);
		assertTrue(sudokuController.getSudoku().getTable()[2][5] == 9);
		assertTrue(sudokuController.getSudoku().getTable()[2][7] == 0);
		assertTrue(!sudokuController.getSudoku().isOver());

		mockMvc.perform(get("/sudoku")
				.param("value", "5")
				.param("row", "2")
				.param("column", "7"))
		.andExpect(status().isConflict());
		assertTrue(sudokuController.getSudoku().getValuesLeft() == startingValuesLeft - 1);
		assertTrue(sudokuController.getSudoku().getTable()[2][7] == 0);
		assertTrue(!sudokuController.getSudoku().isOver());
	}

	@Test
	public void sudokuNewTest() throws Exception {

		Sudoku sudoku = SudokuMockData.createSudokuMockData();		
		sudokuController.setSudoku(sudoku);

		int startingValuesLeft = sudoku.getValuesLeft();

		mockMvc.perform(get("/sudoku"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,9,2,1,4,6,5,3,8],[4,6,5,2,3,8,7,1,9],[3,1,8,5,7,9,0,0,2],[5,3,9,8,6,4,2,7,1],[2,7,6,0,1,3,4,8,5],[8,4,1,7,2,5,9,6,3],[9,5,7,4,8,1,3,2,0],[1,2,3,6,5,7,8,9,4],[6,8,4,3,9,2,1,5,7]],\"over\":false}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == startingValuesLeft);
		assertTrue(!sudokuController.getSudoku().isOver());

		mockMvc.perform(get("/sudoku/new"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,0,0,0,4,0,5,3,0],[0,0,5,0,0,8,0,1,0],[0,0,8,5,0,9,0,4,0],[5,3,9,0,6,0,0,0,1],[0,0,0,0,1,0,0,0,5],[8,0,0,7,2,0,9,0,0],[9,0,7,4,0,0,0,0,0],[0,0,0,0,5,7,0,0,0],[6,0,0,0,0,0,0,5,0]],\"over\":false}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == 52);
		assertTrue(!sudokuController.getSudoku().isOver());
	}

	@Test
	public void sudokuRevertTest() throws Exception {

		Sudoku sudoku = SudokuMockData.createSudokuMockData();		
		sudokuController.setSudoku(sudoku);

		int startingValuesLeft = sudoku.getValuesLeft();

		mockMvc.perform(get("/sudoku"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,9,2,1,4,6,5,3,8],[4,6,5,2,3,8,7,1,9],[3,1,8,5,7,9,0,0,2],[5,3,9,8,6,4,2,7,1],[2,7,6,0,1,3,4,8,5],[8,4,1,7,2,5,9,6,3],[9,5,7,4,8,1,3,2,0],[1,2,3,6,5,7,8,9,4],[6,8,4,3,9,2,1,5,7]],\"over\":false}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == startingValuesLeft);
		assertTrue(!sudokuController.getSudoku().isOver());

		mockMvc.perform(get("/sudoku/revert"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,9,2,1,4,6,5,3,8],[4,0,5,2,3,8,7,1,9],[3,1,8,5,7,9,0,0,2],[5,3,9,8,6,4,2,7,1],[2,7,6,0,1,3,4,8,5],[8,4,1,7,2,5,9,6,3],[9,5,7,4,8,1,3,2,0],[1,2,3,6,5,7,8,9,4],[6,8,4,3,9,2,1,5,7]],\"over\":false}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == startingValuesLeft + 1);
		assertTrue(!sudokuController.getSudoku().isOver());
	}	

	@Test
	public void sudokuOverTest() throws Exception {

		Sudoku sudoku = SudokuMockData.createSudokuMockDataOver();		
		sudokuController.setSudoku(sudoku);

		mockMvc.perform(get("/sudoku"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,9,2,1,4,6,5,3,8],[4,6,5,2,3,8,7,1,9],[3,1,8,5,7,9,6,4,2],[5,3,9,8,6,4,2,7,1],[2,7,6,9,1,3,4,8,5],[8,4,1,7,2,5,9,6,3],[9,5,7,4,8,1,3,2,6],[1,2,3,6,5,7,8,9,4],[6,8,4,3,9,2,1,0,7]],\"over\":false}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == 1);
		assertTrue(!sudokuController.getSudoku().isOver());

		mockMvc.perform(get("/sudoku")
				.param("value", "5")
				.param("row", "8")
				.param("column", "7"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,9,2,1,4,6,5,3,8],[4,6,5,2,3,8,7,1,9],[3,1,8,5,7,9,6,4,2],[5,3,9,8,6,4,2,7,1],[2,7,6,9,1,3,4,8,5],[8,4,1,7,2,5,9,6,3],[9,5,7,4,8,1,3,2,6],[1,2,3,6,5,7,8,9,4],[6,8,4,3,9,2,1,5,7]],\"over\":true}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == 0);
		assertTrue(sudokuController.getSudoku().isOver());
		assertTrue(sudokuController.getSudoku().getLastMove()[0] == 8 && sudokuController.getSudoku().getLastMove()[1] == 7);

		mockMvc.perform(get("/sudoku/revert"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().string("{\"table\":[[7,9,2,1,4,6,5,3,8],[4,6,5,2,3,8,7,1,9],[3,1,8,5,7,9,6,4,2],[5,3,9,8,6,4,2,7,1],[2,7,6,9,1,3,4,8,5],[8,4,1,7,2,5,9,6,3],[9,5,7,4,8,1,3,2,6],[1,2,3,6,5,7,8,9,4],[6,8,4,3,9,2,1,0,7]],\"over\":false}"));
		assertTrue(sudokuController.getSudoku().getValuesLeft() == 1);
		assertTrue(!sudokuController.getSudoku().isOver());
	}	
}
