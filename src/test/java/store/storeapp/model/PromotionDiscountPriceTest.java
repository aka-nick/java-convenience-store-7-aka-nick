package store.storeapp.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.storeapp.value.Price;

class PromotionDiscountPriceTest {

    @DisplayName("행사할인은 가격를 가지고 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> new PromotionDiscountPrice(Price.of(10)))
                .doesNotThrowAnyException();
    }

}