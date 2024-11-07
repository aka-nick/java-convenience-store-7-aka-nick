package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductNameTest {

    @DisplayName("상품명은 15자 이하의 문자열로 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> ProductName.of("1")).doesNotThrowAnyException();
        assertThatCode(() -> ProductName.of("123456789012345        ")).doesNotThrowAnyException();

        assertThatCode(() -> ProductName.of(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> ProductName.of("")).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> ProductName.of("   ")).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> ProductName.of("12345678901234567890")).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품명은 비교가능하다")
    @Test
    void compareTo() {
        assertThat(ProductName.of("가나다").compareTo(ProductName.of("가나다"))).isEqualTo(0);
        assertThat(ProductName.of("가나다").compareTo(ProductName.of("마바사"))).isLessThan(0);
        assertThat(ProductName.of("마바사").compareTo(ProductName.of("가나다"))).isGreaterThan(0);
    }

}