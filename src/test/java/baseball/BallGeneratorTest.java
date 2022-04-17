package baseball;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BallGeneratorTest {

    @DisplayName("BallGenerator create test")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void createTest(final int size) {
        BallNumber[] buffer = BallGenerator.create(1,9, size);
        assertThat(buffer.length).isEqualTo(size);
    }


    @DisplayName("BallGenerator invalid range test")
    @ParameterizedTest
    @CsvSource(value = {"2:1","-1:-2"}, delimiter = ':')
    public void invalidRangeTest(final int start, final int end) {
        expectedIllegalArgumentException(start, end, 1);
    }

    @DisplayName("BallGenerator invalid range test")
    @ParameterizedTest
    @CsvSource(value = {"2:3:2","1:5:6","1:3:0"}, delimiter = ':')
    public void invalidSizeTest(final int start, final int end, final int size) {
        expectedIllegalArgumentException(start, end, size);
    }

    private void expectedIllegalArgumentException(final int start, final int end, final int size) {
        assertThatThrownBy(() -> BallGenerator.create(start, end, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(BallGenerator.argumentExceptionMsg);
    }
}