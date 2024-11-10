package store.storeapp.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @DisplayName("프로모션은 "
            + "프로모션명, 프로모션적용에 필요한 구매수량, 프로모션으로 증정되는 수량, 행사기간으로 "
            + "생성가능하다")
    @Test
    void init() {
        assertThatCode(() -> Promotion.of(PromotionName.of("테스트행사"),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2020-01-01", "2030-01-01")))
                .doesNotThrowAnyException();
        assertThatCode(() -> Promotion.of(PromotionName.of("테스트행사"),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2020-01-01", "2020-01-01")))
                .doesNotThrowAnyException();

        assertThatCode(() -> Promotion.of(PromotionName.of("테스트행사"),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2020-01-01", "2010-01-01")))
                .isInstanceOf(IllegalArgumentException.class); // 기간 역전
        assertThatCode(() -> Promotion.of(PromotionName.of("    "),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2020-01-01", "2030-01-01")))
                .isInstanceOf(IllegalArgumentException.class); // 공백 행사명
        assertThatCode(() -> Promotion.of(PromotionName.of("테스트행사"),
                PromotionQuantity.of(Quantity.of(0), Quantity.of(1)),
                Period.of("2020-01-01", "2030-01-01")))
                .isInstanceOf(IllegalArgumentException.class); // 구매수량 제로
        assertThatCode(() -> Promotion.of(PromotionName.of("테스트행사"),
                PromotionQuantity.of(Quantity.of(1), Quantity.of(0)),
                Period.of("2020-01-01", "2030-01-01")))
                .isInstanceOf(IllegalArgumentException.class); // 증정수량 제로
    }

    @DisplayName("프로모션 기간 내면 true를 아니면 false를 반환한다.")
    @Test
    void isPromotionPeriod() {
        Promotion promotion = Promotion.of(PromotionName.of("테스트행사"),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2020-01-01", "2030-01-01"));

        assertThat(promotion.isPromotionPeriod(Date.from("2010-01-01"))).isFalse();
        assertThat(promotion.isPromotionPeriod(Date.from("2025-01-01"))).isTrue();
        assertThat(promotion.isPromotionPeriod(Date.from("2040-01-01"))).isFalse();
    }

    @DisplayName("현재 구매수량이"
            + "프로모션 적용에 필요한 구매수량보다 같거나 더 크면"
            + "true를, 아니면 false를 반환한다.")
    @Test
    void isSatisfyForPromotionRequiredQuantity() {
        Promotion promotion = Promotion.of(PromotionName.of("테스트행사"),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2020-01-01", "2030-01-01"));

        assertThat(promotion.isSatisfyForPromotionRequiredQuantity(Quantity.of(3))).isTrue();
        assertThat(promotion.isSatisfyForPromotionRequiredQuantity(Quantity.of(2))).isTrue();
        assertThat(promotion.isSatisfyForPromotionRequiredQuantity(Quantity.of(1))).isFalse();
    }

    @DisplayName("한번의 프로모션으로 제공되어야 할 상품 수량(프로모션 적용 필요 수량 + 증정 수량)을 조회할 수 있다.")
    @Test
    void getQuantityProvidedAtOnce() {
        Promotion promotion = Promotion.of(PromotionName.of("테스트행사"),
                PromotionQuantity.of(Quantity.of(2), Quantity.of(1)),
                Period.of("2020-01-01", "2030-01-01"));

        assertThat(promotion.getQuantityProvidedAtOnce()).isEqualTo(Quantity.of(3));
    }

}