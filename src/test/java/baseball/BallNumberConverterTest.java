package baseball;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BallNumberConverterTest {

    @DisplayName("ballNumberConverter resolve test")
    @ParameterizedTest
    @MethodSource("provideStrInputAndIntInputs")
    public void testResolve(final String strInput, final int[] intInputs) {
        BallNumber[] ballNumbers = BallNumberConverter.resolve(strInput);
        assertThat(ballNumbers.length).isEqualTo(intInputs.length);
        assertThat(ballNumbers).containsExactly(makeBallNumbersByIntegers(intInputs));
    }

    private static Stream<Arguments> provideStrInputAndIntInputs() {
        return Stream.of(
                Arguments.of("123", new int[]{1, 2, 3}),
                Arguments.of("45", new int[]{4, 5})
        );
    }

    private BallNumber[] makeBallNumbersByIntegers(final int[] inputs) {
        BallNumber[] res = new BallNumber[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            res[i] = new BallNumber(inputs[i]);
        }
        return res;
    }

    @DisplayName("ballNumberConverter invalid param test")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "-123", ""})
    public void testInvalidParam(final String input) {
        assertThatThrownBy(() -> BallNumberConverter.resolve(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
