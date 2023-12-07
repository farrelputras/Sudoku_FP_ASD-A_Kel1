package sudoku;
/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int cellsToGuess) {
        // Membuat papan kosong
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0;  // Set semua nilai di papan sudoku menjadi 0
                isGiven[row][col] = false;  // Semua kotak tidak diisi
            }
        }

        // Mengisi angka di papan sudoku secara acak dengan aturan sudoku
        solveSudoku();

        // Menentukan kotak yang belum terisi (ditebak)
        setGuesses(cellsToGuess);
    }

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
        // Mengatur angka yang belum muncul sesuai aturan Sudoku
        int targetFilledCells = cellsToGuess + (SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) / 2; // Memastikan lebih banyak kotak yang terisi
        java.util.List<Integer> indexes = new java.util.ArrayList<>();
        for (int i = 0; i < SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE; i++) {
            indexes.add(i);
        }
        java.util.Collections.shuffle(indexes);

        int givenCells = 0;
        for (int i = 0; i < indexes.size() && givenCells < targetFilledCells; i++) {
            int idx = indexes.get(i);
            int row = idx / SudokuConstants.GRID_SIZE;
            int col = idx % SudokuConstants.GRID_SIZE;
            if (numbers[row][col] != 0) {
                isGiven[row][col] = true;
                givenCells++;
            }
        }
    }




    //(For advanced students) use singleton design pattern for this class
}
