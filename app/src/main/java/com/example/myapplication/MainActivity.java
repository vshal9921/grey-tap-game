package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler = new Handler();
    private int gameDelay = 1000;
    private View redBox;
    private View blueBox;
    private View yellowBox;
    private View greenBox;
    private AppCompatTextView textScore;
    private AppCompatTextView textGameOver;
    private ConstraintLayout gameStartLayout;
    private ConstraintLayout gameStopLayout;
    private AppCompatButton buttonRestart;
    private int randomNumber;
    private int prevRandomNumber = -1;
    private int clickedBoxId = -1;
    private int score = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redBox = findViewById(R.id.redBox);
        blueBox = findViewById(R.id.blueBox);
        yellowBox = findViewById(R.id.yellowBox);
        greenBox = findViewById(R.id.greenBox);
        textScore = (AppCompatTextView) findViewById(R.id.textScore);
        textGameOver = (AppCompatTextView) findViewById(R.id.textGameOver);
        gameStartLayout = (ConstraintLayout) findViewById(R.id.gameStartLayout);
        gameStopLayout = (ConstraintLayout) findViewById(R.id.gameStopLayout);
        buttonRestart = (AppCompatButton) findViewById(R.id.buttonRestart);

        buttonRestart.setOnClickListener(this);

        startGame();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if(clickedBoxId == -2){
                stopGame();
            }
            else {

                textScore.setText(getString(R.string.score) + " " + score);
                randomNumber =  (int) (Math.random() * 4) + 1;

                while(prevRandomNumber == randomNumber){
                    randomNumber =  (int) (Math.random() * 4) + 1;
                }

                prevRandomNumber = randomNumber;

                switch (randomNumber){

                    case 1 :
                        redBox.setBackgroundColor(getResources().getColor(R.color.grey));
                        blueBox.setBackgroundColor(getResources().getColor(R.color.blue));
                        yellowBox.setBackgroundColor(getResources().getColor(R.color.yellow));
                        greenBox.setBackgroundColor(getResources().getColor(R.color.green));
                        break;

                    case 2 :
                        redBox.setBackgroundColor(getResources().getColor(R.color.red));
                        blueBox.setBackgroundColor(getResources().getColor(R.color.grey));
                        yellowBox.setBackgroundColor(getResources().getColor(R.color.yellow));
                        greenBox.setBackgroundColor(getResources().getColor(R.color.green));
                        break;

                    case 3 :
                        redBox.setBackgroundColor(getResources().getColor(R.color.red));
                        blueBox.setBackgroundColor(getResources().getColor(R.color.blue));
                        yellowBox.setBackgroundColor(getResources().getColor(R.color.grey));
                        greenBox.setBackgroundColor(getResources().getColor(R.color.green));
                        break;

                    case 4 :
                        redBox.setBackgroundColor(getResources().getColor(R.color.red));
                        blueBox.setBackgroundColor(getResources().getColor(R.color.blue));
                        yellowBox.setBackgroundColor(getResources().getColor(R.color.yellow));
                        greenBox.setBackgroundColor(getResources().getColor(R.color.grey));
                        break;
                }

                Log.d("game", "game score = " + score);
                Log.d("game", "game randomNumber = " + randomNumber);
                Log.d("game", "game clickedBoxId = " + clickedBoxId);

                redBox.setOnClickListener(MainActivity.this);
                blueBox.setOnClickListener(MainActivity.this);
                yellowBox.setOnClickListener(MainActivity.this);
                greenBox.setOnClickListener(MainActivity.this);

                clickedBoxId = -2;

                handler.postDelayed(this, gameDelay);
            }
        }
    };

    private void startGame(){
        handler.removeCallbacks(runnable);
        redBox.setOnClickListener(this);
        blueBox.setOnClickListener(this);
        yellowBox.setOnClickListener(this);
        greenBox.setOnClickListener(this);
        score = 0;
        textScore.setText(getString(R.string.score) + " " + score);
        gameStartLayout.setVisibility(View.VISIBLE);
        gameStopLayout.setVisibility(View.GONE);

        handler.postDelayed(runnable, gameDelay);
    }

    private void stopGame(){

        handler.removeCallbacks(runnable);

        redBox.setBackgroundColor(getResources().getColor(R.color.red));
        blueBox.setBackgroundColor(getResources().getColor(R.color.blue));
        yellowBox.setBackgroundColor(getResources().getColor(R.color.yellow));
        greenBox.setBackgroundColor(getResources().getColor(R.color.green));
        gameStartLayout.setVisibility(View.GONE);
        gameStopLayout.setVisibility(View.VISIBLE);
        textGameOver.setText(getString(R.string.game_over) + " " +  score);

        prevRandomNumber = -1;
        clickedBoxId = -1;
        score = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {

        redBox.setOnClickListener(null);
        blueBox.setOnClickListener(null);
        yellowBox.setOnClickListener(null);
        greenBox.setOnClickListener(null);

        int viewId = view.getId();

        if(viewId == R.id.buttonRestart){
            startGame();
        }
        else if(viewId == R.id.redBox){
            clickedBoxId = 1;
            checkClickedBox();
        }
        else if(viewId == R.id.blueBox){
            clickedBoxId = 2;
            checkClickedBox();
        }
        else if(viewId == R.id.yellowBox){
            clickedBoxId = 3;
            checkClickedBox();
        }
        else if(viewId == R.id.greenBox){
            clickedBoxId = 4;
            checkClickedBox();
        }
    }

    private void checkClickedBox(){

        // grey box clicked by user
        if(randomNumber == clickedBoxId){
            score += 1;
            textScore.setText(getString(R.string.score) + " " + score);
        }
        // wrong box clicked by user
        else {
            stopGame();
            Log.d("game", "gameover");
        }
    }
}
