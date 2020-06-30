package com.epam.izh.rd.online.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SimpleDateService implements DateService {

    /**
     * Метод парсит дату в строку
     *
     * @param localDate дата
     * @return строка с форматом день-месяц-год(01-01-1970)
     */
    @Override
    public String parseDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * Метод парсит строку в дату
     *
     * @param string строка в формате год-месяц-день часы:минуты (1970-01-01 00:00)
     * @return дата и время
     */
    @Override
    public LocalDateTime parseString(String string) {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * Метод конвертирует дату в строку с заданным форматом
     *
     * @param localDate исходная дата
     * @param formatter формат даты
     * @return полученная строка
     */
    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        return localDate.format(formatter);
    }

    /**
     * Метод получает следующий високосный год
     *
     * @return високосный год
     */
    @Override
    public long getNextLeapYear() {
        long y = LocalDate.now().getYear();
        while (true) {
            if (y % 400 == 0 || y % 4 == 0 && y % 100 != 0) {
                return y;
            }
            ++y;
        }
    }

    /**
     * Метод считает число секунд в заданном году
     *
     * @return число секунд
     */
    @Override
    public long getSecondsInYear(int year) {
        if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0 ) {
            return 366 * 24 * 60 * 60;
        }
        return 365 * 24 * 60 * 60;
//        return ChronoUnit.SECONDS.between(LocalDateTime.of(year, 01, 01, 00, 00),
//                LocalDateTime.of(year + 1, 01, 01, 00, 00));
    }


}
