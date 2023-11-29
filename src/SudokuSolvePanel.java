import javax.swing.*;

import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class SudokuSolvePanel extends JPanel implements ActionListener {
    private static final int SIZE = 9;
    private static final int GRID_SIZE = 9;
    private static final int CELL_SIZE = 60;
    private Cell[][] cells;
    private int[][] grid = new int[SIZE][SIZE];
    public int GameState;

    public SudokuSolvePanel() {
        GameState = 0;
        JPanel panel = new JPanel(new GridLayout(SIZE + 1, SIZE));
        cells = new Cell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new Cell(row, col);
                panel.add(cells[row][col]);
            }
        }
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col].addActionListener(this);
            }
        }
        panel.setPreferredSize(new Dimension(CELL_SIZE * SIZE, CELL_SIZE * SIZE));
        JButton Solve = new JButton("Sol");
        Solve.setBounds(200, 500, 140, 50);
        panel.add(Solve);
        JButton menu = new JButton("");
        menu.setBounds(200, 500, 60, 50);
        panel.add(menu);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
        Solve.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xử lý khi nút Solve được click
                newGame();
            }
        });
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xử lý khi nút Menu được click
                GameState = 1;
            }
        });
    }

    boolean check = true;

    public void newGame() {
        SudokuSolve sol = new SudokuSolve(grid);
        if (check) {
            sol.Issolve();
            painter();
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy giải pháp cho Sudoku");
            check = true;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);
                    grid[row][col] = 0;
                }
            }
        }

    }

    public void reset() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText("");
                cells[row][col].setEditable(true);
                grid[row][col] = 0;
            }
        }
    }

    public void painter() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText(grid[row][col] + "");
                cells[row][col].setEditable(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Cell efi = (Cell) e.getSource();
        efi.numb = Integer.parseInt(efi.getText());
        System.out.println("Input " + efi.numb);
        grid[efi.row][efi.col] = efi.numb;
        if (!checksudoku(efi.row, efi.col, efi.numb)) {
            check = false;
        }
    }

    public boolean checksudoku(int row, int col, int num) {
        // Kiểm tra hàng
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == num && i != col) {
                return false;
            }
        }

        // Kiểm tra cột
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][col] == num && i != row) {
                return false;
            }
        }

        // Kiểm tra ô vuông 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == num && startRow + i != row && startCol + j != col) {
                    return false;
                }
            }
        }

        return true;
    }
}