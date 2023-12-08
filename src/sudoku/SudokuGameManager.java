// SudokuGameManager.java
package sudoku;

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
