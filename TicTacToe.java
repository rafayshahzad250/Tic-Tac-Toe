import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int width = 600; // width of board ////
    int height = 750; // height of board

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JPanel text = new JPanel();
    JPanel board = new JPanel();
    JPanel bottomPanel = new JPanel(); // for functions like restart button
    JLabel leaderboardLabel = new JLabel();

    JButton[][] buttons = new JButton[3][3];
    JButton restartButton = new JButton("Restart");
    String player1 = "X";
    String player2 = "O";
    String currentPlayer = player1;

    boolean gameOver = false;
    int turns = 0; // track how many turns taken
    int player1Wins = 0; // track wins for Player 1 (X)
    int player2Wins = 0; // track wins for Player 2 (O)

    TicTacToe() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // centers frame
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        label.setBackground(Color.darkGray);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.BOLD, 50));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setText("Tic-Tac-Toe");
        label.setOpaque(true);

        panel.setLayout(new BorderLayout());
        panel.add(label);
        frame.add(panel, BorderLayout.NORTH);

        board.setLayout(new GridLayout(3, 3));
        board.setBackground(Color.darkGray);
        frame.add(board);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton tile = new JButton();
                buttons[i][j] = tile;
                board.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (gameOver)
                            return;
                        JButton tile = (JButton) event.getSource();
                        if (tile.getText() == "") {
                            tile.setText(currentPlayer);

                            if (currentPlayer.equals(player1)) {
                                tile.setForeground(new Color(173, 216, 230));
                            } else {
                                tile.setForeground(new Color(255, 182, 193));
                            }

                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
                                label.setText(currentPlayer + " 's turn.");
                            }

                        }

                    }
                });

            }
        }

        bottomPanel.setLayout(new GridLayout(2, 1));

        // Leaderboard configuration
        leaderboardLabel = new JLabel(
                "<html><span style='color: rgb(173, 216, 230);'>Player 1 (X): 0</span> " +
                        "<span style='color: white;'>|</span> " +
                        "<span style='color: rgb(255, 182, 193);'>Player 2 (O): 0</span></html>");
        leaderboardLabel.setFont(new Font("Arial", Font.BOLD, 20));
        leaderboardLabel.setHorizontalAlignment(JLabel.CENTER);
        leaderboardLabel.setOpaque(true);
        leaderboardLabel.setBackground(Color.darkGray);

        bottomPanel.add(leaderboardLabel);

        // Restart button configuration
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setFocusable(false);
        restartButton.setBackground(Color.gray);
        restartButton.setForeground(Color.white);
        restartButton.setOpaque(true);
        restartButton.setContentAreaFilled(true);
        restartButton.setBorderPainted(false);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        bottomPanel.add(restartButton);

        // Add the bottom panel to the frame
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    void checkWinner() {
        // check horizontal 3 in a row
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText() == "")
                continue;

            if (buttons[i][0].getText() == buttons[i][1].getText() &&
                    buttons[i][1].getText() == buttons[i][2].getText()) {
                for (int j = 0; j < 3; j++) {
                    setWinner(buttons[i][j]);
                }
                updateScore();
                gameOver = true;
                return;
            }
        }

        // check vertical 3 in a row
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText() == "")
                continue;

            if (buttons[0][j].getText() == buttons[1][j].getText() &&
                    buttons[1][j].getText() == buttons[2][j].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(buttons[i][j]);
                }
                updateScore();
                gameOver = true;
                return;
            }
        }

        // check diagonally 3 in a row fom top left to bottom right
        if (buttons[0][0].getText() == buttons[1][1].getText()
                && buttons[1][1].getText() == buttons[2][2].getText()
                && buttons[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(buttons[i][i]);
            }
            updateScore();
            gameOver = true;
            return;

        }

        // check diagonally 3 in a row fom top right to bottom left
        if (buttons[0][2].getText() == buttons[1][1].getText()
                && buttons[1][1].getText() == buttons[2][0].getText()
                && buttons[0][2].getText() != "") {
            setWinner(buttons[0][2]);
            setWinner(buttons[1][1]);
            setWinner(buttons[2][0]);
            updateScore();
            gameOver = true;
            return;

        }

        if (turns == 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    tieGame(buttons[i][j]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton button) {
        button.setForeground(Color.green);
        button.setBackground(Color.gray);
        label.setText(currentPlayer + " is the winner!");

    }

    void tieGame(JButton button) {
        button.setForeground(Color.orange);
        button.setBackground(Color.gray);
        label.setText("Tie Game!");
    }

    void resetGame() {
        // Reset the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.darkGray);
                buttons[i][j].setForeground(Color.white);
            }
        }

        // Reset variables
        currentPlayer = player1;
        turns = 0;
        gameOver = false;

        // Reset label
        label.setText("Tic-Tac-Toe");
    }

    void updateScore() {
        if (currentPlayer.equals(player1)) {
            player1Wins++;
        } else {
            player2Wins++;
        }

        leaderboardLabel.setText(
                "<html><span style='color: rgb(173, 216, 230);'>Player 1 (X): " + player1Wins + "</span> " +
                        "<span style='color: white;'>|</span> " +
                        "<span style='color: rgb(255, 182, 193);'>Player 2 (O): " + player2Wins + "</span></html>");
    }
}
