package com.example.connectfour;

public class ConnectFourGame {
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42; //Total number of discs that can exist.
    private int player = BLUE;
    private final int[][] boardGrid;

    //Initializing game board.
    public ConnectFourGame() {
        boardGrid = new int[ROW][COL];
    }

    //The entire purpose of the function below is to return which player won, and is primarily used by BoardFragment.
    public int getPlayer() {
        player = (player == BLUE) ? RED : BLUE;
        return player;
    }

    //Establishes a new game, emptying out any tokens in the board, and setting the first player to Blue.
    public void newGame() {
        player = BLUE;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = EMPTY;
            }
        }
    }

    //Returns the token found at a specific location.
    public int getDisc(int row, int col) {
        return boardGrid[row][col];
    }

    //Verifies whether the game has ended in a win or if the board is full.
    public boolean isGameOver() {
        return isBoardFull() || isWin();
    }

    //Checks the different possible win conditions, ending if one is true.
    public boolean isWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    //Verifies whether the game board is full.
    private boolean isBoardFull() {
        int count = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (boardGrid[row][col] != EMPTY) {
                    count++;
                }
            }
        }
        return count == DISCS;
    }

    //Checks if a horizontal win exist.
    private boolean checkHorizontal() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL - 3; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row][col + 1] &&
                        disc == boardGrid[row][col + 2] &&
                        disc == boardGrid[row][col + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    //Checks if a vertical win exist.
    private boolean checkVertical() {
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 0; col < COL; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col] &&
                        disc == boardGrid[row + 2][col] &&
                        disc == boardGrid[row + 3][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        //Check thew board diagonally from top-left to bottom-right for a win.
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 0; col < COL - 3; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col + 1] &&
                        disc == boardGrid[row + 2][col + 2] &&
                        disc == boardGrid[row + 3][col + 3]) {
                    return true;
                }
            }
        }

        //Check the board diagonally from top-right to bottom-left for a win.
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 3; col < COL; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col - 1] &&
                        disc == boardGrid[row + 2][col - 2] &&
                        disc == boardGrid[row + 3][col - 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    //Sets the game state.
    public void setState(String gameState) {
        int index = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                char cellState = gameState.charAt(index++);
                boardGrid[row][col] = Character.getNumericValue(cellState);
            }
        }
    }

    //Allows a player to check the column they wish to place their disc.
    public void selectDisc(int row, int col) {
        for (row = ROW - 1; row >= 0; row--) {
            if (boardGrid[row][col] == EMPTY) {
                boardGrid[row][col] = player;
                player = (player == BLUE) ? RED : BLUE; //Switch players.
                break;
            }
        }
    }

    //Grabs the current state of the game as a string.
    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardString.append(boardGrid[row][col]);
            }
        }

        return boardString.toString();
    }
}