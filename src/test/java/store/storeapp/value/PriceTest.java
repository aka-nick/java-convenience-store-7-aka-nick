package store.storeapp.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

    @DisplayName("가격은 0 또는 양의정수로 생성할 수 있다.")
    @Test
    void initOf() {
        assertThatCode(() -> Price.of(1)).doesNotThrowAnyException();
        assertThatCode(() -> Price.of(1L)).doesNotThrowAnyException();
        assertThatCode(() -> Price.of(0)).doesNotThrowAnyException();
        assertThatCode(() -> Price.of(-1)).doesNotThrowAnyException();
        assertThatCode(() -> new Price(BigInteger.ONE)).doesNotThrowAnyException();

        assertThatCode(() -> Price.of((Long) null)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Price.of((Integer) null)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new Price(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격은 더하기연산이 가능하다")
    @Test
    void add() {
        Price price1 = Price.of(2);
        Price price2 = Price.of(4);

        assertThat(price1.add(price2)).isEqualTo(Price.of(6));
    }

    @DisplayName("가격은 빼기연산이 가능하다")
    @Test
    void minus() {
        Price price1 = Price.of(2);
        Price price2 = Price.of(4);

        assertThat(price1.minus(price2)).isEqualTo(Price.of(-2));
    }

    @DisplayName("가격은 곱하기연산이 가능하다")
    @Test
    void multiply() {
        Price price1 = Price.of(2);
        Price price2 = Price.of(4);

        assertThat(price1.multiply(price2)).isEqualTo(Price.of(8));
    }

    @Test
    void longValue() {
        Price price = Price.of(10);
        assertThat(price.longValue())
                .isInstanceOf(Long.class)
                .isEqualTo(10L);
    }

    @DisplayName("가격은 대소비교가 가능하다")
    @Test
    void compareTo() {
        Price standard = Price.of(100);
        Price same = Price.of(100);
        Price greater = Price.of(300);
        Price smaller = Price.of(50);

        assertThat(standard.compareTo(same)).isEqualTo(0);
        assertThat(standard.compareTo(greater)).isEqualTo(-1);
        assertThat(standard.compareTo(smaller)).isEqualTo(1);
    }

    @DisplayName("가격은 표기 시 천단위 콤마 표시된다")
    @Test
    void testToString() {
        Price price1 = Price.of(10_000);
        Price price2 = Price.of(100_000_000);

        assertThat(price1 + "원").isEqualTo("10,000원");
        assertThat(price2 + "원").isEqualTo("100,000,000원");
    }

}