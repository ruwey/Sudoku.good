import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorPalette;
import org.apache.commons.lang3.time.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize Terminal Graphics
        Terminal term = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(50, 33))
                .setTerminalEmulatorColorConfiguration(TerminalEmulatorColorConfiguration.newInstance(new TerminalEmulatorPalette(
                        Color.BLACK,
                        Color.BLACK,
                        Color.WHITE,
                        Color.BLACK,
                        Color.BLACK,
                        new Color(133, 66, 45),
                        new Color(229, 69, 46),
                        new Color(25, 112, 25),
                        new Color(27, 199, 27),
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.BLUE,
                        Color.BLUE,
                        Color.MAGENTA,
                        Color.MAGENTA,
                        Color.CYAN,
                        Color.CYAN,
                        Color.WHITE,
                        Color.WHITE)))
                .createTerminal();
        Screen main_screen = new TerminalScreen(term);
        main_screen.startScreen();
        main_screen.setCursorPosition(null);

        // Draw Loop
        draw(main_screen);

        main_screen.stopScreen();
    }

    static void draw(Screen scr) throws InterruptedException, IOException {
        while (true) {
            scr.clear();
            switch (main_menu(scr)) {
                case "Start" -> sudoku_game(scr);
                case "How To Play" -> tutorial(scr);
                case "Credits" -> credits(scr);
                case "Quit" -> {
                    return;
                }
            }
        }
    }

    static String main_menu(Screen scr) throws InterruptedException, IOException {
        String[] options = {"Start", "How To Play", "Credits", "Quit"};
        int selected = 0;

        while (true) {
            // Title
            draw_center(scr, 3,"""
                   ^G┌┬┐┌─┐┬─┐┌┬┐┬┌┐┌┌─┐┬
                    │ ├┤ ├┬┘│││││││├─┤│
                    ┴ └─┘┴└─┴ ┴┴┘└┘┴ ┴┴─┘
                     ┌─┐┬ ┬┌┬┐┌─┐┬┌─┬ ┬
                     └─┐│ │ │││ │├┴┐│ │
                     └─┘└─┘─┴┘└─┘┴ ┴└─┘
                    """);
            for (int i = 0; i < options.length; i++) {
                String option;
                if (i == selected)
                    option = "^r^W" + options[i];
                else
                    option = "^R" + options[i];
                draw_center(scr, 12 + 2 * i, option);
            }
            scr.refresh();
            switch (scr.readInput().getKeyType()) {
                case ArrowUp:
                    selected--;
                    break;
                case ArrowDown:
                    selected++;
                    break;
                case Enter:
                    return options[selected];
            }
            if (selected == -1)
                selected = 3;
            else if (selected == 4)
                selected = 0;
        }
    }

    static void sudoku_game(Screen scr) throws IOException {
        Board sudoku = new Board();
        StopWatch s = new StopWatch();
        s.start();
        while (true) {
            draw_center(scr, 12, sudoku.toString());
            scr.refresh();
            KeyStroke input = scr.readInput();
            switch (input.getKeyType()) {
                case ArrowUp -> sudoku.move(-1, 0);
                case ArrowDown -> sudoku.move(1, 0);
                case ArrowLeft -> sudoku.move(0, -1);
                case ArrowRight -> sudoku.move(0, 1);
                case Escape -> {return;}
                case Character -> {
                    char c = input.getCharacter();
                    if (c >= '0' && c <= '9')
                        sudoku.enter((int)c - 48);
                }
            }

            if (sudoku.check_complete())
                break;
        }
        s.stop();
        System.out.println("Game completed in " + (double)s.getTime()/1000 + " seconds");
    }

    static void tutorial(Screen scr) throws IOException {
        scr.clear();
        // Title
        draw_center(scr, 3,"""
                   ^G┌┬┐┌─┐┬─┐┌┬┐┬┌┐┌┌─┐┬
                    │ ├┤ ├┬┘│││││││├─┤│
                    ┴ └─┘┴└─┴ ┴┴┘└┘┴ ┴┴─┘
                     ┌─┐┬ ┬┌┬┐┌─┐┬┌─┬ ┬
                     └─┐│ │ │││ │├┴┐│ │
                     └─┘└─┘─┴┘└─┘┴ ┴└─┘
                    """);
        draw_center(scr, 12, """
                ^GSudoku^B is a game in which one must complete
                a board such that ^Gevery line, column, and 3x3
                box^B has every number from ^Gone to nine.^B Use
                the arrow keys and number keys to navigate 
                and fill out the board.
                """);

        scr.refresh();

        scr.readInput();
    }

    static void credits (Screen scr) throws IOException {
        scr.clear();
        // Title
        draw_center(scr, 3,"""
                   ^G┌┬┐┌─┐┬─┐┌┬┐┬┌┐┌┌─┐┬
                    │ ├┤ ├┬┘│││││││├─┤│
                    ┴ └─┘┴└─┴ ┴┴┘└┘┴ ┴┴─┘
                     ┌─┐┬ ┬┌┬┐┌─┐┬┌─┬ ┬
                     └─┐│ │ │││ │├┴┐│ │
                     └─┘└─┘─┴┘└─┘┴ ┴└─┘
                    """);
        draw_text(scr, 4, 12, """
                Credits:
                
                Gordon Dewey - Actual Work
                Matt De Robles - Chill Cool Guy
                Aramie Ewen - Emotional Support
                Mr Turner - Helping Me Figure Out
                            My Algorithm
                Mr Holmer - Creating Beauty""");

        scr.refresh();

        scr.readInput();
    }

    // Helpers
    static void draw_text(Screen scr, int col, int row, String text) {
        boolean special = false;
        int initial_col = col;
        TextGraphics graph = scr.newTextGraphics();
        // Default FG/BG
        graph.setBackgroundColor(new TextColor.RGB(255, 255, 255));
        graph.setForegroundColor(new TextColor.RGB(0, 0, 0));
        for (String cell: text.split("(?<!^)")) {
            if (special) {
                // Special Parsing
                switch (cell) {
                    case "R" -> graph.setForegroundColor(TextColor.ANSI.RED);
                    case "r" -> graph.setBackgroundColor(TextColor.ANSI.RED);
                    case "Q" -> graph.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                    case "G" -> graph.setForegroundColor(TextColor.ANSI.GREEN);
                    case "g" -> graph.setBackgroundColor(TextColor.ANSI.GREEN);
                    case "H" -> graph.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                    case "X" -> graph.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                    case "W" -> graph.setForegroundColor(TextColor.ANSI.WHITE);
                    case "w" -> graph.setBackgroundColor(TextColor.ANSI.WHITE);
                    case "B" -> graph.setForegroundColor(TextColor.ANSI.BLACK);
                    case "b" -> graph.setBackgroundColor(TextColor.ANSI.BLACK);
                }
                special = false;
            }
            else if (cell.equals("^"))
                special = true;
            else if (cell.equals("\n")) {
                row++;
                col = initial_col;
            }
            else {
                graph.putString(col, row, cell);
                col++;
            }
        }
    }

    static void draw_center(Screen scr, int row, String text) {
        int col = 0;
        for (char i: text.toCharArray()) {
            if (i == '\n')
                break;
            else if (i == '^')
                col--;
            else
                col++;
        }
        col = scr.getTerminalSize().getColumns()/2 - col/2;
        draw_text(scr, col, row, text);
    }
}
