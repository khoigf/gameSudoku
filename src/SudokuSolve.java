public class SudokuSolve {
    private static final int SIZE = 9;
    private int[][] grid;

    public SudokuSolve(int[][] grid) {
        this.grid = grid;
    }

    public boolean Issolve() {
        int row = 0;
        int col = 0;

        // Tìm ô trống đầu tiên
        boolean isEmpty = false;
        for (row = 0; row < SIZE; row++) {
            for (col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    isEmpty = true;
                    break;
                }
            }
            if (isEmpty) {
                break;
            }
        }

        // Nếu không còn ô trống, Sudoku đã được giải
        if (!isEmpty) {
            return true;
        }

        // Thử các số từ 1 đến 9 vào ô trống
        for (int num = 1; num <= SIZE; num++) {
            if (isTrue(row, col, num)) {
                grid[row][col] = num;
                if (Issolve()) {
                    return true;
                }
                grid[row][col] = 0; // Quay lui nếu không tìm được giải pháp
            }
        }

        return false;
    }

    public boolean isTrue(int row, int col, int num) {
        // Kiểm tra hàng
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // Kiểm tra cột
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        // Kiểm tra ô vuông 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

}