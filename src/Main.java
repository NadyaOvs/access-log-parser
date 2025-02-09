import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число");
        int number1 = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число");
        int number2 = new Scanner(System.in).nextInt();
        int sum = (number1 + number2);
        int diff = (number1 - number2);
        int mult = (number1 * number2);
        double div = (double) number1 / number2;
        System.out.println("Сумма: "+ sum);
        System.out.println("Разность: "+ diff);
        System.out.println("Произведение: "+ mult);
        System.out.println("Частное: "+ div);
    }
}
