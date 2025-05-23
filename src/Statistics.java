import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    private final HashSet<String> existingPages = new HashSet<>();
    private final HashMap<String, Integer> osFrequency = new HashMap<>();
    private int totalOsCount = 0;


    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }


    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();


        LocalDateTime entryTime = LocalDateTime.from(logEntry.getTimestamp());
        if (minTime == null || entryTime.isBefore(minTime)) {
            minTime = entryTime;
        }
        if (maxTime == null || entryTime.isAfter(maxTime)) {
            maxTime = entryTime;
        }

        // Подсчитываем частоту операционных систем
        UserAgent userAgent = logEntry.getUserAgent();
        if (userAgent != null) {
            String os = userAgent.getOperatingSystem();
            osFrequency.put(os, osFrequency.getOrDefault(os, 0) + 1);
            totalOsCount++;
        }
    }
    //метод по добавлению существующих сайтов в множество
    public HashSet<String> getExistingPages(LogEntry logEntry) {
        // Добавляем адреса страниц с кодом ответа 200
        if (logEntry.getResponseCode() == 200) {
            existingPages.add(logEntry.getReferer());
        }
//            System.out.println(page);
//        }
        return existingPages;
    }

    public HashMap<String, Double> getOsStatistics() {
        HashMap<String, Double> osStatistics = new HashMap<>();
        for (Map.Entry<String, Integer> entry : osFrequency.entrySet()) {
            String os = entry.getKey();
            int count = entry.getValue();
            double proportion = (double) count / totalOsCount;
            osStatistics.put(os, proportion);
        }
        return osStatistics;
    }


    public double getTrafficRate() {
        if (minTime == null || maxTime == null || minTime.isEqual(maxTime)) {
            return 0; // нет данных или время одинаковое
        }


        long hoursDifference = java.time.Duration.between(minTime, maxTime).toHours();
        if (hoursDifference == 0) {
            return totalTraffic; // Если разница 0, возвращается общий трафик
        }


        return (double) totalTraffic / hoursDifference;
    }


    public int getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }
}