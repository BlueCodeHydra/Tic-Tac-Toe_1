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
# 11-18-2023


# Game- Has all the game logic

#===========================================
*/


import java.util.Arrays;

public class Game {
    private char[][] board = new char[3][3];
    private char currentPlayer = 'X';

    public Game() {
        initializeBoard();
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public void printboard() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }

    public boolean makeMove(int row, int col) {
        if (isValidMove(row, col)) {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public boolean isGameOver() {
        return (getWinner() != ' ' || isBoardFull());
    }

    public char getWinner() {
        char[][] board = getBoard();

        // Check rows, columns, and diagonals for a win
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == 'O') return 'O';
                else if (board[row][0] == 'X') return 'X';
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == 'O') return 'O';
                else if (board[0][col] == 'X') return 'X';
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'O') return 'O';
            else if (board[0][0] == 'X') return 'X';
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'O') return 'O';
            else if (board[0][2] == 'X') return 'X';
        }

        return ' '; // No winner yet
    }

    public char[][] getBoard() {
        return board;
    }

    public void resetBoard() {
        printboard();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ');
    }

    private boolean isBoardFull() {
        char[][] board = getBoard();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ')
                    return false; // If there is an empty cell, the board is not full
            }
        }
        return true; // All cells are occupied, and no winner, it's a draw
    }
}
