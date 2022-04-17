package baseball;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Judge {
    private final Map<BallNumber, Integer> ballNumberPlaceMap;
    private final int SCORE_BUFFER_SIZE = 2;
    private final int BALL_INDEX = 0;
    private final int STRIKE_INDEX = 1;
    private final int NO_KEY_VALUE = -1;
    private int ballNumberSize = 0;

    public Judge() {
        this.ballNumberPlaceMap = new HashMap<>();
    }

    public int setup(final BallNumber[] ballNumbers) {
        if (isInvalidParam(ballNumbers)) {
            throw new IllegalArgumentException();
        }
        if (updatedSize() > 0) {
            clearTable();
        }
        updateTable(ballNumbers);
        this.ballNumberSize = ballNumbers.length;
        return updatedSize();
    }

    private boolean isInvalidParam(final BallNumber[] ballNumbers) {
        return Objects.isNull(ballNumbers) || ballNumbers.length < 1;
    }

    private void clearTable() {
        ballNumberPlaceMap.clear();
    }

    private int updatedSize() {
        return ballNumberPlaceMap.size();
    }

    private void updateTable(final BallNumber[] ballNumbers) {
        for (int i = 1; i <= ballNumbers.length; i++) {
            ballNumberPlaceMap.put(ballNumbers[i - 1], i);
        }
    }

    public PlayResult ask(final BallNumber[] userInputBallNumbers) {
        if (isInvalidParam(userInputBallNumbers) || isOverInputBallNumbers(userInputBallNumbers)) {
            throw new IllegalArgumentException();
        }
        final int[] score = new int[SCORE_BUFFER_SIZE];
        for (int i = 1; i <= userInputBallNumbers.length; i++) {
            writeScore(score, ballNumberPlaceMap.getOrDefault(userInputBallNumbers[i - 1], NO_KEY_VALUE), i);
        }
        return new PlayResult(this.ballNumberSize, score[BALL_INDEX], score[STRIKE_INDEX]);
    }

    private boolean isOverInputBallNumbers(final BallNumber[] inputBallNumbers) {
        return this.ballNumberSize < inputBallNumbers.length;
    }

    private void writeScore(final int[] score, final int savedBallNumberIndex, int inputBallNumberIndex) {
        if (Objects.equals(savedBallNumberIndex, NO_KEY_VALUE)) {
            return;
        }
        int scoreIndex = Objects.equals(savedBallNumberIndex, inputBallNumberIndex) ? STRIKE_INDEX : BALL_INDEX;
        score[scoreIndex] += 1;
    }
}
