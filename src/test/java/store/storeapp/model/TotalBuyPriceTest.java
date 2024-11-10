package store.storeapp.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.storeapp.value.Won;

class TotalBuyPriceTest {

    @DisplayName("총구매액은 기본 0원으로 생성가능하다")
    @Test
    void init() {
        assertThatCode(TotalBuyPrice::new)
                .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> new TotalBuyPrice(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("총구매액은 덧셈 연산이 가능하다")
    @Test
    void add() {
        TotalBuyPrice totalBuyPrice = new TotalBuyPrice();
        assertThat(totalBuyPrice.add(Won.of(100)))
                .isEqualTo(Won.of(100));
    }

}