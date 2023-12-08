// SudokuGameManager.java
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

public class SudokuGameManager {
    private static SudokuGameManager instance;

    private SudokuGameManager() {
        // private constructor to enforce singleton pattern
    }

    public static SudokuGameManager getInstance() {
        if (instance == null) {
            instance = new SudokuGameManager();
        }
        return instance;
    }

    public void updateTimerLabel() {
        GameBoardPanel gameBoardPanel = SudokuMain.getInstance().getGameBoardPanel();
        gameBoardPanel.updateTimerLabel();
    }
}
