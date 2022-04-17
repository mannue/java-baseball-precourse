package baseball;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static camp.nextstep.edu.missionutils.Randoms.pickNumberInRange;

public class BallGenerator {
    final static String argumentExceptionMsg = "Invalid params";

    public static BallNumber[] create(final int start, final int end, final int size) {
        if (isInvalidParams(start, end, size)) {
            throw new IllegalArgumentException(argumentExceptionMsg);
        }
        return getBallNumbers(start, end, size);
    }

    private static BallNumber[] getBallNumbers(final int minStart,final int end,final int size) {
        Set<BallNumber> buffer = new HashSet<>();

        do{
            getNumber(minStart, end).ifPresent(buffer::add);
        }while (isNotFull(buffer,size));

        return buffer.toArray(new BallNumber[0]);
    }

    private static Optional<BallNumber> getNumber(final int min, final int max) {
        final int item = pickNumberInRange(min, max);
        if (isInputGreaterThanTarget(min, item) || isInputGreaterThanTarget(item, max)) return Optional.empty();
        return Optional.of(new BallNumber(item));
    }

    private static boolean isInvalidParams(int start, int end, int size) {
        return isInputGreaterThanTarget(start, end) || isInvalidSize(end-start, size);
    }

    private static boolean isInvalidSize(final int range, final int size) {
        return !Objects.equals(Math.min(range, size), size) || size < 1;
    }

    private static boolean isInputGreaterThanTarget(final int input,final int target) {
        return input > target;
    }

    private static boolean isNotFull(final Set<BallNumber> buffer, final int size) {
        return buffer.size() <  size;
    }
}
