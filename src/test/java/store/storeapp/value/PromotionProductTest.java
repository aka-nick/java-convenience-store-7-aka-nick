package store.storeapp.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionProductTest {

    @DisplayName("증정상품은 상품명을 기준으로 동등비교 가능하다")
    @Test
    void testEquals() {
        assertThat(PromotionProduct.of(ProductName.of("상품"), Quantity.of(10)))
                .isEqualTo(PromotionProduct.of(ProductName.of("상품"), Quantity.of(10)));

        assertThat(PromotionProduct.of(ProductName.of("상품"), Quantity.of(10)))
                .isNotEqualTo(PromotionProduct.of(ProductName.of("다른상품"), Quantity.of(10)));
    }

}