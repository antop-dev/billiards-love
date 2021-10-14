package org.antop.billiardslove.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TemporalUtils {
    private TemporalUtils() {
    }

    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_DATE_TIME = FORMAT_DATE + FORMAT_TIME;

    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(FORMAT_DATE);
    public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern(FORMAT_TIME);

    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 일자를 문자열로 변경 (포멧: {@link TemporalUtils#FORMAT_DATE})
     *
     * @param date {@link LocalDate}
     * @return {@link String}
     */
    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.format(FORMATTER_DATE);
    }

    /**
     * 시간을 문자열로 변경 (포멧: {@link TemporalUtils#FORMAT_TIME})
     *
     * @param time {@link LocalDate}
     * @return {@link String}
     */
    public static String format(LocalTime time) {
        if (time == null) return "";
        return time.format(FORMATTER_TIME);
    }

}
