package store.storeapp.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.storeapp.model.value.Price;
import store.storeapp.model.value.Won;

class MembershipDiscountPriceTest {

    @DisplayName("멤버십할인은 가격를 가지고 생성가능하다")
    @Test
    void init() {
        assertThatCode(PromotionDiscountPrice::new)
                .doesNotThrowAnyException();
    }

    @DisplayName("멤버십할인은 뺄셈 연산이 가능하다")
    @Test
    void minus() {
        assertThat(new MembershipDiscountPrice().minus(Price.of(100)))
                .isEqualTo(Price.of(-100));
        assertThat(new MembershipDiscountPrice().minus(Won.of(100)))
                .isEqualTo(Price.of(-100));

        assertThatCode(() -> new MembershipDiscountPrice().minus((Price) null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new MembershipDiscountPrice().minus((Won) null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}