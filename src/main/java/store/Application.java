package store;

import camp.nextstep.edu.missionutils.Console;
import store.storeapp.controller.StoreController;
import store.storeapp.data.StoreDataReader;
import store.storeapp.view.StoreInput;
import store.storeapp.view.StoreOutput;

public class Application {

    public static void main(String[] args) {
        try {
            StoreController storeController = new StoreController(new StoreInput(),
                    new StoreOutput(),
                    new StoreDataReader());
            storeController.doHandleStore();
        } catch (Error e) {
            e.printStackTrace();
            throw new IllegalArgumentException("예상치 못한 오류로 프로그램을 더 이상 실행할 수 없습니다.");
        } finally {
            Console.close();
        }
    }

}
