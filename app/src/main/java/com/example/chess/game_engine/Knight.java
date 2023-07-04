package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Figure {
    public Knight(FigColor color, Pair position) {
        super(color, position);
    }

    @Override
    public List<Pair> getMoves(Figure[][] board) {
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

        x = numberOfRows + 2;
        y = numberOfColumns + 1;
        if (x <= numberOfRows && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = numberOfRows + 1;
        y = numberOfColumns + 2;
        if (x <= numberOfRows && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = numberOfRows - 1;
        y = numberOfColumns + 2;
        if (x > 0 && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = numberOfRows - 2;
        y = numberOfColumns + 1;
        if (x > 0 && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = numberOfRows - 2;
        y = numberOfColumns - 1;
        if (x > 0 && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = numberOfRows - 1;
        y = numberOfColumns - 2;
        if (x > 0 && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = numberOfRows + 1;
        y = numberOfColumns - 2;
        if (x <= numberOfRows && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        x = numberOfRows + 2;
        y = numberOfColumns - 1;
        if (x <= numberOfRows && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "S";
    }
}
