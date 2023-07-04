package com.example.chess.game_engine;

import com.example.chess.FigColor;

import java.util.List;

public abstract class Figure {
    protected FigColor color;  // 0 - white, 1 - black
    protected Pair position;
    int numberOfColumns = 8;
    int numberOfRows = 8;

    public Figure(FigColor color, Pair position) {
        this.color = color;
        this.position = position;
    }

    public abstract List<Pair> getMoves(Figure[][] board);

    public int getRGBColor() {
        return color == FigColor.WHITE ? 0xFFFFFFFF : 0xFF000000;
    }

    public FigColor getColor() {
        return color;
    }

    public abstract String toString();
}
