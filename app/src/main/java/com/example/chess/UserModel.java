package com.example.chess;

public class UserModel {

    private int id;
    private String name;
    private long wins;
    private long draws;
    private long loses;

    public UserModel(int id, String name) {
        this.id = id;
        this.name = name;
        this.wins = 0;
        this.draws = 0;
        this.loses = 0;
    }

    public UserModel(int id, String name, long wins, long draws, long loses) {
        this.id = id;
        this.name = name;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wins=" + wins +
                ", draws=" + draws +
                ", loses=" + loses +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getDraws() {
        return draws;
    }

    public void setDraws(long draws) {
        this.draws = draws;
    }

    public long getLoses() {
        return loses;
    }

    public void setLoses(long loses) {
        this.loses = loses;
    }
}
