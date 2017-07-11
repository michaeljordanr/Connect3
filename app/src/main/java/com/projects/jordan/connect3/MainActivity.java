package com.projects.jordan.connect3;

import android.net.LinkAddress;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow, 1 = red;
    int activePlaver = 0;

    boolean gameIsActive = true;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPosiitons = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameIsActive) {
            if (gameState[tappedCounter] == 2) {

                gameState[tappedCounter] = activePlaver;

                counter.setTranslationY(-1000f);
                if (activePlaver == 0) {
                    counter.setImageResource(R.drawable.yellow);
                    activePlaver = 1;
                } else {
                    counter.setImageResource(R.drawable.red);
                    activePlaver = 0;
                }

                counter.animate().translationYBy(1000f).setDuration(300);

                for(int[] winningPositions : winningPosiitons){
                    if(gameState[winningPositions[0]] == gameState[winningPositions[1]] &&
                            gameState[winningPositions[1]] == gameState[winningPositions[2]] &&
                            gameState[winningPositions[0]] != 2){

                        String winner = "Red";

                        if(gameState[winningPositions[0]] == 0){
                            winner = "Yellow";
                        }

                        gameIsActive = false;
                        // Someone has one
                        resetGame(winner);

                    } else {

                        boolean gamesIsOver = true;

                        for(int counterState : gameState){
                            if (counterState == 2){
                                gamesIsOver = false;
                            }

                            if(gamesIsOver){
                                resetGame("No one");
                            }
                        }
                    }
                }
            }
        }

    }

    public void playAgain(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);

        layout.setVisibility(View.INVISIBLE);

        activePlaver = 0;

        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

        gameIsActive = true;
    }

    public void resetGame(String msg){
        gameIsActive = false;
        // Someone has one
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        winnerMessage.setText(msg + " has won!");

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }
}
