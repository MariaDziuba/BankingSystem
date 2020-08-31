package src;

public class Data {
    private String currentNumber;
    private String currentPin;
    private int currentBalance;

    public String getCurrentCard() {
        return currentNumber;
    }

    public void setCurrentCard(String currentCard, String pin) {
        this.currentNumber = currentCard;
        this.currentPin = pin;
        this.currentBalance = Sql.findCurrentBalance(currentCard);
    }

    public int getBalance() {
        return this.currentBalance;
    }

    public void setCurrentBalance(int balance) {
        this.currentBalance = balance;
    }

    public Data(String fileName) {
        Sql.createNewDatabase(fileName);
        Sql.createNewTable();
    }


    public void add(String lValue, String intValue, int balanceValue) {
        Sql.insert(lValue, intValue, balanceValue);
        this.currentNumber = lValue;
        this.currentPin = intValue;
        this.currentBalance = balanceValue;
    }

    public boolean containsCardAndPin(String number, String pin) {
        return Sql.contains(number, pin);
    }
}
