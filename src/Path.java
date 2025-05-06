import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Path {
    public static void main(String[] args) throws IOException {
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

            int lineCount = 0;
            int maxLength = 0;
            int minLength = Integer.MAX_VALUE; // Инициализируем максимальным значением

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();

                    if (length > 1024) {
                        throw new LineTooLongException("Ошибка: строка длиной " + length + " символов превышает максимально допустимую длину 1024 символа.");
                    }

                    lineCount++;

                    if (length > maxLength) {
                        maxLength = length;
                    }
                    if (length < minLength) {
                        minLength = length;
                    }
                }

                reader.close();
            } catch (LineTooLongException ex) {
                System.err.println(ex.getMessage());
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Общее количество строк в файле: " + lineCount);
            if (lineCount > 0) {
                System.out.println("Длина самой длинной строки: " + maxLength);
                System.out.println("Длина самой короткой строки: " + minLength);
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