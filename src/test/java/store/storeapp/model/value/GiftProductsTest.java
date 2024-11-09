package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatCollection;

import java.util.TreeSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GiftProductsTest {

    @DisplayName("증정상품목록은 기본 또는 '증정상품-카운트'맵으로 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> GiftProducts.of()).doesNotThrowAnyException();
        assertThatCode(() -> GiftProducts.of(new TreeSet<>() {{
            add(GiftProduct.of(ProductName.of("상품명"), Quantity.of(10)));
        }})).doesNotThrowAnyException();

        assertThatCode(() -> GiftProducts.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("증정상품수를 계속 추가하며 카운트가능하다.")
    @Test
    void addGifts() {
        GiftProducts giftProducts = GiftProducts.of();
        GiftProduct origin = GiftProduct.of(ProductName.of("상품명"), Quantity.of(10));
        GiftProduct addedSame = GiftProduct.of(ProductName.of("상품명"), Quantity.of(20));
        GiftProduct addedDiff = GiftProduct.of(ProductName.of("다른상품명"), Quantity.of(30));

        giftProducts.addGifts(origin);
        assertThat(giftProducts.stream()
                .mapToInt(gift -> gift.quantity().get()).sum())
                .isEqualTo(origin.quantity().get());

        giftProducts.addGifts(addedSame);
        assertThat(giftProducts.stream()
                .mapToInt(gift -> gift.quantity().get()).sum())
                .isEqualTo(origin.quantity().add(addedSame.quantity()).get());

        giftProducts.addGifts(addedDiff);
        assertThat(giftProducts.stream().count()).isEqualTo(2);
        assertThatCollection(giftProducts.stream().toList()).contains(
                GiftProduct.of(ProductName.of("상품명"), Quantity.of(30)),
                GiftProduct.of(ProductName.of("다른상품명"), Quantity.of(30))
        );
    }

}