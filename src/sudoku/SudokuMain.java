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
import java.awt.event.*;


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
            String selectedDifficulty = (String) board.getDifficultyComboBox().getSelectedItem();
            board.handleDifficultyChange(selectedDifficulty);
            board.resetPoints();
        });
        mainPanel.add(btnNewGame, BorderLayout.SOUTH);

        cp.add(mainPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setHorizontalAlignment(SwingConstants.CENTER);

        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon("group1.jpg");

                ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(board.getWidth(), board.getHeight(), Image.SCALE_SMOOTH));

                JLabel imageLabel = new JLabel(scaledIcon);
                imageLabel.setPreferredSize(new Dimension(board.getWidth(), board.getHeight()));

                JLabel textLabel = new JLabel("<html><center>Sudoku Game<br>Version 1.0<br>Created by Group 1</center></html>");
                textLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel textPanel = new JPanel(new BorderLayout());
                textPanel.add(imageLabel, BorderLayout.CENTER);
                textPanel.add(textLabel, BorderLayout.SOUTH);


                JOptionPane optionPane = new JOptionPane(textPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
                JDialog dialog = optionPane.createDialog(SudokuMain.this, "About");

                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // Restart the game when the dialog is closed
                        board.newGame();
                    }
                });

                dialog.setVisible(true);
            }
        });

        menuBar.add(Box.createHorizontalGlue()); // Untuk menempatkan menu "About" di tengah
        menuBar.add(aboutMenuItem);
        setJMenuBar(menuBar);

        board.startTimer();
//        board.handleDifficultyChange();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
        setResizable(false);
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
