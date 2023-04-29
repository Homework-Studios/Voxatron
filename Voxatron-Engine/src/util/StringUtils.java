package util;

public class StringUtils {
    public static String getStringBetween(String string, String start, String end) {
        int startIndex = string.indexOf(start) + start.length();
        int endIndex = string.indexOf(end, startIndex);
        return string.substring(startIndex, endIndex);
    }
}
