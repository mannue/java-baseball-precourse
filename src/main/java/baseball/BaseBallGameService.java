package baseball;

import java.util.Objects;

public class BaseBallGameService {

    private final Judge baseBallJudge;
    private final Presenter presenter;

    public BaseBallGameService() {
        this.baseBallJudge = new Judge();
        this.presenter = new Presenter();
    }

    public void run(final BaseBallGameConfig config) {
        do {
            if(!initSuccess(config)) {
                throw new IllegalArgumentException();
            }
            startGame();
        } while (Objects.equals(isContinueGame(config.getInputSize()), PlayCode.RETRY));
    }

    private PlayCode isContinueGame(final int numberSize) {
        final int continueCode = this.presenter.finishPrint(numberSize);
        return PlayCode.valueOf(continueCode);
    }

    private void startGame() {
        PlayResult result = null;
        do {
            BallNumber[] inputNumbers = BallNumberConverter.resolve(presenter.inputPrint());
            result = this.baseBallJudge.ask(inputNumbers);
            presenter.resultPrint(result);
        } while (!result.isFinish());
    }

    private boolean initSuccess(BaseBallGameConfig config) {
        int res = this.baseBallJudge.setup(BallGenerator.create(config.getStartRange(), config.getEndRange(), config.getInputSize()));
        return Objects.equals(res, config.getInputSize());
    }
}
