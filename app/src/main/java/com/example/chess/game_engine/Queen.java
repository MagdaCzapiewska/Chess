package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Figure {
    public Queen(FigColor color, Pair position) {
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
        while (x <= numberOfRows && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x++; y++;
        }

        x = position.row + 1;
        y = position.column - 1;
        while (x <= numberOfRows && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x++; y--;
        }

        x = position.row - 1;
        y = position.column + 1;
        while (x > 0 && y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x--; y++;
        }

        x = position.row - 1;
        y = position.column - 1;
        while (x > 0 && y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x--; y--;
        }

        // Ruch w boki
        x = position.row + 1;
        y = position.column;
        while (x <= numberOfRows) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x++;
        }

        x = position.row;
        y = position.column + 1;
        while (y <= numberOfColumns) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            y++;
        }

        x = position.row - 1;
        y = position.column;
        while (x > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            x--;
        }

        x = position.row;
        y = position.column - 1;
        while (y > 0) {
            if (board[x][y] == null || board[x][y].getColor() != color)
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

            if (board[x][y] != null) break;
            y--;
        }

        return possibleMoves;
    }

    @Override
    public String toString() {
        return "H";
    }
}
