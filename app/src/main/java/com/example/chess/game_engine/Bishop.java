package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Figure {
    public Bishop(FigColor color, Pair position) {
        super(color, position);
    }

    @Override
    public List<Pair> getMoves(Figure[][] board) {
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

        x = position.row + 1;
        y = position.column + 1;
        while (x <= numberOfRows && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x++; y++;
        }

        x = position.row + 1;
        y = position.column - 1;
        while (x <= numberOfRows && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x++; y--;
        }

        x = position.row - 1;
        y = position.column + 1;
        while (x > 0 && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x--; y++;
        }

        x = position.row - 1;
        y = position.column - 1;
        while (x > 0 && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x--; y--;
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "G";
    }
}
