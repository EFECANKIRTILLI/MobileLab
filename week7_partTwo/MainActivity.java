package com.ceng.madlab.week7;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ceng.madlab.week7.R;

public class MainActivity extends AppCompatActivity implements msku.ceng.week7.BoardView {
    TableLayout board;
    msku.ceng.week7.BoardPresenter boardPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = findViewById(R.id.board);
        boardPresenter = new msku.ceng.week7.BoardPresenter(this);
        for(byte row=0; row<3 ; row++){
            TableRow tableRow =(TableRow) board.getChildAt(row);
            for (byte col=0; col<3 ; col++){
                Button button = (Button) tableRow.getChildAt(col);
                BoardPresenter.CellListener cellListener = new BoardPresenter.CellListener(boardPresenter,row,col);
                button.setOnClickListener(cellListener);
            }
        }
    }


    @Override
    public void newGame() {
        for(byte row=0; row<3 ; row++){
            TableRow tableRow =(TableRow) board.getChildAt(row);
            for (byte col=0; col<3 ; col++){
                Button button = (Button) tableRow.getChildAt(col);
                button.setText("");
                button.setEnabled(true);
            }
        }

    }

    @Override
    public void putSymbol(char symbol, byte row, byte col) {
        TableRow tableRow =(TableRow) board.getChildAt(row);
        Button button  =(Button) tableRow.getChildAt(col);
        button.setText(Character.toString(symbol));
    }

    @Override
    public void gameEnded(byte winner) {
        for(byte row=0; row<3 ; row++){
            TableRow tableRow =(TableRow) board.getChildAt(row);
            for (byte col=0; col<3 ; col++){
                Button button = (Button) tableRow.getChildAt(col);
                button.setText("");
                button.setEnabled(false);
            }
        }
        switch (winner){
            case BoardView.Draw:
                Toast.makeText(this,"Game is draw",Toast.LENGTH_SHORT).show();
                break;
            case BoardView.PLAYER_1_WINNER:
                Toast.makeText(this,"Player 1 wins",Toast.LENGTH_SHORT).show();
            case BoardView.PLAYER_2_WINNER:
                Toast.makeText(this,"Player 2 wins",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void invalidPlay(byte row, byte col) {
        Toast.makeText(this,"Invalid move",Toast.LENGTH_SHORT).show();
    }
}