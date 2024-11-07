package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PeriodTest {

    @DisplayName("기간은 시작일과 같거나 큰 종료일로 생성가능하다")
    @Test
    void of() {
        assertThatCode(() -> Period.of("2024-01-01", "2024-01-01")).doesNotThrowAnyException();
        assertThatCode(() -> Period.of("2024-01-01", "2025-01-01")).doesNotThrowAnyException();

        assertThatCode(() -> Period.of("2024-01-01", "2000-01-01")).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("기간은 범위포함판단이 가능하다")
    @Test
    void include() {
        Period standard = Period.of("2024-01-01", "2025-01-01");
        Date before = Date.from("2020-01-01");
        Date between1 = Date.from("2024-01-01");
        Date between2 = Date.from("2024-01-01");
        Date between3 = Date.from("2024-01-01");
        Date after = Date.from("2030-01-01");

        assertThat(standard.include(before)).isFalse();
        assertThat(standard.include(between1)).isTrue();
        assertThat(standard.include(between2)).isTrue();
        assertThat(standard.include(between3)).isTrue();
        assertThat(standard.include(after)).isFalse();
    }

}