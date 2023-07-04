package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {
    private boolean wasMoved;
    public Pawn(FigColor color, Pair position) {
        super(color, position);
        wasMoved = false;
    }

    // TODO: bicie w przelocie, wymiana figury
    @Override
    public List<Pair> getMoves(Figure[][] board) {
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

        y = position.column;

        if (color == FigColor.WHITE) {
            x = position.row - 1;

            if (x > 0 && y + 1 <= numberOfColumns && board[x][y + 1] != null && board[x][y].getColor() != color)
                    possibleMoves.add(new Pair(x, y + 1));

            if (x > 0 && y - 1 > 0 && board[x][y - 1] != null && board[x][y].getColor() != color)
                    possibleMoves.add(new Pair(x, y - 1));

            if (x > 0 && board[x][y] == null) {
                possibleMoves.add(new Pair(x, y));

                x--;
                if (!wasMoved && board[x][y] == null) possibleMoves.add(new Pair(x, y));
            }
        }

        if (color == FigColor.BLACK) {
            x = position.row + 1;

            if (x < numberOfRows && y + 1 <= numberOfColumns && board[x][y + 1] != null && board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y + 1));

            if (x < numberOfRows && y - 1 > 0 && board[x][y - 1] != null && board[x][y].getColor() != color)
                possibleMoves.add(new Pair(x, y - 1));

            if (x < numberOfRows && board[x][y] == null) {
                possibleMoves.add(new Pair(x, y));

                x++;
                if (!wasMoved && board[x][y] == null) possibleMoves.add(new Pair(x, y));
            }
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "p";
    }
}
