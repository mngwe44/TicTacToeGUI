// TicTacToeFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] buttons = new TicTacToeButton[3][3];
    private String currentPlayer = "X";  // Start with X
    private boolean gameOver = false;
    private int moveCount = 0;  // To track the number of moves made

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());
        JPanel board = new JPanel(new GridLayout(3, 3));  // 3x3 grid for the board
        initializeButtons(board);
        add(board, BorderLayout.CENTER);
        addQuitButton();
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center window
    }

    private void initializeButtons(JPanel board) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new TicTacToeButton();
                buttons[row][col].addActionListener(new ButtonListener(row, col));
                board.add(buttons[row][col]);
            }
        }
    }

    private void addQuitButton() {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Quit the application
            }
        });
        add(quitButton, BorderLayout.SOUTH);
    }

    private class ButtonListener implements ActionListener {
        private int row, col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            if (gameOver || !buttons[row][col].getState().equals("")) {
                return;  // Ignore click if the game is over or the square is already taken
            }

            buttons[row][col].setState(currentPlayer);
            moveCount++;

            if (checkForWin()) {
                JOptionPane.showMessageDialog(null, currentPlayer + " wins!");
                gameOver = true;
                promptToPlayAgain();
            } else if (isTie()) {
                JOptionPane.showMessageDialog(null, "It's a tie!");
                gameOver = true;
                promptToPlayAgain();
            } else {
                currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";  // Switch player
            }
        }
    }

    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getState().equals(currentPlayer) && buttons[i][1].getState().equals(currentPlayer) && buttons[i][2].getState().equals(currentPlayer)) {
                return true;  // Row win
            }
            if (buttons[0][i].getState().equals(currentPlayer) && buttons[1][i].getState().equals(currentPlayer) && buttons[2][i].getState().equals(currentPlayer)) {
                return true;  // Column win
            }
        }
        // Check diagonals
        if (buttons[0][0].getState().equals(currentPlayer) && buttons[1][1].getState().equals(currentPlayer) && buttons[2][2].getState().equals(currentPlayer)) {
            return true;  // Diagonal win
        }
        if (buttons[0][2].getState().equals(currentPlayer) && buttons[1][1].getState().equals(currentPlayer) && buttons[2][0].getState().equals(currentPlayer)) {
            return true;  // Diagonal win
        }
        return false;
    }

    private boolean isTie() {
        if (moveCount < 9) {
            return false;  // The game is still ongoing
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getState().equals("")) {
                    return false;  // There is still an empty space
                }
            }
        }
        return true;  // All spots are filled, it's a tie
    }

    private void promptToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);  // Exit the application
        }
    }

    private void resetGame() {
        // Reset the game for a new round
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setState("");  // Reset each button to empty
            }
        }
        currentPlayer = "X";  // Start with X again
        moveCount = 0;  // Reset move count
        gameOver = false;  // Game is not over
    }
}
