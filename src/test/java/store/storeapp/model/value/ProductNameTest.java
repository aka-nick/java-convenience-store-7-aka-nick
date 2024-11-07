package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductNameTest {

    @DisplayName("상품명은 15자 이하의 문자열로 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> new ProductName("1")).doesNotThrowAnyException();
        assertThatCode(() -> new ProductName("123456789012345        ")).doesNotThrowAnyException();

        assertThatCode(() -> new ProductName(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new ProductName("")).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new ProductName("   ")).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품명은 비교가능하다")
    @Test
    void compareTo() {
        assertThat(new ProductName("가나다").compareTo(new ProductName("가나다"))).isEqualTo(0);
        assertThat(new ProductName("가나다").compareTo(new ProductName("마바사"))).isLessThan(0);
        assertThat(new ProductName("마바사").compareTo(new ProductName("가나다"))).isGreaterThan(0);
    }

}