import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Path {
    public static void main(String[] args) {
        int count = 0;
        System.out.println("Укажите путь к файлу: ");

        while (true) {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            if (!file.exists() || file.isDirectory()) {
                System.out.println("Файл не существует или путь ведет к папке, а не к файлу");
                continue;
            }
            System.out.println("Путь указан верно");
            count++;
            System.out.println("Это файл номер " + count);
            Statistics statistics = new Statistics();




            int googlebotCount = 0;
            int yandexbotCount = 0;

            int lineCount = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.length() > 1024) {
                        throw new LineTooLongException("Ошибка: строка длиной " + line.length() + " символов превышает максимально допустимую длину 1024 символа.");
                    }

                    lineCount++;


                    LogEntry logEntry = new LogEntry(line);
                    statistics.addEntry(logEntry);

                    // Вызываем метод getExistingPages

                    //statistics.getExistingPages(logEntry);
                    // Дополнительно можно вывести все существующие страницы
                    statistics.getExistingPages(logEntry);
                    statistics.getOsStatistics();

                    int startIndex = line.indexOf("(");
                    int endIndex = line.indexOf(")");

                    if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                        String firstBrackets = line.substring(startIndex + 1, endIndex);
                        String[] parts = firstBrackets.split(";");
                        if (parts.length >= 2) {
                            String fragment = parts[1].trim();
                            String program = fragment.split("/")[0].trim();


                            if (program.equals("Googlebot")) {
                                googlebotCount++;
                            } else if (program.equals("YandexBot")) {
                                yandexbotCount++;
                            }
                        }
                    }
                }


            } catch (LineTooLongException ex) {
                System.err.println(ex.getMessage());
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Общее количество строк в файле: " + lineCount);
            if (lineCount > 0) {

                double googlebotShare = (double) googlebotCount / lineCount * 100;
                double yandexbotShare = (double) yandexbotCount / lineCount * 100;
                System.out.printf("Доля запросов от Googlebot: %.2f%%\n", googlebotShare);
                System.out.printf("Доля запросов от YandexBot: %.2f%%\n", yandexbotShare);
            } else {
                System.out.println("Файл пуст.");
            }
        }
    }

    static class LineTooLongException extends RuntimeException {
        public LineTooLongException(String message) {
            super(message);
        }
    }
}



//C:\Users\1\Desktop\StepUp\access.log
//C:\Users\1\Desktop\StepUp\Курсовой проект.docx
//C:\Users\1\Desktop\StepUp\access — копия.log