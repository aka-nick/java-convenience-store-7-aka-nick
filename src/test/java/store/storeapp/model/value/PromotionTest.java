package store.storeapp.model.value;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @DisplayName("프로모션은 "
            + "프로모션명, 프로모션적용에 필요한 구매수량, 프로모션으로 증정되는 수량, 행사기간으로 "
            + "생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> new Promotion(PromotionName.of("테스트행사"),
                PromotionQuantity.of(new Quantity(2), new Quantity(1)),
                Period.of("2020-01-01", "2030-01-01")))
                .doesNotThrowAnyException();
        assertThatCode(() -> new Promotion(PromotionName.of("테스트행사"),
                PromotionQuantity.of(new Quantity(2), new Quantity(1)),
                Period.of("2020-01-01", "2020-01-01")))
                .doesNotThrowAnyException();

        assertThatCode(() -> new Promotion(PromotionName.of("테스트행사"),
                PromotionQuantity.of(new Quantity(2), new Quantity(1)),
                Period.of("2020-01-01", "2010-01-01")))
                .isInstanceOf(IllegalArgumentException.class); // 기간 역전
        assertThatCode(() -> new Promotion(PromotionName.of("    "),
                PromotionQuantity.of(new Quantity(2), new Quantity(1)),
                Period.of("2020-01-01", "2030-01-01")))
                .isInstanceOf(IllegalArgumentException.class); // 공백 행사명
        assertThatCode(() -> new Promotion(PromotionName.of("테스트행사"),
                PromotionQuantity.of(new Quantity(0), new Quantity(1)),
                Period.of("2020-01-01", "2030-01-01")))
                .isInstanceOf(IllegalArgumentException.class); // 구매수량 제로
        assertThatCode(() -> new Promotion(PromotionName.of("테스트행사"),
                PromotionQuantity.of(new Quantity(1), new Quantity(0)),
                Period.of("2020-01-01", "2030-01-01")))
                .isInstanceOf(IllegalArgumentException.class); // 증정수량 제로
    }

}