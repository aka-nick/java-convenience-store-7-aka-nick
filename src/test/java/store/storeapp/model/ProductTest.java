package store.storeapp.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.storeapp.value.Period;
import store.storeapp.value.Price;
import store.storeapp.value.ProductName;
import store.storeapp.value.Promotion;
import store.storeapp.value.PromotionName;
import store.storeapp.value.PromotionQuantity;
import store.storeapp.value.Quantity;

class ProductTest {

    private ProductName name;
    private Price price;
    private Promotion promotion;
    private ProductQuantity regularQuantity;
    private ProductQuantity promotionQuantity;

    @BeforeEach
    void setUp() {
        name = ProductName.of("테스트상품");
        price = Price.of(3000);
        regularQuantity = new ProductQuantity(Quantity.of(10));
        promotionQuantity = new ProductQuantity(Quantity.of(10));
        promotion = Promotion.of(
                PromotionName.of("테스트프로모션"),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2024-12-24", "2024-12-25"));
    }

    @DisplayName("상품은 상품명, 가격, 정가상품수량, 프로모션상품수량, 프로모션으로 생성가능하다")
    @Test
    void initProduct() {
        assertThatCode(() -> new Product(name, price, regularQuantity, promotionQuantity, promotion))
                .doesNotThrowAnyException();
    }

    @DisplayName("상품은 현재구매건 구매수량이 재고수량을 초과하면 예외일으킨다")
    @Test
    void thrownByOutOfStock() {
        Product product = new Product(name, price, regularQuantity, promotionQuantity, promotion);

        assertThatCode(() -> product.thrownByOutOfStock(Quantity.of(1_000)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

}