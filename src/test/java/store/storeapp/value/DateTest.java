package store.storeapp.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTest {

    @DisplayName("일자는 올바른 날짜 형식 문자열로 생성가능하다")
    @Test
    void from() {
        assertThat(Date.from("2022-04-20")).isEqualTo(new Date(LocalDate.of(2022, 4, 20)));

        assertThatCode(() -> Date.from("20222-04-20"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Date.from("2024-13-  "))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Date.from(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("일자는 전후(대소)비교를 할 수 있다.")
    @Test
    void comparisonTest() {
        Date standard = Date.from("2024-10-10");
        Date greater = Date.from("2024-10-11");
        Date equals = Date.from("2024-10-10");
        Date less = Date.from("2024-10-09");

        assertThat(standard.equals(equals)).isTrue();
        assertThat(standard.isAfterThanOrSame(equals)).isTrue();
        assertThat(standard.isBeforeThanOrSame(equals)).isTrue();

        assertThat(standard.isAfterThan(greater)).isFalse();
        assertThat(standard.isAfterThanOrSame(greater)).isFalse();
        assertThat(standard.isAfterThan(less)).isTrue();
        assertThat(standard.isAfterThanOrSame(less)).isTrue();

        assertThat(standard.isBeforeThan(greater)).isTrue();
        assertThat(standard.isBeforeThanOrSame(greater)).isTrue();
        assertThat(standard.isBeforeThan(less)).isFalse();
        assertThat(standard.isBeforeThanOrSame(less)).isFalse();
    }

}