import java.io.File;
import java.util.Scanner;

public class Path {
    public static void main(String[] args) {
        int count = 0;
        boolean fileExists;
        boolean isDirectory;
        System.out.println("Укажите путь в файлу: ");
        while (true) {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            fileExists = file.exists();
            isDirectory = file.isDirectory();
            if (!fileExists || isDirectory) {
                System.out.println("Файл не существует или путь ведет к папке, а не к файлу");
                continue;
            }
            if (fileExists) {
                System.out.println("Путь указан верно");
                count++;
                System.out.println("Это файл номер " + count);
            }
        }
    }
}
