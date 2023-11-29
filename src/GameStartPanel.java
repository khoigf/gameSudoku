import java.awt.*;
import java.awt.event.MouseAdapter;

import javax.swing.*;

import java.awt.event.MouseEvent;

public class GameStartPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int GRID_SIZE = 9;
    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH = CELL_SIZE * GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    public int GameState;

    public GameStartPanel() {
        GameState = 0;
        super.setLayout(new GridLayout(GRID_SIZE + 1, GRID_SIZE));

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);
            }
        }
        CellInputListener listener = new CellInputListener();
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        JButton menu = new JButton("");
        menu.setBounds(200, 500, 140, 50);
        super.add(menu, BorderLayout.CENTER);
        setVisible(true);
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GameState = 2;
            }
        });
    }

    public void newGame() {
        puzzle.newPuzzle();
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }

    }

    public int checkState() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.WIN) {
                    return 2;
                }
            }
        }
        return 0;
    }

    public void reset() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col].newGame(0, false);
            }
        }
        puzzle.reset();
    }

    public boolean isSolved() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

}