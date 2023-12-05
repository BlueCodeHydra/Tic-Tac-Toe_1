import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Gui extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private Game game;
    private TicTacToeAI ai;
    private String selectedDifficulty;

    private ImageIcon xIcon;
    private ImageIcon oIcon;

    public Gui() {
        selectDifficulty();
    }

    private void selectDifficulty() {
        Object[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(this, "Choose Difficulty", "Difficulty Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == JOptionPane.CLOSED_OPTION) {
            System.exit(0); // Close the game if the user cancels difficulty selection
        } else {
            int size = Integer.parseInt(JOptionPane.showInputDialog("Enter board size (3 to 10):")); // Ask user for board size
            if (size < 3 || size > 10) {
                JOptionPane.showMessageDialog(this, "Invalid board size! Please select a size between 3 and 10.");
                selectDifficulty(); // Restart difficulty selection process if size is invalid
                return;
            }

            selectedDifficulty = options[choice].toString();
            ai = new TicTacToeAI(); // Initialize AI based on selected difficulty
            initializeBoard(size); // Initialize board based on selected size
        }

        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeBoard(int size) {
        game = new Game(size);
        buttons = new JButton[size][size];
    
        setLayout(new GridLayout(size, size, 5, 5)); // Adjust layout based on selected size
        getContentPane().setBackground(Color.BLACK);
    
        try {
            BufferedImage xImg = ImageIO.read(new File("Images\\x_image.png"));
            BufferedImage oImg = ImageIO.read(new File("Images\\o_image.png"));
    
            xIcon = new ImageIcon(xImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            oIcon = new ImageIcon(oImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
        initializeButtons(size); // Initialize buttons based on selected size
        pack();
    }
    
    private void initializeButtons(int size) {
        buttons = new JButton[size][size];
    
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
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
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col].setPreferredSize(buttonSize);
            }
        }
    }    

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
    
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
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
                        char[][] board = new char[buttons.length][buttons.length];
                        for (int i = 0; i < buttons.length; i++) {
                            for (int j = 0; j < buttons[i].length; j++) {
                                board[i][j] = game.getCell(i, j);
                            }
                        }
                        int[] aiMove = ai.getAIMove(board, selectedDifficulty);
                        game.makeMove(aiMove[0], aiMove[1]);
                        buttons[aiMove[0]][aiMove[1]].setIcon((game.getCurrentPlayer() == 'X') ? xIcon : oIcon);
                        buttons[aiMove[0]][aiMove[1]].setEnabled(false);
    
                        if (game.isGameOver()) {
                            char aiWinner = game.getWinner();
                            if (aiWinner != ' ') {
                                JOptionPane.showMessageDialog(this, "Player " + aiWinner + " wins!");
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
