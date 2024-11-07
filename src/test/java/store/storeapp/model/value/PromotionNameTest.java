package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionNameTest {

    @DisplayName("프로모션명은 20자 이하의 문자열로 생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> PromotionName.of("1")).doesNotThrowAnyException();
        assertThatCode(() -> PromotionName.of("12345678901234567890")).doesNotThrowAnyException();

        assertThatCode(() -> PromotionName.of(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> PromotionName.of("")).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> PromotionName.of("   ")).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> PromotionName.of("1234567890123456789012345")).isInstanceOf(
                IllegalArgumentException.class);
    }

    @DisplayName("상품명은 비교가능하다")
    @Test
    void compareTo() {
        assertThat(PromotionName.of("가나다").compareTo(PromotionName.of("가나다"))).isEqualTo(0);
        assertThat(PromotionName.of("가나다").compareTo(PromotionName.of("마바사"))).isLessThan(0);
        assertThat(PromotionName.of("마바사").compareTo(PromotionName.of("가나다"))).isGreaterThan(0);
    }

}