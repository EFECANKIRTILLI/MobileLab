package com.ceng.madlab.week8;

public interface BoardListener {
    byte NO_ONE = 0;
    byte PLAYER_1=1;
    byte PLAYER_2 = 2;

    public void playedAt(byte player1, byte row, byte col);
    public void gameEnded(byte winner);
    void  invalidPlay (byte row,byte col);
}