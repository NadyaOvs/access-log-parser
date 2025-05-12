
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LogEntry {
    private final String ipAddress;
    private final LocalDateTime timestamp;
    private final HttpMethod httpMethod;
    private final String requestPath;
    private final int responseCode;
    private final long responseSize;
    private final String referer;
    private final String userAgent;

    // Конструктор, принимающий строку
    public LogEntry(String logLine) {
        String[] parts = logLine.split(" ");

        if (parts.length < 9) {
            throw new IllegalArgumentException("Неверный формат строки лога");
        }

        this.ipAddress = parts[0];

        String dateTimeString = parts[1].replaceAll("[\\[\\]]", ""); // Меняем квадратные скобки на ничего
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");
        try {
            this.timestamp = LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты и времени: " + dateTimeString);
        }


        String request = parts[2].replaceAll("\"", ""); // Меняем кавычки на ничего
        String[] requestParts = request.split(" ");
        this.httpMethod = HttpMethod.valueOf(requestParts[0]);
        this.requestPath = requestParts[1];


        this.responseCode = Integer.parseInt(parts[3]);
        this.responseSize = Long.parseLong(parts[4]);


        this.referer = parts[5].replaceAll("\"", "");
        this.userAgent = parts[6].replaceAll("\"", "");
    }

    // Геттеры
    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getTimestamp() {
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

    public String getUserAgent() {
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


