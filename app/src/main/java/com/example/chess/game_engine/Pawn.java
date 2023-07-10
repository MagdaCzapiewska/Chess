package com.example.chess.game_engine;

import android.content.Context;

import com.example.chess.FigColor;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {
    public Pawn(FigColor color, Pair position) {
        super(color, position);
    }

    // TODO: wymiana figury
    @Override
    public List<Pair> getMoves(Game game) {
        Figure[][] board = game.getBoard();
        Game.LastMove lastMove = game.getLastMove();
        int x, y;
        List<Pair> possibleMoves = new ArrayList<>();

        y = position.column;

        if (color == FigColor.WHITE) {
            x = position.row - 1;

            if (x > 0 && y + 1 <= numberOfColumns && board[x][y + 1] != null && board[x][y + 1].getColor() != color)
                if (!isCheckAfterMove(game, x, y + 1))
                    possibleMoves.add(new Pair(x, y + 1));

            if (x > 0 && y - 1 > 0 && board[x][y - 1] != null && board[x][y - 1].getColor() != color)
                if (!isCheckAfterMove(game, x, y - 1))
                    possibleMoves.add(new Pair(x, y - 1));

            if (x > 0 && board[x][y] == null) {
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

                x--;
                if (!wasMoved && board[x][y] == null) {
                    if (!isCheckAfterMove(game, x, y))
                        possibleMoves.add(new Pair(x, y));
                }
            }

            // Bicie w przelocie
            x = position.row - 1;
            if (lastMove.wasDoublePawnMove() && lastMove.to.row == position.row) {
                if (lastMove.to.column == position.column - 1) {
                    if (!isCheckAfterMove(game, x, position.column - 1))
                        possibleMoves.add(new Pair(x, position.column - 1));
                } else if (lastMove.to.column == position.column + 1) {
                    if (!isCheckAfterMove(game, x, position.column + 1))
                        possibleMoves.add(new Pair(x, position.column + 1));
                }
            }
        }

        if (color == FigColor.BLACK) {
            x = position.row + 1;

            if (x <= numberOfRows && y + 1 <= numberOfColumns && board[x][y + 1] != null && board[x][y + 1].getColor() != color)
                if (!isCheckAfterMove(game, x, y + 1))
                    possibleMoves.add(new Pair(x, y + 1));

            if (x <= numberOfRows && y - 1 > 0 && board[x][y - 1] != null && board[x][y - 1].getColor() != color)
                if (!isCheckAfterMove(game, x, y - 1))
                    possibleMoves.add(new Pair(x, y - 1));

            if (x <= numberOfRows && board[x][y] == null) {
                if (!isCheckAfterMove(game, x, y))
                    possibleMoves.add(new Pair(x, y));

                x++;
                if (!wasMoved && board[x][y] == null) {
                    if (!isCheckAfterMove(game, x, y))
                        possibleMoves.add(new Pair(x, y));
                }
            }

            // Bicie w przelocie
            x = position.row + 1;
            if (lastMove.wasDoublePawnMove() && lastMove.to.row == position.row) {
                if (lastMove.to.column == position.column - 1) {
                    if (!isCheckAfterMove(game, x, position.column - 1))
                        possibleMoves.add(new Pair(x, position.column - 1));
                } else if (lastMove.to.column == position.column + 1) {
                    if (!isCheckAfterMove(game, x, position.column + 1))
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

    @Override
    public boolean isCheckAfterMove(Game game, int newRow, int newColumn) {
        Figure[][] board = game.getBoard();
        Figure savedFigure = board[newRow][newColumn];
        Figure optionalSavedPawn = null;
        board[newRow][newColumn] = board[position.row][position.column];
        board[position.row][position.column] = null;

        if (savedFigure == null && position.column != newColumn) {
            optionalSavedPawn = board[position.row][newColumn];
            board[position.row][newColumn] = null;
        }

        boolean result = game.isCheck();

        board[position.row][position.column] = board[newRow][newColumn];
        board[newRow][newColumn] = savedFigure;

        if (optionalSavedPawn != null) {
            board[position.row][newColumn] = optionalSavedPawn;
        }

        return result;
    }

    @Override
    public int getIdOfResource(Context context, String packageName) {
        if (color == FigColor.WHITE) {
            return context.getResources().getIdentifier("@drawable/w_pawn_1x_ns", "id", packageName);
        }
        return context.getResources().getIdentifier("@drawable/b_pawn_1x_ns", "id", packageName);
    }
}
