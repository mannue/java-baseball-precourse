package baseball;

import java.util.Objects;

public class PlayResult {

    enum ERROR {
        NEGATIVE("playResult cannot be negative"),
        MAX("playResult cannot be max of integer"),
        OVER("playResult cannot be over exitCounter");

        private String msg;

        ERROR(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return this.msg;
        }
    }

    private final int exitCounter;
    private final int ball;
    private final int strike;


    public PlayResult(final int exitCounter, final int ball, final int strike) {
        this.exitCounter = exitCounter;
        this.ball = ball;
        this.strike = strike;
        checkParams();
    }

    public boolean isFinish() {
        return Objects.equals(exitCounter, this.strike);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayResult that = (PlayResult) o;
        return exitCounter == that.exitCounter && ball == that.ball && strike == that.strike;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exitCounter, ball, strike);
    }

    @Override
    public String toString() {
        if (hasBall() && hasStrike()) {
            return String.format("%d볼 %d스트라이크", this.ball, this.strike);
        }
        if (hasBall()) {
            return String.format("%d볼", this.ball);
        }
        if (hasStrike()) {
            return String.format("%d스트라이크", this.strike);
        }
        return "낫싱";
    }

    private void checkParams() {
        if (ball < 0 || strike < 0) {
            throw new IllegalArgumentException(ERROR.NEGATIVE.msg);
        }
        if (Objects.equals(ball, Integer.MAX_VALUE) || Objects.equals(strike, Integer.MAX_VALUE)) {
            throw new IllegalArgumentException(ERROR.MAX.msg);
        }
        if (this.ball + this.strike > this.exitCounter) {
            throw new IllegalArgumentException(ERROR.OVER.msg);
        }
    }

    private boolean hasBall() {
        return this.ball > 0;
    }

    private boolean hasStrike() {
        return this.strike > 0;
    }
}
