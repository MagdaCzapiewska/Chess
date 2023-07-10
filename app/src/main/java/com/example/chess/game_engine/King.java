package com.example.chess.game_engine;

import android.content.Context;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class King extends Figure {
    public King(FigColor color, Pair position) {
        super(color, position);
    }

    @Override
    public List<Pair> getMoves(Game game) {
        Figure[][] board = game.getBoard();
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

        // Ruch poprzeczny
        x = position.row + 1;
        y = position.column + 1;
        if (x <= numberOfRows && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row + 1;
        y = position.column - 1;
        if (x <= numberOfRows && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 1;
        y = position.column + 1;
        if (x > 0 && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 1;
        y = position.column - 1;
        if (x > 0 && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        // Ruch w boki
        x = position.row + 1;
        y = position.column;
        if (x <= numberOfRows) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row;
        y = position.column + 1;
        if (y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 1;
        y = position.column;
        if (x > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row;
        y = position.column - 1;
        if (y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        // Wykrywanie możliwości roszady
        if (!wasMoved) {
            // król przesuwa się w prawo o 2 pola
            if (board[position.row][8] != null && !board[position.row][8].getWasMoved() && !game.isCheck()) {
                if (board[position.row][position.column + 1] == null
                        && board[position.row][position.column + 2] == null
                        && !isCheckAfterMove(game, position.row, position.column + 1)
                        && !isCheckAfterMove(game, position.row, position.column + 2)) {
                    possibleMoves.add(new Pair(position.row, position.column + 2));
                }
            }
            if (board[position.row][1] != null && !board[position.row][1].getWasMoved() && !game.isCheck()) {
                if (board[position.row][position.column - 1] == null
                        && board[position.row][position.column - 2] == null
                        && board[position.row][position.column - 3] == null
                        && !isCheckAfterMove(game, position.row, position.column - 1)
                        && !isCheckAfterMove(game, position.row, position.column - 2)) {
                    possibleMoves.add(new Pair(position.row, position.column - 2));
                }
            }
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean isCheckAfterMove(Game game, int newRow, int newColumn) {
        Figure[][] board = game.getBoard();
        Figure savedFigure = board[newRow][newColumn];
        board[newRow][newColumn] = board[position.row][position.column];
        board[position.row][position.column] = null;

        game.moveKing(newRow, newColumn);

        boolean result = game.isCheck();

        board[position.row][position.column] = board[newRow][newColumn];
        board[newRow][newColumn] = savedFigure;
        game.moveKing(position.row, position.column);

        return result;
    }

    @Override
    public int getIdOfResource(Context context, String packageName) {
        if (color == FigColor.WHITE) {
            return context.getResources().getIdentifier("@drawable/w_king_1x_ns", "id", packageName);
        }
        return context.getResources().getIdentifier("@drawable/b_king_1x_ns", "id", packageName);
    }
}
