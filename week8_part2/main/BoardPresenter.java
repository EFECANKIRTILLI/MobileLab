package com.ceng.madlab.week8;

import android.util.Log;
import android.view.View;

public class BoardPresenter implements BoardListener{
    private BoardView boardView;
    private Board board;

    public BoardPresenter(BoardView boardView) {
        this.boardView = boardView;
        board = new Board(this);

    }

    @Override
    public void playedAt(byte player1, byte row, byte col) {
        if (player1 == BoardListener.PLAYER_1){
            boardView.putSymbol(BoardView.PLAYER_1_SYMBOL,row,col);
        } else if (player1 == BoardListener.PLAYER_2) {
            boardView.putSymbol(BoardView.PLAYER_2_SYMBOL,row,col);
        }
    }

    @Override
    public void gameEnded(byte winner) {
        switch (winner){
            case BoardListener.NO_ONE:
                boardView.gameEnded(BoardView.DRAW);
            case BoardListener.PLAYER_1:
                boardView.gameEnded(BoardView.PLAYER_1_WINNER);
            case BoardListener.PLAYER_2:
                boardView.gameEnded(BoardView.PLAYER_2_WINNER);
        }
    }
    public  void move (byte row , byte col){
        board.move(row,col);
    }
    public void  invalidPlay (byte row,byte col){

    }
    static class CellClickListener implements View.OnClickListener{
        BoardPresenter boardPresenter;
        byte row,col;

        public CellClickListener(BoardPresenter boardPresenter, byte row, byte col) {
            this.boardPresenter = boardPresenter;
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            Log.d("CellClicklistener"," at"+ row+"," + col);
            boardPresenter.move(row,col);
        }
    }
}
