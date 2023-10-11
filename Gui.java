import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private Game game = new Game();
    private TicTacToeAI ai = new TicTacToeAI(); // Use the TicTacToeAI class

    public Gui() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 48));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttonClicked == buttons[row][col] && game.makeMove(row, col)) {
                    buttonClicked.setText(Character.toString(game.getCurrentPlayer()));
                    buttonClicked.setEnabled(false);

                    if (game.isGameOver()) {
                        char winner = game.getWinner();
                        if (winner != ' ') {
                            JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                        } else {
                            JOptionPane.showMessageDialog(this, "It's a draw!");
                        }
                        resetBoard();
                    } else { // AI's turn
                        game.switchPlayer(); //gives current player to the ai
                        int[] aiMove = ai.getAIMove(game.getBoard());
                        game.makeMove(aiMove[0], aiMove[1]);
                        buttons[aiMove[0]][aiMove[1]].setText(Character.toString(game.getCurrentPlayer()));
                        buttons[aiMove[0]][aiMove[1]].setEnabled(false);

                        if (game.isGameOver()) {
                            char winner = game.getWinner();
                            if (winner != ' ') {
                                JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                            } else {
                                JOptionPane.showMessageDialog(this, "It's a draw!");
                            }
                            resetBoard();
                        }

                        game.switchPlayer(); //gives current player back to human
                    }
                }
            }
        }
    }

    private void resetBoard() {
        game.resetBoard();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
    }
}
