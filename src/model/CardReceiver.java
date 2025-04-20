package model;

import java.util.Scanner;

public class CardReceiver implements PaymentReceiver {
    private int balance;

    public CardReceiver() {
        this.balance = 100;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void addFunds(int amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Недостаточно средств на карте");
        }
    }


    @Override
    public void displayBalance() {
        System.out.println("Баланс карты: " + balance);
    }

    public void authenticateCard() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите номер карты: ");
        String cardNumber = sc.nextLine();

        System.out.print("Введите одноразовый пароль: ");
        String otp = sc.nextLine();

        System.out.println("Номер карты: " + cardNumber + ", одноразовый пароль: " + otp);
    }

    @Override
    public void deduct(int amount) {
        balance -= amount;
    }
}
