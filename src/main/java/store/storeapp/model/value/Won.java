package store.storeapp.model.value;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Objects;

public class Won implements Comparable<Won> {

    public static final Integer MIN_VALUE = 0;
    public static final Integer MAX_VALUE = 100_000;
    private static final String PATTERN_OF_THOUSANDS_UNIT = "#,###";

    private final BigInteger amount;

    public Won(BigInteger amount) {
        if (amount == null) {
            WonException.CANNOT_BE_INITIALIZED_TO_WRONG_VALUE.raise();
        }
        if (isSmallerThanZero(amount)) {
            WonException.CANNOT_BE_INITIALIZED_TO_WRONG_VALUE.raise();
        }
        this.amount = amount;
    }

    public static Won of(Integer amount) {
        if (amount == null) {
            WonException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return new Won(BigInteger.valueOf(amount));
    }

    public static Won of(Long amount) {
        if (amount == null) {
            WonException.NULL_CANNOT_BE_ENTERED.raise();
        }
        return new Won(BigInteger.valueOf(amount));
    }

    public Won add(Integer amount) {
        return add(Won.of(amount));
    }

    public Won add(Won won) {
        return new Won(amount.add(won.amount));
    }

    public Won minus(Won other) {
        return new Won(amount.subtract(other.amount));
    }

    public Won minus(Integer other) {
        return minus(Won.of(other));
    }

    public Won divide(Won won) {
        return new Won(amount.divide(won.amount));
    }

    public long divide(Integer operand) {
        return divide(Long.valueOf(operand));
    }

    public long divide(Long operand) {
        return amount.divide(BigInteger.valueOf(operand)).longValue();
    }

    public Won reminder(Won won) {
        return new Won(amount.remainder(won.amount));
    }

    public long reminder(Integer operand) {
        return reminder(Long.valueOf(operand));
    }

    public long reminder(Long operand) {
        return amount.remainder(BigInteger.valueOf(operand)).longValue();
    }

    public int getIntValue() {
        return amount.intValue();
    }

    public BigInteger get() {
        return amount;
    }

    private static boolean isSmallerThanZero(BigInteger amount) {
        return amount.compareTo(BigInteger.ZERO) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Won other = (Won) o;
        return Objects.equals(amount, other.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return new DecimalFormat(PATTERN_OF_THOUSANDS_UNIT)
                .format(amount);
    }

    @Override
    public int compareTo(Won o) {
        if (o == null) {
            WonException.NULL_CANNOT_BE_ENTERED.raise();
        }

        return this.amount.compareTo(o.amount);
    }

    private enum WonException {

        CANNOT_BE_INITIALIZED_TO_WRONG_VALUE(() -> {
            throw new IllegalArgumentException(String.format(
                    "금액은 %d ~ %d의 정수로 초기화할 수 있습니다.", Won.MIN_VALUE, Won.MAX_VALUE));
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        WonException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}