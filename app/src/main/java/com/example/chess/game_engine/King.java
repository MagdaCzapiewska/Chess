package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class King extends Figure {
    public King(FigColor color, Pair position) {
        super(color, position);
    }

    @Override
    public List<Pair> getMoves(Figure[][] board, Game.LastMove lastMove) {
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

        // Ruch poprzeczny
        x = position.row + 1;
        y = position.column + 1;
        if (x <= numberOfRows && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = position.row + 1;
        y = position.column - 1;
        if (x <= numberOfRows && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 1;
        y = position.column + 1;
        if (x > 0 && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 1;
        y = position.column - 1;
        if (x > 0 && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        // Ruch w boki
        x = position.row + 1;
        y = position.column;
        if (x <= numberOfRows) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = position.row;
        y = position.column + 1;
        if (y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 1;
        y = position.column;
        if (x > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = position.row;
        y = position.column - 1;
        if (y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "K";
    }
}
