// GameBoardPanel.java
package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    private Image backgroundImage;
    private Timer timer;
    private int totalSeconds;
    private JLabel timerLabel;
    private JPanel timerPanel = new JPanel();
    private JPanel sudokuGrid = new JPanel();

    public GameBoardPanel() {
        super.setLayout(new BorderLayout());

        super.add(timerPanel, BorderLayout.NORTH);
        super.add(sudokuGrid, BorderLayout.CENTER);

        sudokuGrid.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                sudokuGrid.add(cells[row][col]);

                // Add black borders to cells
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Add right and bottom borders for grid separation
                if ((col + 1) % 3 == 0 && col < SudokuConstants.GRID_SIZE - 1) {
                    Border border = new MatteBorder(0, 0, 0, 2, Color.BLACK);
                    cells[row][col].setBorder(new CompoundBorder(cells[row][col].getBorder(), border));
                }
                if ((row + 1) % 3 == 0 && row < SudokuConstants.GRID_SIZE - 1) {
                    Border border = new MatteBorder(0, 0, 2, 0, Color.BLACK);
                    cells[row][col].setBorder(new CompoundBorder(cells[row][col].getBorder(), border));
                }

                // Add action listener to editable cells
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(new CellInputListener());
                }
            }
        }

        // Timer initialization
        totalSeconds = 0;
        timerLabel = new JLabel("Timer: 0s");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        timerPanel.add(timerLabel);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalSeconds++;
                updateTimerLabel();
            }
        });
        timer.start(); // Start the timer

        // Set preferred size and border of the entire panel
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        super.setBorder(new LineBorder(Color.BLACK, 3));
    }




    public void newGame() {
        puzzle.newPuzzle(2);
        totalSeconds = 0; // Reset the timer when starting a new game

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }

        startTimer();
    }

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell)e.getSource();
            int numberIn = Integer.parseInt(sourceCell.getText());
            System.out.println("You entered " + numberIn);

            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint();

            if (isSolved()) {
                JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
                stopTimer();
            }
        }
    }


    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void updateTimerLabel() {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        timerLabel.setText(String.format("Timer: %02d:%02d:%02d", hours, minutes, seconds));
    }
}
