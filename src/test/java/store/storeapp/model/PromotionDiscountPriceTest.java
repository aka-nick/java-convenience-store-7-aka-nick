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

    // TODO: 삭제될 메서드 임시 보관
//    @DisplayName("행사할인은 뺄셈 연산이 가능하다")
//    @Test
//    void minus() {
//        assertThat(new PromotionDiscountPrice().minus(Price.of(100)))
//                .isEqualTo(Price.of(-100));
//        assertThat(new PromotionDiscountPrice().minus(Won.of(100)))
//                .isEqualTo(Price.of(-100));
//
//        assertThatCode(() -> new PromotionDiscountPrice().minus((Price) null))
//                .isInstanceOf(IllegalArgumentException.class);
//        assertThatCode(() -> new PromotionDiscountPrice().minus((Won) null))
//                .isInstanceOf(IllegalArgumentException.class);
//    }

}