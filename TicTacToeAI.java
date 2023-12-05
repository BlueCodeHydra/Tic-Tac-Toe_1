/* ===============================================================================================================
                                                                                                                         
            ___   ___                                                                                                  
 /__  ___/     / /      //   ) )        /__  ___/   // | |     //   ) )        /__  ___/   //   ) )   //   / / 
   / /        / /      //                 / /      //__| |    //                 / /      //   / /   //____    
  / /        / /      //         ____    / /      / ___  |   //         ____    / /      //   / /   / ____     
 / /        / /      //                 / /      //    | |  //                 / /      //   / /   //          
/ /      __/ /___   ((____/ /          / /      //     | | ((____/ /          / /      ((___/ /   //____/ /    

==================================================================================================================

# Michael Dowling
# Michael Richards

# CSE: Artificial Intelegience 
# 12-2-2023


# Game- Has all the game logic

#===========================================
*/

import java.util.Random;

public class TicTacToeAI {
    public int[] getAIMove(char[][] board, String difficulty) {
        if (difficulty.equals("Easy")) {
            return getEasyMove(board);
        } else if (difficulty.equals("Medium")) {
            return getMediumMove(board);
        } else if (difficulty.equals("Hard")) {
            return getHardMove(board);
        } else {
            // Default to random move if difficulty selection is invalid
            return getRandomMove(board);
        }
    }

    private int[] getRandomMove(char[][] board) {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(board.length);
            col = random.nextInt(board.length);
        } while (board[row][col] != ' ');
        return new int[]{row, col};
    }

    private int[] getEasyMove(char[][] board) {
        return getStrategicMove(board, 'X');
    }

    private int[] getMediumMove(char[][] board) {
        // The medium move will aim to block the opponent if they have two in a row.
        return getStrategicMove(board, 'O');
    }

    private int[] getHardMove(char[][] board) {
        // Check if AI can win in the next move
        int[] winningMove = getWinningMove(board, 'O');
        if (winningMove != null) {
            return winningMove; // Winning move found
        }
    
        // Check if AI can block the player's winning move
        int[] blockingMove = getWinningMove(board, 'X');
        if (blockingMove != null) {
            return blockingMove; // Block player's winning move
        }
    
        // Prioritize getting a strategic move
        return getStrategicMove(board, 'O');
    }
    

    // This method identifies a winning move for a given player
    private int[] getWinningMove(char[][] board, char player) {
        int size = board.length;
        // Check rows for a winning move
        for (int i = 0; i < size; i++) {
            int count = 0;
            int emptyCellIndex = -1;
            for (int j = 0; j < size; j++) {
                if (board[i][j] == player) {
                    count++;
                } else if (board[i][j] == ' ') {
                    emptyCellIndex = j;
                }
            }
            if (count == size - 1 && emptyCellIndex != -1) {
                return new int[]{i, emptyCellIndex};
            }
        }

        // Check columns for a winning move
        for (int i = 0; i < size; i++) {
            int count = 0;
            int emptyCellIndex = -1;
            for (int j = 0; j < size; j++) {
                if (board[j][i] == player) {
                    count++;
                } else if (board[j][i] == ' ') {
                    emptyCellIndex = j;
                }
            }
            if (count == size - 1 && emptyCellIndex != -1) {
                return new int[]{emptyCellIndex, i};
            }
        }

        // Check diagonals for a winning move
        int count = 0;
        int emptyCellIndex = -1;
        for (int i = 0; i < size; i++) {
            if (board[i][i] == player) {
                count++;
            } else if (board[i][i] == ' ') {
                emptyCellIndex = i;
            }
        }
        if (count == size - 1 && emptyCellIndex != -1) {
            return new int[]{emptyCellIndex, emptyCellIndex};
        }

        count = 0;
        emptyCellIndex = -1;
        for (int i = 0; i < size; i++) {
            if (board[i][size - 1 - i] == player) {
                count++;
            } else if (board[i][size - 1 - i] == ' ') {
                emptyCellIndex = i;
            }
        }
        if (count == size - 1 && emptyCellIndex != -1) {
            return new int[]{emptyCellIndex, size - 1 - emptyCellIndex};
        }

        return null; // No immediate winning move found
    }

    private int[] getStrategicMove(char[][] board, char player) {
        int size = board.length;

        // Prioritize placing marks to complete a row, column, or diagonal
        for (int i = 0; i < size; i++) {
            int[] rowCol = checkForRowColumn(board, i, player);
            if (rowCol != null) {
                return rowCol;
            }
        }

        // If no immediate winning move, make a random move
        return getRandomMove(board);
    }

    private int[] checkForRowColumn(char[][] board, int index, char player) {
        int size = board.length;
        int countRow = 0, countColumn = 0;
        int emptyCellIndexRow = -1, emptyCellIndexColumn = -1;

        for (int i = 0; i < size; i++) {
            if (board[index][i] == player) {
                countRow++;
            } else if (board[index][i] == ' ') {
                emptyCellIndexRow = i;
            }

            if (board[i][index] == player) {
                countColumn++;
            } else if (board[i][index] == ' ') {
                emptyCellIndexColumn = i;
            }
        }

        if (countRow == size - 1 && emptyCellIndexRow != -1) {
            return new int[]{index, emptyCellIndexRow};
        }

        if (countColumn == size - 1 && emptyCellIndexColumn != -1) {
            return new int[]{emptyCellIndexColumn, index};
        }

        return null;
    }
}
