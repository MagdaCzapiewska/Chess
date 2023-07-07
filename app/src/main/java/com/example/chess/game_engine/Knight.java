package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Figure {
    public Knight(FigColor color, Pair position) {
        super(color, position);
    }

    @Override
    public List<Pair> getMoves(Game game) {
        Figure[][] board = game.getBoard();
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

        x = position.row + 2;
        y = position.column + 1;
        if (x <= numberOfRows && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row + 1;
        y = position.column + 2;
        if (x <= numberOfRows && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 1;
        y = position.column + 2;
        if (x > 0 && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 2;
        y = position.column + 1;
        if (x > 0 && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 2;
        y = position.column - 1;
        if (x > 0 && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row - 1;
        y = position.column - 2;
        if (x > 0 && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row + 1;
        y = position.column - 2;
        if (x <= numberOfRows && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        x = position.row + 2;
        y = position.column - 1;
        if (x <= numberOfRows && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "S";
    }
}
