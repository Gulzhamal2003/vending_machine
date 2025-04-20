import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private PaymentReceiver paymentReceiver;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        choosePaymentMethod();
    }

    private void choosePaymentMethod() {
        print("Выберите способ оплаты:");
        print("1 - Монеты");
        print("2 - Карта");

        String choice = fromConsole();
        while (choice.trim().isEmpty()) {
            choice = fromConsole();
        }
        if ("1".equals(choice)) {
            paymentReceiver = new CoinReceiver();
            print("Вы выбрали оплатить монетами");
        } else if ("2".equals(choice)) {
            paymentReceiver = new CardReceiver();
            CardReceiver cardReceiver = (CardReceiver) paymentReceiver;  // Приводим к типу CardReceiver
            cardReceiver.authenticateCard();
            print("Вы выбрали оплатить картой");

            print("Введите сумму пополнения карты:");
            String amountInput = fromConsole().trim();

            try {
                int amount = Integer.parseInt(amountInput);
                if (amount <= 0) {
                    print("Сумма пополнения должна быть больше нуля");
                } else {
                    cardReceiver.addFunds(amount);  // Предполагается, что в CardReceiver есть метод для пополнения баланса
                    print("Баланс пополнен на " + amount);
                }
            } catch (NumberFormatException e) {
                print("Некорректная сумма. Попробуйте снова.");
            }
        } else {
            print("Неверный выбор. Попробуйте снова.");
            choosePaymentMethod();
        }
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        paymentReceiver.displayBalance();

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentReceiver.getBalance() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            paymentReceiver.addFunds(10);
            print("Вы пополнили баланс на 10");
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                Product selectedProduct = products.get(i);
                if (selectedProduct.getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {

                    if (paymentReceiver.getBalance() >= selectedProduct.getPrice()) {
                        paymentReceiver.deduct(selectedProduct.getPrice());  // списываем деньги
                        print("Вы купили " + selectedProduct.getName());
                    } else {
                        print("Недостаточно средств для покупки " + selectedProduct.getName());
                    }
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попрбуйте еще раз.");
                chooseAction(products);
            }
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
