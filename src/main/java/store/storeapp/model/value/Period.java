package store.storeapp.model.value;

import java.util.Objects;

public class Period {

    private final Date fromDate;
    private final Date toDate;

    public Period(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public static Period of(Date fromDate, Date toDate) {
        if (toDate.isBeforeThan(fromDate)) {
            PeriodException.END_DATE_CANNOT_BE_EARLIER_THAN_START_DATE.raise();
        }
        return new Period(fromDate, toDate);
    }

    public static Period of(String fromDateString, String toDateString) {
        Date fromDate = Date.from(fromDateString);
        Date toDate = Date.from(toDateString);
        return of(fromDate, toDate);
    }

    public boolean include(Date now) {
        return fromDate.isBeforeThanOrSame(now) && now.isBeforeThanOrSame(toDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Period period = (Period) o;
        return Objects.equals(fromDate, period.fromDate) && Objects.equals(toDate, period.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, toDate);
    }

    private enum PeriodException {

        END_DATE_CANNOT_BE_EARLIER_THAN_START_DATE(() -> {
            throw new IllegalArgumentException("시작일 보다 종료일이 앞설 수 없습니다.");
        }),
        CANNOT_BE_INITIALIZED_TO_EMPTY(() -> {
            throw new IllegalArgumentException("공백으로 생성할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        PeriodException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
