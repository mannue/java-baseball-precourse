package baseball;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        BaseBallGameService gameService = new BaseBallGameService();
        gameService.run(new BaseBallGameConfig());
    }
}
