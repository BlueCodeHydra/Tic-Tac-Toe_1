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

import java.util.Arrays;

public class Game {
    private char[][] board;
    private char currentPlayer = 'X';
    private int size;

    public Game(int size) {
        this.size = size;
        this.board = new char[size][size];
        initializeBoard();
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
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
        for (int i = 0; i < size; i++) {
            if (checkLine(board[i])) {
                return board[i][0];
            }

            char[] column = new char[size];
            for (int j = 0; j < size; j++) {
                column[j] = board[j][i];
            }
            if (checkLine(column)) {
                return column[0];
            }
        }

        char[] diagonal1 = new char[size];
        char[] diagonal2 = new char[size];
        for (int i = 0; i < size; i++) {
            diagonal1[i] = board[i][i];
            diagonal2[i] = board[size - 1 - i][i];
        }
        if (checkLine(diagonal1)) {
            return diagonal1[0];
        }
        if (checkLine(diagonal2)) {
            return diagonal2[0];
        }

        return ' '; // No winner yet
    }

    // Updated helper method to check a line for a win
    private boolean checkLine(char[] line) {
        char currentPlayer = line[0];
        int count = 1;

        for (int i = 1; i < line.length; i++) {
            if (line[i] == currentPlayer && currentPlayer != ' ') {
                count++;
                if (count == size) {
                    return true; // Player has reached the win condition
                }
            } else {
                currentPlayer = line[i];
                count = 1;
            }
        }
        return false;
    }


    public char[][] getBoard() {
        return board;
    }

    public void resetBoard() {
        printBoard();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == ' ');
    }

    private boolean isBoardFull() {
        char[][] board = getBoard();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    return false; // If there is an empty cell, the board is not full
                }
            }
        }
        return true; // All cells are occupied, and no winner, it's a draw
    }
}
