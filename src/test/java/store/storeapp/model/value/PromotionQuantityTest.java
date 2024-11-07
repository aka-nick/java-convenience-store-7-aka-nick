package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionQuantityTest {

    @DisplayName("프로모션수량은 양의정수의 수량 두개로 생성가능하다")
    @Test
    void of() {
        assertThatCode(() -> PromotionQuantity.of(new Quantity(1), new Quantity(2)))
                .doesNotThrowAnyException();

        assertThatCode(() -> PromotionQuantity.of(new Quantity(0), new Quantity(2)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> PromotionQuantity.of(new Quantity(2), new Quantity(0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}