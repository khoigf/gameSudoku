import java.util.Random;

public class Puzzle {
    private static final int GRID_SIZE = 9;
    int[][] numbers = new int[GRID_SIZE][GRID_SIZE];
    boolean[][] isGiven = new boolean[GRID_SIZE][GRID_SIZE];

    public Puzzle() {
        super();
    }

    int[][] hardcodedNumbers = new int[SIZE][SIZE];

    public void newPuzzle() {
        generateSudoku(hardcodedNumbers);
        fillDiagonal(numbers);
        fillRemaining(numbers, 0, SIZE / 3);
        boolean[][] hardcodedIsGiven = {
                { false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false, false } };
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (hardcodedNumbers[row][col] != 0) {
                    hardcodedIsGiven[row][col] = true;
                }
            }
        }
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                isGiven[row][col] = hardcodedIsGiven[row][col];
            }
        }
    }

    public void reset() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                hardcodedNumbers[row][col] = 0;
                numbers[row][col] = 0;
            }
        }
    }

    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    public static void generateSudoku(int[][] grid) {
        fillDiagonal(grid);
        fillRemaining(grid, 0, SIZE / 3);
        removeDigits(grid);
    }

    private static void fillDiagonal(int[][] grid) {
        for (int i = 0; i < SIZE; i = i + 3) {
            fillBox(grid, i, i);
        }
    }

    private static boolean isTrue(int[][] grid, int row, int col, int num) {
        return !usedInRow(grid, row, num) &&
                !usedInCol(grid, col, num) &&
                !usedInBox(grid, row - row % 3, col - col % 3, num);
    }

    private static boolean usedInRow(int[][] grid, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInCol(int[][] grid, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInBox(int[][] grid, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (grid[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    // Random ô vuông 1,5,9
    private static void fillBox(int[][] grid, int row, int col) {
        Random random = new Random();
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = random.nextInt(10);
                } while (!isTrue(grid, row + i, col + j, num));
                grid[row + i][col + j] = num;
            }
        }
    }

    private static boolean fillRemaining(int[][] grid, int row, int col) {
        if (col >= SIZE && row < SIZE - 1) {
            row = row + 1;
            col = 0;
        }
        if (row >= SIZE && col >= SIZE) {
            return true;
        }
        if (row < SIZE / 3) {
            if (col < SIZE / 3) {
                col = SIZE / 3;
            }
        } else if (row < SIZE - SIZE / 3) {
            if (col == (int) (row / 3) * 3) {
                col = col + 3;
            }
        } else {
            if (col == SIZE - SIZE / 3) {
                row = row + 1;
                col = 0;
                if (row >= SIZE) {
                    return true;
                }
            }
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isTrue(grid, row, col, num)) {
                grid[row][col] = num;
                if (fillRemaining(grid, row, col + 1)) {
                    return true;
                }
                grid[row][col] = EMPTY;
            }
        }
        return false;
    }

    private static void removeDigits(int[][] grid) {
        Random random = new Random();
        int count = 45;
        while (count != 0) {
            int cellId = random.nextInt(SIZE * SIZE);
            int row = cellId / SIZE;
            int col = cellId % SIZE;
            if (grid[row][col] != EMPTY) {
                count--;
                grid[row][col] = EMPTY;
            }
        }
    }
}
