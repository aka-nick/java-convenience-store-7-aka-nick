package store.storeapp.view;

import store.config.view.ConsoleInput;
import store.storeapp.value.PickProduct;
import store.storeapp.value.PickProducts;
import store.storeapp.value.ProductName;
import store.storeapp.value.Quantity;
import store.storeapp.value.YesOrNo;

public class StoreInput {

    public PickProducts askToBuy() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String separatorOfBuyProduct = "-";
        return new PickProducts(ConsoleInput.readStrings().stream()
                .map(StoreInput::stripBrackets)
                .map(buyProductInput -> buyProductInput.split(separatorOfBuyProduct))
                .map(nameAndQuantity -> new PickProduct(
                        ProductName.of(nameAndQuantity[0]),
                        Quantity.of(Integer.valueOf(nameAndQuantity[1]))))
                .toList());
    }

    public YesOrNo askToReceiveAdditionalGift(PickProduct pickProduct) {
        System.out.println("현재 %s은(는) %s개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"
                .formatted(pickProduct.name(), pickProduct.quantity()));
        return YesOrNo.findByRepresentation(ConsoleInput.readString());
    }

    public YesOrNo askPayForOutOfStock(PickProduct pickProduct) {
        System.out.println("현재 %s %s개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"
                .formatted(pickProduct.name(), pickProduct.quantity()));
        return YesOrNo.findByRepresentation(ConsoleInput.readString());
    }

    public YesOrNo askGetMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까?");
        return YesOrNo.findByRepresentation(ConsoleInput.readString());
    }

    public YesOrNo askBuyMore() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return YesOrNo.findByRepresentation(ConsoleInput.readString());
    }

    private static String stripBrackets(String buyProductInput) {
        return buyProductInput.substring(1, buyProductInput.length() - 1);
    }

}
