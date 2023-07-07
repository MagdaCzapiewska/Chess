package com.example.chess;

import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chess.game_engine.Figure;
import com.example.chess.game_engine.Game;
import com.example.chess.game_engine.King;
import com.example.chess.game_engine.Pair;
import com.example.chess.game_engine.Pawn;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DisplayMetrics displayMetrics;
    int numberOfColumns = 8;
    int numberOfRows = 8;
    String[] player_username = new String[2];
    int[] player_id = new int[2];
    Game game;
    List<Pair> possibleMoves;
    TextView checkText;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game(player_id);

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
        checkText = findViewById(R.id.textViewCheck);

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
                button.setBackgroundColor(Color.GRAY);
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
                if (board[currentRow][currentColumn] != null
                        && whoseTurn == board[currentRow][currentColumn].getColor()) {
                    game.setChosenField(currentRow, currentColumn);
                    ((Button) view).setBackgroundColor(Color.MAGENTA);

                    possibleMoves = board[currentRow][currentColumn].getMoves(game);
                    for (int i = 0; i < possibleMoves.size(); i++) {
                        int tempId = (possibleMoves.get(i).row - 1) * numberOfColumns + (possibleMoves.get(i).column - 1);
                        Button btn = findViewById(tempId);
                        btn.setBackgroundColor(Color.RED);
                    }
                }
            } else {
                if (chosenField.row == currentRow && chosenField.column == currentColumn) {
                    // Czyścimy podświetlenie pól
                    game.setChosenField(0,0);
                    ((Button)view).setBackgroundColor(Color.GRAY);

                    for (int i = 0; i < possibleMoves.size(); i++) {
                        int tempId = (possibleMoves.get(i).row - 1) * numberOfColumns + (possibleMoves.get(i).column - 1);
                        Button btn = findViewById(tempId);
                        btn.setBackgroundColor(Color.GRAY);
                    }

                    possibleMoves.clear();
                } else if (possibleMoves.contains(new Pair(currentRow, currentColumn))) {
                    Figure chosenFigure = board[chosenField.row][chosenField.column];

                    // TODO: zmienić na obrazki
                    // Zamieniamy stary przycisk na '-'
                    int countedId = (chosenField.row - 1) * numberOfColumns + (chosenField.column - 1);
                    Button btn = findViewById(countedId);
                    btn.setText("-");
                    btn.setTextColor(Color.BLACK);
                    btn.setBackgroundColor(Color.GRAY);

                    // Zamieniamy wybrany przycisk na wybraną wcześniej figurę
                    countedId = (currentRow - 1) * numberOfColumns + (currentColumn - 1);
                    btn = findViewById(countedId);
                    btn.setText(board[chosenField.row][chosenField.column].toString());
                    btn.setTextColor(board[chosenField.row][chosenField.column].getRGBColor());

                    // Sprawdzamy bicie w przelocie
                    if (chosenFigure instanceof Pawn
                            && board[currentRow][currentColumn] == null
                            && abs(currentColumn - chosenField.column) == 1) {
                        Pair lastTo = game.getLastMoveTo();

                        countedId = (lastTo.row - 1) * numberOfColumns + (lastTo.column - 1);
                        btn = findViewById(countedId);
                        btn.setText("-");
                        btn.setTextColor(Color.BLACK);
                        btn.setBackgroundColor(Color.GRAY);

                        board[lastTo.row][lastTo.column] = null;
                    }

                    if (chosenFigure instanceof King) {
                        game.moveKing(currentRow, currentColumn);

                        // Sprawdzamy, czy to była roszada
                        if (currentColumn - chosenField.column == 2) {
                            board[chosenField.row][8].setPosition(chosenField.row, currentColumn - 1);
                            board[chosenField.row][currentColumn - 1] = board[chosenField.row][8];
                            board[chosenField.row][8] = null;
                            countedId = (currentRow - 1) * numberOfColumns + ((currentColumn - 1) - 1);
                            btn = findViewById(countedId);
                            btn.setText(board[chosenField.row][currentColumn - 1].toString());
                            btn.setTextColor(board[chosenField.row][currentColumn - 1].getRGBColor());

                            countedId = (currentRow - 1) * numberOfColumns + (8 - 1);
                            btn = findViewById(countedId);
                            btn.setText("-");
                            btn.setTextColor(Color.BLACK);
                        }
                        else if (currentColumn - chosenField.column == -2) {
                            board[chosenField.row][1].setPosition(chosenField.row, currentColumn + 1);
                            board[chosenField.row][currentColumn + 1] = board[chosenField.row][1];
                            board[chosenField.row][1] = null;
                            countedId = (currentRow - 1) * numberOfColumns + ((currentColumn + 1) - 1);
                            btn = findViewById(countedId);
                            btn.setText(board[chosenField.row][currentColumn + 1].toString());
                            btn.setTextColor(board[chosenField.row][currentColumn + 1].getRGBColor());

                            countedId = (currentRow - 1) * numberOfColumns + (1 - 1);
                            btn = findViewById(countedId);
                            btn.setText("-");
                            btn.setTextColor(Color.BLACK);
                        }
                    }

                    // Zmieniamy stan gry
                    board[currentRow][currentColumn] = chosenFigure;
                    board[chosenField.row][chosenField.column] = null;

                    chosenFigure.setPosition(currentRow, currentColumn);
                    chosenFigure.setWasMoved();
                    game.setLastMove(chosenField.row, chosenField.column, currentRow, currentColumn, chosenFigure);
                    game.switchTurn();

                    // Czyścimy podświetlenie pól
                    game.setChosenField(0,0);
                    for (int i = 0; i < possibleMoves.size(); i++) {
                        int tempId = (possibleMoves.get(i).row - 1) * numberOfColumns + (possibleMoves.get(i).column - 1);
                        btn = findViewById(tempId);
                        btn.setBackgroundColor(Color.GRAY);
                    }
                    possibleMoves.clear();

                    GameStatus status = game.getStatus();
                    if (status == GameStatus.CHECK) {
                        checkText.setText("Check");
                    }
                    else if (status == GameStatus.CHECKMATE) {
                        checkText.setText("Checkmate");
                    }
                    else if (status == GameStatus.STALEMATE) {
                        checkText.setText("Stalemate");
                    }
                    else {
                        checkText.setText("");
                    }
                }
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