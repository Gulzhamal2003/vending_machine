package model;

public interface PaymentReceiver {
    int getBalance();
    void addFunds(int amount);
    void displayBalance();
    void deduct(int amount);
}
