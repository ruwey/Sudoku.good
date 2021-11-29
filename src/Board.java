import java.util.*;

public class Board {
    int[][] correct_board;
    int[][] current_board;

    // fuck my life
    /* fuck my life: Part 2 - recursion
       I need create a function that calls its self to generate every column but the middle, thus if my function fails
       it can go back to the previous function and backtrack, good luck dumbass.
     */
    public Board() {
        correct_board = new_board();
        current_board =
    }

    public String toString() {
        String output = "╔═══╤═══╤═══╦═══╤═══╤═══╦═══╤═══╤═══╗\n";
        output += "║";
        for (int cell = 0; cell < current_board.length; cell++) {
            if (cell % 3 == 2)
                output += " " + current_board[0][cell] + " ║";
            else
                output += " " + current_board[0][cell] + " │";
        }
        for (int col = 1; col < current_board.length; col++) {
            if (col % 3 == 0)
                output += "\n╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣\n║";
            else
                output += "\n╟───┼───┼───╫───┼───┼───╫───┼───┼───╢\n║";
            for (int cell = 0; cell < current_board.length; cell++) {
                if (cell % 3 == 2)
                    output += " " + current_board[col][cell] + " ║";
                else
                    output += " " + current_board[col][cell] + " │";
            }
        }
        output += "\n╚═══╧═══╧═══╩═══╧═══╧═══╩═══╧═══╧═══╝\n";
        return output;
    }

    /**
     * Generates a fully completed sudoku boards by wrapping new_cell to abstract my specific implementation.
     * @return A full sudoku board as a two-dimensional int array (int[][])
     */
    public int[][] new_board() {
        int[][] board = new int[9][9];
        new_cell(board, 0, 0);
        return board;
    }
    /**
     * Finds a value for the specified position in board and adds it, then calls its self to fill next cell until the
     * board has been filled. This should really only be called from the new_board() method.
     * @param board the board to fill
     * @param row the row of the cell in question
     * @param column the column of the cell in question
     * @return true if the board has been successfully filled, false if it has to backtrack.
     */
    private boolean new_cell(int[][] board, int row, int column) {
        List<Integer> pos = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(pos);
        for (int num: pos) {
            if (check_column(board, column, num) && check_box(board, row, column, num) && check_row(board, row, num)) {
                board[row][column] = num;
                if (row == 8 && column == 8)
                    return true;
                else if (column == 8) {
                    row++;
                    column = 0;
                }
                else
                    column++;
                if (new_cell(board, row, column))
                    return true;
            }
        }
        board[row][column] = 0;
        return false;
    }

    /**
     * Removes values from a completed board to create an active one.
     * @param board the board to take from
     * @param difficulty amount of cells to remove
     * @return amount of 
     */

    // Checkers
    /**
     * Checks if a number can be placed in a column
     * @param board board where all of this takes place
     * @param column column where number is being put
     * @param num number in question
     * @return true if number can be put into said row, false otherwise
     */
    public boolean check_column(int[][] board, int column, int num) {
        for (int i = 0; i < 9; i++) //loop through the board
            if (board[i][column] == num)
                return false;
        return true;
    }
    /**
     * Checks if a number can be placed in a row
     * @param board board where all of this takes place
     * @param row row where number is being put
     * @param num number in question
     * @return true if number can be put into said row, false otherwise
     */
    public boolean check_row(int[][] board, int row, int num) {
        for (int i = 0; i < 9; i++) //loop through the board
            if (board[row][i] == num)
                return false;
        return true;
    }
    /**
     * Checks if a number can be placed in a box
     * @param board board to check
     * @param row row of the cell in question
     * @param column column of the cell in question
     * @param num the cell in question
     * @return true if the number could be placed in the box, false otherwise
     */
    public boolean check_box(int[][] board, int row, int column, int num) {
        for (int i = (row/3) * 3; i < (row/3+1)*3; i++) //for each row in the 3x3 box
            for (int j = (column/3) * 3; j < (column/3+1)*3; j++) //for each column in said row (in said box)
                if (board[i][j] == num) //check if the current possible number conflicts
                    return false;
        return true;
    }
}