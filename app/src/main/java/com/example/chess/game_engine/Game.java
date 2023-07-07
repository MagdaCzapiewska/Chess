package com.example.chess.game_engine;

import static java.lang.Math.abs;

import com.example.chess.FigColor;
import com.example.chess.GameStatus;

import java.util.List;

public class Game {
    int numberOfColumns = 8;
    int numberOfRows = 8;

    private int[] player_id;
    private Figure[][] board;
    private FigColor whoseTurn;
    private final Pair chosenField;
    private final LastMove lastMove;
    private final Pair[] kingPosition;

    public Game(int[] player_id) {
        this.player_id = player_id;
        createBoard();
        whoseTurn = FigColor.WHITE;
        chosenField = new Pair(0, 0);
        lastMove = new LastMove();

        kingPosition = new Pair[2];
        kingPosition[0] = new Pair(8,5);
        kingPosition[1] = new Pair(1,5);
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

    public void switchTurn() {
        if (whoseTurn == FigColor.WHITE)
            whoseTurn = FigColor.BLACK;
        else
            whoseTurn = FigColor.WHITE;
    }

    public Pair getChosenField() {
        return chosenField;
    }

    public void setChosenField(int row, int column) {
        chosenField.setPair(row, column);
    }

    public LastMove getLastMove() {
        return lastMove;
    }

    public void setLastMove(int from_row, int from_column, int to_row, int to_column, Figure figure) {
        lastMove.setLastMove(from_row, from_column, to_row, to_column, figure);
    }

    public Pair getLastMoveTo() {
        return lastMove.to;
    }

    public boolean wasDoublePawnMove() {
        return lastMove.wasDoublePawnMove();
    }

    public void moveKing(int row, int column) {
        if (whoseTurn == FigColor.WHITE)
            kingPosition[0].setPair(row, column);
        else
            kingPosition[1].setPair(row, column);
    }

    public GameStatus getStatus() {
        boolean isThereAMove = false;
        for (int i = 1; i <= numberOfRows && !isThereAMove; i++) {
            for (int j = 1; j <= numberOfColumns && !isThereAMove; j++) {
                if (board[i][j] != null && board[i][j].getColor() == whoseTurn) {
                    List<Pair> moves = board[i][j].getMoves(this);
                    if (!moves.isEmpty()) {
                        isThereAMove = true;
                        moves.clear();
                    }
                }
            }
        }
        boolean isCheck = isCheck();
        if (isThereAMove && isCheck) {
            return GameStatus.CHECK;
        }
        if (!isThereAMove && isCheck) {
            return GameStatus.CHECKMATE;
        }
        if (!isThereAMove) {    // na pewno isCheck == false
            return GameStatus.STALEMATE;
        }
        return GameStatus.NOTHING;
    }

    public boolean isCheck() {
        int color = (whoseTurn == FigColor.WHITE ? 0 : 1);
        int kingRow = kingPosition[color].row; int kingColumn = kingPosition[color].column;
        int x, y;

        // Ruch poprzeczny
        x = kingRow + 1;
        y = kingColumn + 1;
        while (x <= numberOfRows && y <= numberOfColumns && board[x][y] == null) { x++; y++; }
        if (x <= numberOfRows && y <= numberOfColumns) {    // Wiemy, że (board[x][y] != null)
            Figure potentialAttacker = board[x][y];
            if (potentialAttacker.getColor() != whoseTurn) {
                if (potentialAttacker instanceof Bishop || potentialAttacker instanceof Queen)
                    return true;
                if (potentialAttacker instanceof Pawn && abs(kingRow - x) == 1 && abs(kingColumn - y) == 1)
                    return true;
            }
        }

        x = kingRow + 1;
        y = kingColumn - 1;
        while (x <= numberOfRows && y > 0 && board[x][y] == null) { x++; y--; }
        if (x <= numberOfRows && y > 0) {    // Wiemy, że (board[x][y] != null)
            Figure potentialAttacker = board[x][y];
            if (potentialAttacker.getColor() != whoseTurn) {
                if (potentialAttacker instanceof Bishop || potentialAttacker instanceof Queen)
                    return true;
                if (potentialAttacker instanceof Pawn && abs(kingRow - x) == 1 && abs(kingColumn - y) == 1)
                    return true;
            }
        }

        x = kingRow - 1;
        y = kingColumn + 1;
        while (x > 0 && y <= numberOfColumns && board[x][y] == null) { x--; y++; }
        if (x > 0 && y <= numberOfColumns) {    // Wiemy, że (board[x][y] != null)
            Figure potentialAttacker = board[x][y];
            if (potentialAttacker.getColor() != whoseTurn) {
                if (potentialAttacker instanceof Bishop || potentialAttacker instanceof Queen)
                    return true;
                if (potentialAttacker instanceof Pawn && abs(kingRow - x) == 1 && abs(kingColumn - y) == 1)
                    return true;
            }
        }

        x = kingRow - 1;
        y = kingColumn - 1;
        while (x > 0 && y > 0 && board[x][y] == null) { x--; y--; }
        if (x > 0 && y > 0) {    // Wiemy, że (board[x][y] != null)
            Figure potentialAttacker = board[x][y];
            if (potentialAttacker.getColor() != whoseTurn) {
                if (potentialAttacker instanceof Bishop || potentialAttacker instanceof Queen)
                    return true;
                if (potentialAttacker instanceof Pawn && abs(kingRow - x) == 1 && abs(kingColumn - y) == 1)
                    return true;
            }
        }

        // Ruch w boki
        x = kingRow + 1;
        y = kingColumn;
        while (x <= numberOfRows && board[x][y] == null) { x++; }
        if (x <= numberOfRows) {    // Wiemy, że (board[x][y] != null)
            Figure potentialAttacker = board[x][y];
            if (potentialAttacker.getColor() != whoseTurn) {
                if (potentialAttacker instanceof Rook || potentialAttacker instanceof Queen)
                    return true;
            }
        }

        x = kingRow;
        y = kingColumn + 1;
        while (y <= numberOfColumns && board[x][y] == null) { y++; }
        if (y <= numberOfColumns) {    // Wiemy, że (board[x][y] != null)
            Figure potentialAttacker = board[x][y];
            if (potentialAttacker.getColor() != whoseTurn) {
                if (potentialAttacker instanceof Rook || potentialAttacker instanceof Queen)
                    return true;
            }
        }

        x = kingRow - 1;
        y = kingColumn;
        while (x > 0 && board[x][y] == null) { x--; }
        if (x > 0) {    // Wiemy, że (board[x][y] != null)
            Figure potentialAttacker = board[x][y];
            if (potentialAttacker.getColor() != whoseTurn) {
                if (potentialAttacker instanceof Rook || potentialAttacker instanceof Queen)
                    return true;
            }
        }

        x = kingRow;
        y = kingColumn - 1;
        while (y > 0 && board[x][y] == null) { y--; }
        if (y > 0) {    // Wiemy, że (board[x][y] != null)
            Figure potentialAttacker = board[x][y];
            if (potentialAttacker.getColor() != whoseTurn) {
                if (potentialAttacker instanceof Rook || potentialAttacker instanceof Queen)
                    return true;
            }
        }

        // Ruchy skoczka
        x = kingRow + 2;
        y = kingColumn + 1;
        if (x <= numberOfRows && y <= numberOfColumns && board[x][y] != null
                && board[x][y].getColor() != whoseTurn && board[x][y] instanceof Knight) {
                return true;
        }

        x = kingRow + 1;
        y = kingColumn + 2;
        if (x <= numberOfRows && y <= numberOfColumns && board[x][y] != null
                && board[x][y].getColor() != whoseTurn && board[x][y] instanceof Knight) {
            return true;
        }

        x = kingRow - 1;
        y = kingColumn + 2;
        if (x > 0 && y <= numberOfColumns && board[x][y] != null
                && board[x][y].getColor() != whoseTurn && board[x][y] instanceof Knight) {
            return true;
        }

        x = kingRow - 2;
        y = kingColumn + 1;
        if (x > 0 && y <= numberOfColumns && board[x][y] != null
                && board[x][y].getColor() != whoseTurn && board[x][y] instanceof Knight) {
            return true;
        }

        x = kingRow - 2;
        y = kingColumn - 1;
        if (x > 0 && y > 0 && board[x][y] != null
                && board[x][y].getColor() != whoseTurn && board[x][y] instanceof Knight) {
            return true;
        }

        x = kingRow - 1;
        y = kingColumn - 2;
        if (x > 0 && y > 0 && board[x][y] != null
                && board[x][y].getColor() != whoseTurn && board[x][y] instanceof Knight) {
            return true;
        }

        x = kingRow + 1;
        y = kingColumn - 2;
        if (x <= numberOfRows && y > 0 && board[x][y] != null
                && board[x][y].getColor() != whoseTurn && board[x][y] instanceof Knight) {
            return true;
        }

        x = kingRow + 2;
        y = kingColumn - 1;
        if (x <= numberOfRows && y > 0 && board[x][y] != null
                && board[x][y].getColor() != whoseTurn && board[x][y] instanceof Knight) {
            return true;
        }

        return false;
    }

    static class LastMove {
        public Pair from;
        public Pair to;
        public Figure figure;

        public LastMove() {
            from = new Pair(0,0);
            to = new Pair(0,0);
            figure = null;
        }

        public void setLastMove(int from_row, int from_column, int to_row, int to_column, Figure figure) {
            from.setPair(from_row, from_column);
            to.setPair(to_row, to_column);
            this.figure = figure;
        }

        public boolean wasDoublePawnMove() {
            if (figure instanceof Pawn) {
                return abs(from.row - to.row) == 2 && from.column == to.column;
            }

            return false;
        }
    }
}
