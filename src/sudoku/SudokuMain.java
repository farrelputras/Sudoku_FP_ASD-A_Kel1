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

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;

    private static SudokuMain instance;
    private GameBoardPanel board;
//    private Solve solve;

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

//        JButton solveBtn = new JButton("Solve");
//        solveBtn.addActionListener(e -> {
//            solve.solveSudoku();
//        });
//        cp.add(solveBtn, BorderLayout.SOUTH);

        board.startTimer();
        board.newGame();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuMain.getInstance();
        });
    }
}
