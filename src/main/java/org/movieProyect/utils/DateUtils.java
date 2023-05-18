package org.movieProyect.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase necesaria para operar correctamente con fechas y utilizar un mismo formato.
 */

public class DateUtils {
    public static Date stringToDate(String stringDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
