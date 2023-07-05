package com.example.chess.game_engine;

public class Pair {
    public int row;
    public int column;

    public Pair(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setPair(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Pair) {
            Pair other = (Pair) object;
            return row == other.row && column == other.column;
        }
        return false;
    }
}
