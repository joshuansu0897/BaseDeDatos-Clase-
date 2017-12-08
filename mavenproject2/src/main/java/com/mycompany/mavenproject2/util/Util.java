/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author joshuansu
 */
public class Util {

    public static LocalDate getLocalDateFromDate(Date d) {
        if (d == null) {
            return null;
        }
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date getDateFromLocalDate(LocalDate l) {
        if (l == null) {
            return null;
        }
        return Date.from(l.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    //Para mas ejemplos o mas formatos 
    //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
    public static String LocalDateToString(LocalDate l) {
        if (l == null) {
            return null;
        }
        //year-month-day
        return l.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String LocalDateToStringSpanish(LocalDate l) {
        if (l == null) {
            return null;
        }
        Locale locale = new Locale("es", "ES");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM uuuu", locale);
        return l.format(formatter);
    }

    public static String CapitalizeFirstLetter(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean isInt(String str) {
        return str.matches("^[0-9]*$");
    }

    public static boolean isNumber(String str) {
        return str.matches("^[0-9,.]*$");
    }
}
