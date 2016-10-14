package com.example.niall.braintrainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class BrainTrainer {

    private int playerScore = 0;
    private int numOfQuestions = 0;
    private int currentCorrectAnswer;

    private Random random;

    public BrainTrainer() {
        random = new Random();
    }

    public String generateRandomSumText() {
        int leftNum = random.nextInt(20) + 1;
        int rightNum = random.nextInt(20) + 1;

        currentCorrectAnswer = leftNum + rightNum;
        return Integer.toString(leftNum) + " + " + Integer.toString(rightNum);
    }

    public Integer[] generatePossibleAnswers() {
        int numOfPossibleAnswers = 4;
        Integer[] possibleAnswers = new Integer[numOfPossibleAnswers];
        possibleAnswers[0] = currentCorrectAnswer;
        int nextPossibleAnswer;

        for (int i = 1; i < numOfPossibleAnswers; i++) {
            nextPossibleAnswer = random.nextInt(20) + 1;
            while (possibleAnswersContains(possibleAnswers, nextPossibleAnswer)) {
                nextPossibleAnswer = random.nextInt(20) + 1;
            }

            possibleAnswers[i] = nextPossibleAnswer;
        }

        // shuffle array so possible answers is in random order
        // and not index 0 containing the correct answer
        Collections.shuffle(Arrays.asList(possibleAnswers));
        return possibleAnswers;
    }

    private boolean possibleAnswersContains(Integer[] possibleAnswers, Integer possibleDuplicate) {
        for (Integer value : possibleAnswers) {
            if (value != null && value.equals(possibleDuplicate)) {
                return true;
            }
        }

        return false;
    }

    public boolean isCorrectAnswerSubmitted(int answer) {
        boolean isRight = answer == currentCorrectAnswer;

        if (isRight) {
            playerScore++;
        }

        numOfQuestions++;
        return isRight;
    }

    public void resetGame() {
        playerScore = 0;
        numOfQuestions = 0;
    }

    public String getCurrentScoreText() {
        return Integer.toString(playerScore) + "/" + Integer.toString(numOfQuestions);
    }
}
