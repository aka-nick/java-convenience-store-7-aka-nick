package store.storeapp.model.value;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public record Date(LocalDate date) implements Comparable<Date> {

    public static Date from(String dateString) {
        if (dateString == null || dateString.isBlank()) {
            throw new IllegalArgumentException("빈 값을 지정할 수 없습니다.");
        }
        try {
            LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다.");
        }

        return new Date(LocalDate.parse(dateString));
    }

    public static Date now() {
        return new Date(LocalDate.now());
    }

    public boolean isAfterThan(Date other) {
        return this.compareTo(other) > 0;
    }

    public boolean isAfterThanOrSame(Date other) {
        return this.compareTo(other) >= 0;
    }

    public boolean isBeforeThan(Date other) {
        return this.compareTo(other) < 0;
    }

    public boolean isBeforeThanOrSame(Date other) {
        return this.compareTo(other) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Date other = (Date) o;
        return Objects.equals(date, other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }

    @Override
    public int compareTo(Date other) {
        if (other == null) {
            throw new IllegalArgumentException("null을 지정할 수 없습니다.");
        }

        return date.compareTo(other.date);
    }

    @Override
    public String toString() {
        return date.toString();
    }

}
