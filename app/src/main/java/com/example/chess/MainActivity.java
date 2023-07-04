package com.example.chess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chess.game_engine.Figure;
import com.example.chess.game_engine.Game;
import com.example.chess.game_engine.Pair;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DisplayMetrics displayMetrics;
    int numberOfColumns = 8;
    int numberOfRows = 8;
    String[] player_username = new String[2];
    int[] player_id = new int[2];
    Game game;
    List<Pair> possibleMoves;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game(player_id[0], player_id[1]);

        Intent i = getIntent();
        player_username[0] = i.getStringExtra("user_1_name");
        player_username[1] = i.getStringExtra("user_2_name");
        player_id[0] = i.getIntExtra("user_1_id", 0);
        player_id[1] = i.getIntExtra("user_2_id", 0);

        Toast.makeText(MainActivity.this, "User 1 id: " + player_id[0] + ", name: " + player_username[0], Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "User 2 id: " + player_id[1] + ", name: " + player_username[1], Toast.LENGTH_SHORT).show();

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int paddingDp = 5;

        LinearLayout boardView = findViewById(R.id.boardView);
        Button player_1_wins = findViewById(R.id.player_1_wins);
        Button player_2_wins = findViewById(R.id.player_2_wins);
        Button draw = findViewById(R.id.draw);

        float scale = getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (paddingDp * scale + 0.5f);
        int btnWeight  = (int) ((screenWidth - 2 * paddingPixel) / numberOfColumns);
        int btnHeight = (int) ((screenWidth - 2 * paddingPixel) / numberOfColumns);

        for (int row = 0; row < numberOfRows; row++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            for (int column = 0; column < numberOfColumns; column++) {
                Button button = new Button(this, null, 0, R.style.SquareStyle);
                button.setId(row * numberOfColumns + column);
                button.setOnClickListener(selectTile);
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        btnWeight,
                        btnHeight
                ));

                // button.setOnClickListener(this);
                rowLayout.addView(button);

                Log.d("MainActivity", "Button " + row + " " + column + " created with id " + button.getId());
            }

            boardView.addView(rowLayout);
        }

        player_1_wins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                dataBaseHelper.addResult(player_id[0], DataBaseHelper.COLUMN_USER_WINS);
                dataBaseHelper.addResult(player_id[1], DataBaseHelper.COLUMN_USER_LOSES);

                UserModel userModel_1 = dataBaseHelper.getOneById(player_id[0]);
                UserModel userModel_2 = dataBaseHelper.getOneById(player_id[1]);
                Toast.makeText(MainActivity.this, userModel_1.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, userModel_2.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        player_2_wins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                dataBaseHelper.addResult(player_id[1], DataBaseHelper.COLUMN_USER_WINS);
                dataBaseHelper.addResult(player_id[0], DataBaseHelper.COLUMN_USER_LOSES);

                UserModel userModel_1 = dataBaseHelper.getOneById(player_id[0]);
                UserModel userModel_2 = dataBaseHelper.getOneById(player_id[1]);
                Toast.makeText(MainActivity.this, userModel_1.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, userModel_2.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                dataBaseHelper.addResult(player_id[0], DataBaseHelper.COLUMN_USER_DRAWS);
                dataBaseHelper.addResult(player_id[1], DataBaseHelper.COLUMN_USER_DRAWS);

                UserModel userModel_1 = dataBaseHelper.getOneById(player_id[0]);
                UserModel userModel_2 = dataBaseHelper.getOneById(player_id[1]);
                Toast.makeText(MainActivity.this, userModel_1.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, userModel_2.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        showFigures();
    }

    View.OnClickListener selectTile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FigColor whoseTurn = game.getWhoseTurn();
            Figure[][] board = game.getBoard();
            Pair chosenField = game.getChosenField();

            int id = view.getId();
            int currentRow = id / numberOfColumns + 1;
            int currentColumn = id % numberOfColumns + 1;

            if (chosenField.row == 0 && chosenField.column == 0) {
                if (whoseTurn == board[currentRow][currentColumn].getColor()) {
                    game.setChosenField(currentRow, currentColumn);
                    ((Button)view).setBackgroundColor(Color.MAGENTA);

                    possibleMoves = board[currentRow][currentColumn].getMoves(board);
                    for (int i = 0; i < possibleMoves.size(); i++) {
                        int tempId = (possibleMoves.get(i).row - 1) * numberOfColumns + (possibleMoves.get(i).column - 1);
                        Button btn = findViewById(tempId);
                        btn.setBackgroundColor(Color.RED);
                    }
                }
            } else {
                if (chosenField.row == currentRow && chosenField.column == currentColumn) {
                    game.setChosenField(0,0);
                    ((Button)view).setBackgroundColor(Color.WHITE);

                    for (int i = 0; i < possibleMoves.size(); i++) {
                        int tempId = (possibleMoves.get(i).row - 1) * numberOfColumns + (possibleMoves.get(i).column - 1);
                        Button btn = findViewById(tempId);
                        btn.setBackgroundColor(Color.WHITE);
                    }

                    possibleMoves.clear();
                }

                // TODO: wykonie ruchu jeśli należy do possibleMoves
                // TODO: skoczek źle wskazuje możliwe ruchy
            }
        }
    };

    public void showFigures() {
        Figure[][] board = game.getBoard();

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                int countedId = (i - 1) * numberOfColumns + (j - 1);
                Button btn = findViewById(countedId);
                if (board[i][j] == null) {
                    btn.setText("-");
                } else {
                    btn.setText(board[i][j].toString());
                    btn.setTextColor(board[i][j].getRGBColor());
                }
            }
        }
    }
}