package baseball;


import java.util.Objects;

class BallNumber{
    private final int value;

    public BallNumber(final int value) {
        this.value = value;
    }

    public BallNumber(final String value) {
        this(Integer.parseInt(value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BallNumber that = (BallNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
