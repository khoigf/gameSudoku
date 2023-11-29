import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App extends JFrame implements Runnable {
    public int GameState = 0;
    Thread gameThread;
    JFrame window = new JFrame();
    GameStartPanel gamePanel = new GameStartPanel();
    SudokuSolvePanel sudokuframe = new SudokuSolvePanel();
    JPanel panel = new JPanel();

    public App() {
        // Tạo panel chứa các nút
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(540, 540);
        window.setBackground(Color.BLACK);
        window.setTitle("Sudoku Game");
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.newGame();
        app.startGameThread();
    }

    public void newGame() {
        GameState = 0;
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(150, 100, 240, 100);
        JButton solveButton = new JButton("Solve sudoku");
        solveButton.setBounds(150, 300, 240, 100);
        JButton ExitButton = new JButton("Exit");
        ExitButton.setBounds(385, 450, 140, 50);
        panel.setLayout(null);
        panel.add(startButton, BorderLayout.CENTER);
        panel.add(solveButton, BorderLayout.CENTER);
        panel.add(ExitButton, BorderLayout.CENTER);
        window.add(panel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xử lý khi nút Start Game được click
                window.remove(panel);
                window.add(gamePanel);
                gamePanel.newGame();
                window.setPreferredSize(new Dimension(540, 600));
                window.pack();
            }
        });
        solveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xử lý khi nút SolveSudoku được click
                window.remove(panel);
                window.add(sudokuframe);
                window.setPreferredSize(new Dimension(540, 600));
                window.pack();
            }
        });
        ExitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xử lý khi nút Exit được click
                System.exit(0);
            }
        });
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (gamePanel.checkState() != 0) {
            GameState = 2;
        }
        if (sudokuframe.GameState != 0) {
            GameState = 1;
        }
        if (gamePanel.GameState != 0) {
            GameState = 2;
        }
        if (GameState == 1) {
            window.remove(sudokuframe);
            sudokuframe.reset();
            window.add(panel);
            window.setPreferredSize(new Dimension(540, 540));
            window.pack();
            window.setVisible(true);
            GameState = 0;
            sudokuframe.GameState = 0;
        }
        if (GameState == 2) {
            window.remove(gamePanel);
            gamePanel.reset();
            window.add(panel);
            window.setPreferredSize(new Dimension(540, 540));
            window.pack();
            window.setVisible(true);
            GameState = 0;
            gamePanel.GameState = 0;
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / 60;
        double denta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            denta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (denta >= 1) {
                update();
                denta--;
            }
            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }
}
