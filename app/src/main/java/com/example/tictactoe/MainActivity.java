package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;

    // Buttons making up the board
    private Button mBoardButtons[];

    // Various text displayed
    private TextView mInfoTextView;

    // Restart Button
    private Button startButton;

    // Game Over
    Boolean mGameOver;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new TicTacToeGame();

        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button0);
        mBoardButtons[1] = (Button) findViewById(R.id.button1);
        mBoardButtons[2] = (Button) findViewById(R.id.button2);
        mBoardButtons[3] = (Button) findViewById(R.id.button3);
        mBoardButtons[4] = (Button) findViewById(R.id.button4);
        mBoardButtons[5] = (Button) findViewById(R.id.button5);
        mBoardButtons[6] = (Button) findViewById(R.id.button6);
        mBoardButtons[7] = (Button) findViewById(R.id.button7);
        mBoardButtons[8] = (Button) findViewById(R.id.button8);
        mInfoTextView = (TextView) findViewById(R.id.information);

        mGame = new TicTacToeGame();

        startNewGame();

    }
    //--- Set up the game board.
    private void startNewGame() {
        mGameOver = false;
        mGame.clearBoard();
        //---Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        //---Human goes first
        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
        mInfoTextView.setText(R.string.open_guide);
        mInfoTextView.animate().scaleX(1f).scaleY(1f).setDuration(1000);
    }


    //---Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        @Override
        public void onClick(View v) {

            if (mGameOver == false) {

                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    //--- If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.android_turn);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.your_turn);
                    } else if (winner == 1) {
                        mInfoTextView.setTextColor(Color.rgb(21, 159, 92));
                        mInfoTextView.setText(R.string.tie);
                        mInfoTextView.animate().scaleX(2f).scaleY(2f).setDuration(1000);
                        Uri uri = Uri.parse("android.resource://"
                                + getPackageName() + "/" + R.raw.tie);
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                        mediaPlayer.start();
                        mGameOver = true;
                    } else if (winner == 2) {
                        mInfoTextView.setTextColor(Color.rgb(74, 138, 244));
                        mInfoTextView.setText(R.string.win);
                        mInfoTextView.animate().scaleX(2f).scaleY(2f).setDuration(1000);
                        Uri uri = Uri.parse("android.resource://"
                                + getPackageName() + "/" + R.raw.win);
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                        mediaPlayer.start();
                        mGameOver = true;
                    } else {
                        mInfoTextView.setTextColor(Color.rgb(216, 84, 72));
                        mInfoTextView.setText(R.string.lose);
                        mInfoTextView.animate().scaleX(2f).scaleY(2f).setDuration(1000);
                        Uri uri = Uri.parse("android.resource://"
                                + getPackageName() + "/" + R.raw.fail);
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                        mediaPlayer.start();
                        mGameOver = true;
                    }
                }
            }

        }

    }
    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(216, 84, 72));
        else
            mBoardButtons[location].setTextColor(Color.rgb(74, 138, 244));
    }
    //--- OnClickListener for Restart a New Game Button
    public void newGame(View v) {
        Uri uri = Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.start);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
        startNewGame();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void EndGame(View v) {
        Uri uri = Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.end);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
        MainActivity.this.finish();
    }
}