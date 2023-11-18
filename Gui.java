import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Gui extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private Game game = new Game();
    private TicTacToeAI ai;
    private String selectedDifficulty;

    private ImageIcon xIcon;
    private ImageIcon oIcon;

    public Gui() {
        selectDifficulty(); // Initial prompt for difficulty selection
    }

    private void selectDifficulty() {
        Object[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(this, "Choose Difficulty", "Difficulty Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == JOptionPane.CLOSED_OPTION) {
            System.exit(0); // Close the game if the user cancels difficulty selection
        } else {
            selectedDifficulty = options[choice].toString();
            ai = new TicTacToeAI(); // Initialize AI based on selected difficulty
        }

        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3, 5, 5)); // Add gaps between buttons
        getContentPane().setBackground(Color.BLACK);

        try {
            BufferedImage xImg = ImageIO.read(new File("Images\\x_image.png"));
            BufferedImage oImg = ImageIO.read(new File("Images\\o_image.png"));

            xIcon = new ImageIcon(xImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            oIcon = new ImageIcon(oImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        initializeButtons();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 48));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);

                buttons[row][col].setBackground(Color.BLACK);
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.WHITE));

                add(buttons[row][col]);
            }
        }

        Dimension buttonSize = new Dimension(250, 250);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setPreferredSize(buttonSize);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttonClicked == buttons[row][col] && game.makeMove(row, col)) {
                    if (game.getCurrentPlayer() == 'X') {
                        buttonClicked.setIcon(xIcon);
                    } else {
                        buttonClicked.setIcon(oIcon);
                    }
                    buttonClicked.setEnabled(false);

                    if (game.isGameOver()) {
                        char winner = game.getWinner();
                        if (winner != ' ') {
                            JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                        } else {
                            JOptionPane.showMessageDialog(this, "It's a draw!");
                        }
                        resetBoard(); // Reset the board after win/loss
                    } else { // AI's turn
                        game.switchPlayer(); // Gives current player to the AI
                        char[][] board = new char[3][3];
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                board[i][j] = game.getCell(i, j);
                            }
                        }
                        int[] aiMove = ai.getAIMove(board, selectedDifficulty);
                        game.makeMove(aiMove[0], aiMove[1]);
                        buttons[aiMove[0]][aiMove[1]].setIcon((game.getCurrentPlayer() == 'X') ? xIcon : oIcon);
                        buttons[aiMove[0]][aiMove[1]].setEnabled(false);

                        if (game.isGameOver()) {
                            char winner = game.getWinner();
                            if (winner != ' ') {
                                JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                            } else {
                                JOptionPane.showMessageDialog(this, "It's a draw!");
                            }
                            resetBoard(); // Reset the board after win/loss
                        }

                        game.switchPlayer(); // Gives current player back to human
                    }
                }
            }
        }
    }

    private void resetBoard() {
        int option = JOptionPane.showConfirmDialog(this, "Play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            this.dispose(); // Close the current game window

            // Create a new instance of Gui to start a new game
            SwingUtilities.invokeLater(() -> {
                Gui newGame = new Gui();
                newGame.selectedDifficulty = selectedDifficulty; // Maintain current difficulty
                newGame.ai = new TicTacToeAI(); // Initialize AI based on selected difficulty
            });
        } else {
            System.exit(0); // Close the game if the user chooses not to play again
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gui::new);
    }
}
