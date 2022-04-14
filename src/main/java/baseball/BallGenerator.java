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
        Set<BallNumber> buffer = new HashSet<>();
        while (isNotFull(buffer, size)) {
            getItem(start, end).ifPresent(buffer::add);
        }
        return buffer.toArray(new BallNumber[0]);
    }

    private static boolean isInvalidParams(int start, int end, int size) {
        return start > end || !Objects.equals(Math.min(end-start,size),size);
    }

    private static Optional<BallNumber> getItem(final int start, final int end) {
        final int item = pickNumberInRange(start, end);
        if (item < start || item > end) return Optional.empty();
        return Optional.of(new BallNumber(item));
    }

    private static boolean isNotFull(final Set<BallNumber> buffer, final int size) {
        return buffer.size() <  size;
    }
}
