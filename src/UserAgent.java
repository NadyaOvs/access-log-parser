import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class UserAgent {
        private final String operatingSystem; // Тип операционной системы
        private final String browser;    // Браузер


        public UserAgent(String userAgentString) {
            this.operatingSystem = extractOperatingSystem(userAgentString);
            this.browser = extractBrowser(userAgentString);
        }

        // Метод для получения операционной системы
        private String extractOperatingSystem(String userAgentString) {
            if (userAgentString.contains("Windows")) {
                return "Windows";
            } else if (userAgentString.contains("Mac OS") || userAgentString.contains("Macintosh")) {
                return "macOS";
            } else if (userAgentString.contains("Linux")) {
                return "Linux";
            } else {
                return "Unknown OS";
            }
        }

        // Метод для извлечения браузера
        private String extractBrowser(String userAgentString) {
            if (userAgentString.contains("Edge")) {
                return "Edge";
            } else if (userAgentString.contains("Firefox")) {
                return "Firefox";
            } else if (userAgentString.contains("Chrome")) {
                return "Chrome";
            } else if (userAgentString.contains("Opera") || userAgentString.contains("OPR")) {
                return "Opera";
            } else {
                return "Other";
            }
        }


        public String getOperatingSystem() {
            return operatingSystem;
        }

        public String getBrowser() {
            return browser;
        }

        @Override
        public String toString() {
            return "User Agent{" +
                    "operatingSystem='" + operatingSystem + '\'' +
                    ", browser='" + browser + '\'' +
                    '}';
        }
    }
