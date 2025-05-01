/*
 * Copyright (c) 2021.  Bradley M. Small
 * All Rights Reserved
 *
 */

package com.small.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Main application class for the Tic-Tac-Toe game, responsible for GUI setup and event coordination.
 */
public class App extends JFrame implements GameEventListener {
    private final transient TicTacToeGamePlayer game;
    private final GameBoardPanel boardPanel;
    private final JLabel statusLabel;
    private final JLabel scoreLabel;
    private final transient ScoreTracker scoreTracker;
    private transient AIPlayer aiPlayer;
    private boolean isSinglePlayer;
    private boolean awaitingAIMove; // Prevent human moves during AI turn

    /**
     * Constructs the application with dependencies and initializes the GUI.
     */
    public App(TicTacToeGamePlayer game, GameBoardPanel boardPanel, ScoreTracker scoreTracker, AIPlayer aiPlayer) {
        this.game = Objects.requireNonNull(game, "Game cannot be null");
        this.boardPanel = Objects.requireNonNull(boardPanel, "Board panel cannot be null");
        this.scoreTracker = Objects.requireNonNull(scoreTracker, "Score tracker cannot be null");
        this.aiPlayer = Objects.requireNonNull(aiPlayer, "AI player cannot be null");
        this.statusLabel = new JLabel("Player X's Turn");
        this.scoreLabel = new JLabel(scoreTracker.getScoreDisplay());
        this.isSinglePlayer = false;
        initGUI();
        this.awaitingAIMove = false;
    }

    /**
     * Sets up the GUI with status, score, board, and buttons.
     */
    private void initGUI() {
        validateWindowConfig();
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        pack();
        setSize(BoardConfig.WINDOW_WIDTH, BoardConfig.WINDOW_HEIGHT + 120);
        setLocationRelativeTo(null);
        setVisible(true);
        boardPanel.newGame();
    }

    private void validateWindowConfig() {
        if (BoardConfig.WINDOW_WIDTH <= 0 || BoardConfig.WINDOW_HEIGHT <= 0) {
            throw new IllegalStateException("Window dimensions must be positive");
        }
        if (BoardConfig.BOARD_SIZE <= 0) {
            throw new IllegalStateException("Board size must be positive");
        }
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(BoardConfig.BACKGROUND_COLOR);
        configureLabel(statusLabel, Font.BOLD, 16);
        topPanel.add(statusLabel);
        configureLabel(scoreLabel, Font.PLAIN, 15);
        topPanel.add(scoreLabel);
        return topPanel;
    }

    private void configureLabel(JLabel label, int fontStyle, int fontSize) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", fontStyle, fontSize));
        label.setForeground(Color.WHITE);
        label.setBackground(BoardConfig.BACKGROUND_COLOR);
        label.setOpaque(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BoardConfig.BACKGROUND_COLOR);
        buttonPanel.add(createButton("New Game", e -> onNewGame()));
        buttonPanel.add(createButton("Reset Scores", e -> resetScores()));
        buttonPanel.add(createToggleModeButton());
        return buttonPanel;
    }

    private JButton createButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(listener);
        return button;
    }

    private JButton createToggleModeButton() {
        JButton toggleModeButton = createButton("Single-Player Mode", e -> {
        });
        toggleModeButton.addActionListener(e -> toggleGameMode(toggleModeButton));
        return toggleModeButton;
    }

    private void toggleGameMode(JButton toggleModeButton) {
        isSinglePlayer = !isSinglePlayer;
        Logger.debug("App.toggleGameMode: isSinglePlayer set to " + isSinglePlayer);
        toggleModeButton.setText(isSinglePlayer ? "Two-Player Mode" : "Single-Player Mode");
        onNewGame();
    }

    private void resetScores() {
        scoreTracker.reset();
        scoreLabel.setText(scoreTracker.getScoreDisplay());
    }

    /**
     * Creates a new game instance with dependencies.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGamePlayer game = new TicTacToeGame();
            GameBoardPanel boardPanel = new GameBoardPanel(game, null);
            ScoreTracker scoreTracker = new ScoreTracker();
            App app = new App(game, boardPanel, scoreTracker, new AIPlayer((BoardReader) game, game, null));
            boardPanel.setListener(app);
            AIPlayer aiPlayer = new AIPlayer((BoardReader)game, game, app);
            app.setAIPlayer(aiPlayer);
        });
    }

    public void setAIPlayer(AIPlayer aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    @Override
    public void onNewGame() {
        awaitingAIMove = false;
        scoreTracker.updateScore(game.getResult());
        scoreLabel.setText(scoreTracker.getScoreDisplay());
        boardPanel.newGame();
    }

    @Override
    public void onExit() {
        System.exit(0);
    }

    @Override
    public void updateStatus() {
        GameResult result = game.getResult();
        statusLabel.setText(result == GameResult.ONGOING
                ? "Player " + game.getCurrentPlayer().name().replace("CROSS", "X").replace("NOUGHT", "O") + "'s Turn"
                : result.getDisplayText());
    }

    @Override
    public void onMoveMade(int row, int column, boolean isAIMove) {
        Logger.debug("App.onMoveMade: Received move at (" + row + ", " + column + "), isAIMove=" + isAIMove + ", isSinglePlayer=" + isSinglePlayer + ", currentPlayer=" + game.getCurrentPlayer() + ", gameResult=" + game.getResult());
        if (isAIMove) {
            boardPanel.setTileForAI(row, column, TileValue.NOUGHT);
            awaitingAIMove = false;
        } else if (!awaitingAIMove) {
            boardPanel.playSquare(row, column);
        } else {
            Logger.debug("App.onMoveMade: Ignoring human move at (" + row + ", " + column + ") while awaiting AI move");
            return;
        }
        updateStatus();
        if (game.getResult() == GameResult.ONGOING && isSinglePlayer && !isAIMove) {
            awaitingAIMove = true;
            aiPlayer.executeMove();
        }
    }
}