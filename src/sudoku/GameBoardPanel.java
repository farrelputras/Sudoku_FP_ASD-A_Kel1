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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class GameBoardPanel extends JPanel {
    public JComboBox<String> getDifficultyComboBox() {
        return difficultyComboBox;
    }

    private static final long serialVersionUID = 1L;
    public static final int CELL_SIZE = 60;
    public static final int BOARD_SIZE = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_WIDTH  = BOARD_SIZE;
    public static final int BOARD_HEIGHT = BOARD_SIZE;
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    private Image backgroundImage;
    private Timer timer;
    private int totalSeconds;
    private JLabel timerLabel;
    private JPanel timerPanel = new JPanel();
    private JPanel sudokuGrid = new JPanel();
    private int wrongGuessCount;
    private JComboBox<String> difficultyComboBox;
    public int cellsToGuess;
    private int points;
    private JLabel pointsLabel;

    public GameBoardPanel() {
        super.setLayout(new BorderLayout());
        wrongGuessCount = 0; //reset the wrong counter
        super.add(timerPanel, BorderLayout.NORTH);
        super.add(sudokuGrid, BorderLayout.CENTER);
        JPanel pointsPanel = new JPanel();
        pointsLabel = new JLabel("Points: 0");
        pointsPanel.add(pointsLabel);
        super.add(pointsPanel, BorderLayout.SOUTH);

        sudokuGrid.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                int value = 0;
                cells[row][col] = new Cell(row, col, value);
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

        String[] difficultyOptions = {"Easy", "Medium", "Hard"};
        difficultyComboBox = new JComboBox<>(difficultyOptions);
        difficultyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                String selectedDifficulty = (String) comboBox.getSelectedItem();
                handleDifficultyChange(selectedDifficulty);
            }
        });

        // Tambahkan komponen ke panel timerPanel
        timerPanel.add(difficultyComboBox);
        handleDifficultyChange("Medium");
        difficultyComboBox.setSelectedItem("Medium");
    }

    public void newGame(int cellsToGuess) {
        puzzle.newPuzzle(cellsToGuess);
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
            Cell sourceCell = (Cell) e.getSource();
            int numberIn;
            try {
                numberIn = Integer.parseInt(sourceCell.getText());
                if (numberIn < 1 || numberIn > 9) {
                    JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 9.");
                    return;
                }
            } catch (NumberFormatException ex) {
                // Handle jika input bukan angka
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                return;
            }


            System.out.println("You entered " + numberIn);

            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
                wrongGuessCount++;

                // Cek apakah sudah mencapai 5 kesalahan
                if (wrongGuessCount >= 5) {
                    handleGameOver();
                }
            }
            sourceCell.paint();

            // Memanggil isSolved() dari instance GameBoardPanel yang sama
            if (isSolved()) {
                JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
                stopTimer();
            }
            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                points++; // Tambah poin jika jawaban benar
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
                points--; // Kurangi poin jika jawaban salah
                // Cek apakah sudah mencapai 5 kesalahan
                if (wrongGuessCount >= 5) {
                    handleGameOver();
                }
            }
            sourceCell.paint();

            updatePointsLabel(); // Update label poin

            // ... (kode setelahnya)
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
    private void handleGameOver() {
        JOptionPane.showMessageDialog(null, "Game Over! You've made 5 wrong guesses. Starting a new game.");
        newGame(cellsToGuess);  // Memulai permainan baru setelah mencapai batas kesalahan
        resetPoints();
        resetColumnColors();
        wrongGuessCount = 0;  // Mereset jumlah kesalahan
    }

    private void resetColumnColors() {
        for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                cells[row][col].resetBackgroundColor();
            }
        }
    }
    public void handleDifficultyChange(String selectedDifficulty) {
        int cellsToGuess;
        switch (selectedDifficulty) {
            case "Hard":
                cellsToGuess = (SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) / 16;
                break;
            case "Medium":
                cellsToGuess = (SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) / 8;
                break;
            case "Easy":
                cellsToGuess = (SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) / 4;
                break;
            default:
                cellsToGuess = (SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE) / 8; // Default to Medium
                break;
        }

        // Reset game with the new difficulty
        newGame(cellsToGuess);
    }
    private void updatePointsLabel() {
        pointsLabel.setText("Points: " + points);
    }
    void resetPoints() {
        points = 0;
        updatePointsLabel();
    }



}
