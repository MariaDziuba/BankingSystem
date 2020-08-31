package src;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Data data = new Data(args[1]);
        BankingSystem system = new BankingSystem(data);
        system.start();
    }
}
