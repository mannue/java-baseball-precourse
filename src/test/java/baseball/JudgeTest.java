package baseball;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JudgeTest {
    private final static Judge judge = new Judge();
    private final int startRange = 1;
    private final int endRange = 9;

    @DisplayName("Judge setup test")
    @ParameterizedTest
    @MethodSource("provideIntegerArrayForBallNumbers")
    public void testSetup(final int[] numbers) {
        judgeSetupUsingMockBallGenerator(numbers);
    }

    private void judgeSetupUsingMockBallGenerator(int[] numbers) {
        templateUsingBallGeneratorMock(ballGenerator -> {
            ballGenerator.when(() -> BallGenerator.create(startRange, endRange, numbers.length)).thenReturn(mockBallGeneratorReturnValues(numbers));
            int updatedCount = judge.setup(BallGenerator.create(startRange, endRange, numbers.length));
            assertThat(updatedCount).isEqualTo(numbers.length);
            ballGenerator.verify(() -> BallGenerator.create(startRange, endRange, numbers.length));
        });
    }

    private static Stream<Arguments> provideIntegerArrayForBallNumbers() {
        return Stream.of(
                Arguments.of((Object) new int[]{1, 2, 3}),
                Arguments.of((Object) new int[]{4, 5, 6}),
                Arguments.of((Object) new int[]{7, 8, 9})
        );
    }

    @DisplayName("Judge setup parameter is empty")
    @ParameterizedTest
    @MethodSource("provideInvalidBallNumbers")
    public void testSetupParamIsEmpty(final BallNumber[] invalidParam) {
        assertThatThrownBy(() -> judge.setup(invalidParam))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidBallNumbers() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of((Object) new BallNumber[0])
        );
    }

    @DisplayName("Judge ask test")
    @ParameterizedTest
    @MethodSource("provideMockNumbersAndInputBallNumbersAndExpectedPlayResult")
    public void testAsk(final int[] mockNumbers, final BallNumber[] userInputBallNumbers, final PlayResult expectedPlayResult) {
        judgeSetupUsingMockBallGenerator(mockNumbers);
        PlayResult result = judge.ask(userInputBallNumbers);
        assertThat(result).isEqualTo(expectedPlayResult);
    }

    private static Stream<Arguments> provideMockNumbersAndInputBallNumbersAndExpectedPlayResult() {
        return Stream.of(
                Arguments.of(
                        new int[]{1, 2, 3},
                        BallNumberConverter.resolve("145"),
                        new PlayResult(3, 0, 1)),

                Arguments.of(
                        new int[]{2, 4, 5},
                        BallNumberConverter.resolve("426"),
                        new PlayResult(3, 2, 0)),

                Arguments.of(
                        new int[]{4, 5, 6},
                        BallNumberConverter.resolve("456"),
                        new PlayResult(3, 0, 3)
                )
        );
    }

    @DisplayName("Judge ask invalid param test")
    @ParameterizedTest
    @MethodSource("provideInvalidBallNumbers")
    public void testAskInvalidParams(final BallNumber[] invalidParam) {
        assertThatThrownBy(() -> judge.ask(invalidParam))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void templateUsingBallGeneratorMock(Consumer<MockedStatic<BallGenerator>> consumer) {
        try (MockedStatic<BallGenerator> ballGeneratorMockedStatic = Mockito.mockStatic(BallGenerator.class)) {
            consumer.accept(ballGeneratorMockedStatic);
        }
    }

    private BallNumber[] mockBallGeneratorReturnValues(int... values) {
        BallNumber[] ballNumbers = new BallNumber[values.length];
        for (int i = 1; i <= values.length; i++) {
            ballNumbers[i - 1] = new BallNumber(values[i - 1]);
        }
        return ballNumbers;
    }
}
