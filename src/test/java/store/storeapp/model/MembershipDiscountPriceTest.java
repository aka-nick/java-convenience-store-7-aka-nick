package store.storeapp.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.config.value.YesOrNo;
import store.storeapp.value.Price;
import store.storeapp.value.ProductName;
import store.storeapp.value.Promotion;
import store.storeapp.value.Quantity;

class MembershipDiscountPriceTest {

    @DisplayName("멤버십할인은 가격를 가지고 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> MembershipDiscountPrice.from(new Product(ProductName.of("test"),
                Price.of(100),
                new ProductQuantity(Quantity.ZERO),
                new ProductQuantity(Quantity.ZERO),
                Promotion.empty()), YesOrNo.YES))
                .doesNotThrowAnyException();
    }

}