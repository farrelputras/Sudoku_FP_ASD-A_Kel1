package sudoku;

/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2023/2024
 * Group Capstone Project
 * Group #1
 * 1 - 5026221035 - Mufidhatul Nafisa
 * 2 - 5026221120 - M. Shalahuddin Arif Laksono
 * 3 - 5026221102 - Fernandio Farrel Putra S.
 */

/**
 * The Sudoku number puzzle to be solved
 */
import javax.swing.*;
import java.util.Collections;
import java.util.Stack;

public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0;
                isGiven[row][col] = false;
            }
        }
        solve();
        setGuesses(cellsToGuess);
    }

    private boolean solve() {
        Stack<Integer> numStack = new Stack<>();
        for (int i = 1; i <= SudokuConstants.GRID_SIZE; i++) {
            numStack.push(i);
        }

        Collections.shuffle(numStack);
        return fillSudoku(numStack);
    }

    private boolean fillSudoku(Stack<Integer> numStack) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0;
            }
        }
        // Fill the puzzle randomly
        return fillSudokuRecursively(0, 0, numStack);
    }
    private boolean fillSudokuRecursively(int row, int col, Stack<Integer> numStack) {
        if (row == SudokuConstants.GRID_SIZE - 1 && col == SudokuConstants.GRID_SIZE) {
            return true;
        }
        if (col == SudokuConstants.GRID_SIZE) {
            row++;
            col = 0;
        }
        Collections.shuffle(numStack);

        for (int num : numStack) {
            if (isSafe(row, col, num)) {
                numbers[row][col] = num;
                if (fillSudokuRecursively(row, col + 1, numStack)) {
                    return true;
                }
                numbers[row][col] = 0;
            }
        }
        return false;
    }
    public boolean isSafe(int row, int col, int num) {
        return isSafeRow(row, num) && isSafeCol(col, num) && isSafeSubgrid(row - row % SudokuConstants.SUBGRID_SIZE, col - col % SudokuConstants.SUBGRID_SIZE, num);
    }
    private boolean isSafeRow(int row, int num) {
        for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            if (numbers[row][col] == num) {
                return false;
            }
        }
        return true;
    }
    private boolean isSafeCol(int col, int num) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            if (numbers[row][col] == num) {
                return false;
            }
        }
        return true;
    }
    private boolean isSafeSubgrid(int rowStart, int colStart, int num) {
        for (int row = 0; row < SudokuConstants.SUBGRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.SUBGRID_SIZE; col++) {
                if (numbers[row + rowStart][col + colStart] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setGuesses(int cellsToGuess) {
        int targetFilledCells = cellsToGuess + (SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) / 2; //number of cells to be filled
        Stack<Integer> indexes = new Stack<>();
        for (int i = 0; i < SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE; i++) {
            indexes.push(i);
        }
        Collections.shuffle(indexes);

        int filledCells = 0;
        while (!indexes.isEmpty() && filledCells < targetFilledCells) {
            int idx = indexes.pop();
            int row = idx / SudokuConstants.GRID_SIZE;
            int col = idx % SudokuConstants.GRID_SIZE;
            if (numbers[row][col] != 0) {
                isGiven[row][col] = true;
                filledCells++;
            }
        }
    }
}
