package src;

import java.util.Random;

public class CardGenerator {

    Random random = new Random();

    String generate() {
        StringBuilder s = new StringBuilder("400000");
        for (int i = 0; i < 9; i++) {
            s.append(random.nextInt(10));
        }
        s.append(getLastDigit(s.toString()));
        return s.toString();
    }

    int getLastDigit(String number) {
            if (number.length() != 15)
                System.exit(1);
            int[] mas = new int[15];
            int sum = 0;
            for (int i = 0; i <= 14; i++) {
                mas[i] = (i % 2 == 0) ? 2 * (number.charAt(i) - '0') : (number.charAt(i) - '0');
                mas[i] = mas[i] > 9 ? mas[i] - 9 : mas[i];
                sum += mas[i];
            }
            return  (sum % 10 == 0) ? 0 : 10 - (sum % 10);
        }
}
