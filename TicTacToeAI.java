

public class TicTacToeAI {
    public int[] getAIMove(char[][] board) {
        int[] bestMove = minimax(board, 'O'); // 'O' represents the AI player
        return bestMove;
    }

    private static int[] minimax(char[][] board, char player) {
        // Check for terminal states (win/lose/tie)
        int[] bestMove = { -1, -1 };
        int bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        char opponent = (player == 'O') ? 'X' : 'O';

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = player;
                    int score = minimaxHelper(board, player, opponent, 0, false);
                    board[row][col] = ' '; // Undo the move

                    if ((player == 'O' && score > bestScore) || (player == 'X' && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimaxHelper(char[][] board, char player, char opponent, int depth, boolean isMaximizing) {
        // Evaluate the board and return a score
        int score = evaluate(board);

        // If the game is over, return the score
        if (score == 10 || score == -10)
            return score;

        // If there are no more moves, it's a tie
        if (!isMoveLeft(board))
            return 0;

        // If it's the AI's turn
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = player;
                        int currentScore = minimaxHelper(board, player, opponent, depth + 1, false);
                        bestScore = Math.max(bestScore, currentScore);
                        board[row][col] = ' '; // Undo the move
                    }
                }
            }
            return bestScore;
        } else { // If it's the opponent's turn
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = opponent;
                        int currentScore = minimaxHelper(board, player, opponent, depth + 1, true);
                        bestScore = Math.min(bestScore, currentScore);
                        board[row][col] = ' '; // Undo the move
                    }
                }
            }
            return bestScore;
        }
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

    private static boolean isMoveLeft(char[][] board) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ')
                    return true;
            }
        }
        return false;
    }
}
