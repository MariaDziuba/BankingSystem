package src;

import java.util.*;

public class BankingSystem {

    Data data;

    BankingSystem(Data data) {
        this.data = data;
    }

    private Map<Long, Integer> accounts = new HashMap<>(); // card number + pin
    private Scanner sc = new Scanner(System.in);
    private CardGenerator gen = new CardGenerator();
    public void start() {
        while (true) {
            outerMenu();
            innerMenu();
        }
    }

    private void createAccount() {
        Random random = new Random();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        long num = Long.parseLong(gen.generate());
        System.out.println(num);
        accounts.put(num, random.nextInt(10000));
        System.out.println("Your card PIN:");
        System.out.println(accounts.get(num));
        data.add(Long.toString(num), Integer.toString(accounts.get(num)), 0);
    }

    private void logIn() throws BankingException {
        System.out.println("Enter your card number:");
        long num = sc.nextLong();
        if (accounts.get(num) == null) {
            throw new BankingException("Wrong card number!");
        }
        System.out.println("Enter your PIN:");
        int pin = sc.nextInt();
        if (accounts.get(num) != pin) {
            throw new BankingException("Wrong PIN");
        }
        data.setCurrentCard(Long.toString(num), Integer.toString(pin));
        System.out.println("You have successfully logged in!");
    }

    private void outerMenu() {
        loop:
        while (true) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            String input = sc.next();
            switch (input) {
                case "1": {
                    createAccount();
                    break;
                }
                case "2": {
                    try {
                        logIn();
                    } catch (BankingException e) {
                        System.out.println(e.getMessage());
                        continue loop;
                    }
                    break loop;
                }
                case "0": {
                    System.exit(0);
                }
            }
        }
    }

    private void innerMenu() {
        loop:
        while (true) {
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
            String input = sc.next();
            switch (input) {
                case "1" : {
                    System.out.println("Balance: " + data.getBalance());
                    System.out.println();
                    break;
                }
                case "2": {
                    System.out.println("Enter income:");
                    int sum = Integer.parseInt(sc.next());
                    sum += Sql.findCurrentBalance(data.getCurrentCard());
                    Sql.makeBalanceChange(data.getCurrentCard(), sum);
                    data.setCurrentBalance(sum);
                    System.out.println("Income was added!");
                    break;
                }
                case "3": {
                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    String num = sc.next();
                    String check = Integer.toString(gen.getLastDigit(num.substring(0, num.length() - 2)));
                    if (!num.substring(num.length() - 2, num.length() - 1).equals(check)) {
                        System.out.println("Probably you made mistake in the card number. Please try again");
                    }
                    if (data.getCurrentCard().equals(num)) {
                        System.out.println("You can't transfer money to the same account!");
                    }
                    if (!Sql.numberExist(num)) {
                        System.out.println("Such a card does not exist.");
                    }
                    System.out.println("How much money do you want to transfer?");
                    int money = sc.nextInt();
                    int balance = data.getBalance();
                    data.setCurrentBalance(balance - money);
                    Sql.makeBalanceChange(data.getCurrentCard(), balance - money);
                    Sql.makeBalanceChange(num, Sql.findCurrentBalance(num) + money);
                    break;
                }
                case "4" : {
                    Sql.deleteRow(data.getCurrentCard());
                    System.out.println("Your account was successfully deleted!");
                    break loop;
                }
                case "5" : {
                    System.out.println("You have successfully logged out!");
                    break loop;
                }
            }
        }
    }
}
