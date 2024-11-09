package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GiftProductTest {

    @DisplayName("증정상품은 상품명을 기준으로 동등비교 가능하다")
    @Test
    void testEquals() {
        assertThat(GiftProduct.of(ProductName.of("상품"), Quantity.of(10)))
                .isEqualTo(GiftProduct.of(ProductName.of("상품"), Quantity.of(10)));

        assertThat(GiftProduct.of(ProductName.of("상품"), Quantity.of(10)))
                .isNotEqualTo(GiftProduct.of(ProductName.of("다른상품"), Quantity.of(10)));
    }

}