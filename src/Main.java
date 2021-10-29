public class Main {
    public static void main(String[] args) {
        Board main_game = new Board();
        int[][] test_board = {
                {4,3,5,2,6,9,7,8,1},
                {6,8,2,5,7,1,4,9,3},
                {1,9,7,8,3,4,5,6,2},
                {8,2,6,1,9,5,3,4,7},
                {3,7,4,6,8,2,9,1,5},
                {9,5,1,7,4,3,6,2,8},
                {5,1,9,3,2,6,8,7,4},
                {2,4,8,9,5,7,1,3,6},
                {7,6,3,4,1,8,2,5,9} };
//        main_game.correct_board = test_board;
//        System.out.println(main_game.check_box(6, 1, 1));
//        System.out.println(main_game.check_box(0, 1, 3));
//        System.out.println(main_game.check_column(0, 7));
        System.out.println(main_game);
    }
}
