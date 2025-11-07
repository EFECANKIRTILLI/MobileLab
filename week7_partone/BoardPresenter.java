package com.ceng.madlab.week7;

import android.util.Log;
import android.view.View;

public class BoardPresenter implements BoardListener {

    private BoardView boardView;
    private Board board;

    public BoardPresenter(BoardView boardView) {
        this.boardView = boardView;
        board = new Board(this);
    }

    @Override
    public void gameEnded(byte winner) {
        switch (winner){
            case BoardListener.NO_ONE:
                boardView.gameEnded(boardView.DRAW);
            case BoardListener.PLAYER_1:
                boardView.gameEnded(boardView.PLAYER_1_WINNER);
            case BoardListener.PLAYER_2:
                boardView.gameEnded(boardView.PLAYER_2_WINNER);
        }
    }

    @Override
    public void playedAt(byte player, byte row, byte col) {
        if (player == BoardListener.PLAYER_1){
            boardView.putSymbol(boardView.PLAYER_1_SYMBOL, row, col);
        }else{
            boardView.putSymbol(boardView.PLAYER_2_SYMBOL, row, col);

        }
    }

    @Override
    public void invalidPlay(byte row, byte col) {
        boardView.invalidPlay(row, col);

    }

    public void move(byte row, byte col){
        board.move(row,col);
    }

    static class CellListener implements View.OnClickListener{

        BoardPresenter boardPresenter;
        byte row, col;

        public CellListener(BoardPresenter boardPresenter, byte row, byte col) {
            this.boardPresenter = boardPresenter;
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View view) {
            Log.d("CellClickListener", "at"+ row + "," + col);
            boardPresenter.move(row,col);
        }
    }
}






