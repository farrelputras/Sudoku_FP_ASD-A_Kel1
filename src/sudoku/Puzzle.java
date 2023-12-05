package sudoku;

import java.util.Random;

public class Puzzle {
    // ... kode lainnya ...

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int cellsToGuess) {
        Random random = new Random();

        // Inisialisasi numbers secara acak
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = random.nextInt(9) + 1; // angka acak dari 1 hingga 9
            }
        }

        // Inisialisasi isGiven secara acak sesuai dengan jumlah cellsToGuess
        int cellsGuessed = 0;
        while (cellsGuessed < cellsToGuess) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);

            // Jika cell belum ditebak sebelumnya
            if (!isGiven[row][col]) {
                isGiven[row][col] = true;
                cellsGuessed++;
            }
        }
    }

    // ... kode lainnya ...
}
