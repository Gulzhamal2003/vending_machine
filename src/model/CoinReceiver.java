package model;

public class CoinReceiver implements PaymentReceiver {
    private int balance;

    public CoinReceiver() {
        this.balance = 100;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void addFunds(int amount) {
        this.balance += amount;
    }

    @Override
    public void deduct(int amount) {
        balance -= amount;
    }

    @Override
    public void displayBalance() {
        System.out.println("Монет на сумму: " + this.balance);
    }
}
