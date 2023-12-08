// SudokuMain.java
package sudoku;

import java.awt.*;
import javax.swing.*;

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;

    private static SudokuMain instance;
    private GameBoardPanel board;

    public static SudokuMain getInstance() {
        if (instance == null) {
            instance = new SudokuMain();
        }
        return instance;
    }

    private SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        board = new GameBoardPanel();
        mainPanel.add(board, BorderLayout.CENTER);

        JButton btnNewGame = new JButton("New Game");
        btnNewGame.addActionListener(e -> {
            board.newGame();
        });
        mainPanel.add(btnNewGame, BorderLayout.SOUTH);

        cp.add(mainPanel, BorderLayout.CENTER);

        board.startTimer();
        board.newGame();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    public GameBoardPanel getGameBoardPanel() {
        return board;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuMain.getInstance();
        });
    }
}
