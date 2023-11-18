import java.util.Random;

public class TicTacToeAI {
    public int[] getAIMove(char[][] board, String difficulty) {
        if (difficulty.equals("Easy")) {
            return getRandomMove(board);
        } else if (difficulty.equals("Medium")) {
            return getStrategicMove(board);
        } else if (difficulty.equals("Hard")) {
            return getAggressiveMove(board);
        } else {
            // Default to random move if difficulty selection is invalid
            return getRandomMove(board);
        }
    }

    private int[] getRandomMove(char[][] board) {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != ' ');
        return new int[]{row, col};
    }

    private int[] getStrategicMove(char[][] board) {
        // Implement logic for medium difficulty
        // Placeholder logic to prioritize blocking the player if they are close to winning
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == board[i][1] && board[i][0] != ' ') || (board[i][1] == board[i][2] && board[i][1] != ' ')) {
                if (board[i][0] == ' ') {
                    return new int[]{i, 0};
                } else if (board[i][1] == ' ') {
                    return new int[]{i, 1};
                } else if (board[i][2] == ' ') {
                    return new int[]{i, 2};
                }
            }

            if ((board[0][i] == board[1][i] && board[0][i] != ' ') || (board[1][i] == board[2][i] && board[1][i] != ' ')) {
                if (board[0][i] == ' ') {
                    return new int[]{0, i};
                } else if (board[1][i] == ' ') {
                    return new int[]{1, i};
                } else if (board[2][i] == ' ') {
                    return new int[]{2, i};
                }
            }
        }

        // Add logic for blocking diagonals if needed...

        return getRandomMove(board); // Placeholder random move
    }

    private int[] getAggressiveMove(char[][] board) {
        // Check if AI can win in the next move
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = 'O';
                    if (evaluate(board) == 10) {
                        board[row][col] = ' '; // Reset the test move
                        return new int[]{row, col}; // Winning move found
                    }
                    board[row][col] = ' '; // Reset the test move
                }
            }
        }
    
        // Check if player can win in the next move and block it
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = 'X';
                    if (evaluate(board) == -10) {
                        board[row][col] = ' '; // Reset the test move
                        return new int[]{row, col}; // Block player's winning move
                    }
                    board[row][col] = ' '; // Reset the test move
                }
            }
        }
    
        // If no immediate winning or blocking moves are available,
        // prioritize getting a strategic move
        return getStrategicMove(board);
    }
    

    private static int evaluate(char[][] board) {
        // Check rows, columns, and diagonals for a win
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == 'O') return 10;
                else if (board[row][0] == 'X') return -10;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == 'O') return 10;
                else if (board[0][col] == 'X') return -10;
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'O') return 10;
            else if (board[0][0] == 'X') return -10;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'O') return 10;
            else if (board[0][2] == 'X') return -10;
        }

        // No winner yet
        return 0;
    }
}
