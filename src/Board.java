import java.util.*;

public class Board {
    int[][] correct_board = new int[9][9];
    int[][] current_board;

    // fuck my life
    /* fuck my life: Part 2 - recursion
       I need create a function that calls its self to generate every column but the middle, thus if my function fails
       it can go back to the previous function and backtrack, good luck dumbass.
     */
    public Board() {
        Random r = new Random();
        for (int row = 0; row < correct_board.length; row++) {
            ArrayList<Integer> nums = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)); // she can't count ha
            new_cell(0, 0, nums, r);
        }
    }

    public String toString() {
        String output = "╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗\n";
        output += "║";
        for (int cell : correct_board[0]) {
            output += " " + cell + " ║";
        }
        for (int col = 1; col < correct_board.length; col++) {
            output += "\n╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣\n║";
            for (int cell : correct_board[col]) {
                output += " " + cell + " ║";
            }
        }
        output += "\n╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝\n";
        return output;
    }

    public boolean check_column(int column, int num) {
        for (int i = 0; i < 9; i++)
            if (this.correct_board[i][column] == num)
                return false;
        return true;
    }
    public boolean check_box(int row, int column, int num) {
        for (int i = (row/3) * 3; i < (row/3+1)*3; i++)
            for (int j = (column/3) * 3; j < (column/3+1)*3; j++) {
//                System.out.println(i + ", " + j);
                if (this.correct_board[i][j] == num)
                    return false;
            }
        return true;
    }
    private boolean new_cell(int row, int column, ArrayList<Integer> possibilities, Random r) {
        Collections.shuffle(possibilities);
        for (int num: possibilities) {
            System.out.println(row + ", " + column);
            System.out.println(this);
            if (check_column(column, num) && check_box(row, column, num)) {
                correct_board[row][column] = num;
                possibilities.remove((Integer) num);
                if (row == 8) {
                    column++;
                    row = 0;
                    ArrayList<Integer> pos = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                }
                else if (row == 8 && column ==8)
                    return true;
                else
                    row++;
                boolean ret = new_cell(row, column, possibilities, r);
                if (ret) return true;
            }
        }
        return false;
    }
}
