import java.time.LocalDateTime;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;


    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }


    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();


        LocalDateTime entryTime = logEntry.getTimestamp();
        if (minTime == null || entryTime.isBefore(minTime)) {
            minTime = entryTime;
        }
        if (maxTime == null || entryTime.isAfter(maxTime)) {
            maxTime = entryTime;
        }
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