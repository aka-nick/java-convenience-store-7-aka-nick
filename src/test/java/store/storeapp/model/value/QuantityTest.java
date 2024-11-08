package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QuantityTest {

    @DisplayName("수량은 0 또는 양의 정수로 초기화 가능하다.")
    @Test
    void initialize() {
        assertThatCode(() -> Quantity.of(0))
                .doesNotThrowAnyException();
        assertThatCode(() -> Quantity.of(1))
                .doesNotThrowAnyException();

        assertThatCode(() -> Quantity.of(-1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Quantity.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("수량은 대소비교 가능하다")
    @ParameterizedTest
    @CsvSource({"1,2,-1", "2,1,1", "1,1,0"})
    void compareTo(int left, int right, int expected) {
        Quantity leftQuantity = Quantity.of(left);
        Quantity rightQuantity = Quantity.of(right);
        assertThat(leftQuantity.compareTo(rightQuantity)).isEqualTo(expected);
    }

    @DisplayName("수량은 동등비교 가능하다")
    @Test
    void equals() {
        Quantity origin = Quantity.of(1);
        Quantity sameValue = Quantity.of(1);
        Quantity diffValue = Quantity.of(2);

        assertThat(origin.equals(sameValue)).isTrue();
        assertThat(origin.equals(diffValue)).isFalse();
    }

}