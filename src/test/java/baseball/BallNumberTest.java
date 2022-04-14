package baseball;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BallNumberTest {

    @DisplayName("BallNumber create test by invalid string")
    @ParameterizedTest
    @ValueSource(strings = {"a","b","d","test"})
    public void testCreate(final String input) {
        assertThatThrownBy(() -> new BallNumber(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("BallNumber equal test by string")
    @ParameterizedTest
    @CsvSource(value = {"1:1:true","2:2:true", "2:3:false"},delimiter = ':')
    public void testEqual(final String target, final String compare, final boolean expectedResult) {
        assertThat(Objects.equals(new BallNumber(target),new BallNumber(compare))).isEqualTo(expectedResult);
    }

    @DisplayName("Set remove overlap BallNumber")
    @ParameterizedTest
    @CsvSource(value = {"1,1,1,1:1","1,2,2,1:2"},delimiter = ':')
    public void testRemoveOverlap(final String inputs, final int expectedResult) {
        Set<BallNumber> ballNumbers = createBallNumbersByStrings(inputs.split(","));
        assertThat(ballNumbers.size()).isEqualTo(expectedResult);
    }

    @DisplayName("BallNumberArray contain test")
    @ParameterizedTest
    @MethodSource("provideBallNumberArrayWithBallNumber")
    public void testContain(final Set<BallNumber> ballNumbers, final BallNumber item, final boolean expectedResult) {
        assertThat(ballNumbers.contains(item)).isEqualTo(expectedResult);
    }

    @DisplayName("BallNumber equal test by integer")
    @ParameterizedTest
    @CsvSource(value = {"1:1:true","2:2:true", "2:3:false"},delimiter = ':')
    public void testEqual(final int target, final int compare, final boolean expectedResult) {
        assertThat(Objects.equals(new BallNumber(target),new BallNumber(compare))).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> provideBallNumberArrayWithBallNumber() {
        return Stream.of(
                Arguments.of(
                        createBallNumbersByStrings("1","2","3"),
                        new BallNumber("2"), true),
                Arguments.of(
                        createBallNumbersByStrings("1","2","4"),
                        new BallNumber("4"), true ),
                Arguments.of(
                        createBallNumbersByStrings("3"),
                        new BallNumber("1"), false)
        );
    }
    private static Set<BallNumber> createBallNumbersByStrings(String ... items) {
        Set<BallNumber> ballNumbers = new HashSet<>();
        for (String item: items) {
            ballNumbers.add(new BallNumber(item));
        }
        return ballNumbers;
    }


}
