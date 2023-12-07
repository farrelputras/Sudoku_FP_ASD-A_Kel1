package sudoku;
/**
 * The Sudoku number puzzle to be solved
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess) {
        // Membuat papan kosong
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0;
                isGiven[row][col] = false;
            }
        }

        // Mengacak angka di papan sudoku sebelum menyelesaikan puzzle
        shuffleNumbers();

        // Menyelesaikan puzzle setelah mengacak angka
        solveSudoku();

        // Menentukan kotak yang belum terisi (ditebak)
        setGuesses(cellsToGuess);
    }

    private boolean shuffleNumbers() {
        List<Integer> numList = new ArrayList<>();
        for (int i = 1; i <= SudokuConstants.GRID_SIZE; i++) {
            numList.add(i);
        }

        Collections.shuffle(numList);

        return fillSudoku(numList);
    }

    private boolean fillSudoku(List<Integer> numList) {
        return fillSudokuRecursively(0, 0, numList);
    }

    private boolean fillSudokuRecursively(int row, int col, List<Integer> numList) {
        if (row == SudokuConstants.GRID_SIZE - 1 && col == SudokuConstants.GRID_SIZE) {
            return true;
        }

        if (col == SudokuConstants.GRID_SIZE) {
            row++;
            col = 0;
        }

        if (numbers[row][col] != 0) {
            return fillSudokuRecursively(row, col + 1, numList);
        }

        for (int num : numList) {
            if (isSafe(row, col, num)) {
                numbers[row][col] = num;
                if (fillSudokuRecursively(row, col + 1, numList)) {
                    return true;
                }
                numbers[row][col] = 0;
            }
        }

        return false;
    }

    private boolean isSafe(int row, int col, int num) {
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


    // Metode solveSudoku(), isValidPlacement(), dan setGuesses() tetap sama seperti sebelumnya

    private void solveSudoku() {
        solve(0, 0);
    }


        private boolean solve(int row, int col) {
        if (col == SudokuConstants.GRID_SIZE) {
            col = 0;
            row++;
            if (row == SudokuConstants.GRID_SIZE) {
                return true;
            }
        }

        if (numbers[row][col] != 0) {
            return solve(row, col + 1);
        }

        for (int num = 1; num <= SudokuConstants.GRID_SIZE; num++) {
            if (isValidPlacement(row, col, num)) {
                numbers[row][col] = num;
                if (solve(row, col + 1)) {
                    return true;
                }
                numbers[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isValidPlacement(int row, int col, int num) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (numbers[row][i] == num || numbers[i][col] == num) {
                return false;
            }
        }

        int subgridRowStart = row - row % SudokuConstants.SUBGRID_SIZE;
        int subgridColStart = col - col % SudokuConstants.SUBGRID_SIZE;
        for (int i = subgridRowStart; i < subgridRowStart + SudokuConstants.SUBGRID_SIZE; i++) {
            for (int j = subgridColStart; j < subgridColStart + SudokuConstants.SUBGRID_SIZE; j++) {
                if (numbers[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setGuesses(int cellsToGuess) {
        int targetFilledCells = cellsToGuess + (SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) / 2; // Menargetkan lebih banyak kotak yang terisi
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE; i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);

        int filledCells = 0;
        for (int i = 0; i < indexes.size() && filledCells < targetFilledCells; i++) {
            int idx = indexes.get(i);
            int row = idx / SudokuConstants.GRID_SIZE;
            int col = idx % SudokuConstants.GRID_SIZE;
            if (numbers[row][col] != 0) {
                isGiven[row][col] = true;
                filledCells++;
            }
        }
    }

}
