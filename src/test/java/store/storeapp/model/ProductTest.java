package store.storeapp.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.storeapp.model.value.Period;
import store.storeapp.model.value.Price;
import store.storeapp.model.value.ProductName;
import store.storeapp.model.value.Promotion;
import store.storeapp.model.value.PromotionName;
import store.storeapp.model.value.PromotionQuantity;
import store.storeapp.model.value.Quantity;

class ProductTest {

    @DisplayName("상품은 상품명, 가격, 정가상품수량, 프로모션상품수량, 프로모션으로 생성가능하다")
    @Test
    void initProduct() {
        ProductName name = ProductName.of("테스트상품");
        Price price = Price.of(3000);
        ProductQuantity regularQuantity = new ProductQuantity(Quantity.of(10));
        ProductQuantity promotionQuantity = new ProductQuantity(Quantity.of(10));
        Promotion promotion = Promotion.of(
                PromotionName.of("테스트프로모션"),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2024-12-24", "2024-12-25"));

        assertThatCode(() -> new Product(name, price, regularQuantity, promotionQuantity, promotion))
                .doesNotThrowAnyException();
    }

}