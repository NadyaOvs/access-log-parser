
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.ENGLISH;

public class LogEntry {
    private final String ipAddress;          // IP-адрес
    private final OffsetDateTime timestamp;   // Дата и время запроса
    private final HttpMethod httpMethod;     // Метод запроса
    private final String requestPath;         // Путь запроса
    private final int responseCode;           // Код ответа
    private final long responseSize;          // Размер отданных сервером данных
    private final String referer;             // Referer
    private final UserAgent userAgent;           // User-Agent

    // Конструктор, принимающий строку
    public LogEntry(String logLine) {
        String[] parts = logLine.split(" ");
        if (parts.length < 9) {
            throw new IllegalArgumentException("Неверный формат строки лога");
        }
        //Извлечение IP-адреса
        this.ipAddress = logLine.substring(0, logLine.indexOf(" "));
        // или можно опписать через parts = = parts[0];
        // Извлечение даты и времени
        String dateTimeString = logLine.substring(logLine.indexOf("[") + 1, logLine.indexOf("]"));
        try {
            this.timestamp = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты и времени: " + dateTimeString);
        }

        // Извлечение метода и пути
        this.httpMethod = HttpMethod.valueOf(parts[5].replaceAll("\"", "")); // Метод запроса
        this.requestPath = parts[6]; // Путь запроса

        // Извлечение кода ответа и размера данных
        this.responseCode = Integer.parseInt(parts[8]);
        this.responseSize = Long.parseLong(parts[9]);

//        // Извлечение referer и User-Agent
        String substring = logLine.substring(logLine.indexOf("\"") + 1, logLine.lastIndexOf("\""));
        String[] split = substring.split("\"");
        String refer = split[2].replaceAll("\"", "");
        this.referer = "-".equals(refer) ? null : refer;
        String userAgentInfo = split[4].replaceAll("\"", "");
        this.userAgent = "-".equals(userAgentInfo) ? null : new UserAgent(userAgentInfo);
    }

    // Геттеры
    public String getIpAddress() {
        return ipAddress;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddress='" + ipAddress + '\'' +
                ", timestamp=" + timestamp +
                ", httpMethod=" + httpMethod +
                ", requestPath='" + requestPath + '\'' +
                ", responseCode=" + responseCode +
                ", responseSize=" + responseSize +
                ", referer='" + referer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}

