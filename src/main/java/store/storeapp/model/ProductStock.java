package store.storeapp.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import store.config.value.YesOrNo;
import store.storeapp.value.Date;
import store.storeapp.value.PickProduct;
import store.storeapp.value.ProductName;
import store.storeapp.value.Quantity;
import store.storeapp.value.QuantityOfPaidAndProvided;
import store.storeapp.value.ReceiptOfProduct;

public class ProductStock {

    private final Map<ProductName, Product> stock;

    public ProductStock(Map<ProductName, Product> stock) {
        if (stock == null || stock.isEmpty()) {
            ProductStockException.CANNOT_BE_INITIALIZED_TO_EMPTY.raise();
        }
        this.stock = Map.copyOf(stock);
    }

    public Stream<Entry<ProductName, Product>> entryStream() {
        return stock.entrySet().stream();
    }

    public boolean isSalesAvailable(PickProduct pickProduct) {
        thrownIfIsNotExistsProduct(pickProduct.name());
        thrownIfIsNotEnoughStockLeft(pickProduct.name(), pickProduct.quantity());
        return true;
    }

    private void thrownIfIsNotExistsProduct(ProductName productName) {
        if (stock.get(productName) == null) {
            ProductStockException.IS_NOT_EXISTS_PRODUCT.raise();
        }
    }

    private void thrownIfIsNotEnoughStockLeft(ProductName productName, Quantity quantity) {
        if (!stock.get(productName).isTotalStockGreaterThanOrEqualTo(quantity)) {
            ProductStockException.IS_NOT_ENOUGH_STOCK_LEFT.raise();
        }
    }

    public boolean isPossibleToReceiveGift(PickProduct pick) {
        Product target = stock.get(pick.name());
        return target.isApplicablePromotionButNotReceived(Date.now(), pick.quantity());
    }

    public Quantity getAmountOfPossibleToReceiveGift(PickProduct pick) {
        Product target = stock.get(pick.name());
        return target.promotion().quantity().providedByPromotion();
    }

    public boolean isAnyPromotionNotAvailable(PickProduct pick) {
        Product target = stock.get(pick.name());
        return target.isApplicablePromotionButLackOfStock(Date.now(), pick.quantity());
    }

    public Quantity getQuantityOfPromotionNotAvailable(PickProduct pick) {
        Product target = stock.get(pick.name());
        return target.calculateQuantityOfPromotionNotAvailable(pick.quantity());
    }

    public ReceiptOfProduct salesAndRecordReceiptOfSoldProduct(PickProduct pick, YesOrNo answerForMembershipDiscount) {
        // 판매
        Product target = stock.get(pick.name());

        // [프로모션 기록]
        QuantityOfPaidAndProvided paidAndProvided = target.calculatePaidAndProvided(pick.quantity(), Date.now());
        // 프로모션 기간이냐
        // pickQ % (ruled) == 0    => 전부 가능한 프로모션으로 받음 (결제수: (pickQ / ruled) * req / 증정수: (pickQ / ruled) * prov)
        // pickQ % (ruled) != 0    => 일부 가능한 프로모션 받고 나머지는 제값계산 (결제수: product.calculateQuantityOfPromotionNotAvailable() / 증정수: product.getMaxQuantityOfProvidedAvailable())
        // 프로모션 기간이 아니냐
        // pickQ % (ruled) == 0    => 전부 제값계산 (결제수: pickQ / 증정수: 0)
        // pickQ % (ruled) != 0    => 전부 제값계산 (결제수: pickQ / 증정수: 0)

        // [멤버십 기록]
        MembershipDiscountPrice membershipPrice = MembershipDiscountPrice.from(target, answerForMembershipDiscount);

        // 수량의 소진
        target.deductionQuantity(pick.quantity());

        // [영수 기록 취합]
        return ReceiptOfProduct.from(target, paidAndProvided, membershipPrice);
    }

    private enum ProductStockException {

        IS_NOT_EXISTS_PRODUCT(() -> {
            throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }),
        IS_NOT_ENOUGH_STOCK_LEFT(() -> {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }),
        CANNOT_BE_INITIALIZED_TO_EMPTY(() -> {
            throw new IllegalArgumentException("공백으로 생성할 수 없습니다.");
        }),
        NULL_CANNOT_BE_ENTERED(() -> {
            throw new IllegalArgumentException("null이 입력될 수 없습니다.");
        });

        private final Runnable thrown;

        ProductStockException(Runnable thrown) {
            this.thrown = thrown;
        }

        private void raise() {
            this.thrown.run();
        }

    }

}
