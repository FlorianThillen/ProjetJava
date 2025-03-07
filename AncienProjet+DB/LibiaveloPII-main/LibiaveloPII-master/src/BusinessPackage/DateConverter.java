package BusinessPackage;

import java.time.LocalDate;
import java.time.ZoneId;

public class DateConverter {

    // Convert java.util.Date to LocalDate
    public static LocalDate utilDateToLocalDate(java.util.Date utilDate) {
        return utilDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // Convert LocalDate to java.util.Date
    public static java.util.Date localDateToUtilDate(LocalDate localDate) {
        return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Convert java.sql.Date to LocalDate
    public static LocalDate sqlDateToLocalDate(java.sql.Date sqlDate) {
        return sqlDate.toLocalDate();
    }

    // Convert LocalDate to java.sql.Date
    public static java.sql.Date localDateToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    // Convert java.util.Date to java.sql.Date
    public static java.sql.Date utilDateToSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    // Convert java.sql.Date to java.util.Date
    public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }
}
