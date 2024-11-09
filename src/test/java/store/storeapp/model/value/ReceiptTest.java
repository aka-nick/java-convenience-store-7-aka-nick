package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.storeapp.model.MembershipDiscountPrice;
import store.storeapp.model.PromotionDiscountPrice;

class ReceiptTest {

    @DisplayName("영수증은 "
            + "기본 "
            + "또는 "
            + "구매상품목록, 증정상품목록, 증정할인금액, 멤버십할인금액으로 "
            + "생성가능하다")
    @Test
    void of() {
        assertThatCode(() -> Receipt.of())
                .doesNotThrowAnyException();

        assertThatCode(() -> Receipt.of(BuyProducts.of(),
                PromotionProducts.of(),
                new PromotionDiscountPrice(),
                new MembershipDiscountPrice()))
                .doesNotThrowAnyException();

        assertThatCode(() -> Receipt.of(null, null, null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}