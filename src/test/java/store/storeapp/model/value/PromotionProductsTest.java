package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatCollection;

import java.util.TreeSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionProductsTest {

    @DisplayName("증정상품목록은 기본 또는 '증정상품-카운트'맵으로 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> PromotionProducts.of()).doesNotThrowAnyException();
        assertThatCode(() -> PromotionProducts.of(new TreeSet<>() {{
            add(PromotionProduct.of(ProductName.of("상품명"), Quantity.of(10)));
        }})).doesNotThrowAnyException();

        assertThatCode(() -> PromotionProducts.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("증정상품수를 계속 추가하며 카운트가능하다.")
    @Test
    void addOrUpdate() {
        PromotionProducts promotionProducts = PromotionProducts.of();
        PromotionProduct origin = PromotionProduct.of(ProductName.of("상품명"), Quantity.of(10));
        PromotionProduct addedSame = PromotionProduct.of(ProductName.of("상품명"), Quantity.of(20));
        PromotionProduct addedDiff = PromotionProduct.of(ProductName.of("다른상품명"), Quantity.of(30));

        promotionProducts.addOrUpdate(origin);
        assertThat(promotionProducts.stream()
                .mapToInt(gift -> gift.quantity().get()).sum())
                .isEqualTo(origin.quantity().get());

        promotionProducts.addOrUpdate(addedSame);
        assertThat(promotionProducts.stream()
                .mapToInt(gift -> gift.quantity().get()).sum())
                .isEqualTo(origin.quantity().add(addedSame.quantity()).get());

        promotionProducts.addOrUpdate(addedDiff);
        assertThat(promotionProducts.stream().count()).isEqualTo(2);
        assertThatCollection(promotionProducts.stream().toList()).contains(
                PromotionProduct.of(ProductName.of("상품명"), Quantity.of(30)),
                PromotionProduct.of(ProductName.of("다른상품명"), Quantity.of(30))
        );
    }

}