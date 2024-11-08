package store.storeapp.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.storeapp.model.value.Quantity;

class ProductQuantityTest {

    @DisplayName("상품수량은 0 또는 양의정수로 생성가능하다")
    @Test
    void initProductQuantity() {
        assertThatCode(() -> new ProductQuantity(Quantity.of(0)))
                .doesNotThrowAnyException();
        assertThatCode(() -> new ProductQuantity(Quantity.of(3)))
                .doesNotThrowAnyException();

        assertThatCode(() -> new ProductQuantity(Quantity.of(-1)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new ProductQuantity(Quantity.of(null)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품수량은 구매수량으로 차감가능하다")
    @Test
    void minusSuccess() {
        ProductQuantity productQuantity = new ProductQuantity(Quantity.of(10));

        assertThat(productQuantity.minus(3)).isEqualTo(7);

        assertThatCode(() -> productQuantity.minus(productQuantity.get() + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

}