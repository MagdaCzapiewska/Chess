package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {
    public Pawn(FigColor color, Pair position) {
        super(color, position);
    }

    // TODO: bicie w przelocie, wymiana figury
    @Override
    public List<Pair> getMoves(Figure[][] board, Game.LastMove lastMove) {
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

        y = position.column;

        if (color == FigColor.WHITE) {
            x = position.row - 1;

            if (x > 0 && y + 1 <= numberOfColumns && board[x][y + 1] != null && board[x][y + 1].getColor() != color)
                    possibleMoves.add(new Pair(x, y + 1));

            if (x > 0 && y - 1 > 0 && board[x][y - 1] != null && board[x][y - 1].getColor() != color)
                    possibleMoves.add(new Pair(x, y - 1));

            if (x > 0 && board[x][y] == null) {
                possibleMoves.add(new Pair(x, y));

                x--;
                if (!wasMoved && board[x][y] == null) possibleMoves.add(new Pair(x, y));
            }

            // Bicie w przelocie
            x = position.row - 1;
            if (lastMove.wasDoublePawnMove() && lastMove.to.row == position.row) {
                if (lastMove.to.column == position.column - 1) {
                    possibleMoves.add(new Pair(x, position.column - 1));
                } else if (lastMove.to.column == position.column + 1) {
                    possibleMoves.add(new Pair(x, position.column + 1));
                }
            }
        }

        if (color == FigColor.BLACK) {
            x = position.row + 1;

            if (x < numberOfRows && y + 1 <= numberOfColumns && board[x][y + 1] != null && board[x][y + 1].getColor() != color)
                possibleMoves.add(new Pair(x, y + 1));

            if (x < numberOfRows && y - 1 > 0 && board[x][y - 1] != null && board[x][y - 1].getColor() != color)
                possibleMoves.add(new Pair(x, y - 1));

            if (x < numberOfRows && board[x][y] == null) {
                possibleMoves.add(new Pair(x, y));

                x++;
                if (!wasMoved && board[x][y] == null) possibleMoves.add(new Pair(x, y));
            }

            // Bicie w przelocie
            x = position.row + 1;
            if (lastMove.wasDoublePawnMove() && lastMove.to.row == position.row) {
                if (lastMove.to.column == position.column - 1) {
                    possibleMoves.add(new Pair(x, position.column - 1));
                } else if (lastMove.to.column == position.column + 1) {
                    possibleMoves.add(new Pair(x, position.column + 1));
                }
            }
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "p";
    }
}
