package store.storeapp.controller;

import java.util.List;
import store.config.util.InteractionRepeatable;
import store.config.value.YesOrNo;
import store.storeapp.data.StoreDataReader;
import store.storeapp.model.ProductStock;
import store.storeapp.value.PickProduct;
import store.storeapp.value.PickProducts;
import store.storeapp.value.Quantity;
import store.storeapp.value.TotalReceipt;
import store.storeapp.view.StoreInput;
import store.storeapp.view.StoreOutput;

public class StoreController implements InteractionRepeatable {

    private final StoreInput storeInput;
    private final StoreOutput storeOutput;
    private final ProductStock productStock;

    public StoreController(StoreInput storeInput,
                           StoreOutput storeOutput,
                           StoreDataReader storeDataReader) {
        this.storeInput = storeInput;
        this.storeOutput = storeOutput;
        this.productStock = storeDataReader.readProducts();
    }

    public void doHandleStore() {
        do {
            welcomeGuest();
            TotalReceipt receipt = serviceSale();
            showReceipt(receipt);
        } while (getAnswerToContinue());
    }

    private void welcomeGuest() {
        storeOutput.sayGreeting();
        storeOutput.introduceProductStock(productStock);
    }

    private TotalReceipt serviceSale() {
        PickProducts pickProducts = receiveOrders();
        PickProducts confirmedProducts = confirmFinalPurchaseProducts(pickProducts);
        YesOrNo discountAnswer = storeInput.askGetMembershipDiscount();
        return sellProductsIn(confirmedProducts, discountAnswer);
    }

    private void showReceipt(TotalReceipt receipt) {
        storeOutput.giveReceipt(receipt);
    }

    private boolean getAnswerToContinue() {
        return storeInput.askBuyMore().judge();
    }

    private PickProducts receiveOrders() {
        return supplyWithRetry(() -> {
            PickProducts pickProducts = storeInput.askToBuy();
            pickProducts.forEach(productStock::isSalesAvailable);
            return pickProducts;
        });
    }

    private PickProducts confirmFinalPurchaseProducts(PickProducts pickProducts) {
        return supplyWithRetry(() -> {
            List<PickProduct> reflectedPossibleGifts = askReceiveExtraPromotionGift(pickProducts);
            List<PickProduct> reflectedPurchases = checkPromotionNonApplicable(pickProducts);

            return makeFinalPurchaseProducts(pickProducts,
                    reflectedPossibleGifts,
                    reflectedPurchases);
        });
    }

    private List<PickProduct> askReceiveExtraPromotionGift(PickProducts pickProducts) {
        return supplyWithRetry(() -> pickProducts.stream()
                .filter(productStock::isPossibleToReceiveGift)
                .map(pick -> {
                    Quantity amountOfReceiveGift = productStock.getAmountOfPossibleToReceiveGift(pick);
                    if (storeInput.askToReceiveAdditionalGift(pick, amountOfReceiveGift).judge()) {
                        return new PickProduct(pick.name(), pick.quantity().add(amountOfReceiveGift));
                    }
                    return pick;
                })
                .toList());
    }

    private List<PickProduct> checkPromotionNonApplicable(PickProducts pickProducts) {
        return supplyWithRetry(() -> pickProducts.stream()
                .filter(productStock::isAnyPromotionNotAvailable)
                .map(pick -> {
                    Quantity amountOfPurchased = productStock.getQuantityOfPromotionNotAvailable(pick);
                    if (YesOrNo.NO == storeInput.askToPromotionPurchaseAtRegularPrice(pick, amountOfPurchased)) {
                        return new PickProduct(pick.name(), pick.quantity().minus(amountOfPurchased));
                    }
                    return pick;
                })
                .toList());
    }

    private static PickProducts makeFinalPurchaseProducts(PickProducts pickProducts,
                                                          List<PickProduct> reflectedPossibleGifts,
                                                          List<PickProduct> reflectedPurchases) {
        return new PickProducts(pickProducts.stream()
                .map(pick -> reflectedPossibleGifts.stream()
                        .filter(reflect -> reflect.equals(pick))
                        .findFirst()
                        .orElseGet(() -> reflectedPurchases.stream()
                                .filter(reflect -> reflect.equals(pick))
                                .findFirst()
                                .orElse(pick)))
                .toList());
    }

    private TotalReceipt sellProductsIn(PickProducts finalPickProducts, YesOrNo answerForMembershipDiscount) {
        return new TotalReceipt(finalPickProducts.stream()
                .map(pick -> productStock.salesAndRecordReceiptOfSoldProduct(pick, answerForMembershipDiscount))
                .toList());
    }

}
//public class StoreController implements InteractionRepeatable {
//
//    private final StoreInput storeInput;
//    private final StoreOutput storeOutput;
//    private final StoreService storeService;
//
//    public StoreController(StoreInput storeInput, StoreOutput storeOutput, StoreDataReader storeDataReader) {
//        this.storeInput = storeInput;
//        this.storeOutput = storeOutput;
//        ProductStock productStock = storeDataReader.readProducts();
//        this.storeService = new StoreService(productStock);
//    }
//
//    public void doHandleStore() {
//        do {
//            welcomeGuest();
//            TotalReceipt receipt = processSale();
//            storeOutput.giveReceipt(receipt);
//        } while (storeInput.askBuyMore().judge());
//    }
//
//    private void welcomeGuest() {
//        storeOutput.sayGreeting();
//        storeOutput.introduceProductStock(storeService.getProductStock());
//    }
//
//    private TotalReceipt processSale() {
//        PickProducts pickProducts = receiveOrders();
//        PickProducts confirmedProducts = confirmFinalPurchaseProducts(pickProducts);
//        YesOrNo discountAnswer = storeInput.askGetMembershipDiscount();
//        return storeService.sellProducts(confirmedProducts, discountAnswer);
//    }
//
//    private PickProducts receiveOrders() {
//        return supplyWithRetry(() -> {
//            PickProducts pickProducts = storeInput.askToBuy();
//            storeService.checkStockAvailability(pickProducts);
//            return pickProducts;
//        });
//    }
//
//    private PickProducts confirmFinalPurchaseProducts(PickProducts pickProducts) {
//        return supplyWithRetry(() -> {
//            List<PickProduct> additionalGifts = storeService.applyGifts(pickProducts, storeInput);
//            List<PickProduct> regularPurchased = storeService.checkNonApplicablePromotions(pickProducts,
//                    storeInput);
//            return storeService.finalizePurchase(pickProducts, additionalGifts, regularPurchased);
//        });
//    }
//
//}
