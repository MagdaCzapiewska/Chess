package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.List;

public abstract class Figure {
    protected FigColor color;  // 0 - white, 1 - black
    protected Pair position;
    protected boolean wasMoved;
    int numberOfColumns = 8;
    int numberOfRows = 8;

    public Figure(FigColor color, Pair position) {
        this.color = color;
        this.position = position;
        wasMoved = false;
    }

    public abstract List<Pair> getMoves(Figure[][] board, Game.LastMove lastMove);

    public int getRGBColor() {
        return color == FigColor.WHITE ? 0xFFFFFFFF : 0xFF000000;
    }

    public FigColor getColor() {
        return color;
    }

    public void setPosition(int row, int column) {
        position.setPair(row, column);
    }

    public void setWasMoved() {
        wasMoved = true;
    }

    public abstract String toString();
}
