package com.example.niall.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button playAgainButton;
    private TextView timerText;
    private TextView sumText;
    private TextView scoreText;
    private TextView outcomeText;
    private GridLayout answerGrid;

    private int timeRemaining = 30;
    private BrainTrainer brainTrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brainTrainer = new BrainTrainer();
        initialiseUi();
    }

    private void initialiseUi() {
        findViews();
        setMainGameUiVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        updateTimerText();
        updateScoreText();
    }

    private void findViews() {
        startButton = (Button) findViewById(R.id.startButton);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        timerText = (TextView) findViewById(R.id.timerText);
        sumText = (TextView) findViewById(R.id.sumText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        outcomeText = (TextView) findViewById(R.id.outcomeText);

        answerGrid = (GridLayout) findViewById(R.id.gridLayout);
    }

    private void setMainGameUiVisibility(int visibility) {
        timerText.setVisibility(visibility);
        sumText.setVisibility(visibility);
        scoreText.setVisibility(visibility);
        answerGrid.setVisibility(visibility);
    }

    private void updateTimerText() {
        timerText.setText(Integer.toString(timeRemaining) + "s");
    }

    private void updateScoreText() {
        scoreText.setText(brainTrainer.getCurrentScoreText());
    }

    public void startGame(View view) {
        startButton.setVisibility(View.INVISIBLE);
        setMainGameUiVisibility(View.VISIBLE);
        startTimer();
        setNextQuestion();
    }

    private void startTimer() {
        new CountDownTimer((1000 * timeRemaining) + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = (int) millisUntilFinished / 1000;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                outcomeText.setText("Your score: " + brainTrainer.getCurrentScoreText());
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void setNextQuestion() {
        sumText.setText(brainTrainer.generateRandomSumText());
        setAnswerButtonText(brainTrainer.generatePossibleAnswers());
    }

    private void setAnswerButtonText(Integer[] possibleAnswers) {
        Button answerButton;

        for (int i = 0; i < possibleAnswers.length; i++) {
            answerButton = (Button) answerGrid.getChildAt(i);
            answerButton.setText(Integer.toString(possibleAnswers[i]));
            answerButton.setTag(Integer.toString(possibleAnswers[i]));
        }
    }

    public void answerSelected(View view) {
        int selectedAnswer = Integer.parseInt(view.getTag().toString());

        if (brainTrainer.isCorrectAnswerSubmitted(selectedAnswer)) {
            outcomeText.setText("Correct!");
        } else {
            outcomeText.setText("Wrong!");
        }

        updateScoreText();
        setNextQuestion();
    }

    public void playAgain(View view) {
        brainTrainer.resetGame();
        timeRemaining = 30;

        updateScoreText();
        updateTimerText();
        startTimer();
        setNextQuestion();

        outcomeText.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
    }
}
