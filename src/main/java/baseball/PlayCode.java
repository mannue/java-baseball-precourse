package baseball;

import java.util.Objects;

public enum PlayCode {
    RETRY(1),
    EXIT(2);

    private int code;

    PlayCode(int code) {
        this.code = code;
    }

    public int of() {
        return this.code;
    }

    public static PlayCode valueOf(final int code) {
        for (PlayCode playCode: PlayCode.values()) {
            if (Objects.equals(playCode.of(), code)) {
                return playCode;
            }
        }
        throw new IllegalArgumentException();
    }
}
