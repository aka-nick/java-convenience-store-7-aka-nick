package store.storeapp.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatCollection;

import java.util.TreeSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BuyProductsTest {

    @DisplayName("증정상품목록은 기본 또는 '증정상품-카운트'맵으로 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> BuyProducts.of()).doesNotThrowAnyException();
        assertThatCode(() -> BuyProducts.of(new TreeSet<>() {{
            add(BuyProduct.of(ProductName.of("상품명"), Quantity.of(10), Price.of(100)));
        }})).doesNotThrowAnyException();

        assertThatCode(() -> BuyProducts.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("증정상품수를 계속 추가하며 카운트가능하다.")
    @Test
    void addOrUpdate() {
        BuyProducts buyProducts = BuyProducts.of();
        BuyProduct origin = BuyProduct.of(ProductName.of("상품명"), Quantity.of(10), Price.of(10));
        BuyProduct addedSame = BuyProduct.of(ProductName.of("상품명"), Quantity.of(20), Price.of(20));
        BuyProduct addedDiff = BuyProduct.of(ProductName.of("다른상품명"), Quantity.of(30), Price.of(30));

        buyProducts.addOrUpdate(origin);
        assertThat(buyProducts.stream()
                .mapToInt(gift -> gift.quantity().get()).sum())
                .isEqualTo(origin.quantity().get());
        assertThat(buyProducts.stream().findFirst().get())
                .isEqualTo(origin);

        buyProducts.addOrUpdate(addedSame);
        assertThat(buyProducts.stream().count())
                .isEqualTo(1L);
        assertThat(buyProducts.stream()
                .mapToInt(gift -> gift.quantity().get()).sum())
                .isEqualTo(origin.quantity().add(addedSame.quantity()).get());

        buyProducts.addOrUpdate(addedDiff);
        assertThat(buyProducts.stream().count())
                .isEqualTo(2L);
        assertThat(buyProducts.stream().count()).isEqualTo(2);
        assertThatCollection(buyProducts.stream().toList()).contains(
                BuyProduct.of(ProductName.of("상품명"), Quantity.of(30), Price.of(30)),
                BuyProduct.of(ProductName.of("다른상품명"), Quantity.of(30), Price.of(30))
        );
    }

}