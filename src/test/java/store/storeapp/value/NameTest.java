package store.storeapp.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("상품명은 공백이 아닌 문자열로 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> new Name("1")).doesNotThrowAnyException();
        assertThatCode(() -> new Name("12345678901234567890")).doesNotThrowAnyException();

        assertThatCode(() -> new Name(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new Name("")).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new Name("   ")).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품명은 비교가능하다")
    @Test
    void compareTo() {
        assertThat(new Name("가나다").compareTo(new Name("가나다"))).isEqualTo(0);
        assertThat(new Name("가나다").compareTo(new Name("마바사"))).isLessThan(0);
        assertThat(new Name("마바사").compareTo(new Name("가나다"))).isGreaterThan(0);
    }

}