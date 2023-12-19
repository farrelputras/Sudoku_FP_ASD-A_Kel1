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

        cp.add(mainPanel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem aboutMenuItem = new JMenuItem("About");

        newGame.addActionListener(e -> {
            String selectedDifficulty = (String) board.getDifficultyComboBox().getSelectedItem();
            board.handleDifficultyChange(selectedDifficulty);
            board.resetPoints();
        });
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon("group1.jpg");

                ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(400,300, Image.SCALE_SMOOTH));

                JLabel imageLabel = new JLabel(scaledIcon);
                imageLabel.setPreferredSize(new Dimension(400, 300));

                JLabel textLabel = new JLabel("<html><center>Sudoku<br>Version 1.0<br>Created by Group 1<br>" +
                        "5026221035 - Mufidhatul Nafisa<br>" +
                        "5026221120 - M. Shalahuddin Arif Laksono<br>" +
                        "5026221102 - Fernandio Farrel Putra S.</center></html>");
                textLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel textPanel = new JPanel(new BorderLayout());
                textPanel.add(imageLabel, BorderLayout.CENTER);
                textPanel.add(textLabel, BorderLayout.SOUTH);


                JOptionPane optionPane = new JOptionPane(textPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
                JDialog dialog = optionPane.createDialog(SudokuMain.this, "About");

                dialog.addWindowListener(new WindowAdapter() {

                });

                dialog.setVisible(true);
            }
        });

        menuBar.add(game);
        game.add(newGame);
        game.add(aboutMenuItem);
        setJMenuBar(menuBar);

        board.startTimer();

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
