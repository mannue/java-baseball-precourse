package baseball;

public class BallNumberConverter {
    public static BallNumber[] resolve(final String input) {
        String[] splitValues = input.split("");
        BallNumber[] ballNumbers = new BallNumber[splitValues.length];
        for (int i = 0; i < splitValues.length; i++) {
            ballNumbers[i] = new BallNumber(splitValues[i]);
        }
        return ballNumbers;
    }
}
