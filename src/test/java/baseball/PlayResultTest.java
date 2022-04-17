package baseball;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PlayResultTest {
    private final int defaultExitCounter = 3;

    @DisplayName("playResult display test")
    @ParameterizedTest
    @MethodSource("provideStrikeAndBallAndExpectedMessage")
    public void testDisplay(final int ball, final int strike, final String expectedMsg) {
        PlayResult playResult = new PlayResult(2, ball, strike);
        System.out.println(playResult);
        assertThat(Objects.compare(playResult.toString(), expectedMsg, String::compareTo)).isEqualTo(0);
    }

    private static Stream<Arguments> provideStrikeAndBallAndExpectedMessage() {
        return Stream.of(
                Arguments.of(0, 1, "1스크라이크"),
                Arguments.of(1, 1, "1볼 1스크라이크"),
                Arguments.of(0, 0, "낫싱")
        );
    }

    @DisplayName("playResult member value cannot be negative")
    @ParameterizedTest
    @CsvSource(value = {"-1:0", "0:-1", "-1:-1"}, delimiter = ':')
    public void testCannotNegative(final int ball, final int strike) {
        expectedIllegalArgumentException(defaultExitCounter, ball, strike, PlayResult.ERROR.NEGATIVE.toString());
    }

    @DisplayName("playResult member value cannot be over max of integer")
    @ParameterizedTest
    @MethodSource("provideInputIntegerMaxValue")
    public void testCannotOver(final int ball, final int strike) {
        expectedIllegalArgumentException(defaultExitCounter, ball, strike, PlayResult.ERROR.MAX.toString());
    }

    private static Stream<Arguments> provideInputIntegerMaxValue() {
        return Stream.of(
                Arguments.of(Integer.MAX_VALUE, 0),
                Arguments.of(0, Integer.MAX_VALUE),
                Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }

    private void expectedIllegalArgumentException(final int exitCounter, final int ball, final int strike, final String msg) {
        Assertions.assertThatThrownBy(() -> new PlayResult(exitCounter, ball, strike))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(msg);
    }

    @DisplayName("playResult isFinish test")
    @ParameterizedTest
    @CsvSource(value = {"3:0:False", "0:3:True", "2:1:False"}, delimiter = ':')
    public void testIsFinish(final int ball, final int strike, final boolean expectedResult) {
        final int exitCounter = 3;
        PlayResult playResult = new PlayResult(exitCounter, ball, strike);
        assertThat(playResult.isFinish()).isEqualTo(expectedResult);
    }

    @DisplayName("ball or strike value cannot be over exitCounter value")
    @ParameterizedTest
    @CsvSource(value = {"2:3:0"}, delimiter = ':')
    public void testCannotBeOverExitCounter(final int exitCounter, final int ball, final int strike) {
        expectedIllegalArgumentException(exitCounter, ball, strike, PlayResult.ERROR.OVER.toString());
    }

    @DisplayName("playResult equal test")
    @Test
    public void testEqual() {
        PlayResult playResult = new PlayResult(2, 1, 0);
        assertThat(playResult).isEqualTo(new PlayResult(2, 1, 0));
        assertThat(playResult).isNotEqualTo(new PlayResult(2, 1, 1));
    }
}
