package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class RankingActivity extends AppCompatActivity {

    TableLayout tableLayout;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        tableLayout = findViewById(R.id.layout_ranking);
        fillRankingTable(DataBaseHelper.COLUMN_USER_WINS, 1);

        radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.b1) {
                    fillRankingTable(DataBaseHelper.COLUMN_USER_WINS, 1);
                }
                if (checkedId == R.id.b2) {
                    fillRankingTable(DataBaseHelper.COLUMN_USER_DRAWS, 2);
                }
                if (checkedId == R.id.b3) {
                    fillRankingTable(DataBaseHelper.COLUMN_USER_LOSES, 3);
                }
            }
        });
    }

    void fillRankingTable(String column, int highlight) {
        tableLayout.removeAllViews();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(RankingActivity.this);
        List<UserModel> usersSorted = dataBaseHelper.getEveryoneSortedByColumn(column);

        TableRow row = new TableRow(this);
        TextView[] columns = new TextView[4];
        for (int i = 0; i < 4; ++i) {
            columns[i] = new TextView(this);
        }
        columns[0].setText("Name");
        columns[1].setText("Wins");
        columns[2].setText("Draws");
        columns[3].setText("Loses");

        for (int i = 0; i < 4; ++i) {
            columns[i].setWidth(250);
            columns[i].setHeight(100);
            columns[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            columns[i].setBackgroundColor(Color.GRAY);
            columns[i].setGravity(Gravity.CENTER | Gravity.CENTER);
            columns[i].setTypeface(null, Typeface.BOLD);
            columns[i].setTextSize(14);

            if (i == highlight) {
                columns[i].setBackgroundColor(Color.MAGENTA);
            }
            row.addView(columns[i]);
        }
        tableLayout.addView(row);

        for (UserModel user: usersSorted) {
            row = new TableRow(this);
            for (int i = 0; i < 4; ++i) {
                columns[i] = new TextView(this);
            }
            Log.d("Results: ", user.getName() + user.getWins() + user.getDraws() + user.getLoses());
            columns[0].setText(user.getName());
            columns[1].setText(String.valueOf((int)user.getWins()));
            columns[2].setText(String.valueOf((int)user.getDraws()));
            columns[3].setText(String.valueOf((int)user.getLoses()));
            for (int i = 0; i < 4; ++i) {
                columns[i].setWidth(250);
                columns[i].setHeight(100);
                columns[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                columns[i].setBackgroundColor(Color.LTGRAY);
                columns[i].setGravity(Gravity.CENTER | Gravity.CENTER);
                columns[i].setTypeface(null, Typeface.BOLD);
                columns[i].setTextSize(14);
                row.addView(columns[i]);
            }
            tableLayout.addView(row);
        }
    }
}