package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final int player1;
    private final int player2;
    private Figure[][] board;
    private FigColor whoseTurn;
    private Pair chosenField;
    private List<Pair> possbileMoves;

    public Game(int player1, int player2) {
        this.player1 = player1;
        this.player2 = player2;
        createBoard();
        whoseTurn = FigColor.WHITE;
        chosenField = new Pair(0, 0);
        possbileMoves = new ArrayList<>();
    }

    private void createBoard() {
        board = new Figure[9][9];

        // Czarne figury
        board[1][1] = new Rook(FigColor.BLACK, new Pair(1,1));
        board[1][2] = new Knight(FigColor.BLACK, new Pair(1,2));
        board[1][3] = new Bishop(FigColor.BLACK, new Pair(1,3));
        board[1][4] = new Queen(FigColor.BLACK, new Pair(1,4));
        board[1][5] = new King(FigColor.BLACK, new Pair(1,5));
        board[1][6] = new Bishop(FigColor.BLACK, new Pair(1,6));
        board[1][7] = new Knight(FigColor.BLACK, new Pair(1,7));
        board[1][8] = new Rook(FigColor.BLACK, new Pair(1,8));

        // Czarne pionki
        for (int i = 1; i <= 8; i++) {
            board[2][i] = new Pawn(FigColor.BLACK, new Pair(2,i));
        }

        // Białe pionki
        for (int i = 1; i <= 8; i++) {
            board[7][i] = new Pawn(FigColor.WHITE, new Pair(7,i));
        }

        // Białe figury
        board[8][1] = new Rook(FigColor.WHITE, new Pair(8,1));
        board[8][2] = new Knight(FigColor.WHITE, new Pair(8,2));
        board[8][3] = new Bishop(FigColor.WHITE, new Pair(8,3));
        board[8][4] = new Queen(FigColor.WHITE, new Pair(8,4));
        board[8][5] = new King(FigColor.WHITE, new Pair(8,5));
        board[8][6] = new Bishop(FigColor.WHITE, new Pair(8,6));
        board[8][7] = new Knight(FigColor.WHITE, new Pair(8,7));
        board[8][8] = new Rook(FigColor.WHITE, new Pair(8,8));
    }

    public Figure[][] getBoard() {
        return board;
    }

    public FigColor getWhoseTurn() {
        return whoseTurn;
    }

    public Pair getChosenField() {
        return chosenField;
    }

    public void setChosenField(int row, int column) {
        chosenField.setPair(row, column);
    }
}
