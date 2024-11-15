package store.storeapp.view;

import java.util.Map;
import java.util.stream.Collectors;
import store.config.value.YesOrNo;
import store.config.view.ConsoleInput;
import store.storeapp.value.PickProduct;
import store.storeapp.value.PickProducts;
import store.storeapp.value.ProductName;
import store.storeapp.value.Quantity;

public class StoreInput {

    public PickProducts askToBuy() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String separatorOfBuyProduct = "-";

        Map<ProductName, Quantity> productMap = ConsoleInput.readStrings().stream()
                .map(StoreInput::stripBrackets)
                .map(buyProductInput -> buyProductInput.split(separatorOfBuyProduct))
                .peek(this::validateNameAndQuantity)
                .collect(Collectors.toMap(
                        nameAndQuantity -> ProductName.of(nameAndQuantity[0]),
                        nameAndQuantity -> Quantity.of(Integer.valueOf(nameAndQuantity[1])),
                        (existing, duplicate) -> {
                            throw new IllegalArgumentException("중복된 상품명이 있습니다: " + existing);
                        }
                ));

        return new PickProducts(productMap.entrySet().stream()
                .map(entry -> new PickProduct(entry.getKey(), entry.getValue()))
                .toList());
    }

    public YesOrNo askToReceiveAdditionalGift(PickProduct pick, Quantity amountOfReceiveGift) {
        System.out.println("현재 %s은(는) %s개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"
                .formatted(pick.name(), amountOfReceiveGift));
        return ConsoleInput.readYesOrNo();
    }

    public YesOrNo askToPromotionPurchaseAtRegularPrice(PickProduct pick, Quantity amountOfPurchased) {
        System.out.println("현재 %s %s개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"
                .formatted(pick.name(), amountOfPurchased));
        return ConsoleInput.readYesOrNo();
    }

    public YesOrNo askGetMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까?");
        return ConsoleInput.readYesOrNo();
    }

    public YesOrNo askBuyMore() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return ConsoleInput.readYesOrNo();
    }

    private static String stripBrackets(String buyProductInput) {
        if (!buyProductInput.startsWith("[") || !buyProductInput.endsWith("]")) {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
        }
        String stripped = buyProductInput.substring(1, buyProductInput.length() - 1);
        if (stripped.startsWith("-") || stripped.endsWith("-")) {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
        }
        return stripped;
    }

    private void validateNameAndQuantity(String[] nameAndQuantity) {
        if (nameAndQuantity.length != 2) {
            throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
        }
        for (String string : nameAndQuantity) {
            if (string.isBlank()) {
                throw new IllegalArgumentException("잘못된 입력입니다. 다시 입력해 주세요.");
            }
        }
    }

}
