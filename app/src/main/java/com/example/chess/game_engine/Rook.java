package com.example.chess.game_engine;

import android.content.Context;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Figure {
    public Rook(FigColor color, Pair position) {
        super(color, position);
    }

    @Override
    public List<Pair> getMoves(Game game) {
        Figure[][] board = game.getBoard();
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

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
        return "W";
    }

    @Override
    public int getIdOfResource(Context context, String packageName) {
        if (color == FigColor.WHITE) {
            return context.getResources().getIdentifier("@drawable/w_rook_1x_ns", "id", packageName);
        }
        return context.getResources().getIdentifier("@drawable/b_rook_1x_ns", "id", packageName);
    }
}
