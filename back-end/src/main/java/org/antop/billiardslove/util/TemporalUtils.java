package org.antop.billiardslove.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TemporalUtils {
    private TemporalUtils() {
    }

    public static final String FORMAT_DATE = "yyyyMMdd";
    public static final String FORMAT_TIME = "HHmmss";
    public static final String FORMAT_DATE_TIME = FORMAT_DATE + FORMAT_TIME;

    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
