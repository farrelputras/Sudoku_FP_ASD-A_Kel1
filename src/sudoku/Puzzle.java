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

        // Inisialisasi numbers dengan angka nol di setiap sel
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0;
            }
        }

        // Generate numbers with unique values for each row and column
        for (int i = 0; i < SudokuConstants.GRID_SIZE; ++i) {
            for (int j = 0; j < SudokuConstants.GRID_SIZE; ++j) {
                int num;
                do {
                    num = random.nextInt(SudokuConstants.GRID_SIZE) + 1; // angka acak dari 1 hingga 9
                } while (!isValid(numbers, i, j, num));

                numbers[i][j] = num;
            }
        }

        // Menginisialisasi isGiven sesuai dengan jumlah cellsToGuess
        int cellsGuessed = 0;
        while (cellsGuessed < cellsToGuess) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);

            // Jika cell belum ditebak sebelumnya
            if (numbers[row][col] != 0) {
                isGiven[row][col] = true;
                cellsGuessed++;
            }
        }
    }

    // Method untuk memeriksa apakah suatu angka valid untuk ditempatkan pada suatu posisi
    private boolean isValid(int[][] board, int row, int col, int num) {
        // Cek apakah angka sudah ada dalam baris
        for (int x = 0; x < SudokuConstants.GRID_SIZE; ++x) {
            if (board[row][x] == num) {
                return false;
            }
        }

        // Cek apakah angka sudah ada dalam kolom
        for (int y = 0; y < SudokuConstants.GRID_SIZE; ++y) {
            if (board[y][col] == num) {
                return false;
            }
        }

        // Cek apakah angka sudah ada dalam sub-grid 3x3
        int subGridRowStart = row - row % SudokuConstants.SUBGRID_SIZE;
        int subGridColStart = col - col % SudokuConstants.SUBGRID_SIZE;
        for (int i = subGridRowStart; i < subGridRowStart + SudokuConstants.SUBGRID_SIZE; ++i) {
            for (int j = subGridColStart; j < subGridColStart + SudokuConstants.SUBGRID_SIZE; ++j) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // ... kode lainnya ...
}
