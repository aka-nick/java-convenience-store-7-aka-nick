package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WonTest {

    @DisplayName("원화는 0 또는 양의정수로 생성할 수 있다")
    @Test
    void initOf() {
        assertThatCode(() -> Won.of(1)).doesNotThrowAnyException();
        assertThatCode(() -> Won.of(100)).doesNotThrowAnyException();
        assertThatCode(() -> Won.of(10_000L)).doesNotThrowAnyException();

        assertThatCode(() -> Won.of(-1)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Won.of((Integer) null)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> Won.of((Long) null)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("원화는 덧셈연산 가능하다")
    @Test
    void add() {
        Won won1 = Won.of(100);
        Won won2 = Won.of(300);
        assertThat(won1.add(won2)).isEqualTo(Won.of(400L));
    }

    @DisplayName("원화는 나누기연산 가능하다")
    @Test
    void divide() {
        Won origin = Won.of(100);
        Won operandWon = Won.of(2);
        Long operandLong = 2L;
        Integer operandInteger = 2;

        assertThat(origin.divide(operandWon)).isEqualTo(Won.of(50L));
        assertThat(origin.divide(operandLong)).isEqualTo(50L);
        assertThat(origin.divide(operandInteger)).isEqualTo(50L);
    }

    @DisplayName("원화는 나머지연산 가능하다")
    @Test
    void reminder() {
        Won origin = Won.of(100);
        Won operandWon = Won.of(30);
        Long operandLong = 30L;
        Integer operandInteger = 30;

        assertThat(origin.reminder(operandWon)).isEqualTo(Won.of(10L));
        assertThat(origin.reminder(operandLong)).isEqualTo(10L);
        assertThat(origin.reminder(operandInteger)).isEqualTo(10L);
    }

    @DisplayName("원화는 동등비교 가능하다")
    @Test
    void testEquals() {
        Won origin = Won.of(1);
        Won same = Won.of(1L);
        Won diff = Won.of(2);

        assertThat(origin.equals(same)).isTrue();
        assertThat(origin.equals(diff)).isFalse();
    }

    @DisplayName("원화는 표기 시 천단위 콤마 표시된다")
    @Test
    void testToString() {
        Won won1 = Won.of(10_000);
        Won won2 = Won.of(100_000_000);

        assertThat(won1 + "원").isEqualTo("10,000원");
        assertThat(won2 + "원").isEqualTo("100,000,000원");
    }

    @DisplayName("원화는 대소비교 가능하다")
    @Test
    void compareTo() {
        Won standard = Won.of(100);
        Won same = Won.of(100);
        Won greater = Won.of(300);
        Won smaller = Won.of(50);

        assertThat(standard.compareTo(same)).isEqualTo(0);
        assertThat(standard.compareTo(greater)).isEqualTo(-1);
        assertThat(standard.compareTo(smaller)).isEqualTo(1);
    }

}