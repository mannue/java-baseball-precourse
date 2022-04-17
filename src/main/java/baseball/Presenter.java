package baseball;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class Presenter {

    public String inputPrint() {
        System.out.print("숫자를 입력해주세요:");
        return readLine();
    }

    public void resultPrint(final PlayResult playResult) {
        System.out.println(playResult);
    }

    public int finishPrint(final int hintCount) {
        System.out.printf("%d개의 숫자를 모두 맞히셨습니다! 게임종료%n", hintCount);
        System.out.println("게임을 새로 시작하려면 1,종료하려면 2를 입력하세요");
        try {
            return Integer.parseInt(readLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
