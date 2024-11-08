package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionQuantityTest {

    @DisplayName("프로모션수량은 양의정수의 수량 두개로 생성가능하다")
    @Test
    void of() {
        assertThatCode(() -> PromotionQuantity.of(Quantity.of(1), Quantity.of(2)))
                .doesNotThrowAnyException();

        assertThatCode(() -> PromotionQuantity.of(Quantity.of(0), Quantity.of(2)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> PromotionQuantity.of(Quantity.of(2), Quantity.of(0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구매수량이 프로모션적용 필요수량보다 같거나 더 크면 true를, 아니면 false를 반환한다")
    @Test
    void isRequiredQuantityLessThanOrEqual() {
        Quantity purchased2 = Quantity.of(2);
        Quantity purchased10 = Quantity.of(10);
        PromotionQuantity required10 = PromotionQuantity.of(Quantity.of(10), Quantity.of(3));
        PromotionQuantity required1 = PromotionQuantity.of(Quantity.of(1), Quantity.of(1));

        assertThat(required10.isRequiredQuantityLessThanOrEqual(purchased2)).isFalse();
        assertThat(required10.isRequiredQuantityLessThanOrEqual(purchased10)).isTrue();
        assertThat(required1.isRequiredQuantityLessThanOrEqual(purchased2)).isTrue();
    }

}