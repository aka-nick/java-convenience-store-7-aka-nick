package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BuyProductTest {

    @DisplayName("구매상품은 상품명을 기준으로 동등비교 가능하다")
    @Test
    void testEquals() {
        assertThat(BuyProduct.of(ProductName.of("상품"), Quantity.of(10), Price.of(100)))
                .isEqualTo(BuyProduct.of(ProductName.of("상품"), Quantity.of(10), Price.of(300)));

        assertThat(BuyProduct.of(ProductName.of("상품"), Quantity.of(10), Price.of(100)))
                .isNotEqualTo(BuyProduct.of(ProductName.of("다른상품"), Quantity.of(10), Price.of(100)));
    }

}