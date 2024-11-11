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

    // TODO: 삭제할 테스트 케이스 임시보관
//    @DisplayName("멤버십할인은 뺄셈 연산이 가능하다")
//    @Test
//    void minus() {
//        assertThat(new MembershipDiscountPrice(0).minus(Price.of(100)))
//                .isEqualTo(Price.of(-100));
//        assertThat(new MembershipDiscountPrice(0).minus(Won.of(100)))
//                .isEqualTo(Price.of(-100));
//
//        assertThatCode(() -> new MembershipDiscountPrice(0).minus((Price) null))
//                .isInstanceOf(IllegalArgumentException.class);
//        assertThatCode(() -> new MembershipDiscountPrice(0).minus((Won) null))
//                .isInstanceOf(IllegalArgumentException.class);
//    }

}