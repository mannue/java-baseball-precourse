package baseball;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PresenterTest {
    Presenter presenter = new Presenter();

    @DisplayName("presenter finishPrint invalid param test")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "1.3"})
    public void testInvalidFinishPrint(final String input) {
        try (MockedStatic<Console> mockConsole = Mockito.mockStatic(Console.class)) {
            mockConsole.when(Console::readLine).thenReturn(input);
            assertThatThrownBy(() -> presenter.finishPrint(3))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
