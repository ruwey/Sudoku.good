import java.util.*;

public class Board {
    private final int[][] correct_board;
    private int[][] current_board;
    private int[] pos;

    public Board() {
        correct_board = new_board();
        current_board = remove(correct_board, 2);
        pos = new int[]{0, 0};
    }
    public Board(int diff) {
        correct_board = new_board();
        current_board = remove(correct_board, diff);
        pos = new int[]{0, 0};
    }

    public String toString() {
        String output = "╔═══╤═══╤═══╦═══╤═══╤═══╦═══╤═══╤═══╗\n";
        output += "║";
        for (int row = 0; row < current_board.length; row++) {
            if (row > 0) {
                if (row % 3 == 0)
                    output += "\n╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣\n║";
                else
                    output += "\n╟───┼───┼───╫───┼───┼───╫───┼───┼───╢\n║";
            }
            for (int col = 0; col < current_board.length; col++) {
                String prefix = "";
                int mul = 1;
                if (current_board[row][col] == 0) // if empty, make red
                    prefix = "^Q";
                else if (current_board[row][col] < 0) {
                    prefix = "^H";
                    mul = -1;
                }
                if (row == pos[0] && col == pos[1]) // if selected, flip colors
                    prefix = "^g^W";
                if (col % 3 == 2)
                    output += " " + prefix + (current_board[row][col] * mul) + "^w^B ║";
                else
                    output += " " + prefix + (current_board[row][col] * mul) + "^w^B │";
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
     * @return a board that has a set amount of cell missing
     */
    public int[][] remove(int[][] board, int difficulty) {
        int[][] new_board = new int[9][9];
        for (int i = 0; i < board.length; i++)
            new_board[i] = board[i].clone();
        int[] pos;
        while (difficulty > 0) {
            pos = new int[]{(int) (Math.random() * 9), (int) (Math.random() * 9)};
            if (new_board[pos[0]][pos[1]] != 0) {
                new_board[pos[0]][pos[1]] = 0;
                difficulty--;
            }
        }
        return new_board;
    }

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
    /**
     * Checks if the current_board is equal to the correct_board. Meant to be used to detect a win state in main method.
     * @return true if the user has correctly completed the board, false otherwise
     */
    public boolean check_complete() {
        for (int row = 0; row < current_board.length; row++)
            for (int col = 0; col < current_board[row].length; col++)
                if (current_board[row][col] <= 0) {
                    if (current_board[row][col] * -1 != correct_board[row][col])
                        return false;
                }
        return true;
    }

    // Code for interacting with the board
    // Movement Code
    /**
     * Moves the user's selected cell, keeping the user in bounds
     * @param col amount/direction of columns to move
     * @param row amount/direction of rows to move
     */
    public void move(int col, int row) {
        pos[0] += col;
        pos[1] += row;
        if (pos[0] > 8)
            pos[0]--;
        else if (pos[0] < 0)
            pos[0]++;
        if (pos[1] > 8)
            pos[1]--;
        else if (pos[1] < 0)
            pos[1]++;
    }
    // Enter Values
    /**
     * Enters a user guess as to the value of the selected cell, does it negatively to distinguish between these and
     * the cells set by the program
     * @param val value to enter
     */
    public void enter(int val) {
        if (current_board[pos[0]][pos[1]] <= 0) {
            current_board[pos[0]][pos[1]] = val * -1;
        }
    }
}