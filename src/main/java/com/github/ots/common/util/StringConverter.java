package com.github.ots.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StringConverter {

    public static String toBoolean(String input) {
        return Boolean.toString(Boolean.parseBoolean(input));
    }

    public static Integer toInteger(String input) {
        return Integer.parseInt(input);
    }

    public static Double toDouble(String input) {
        return Double.parseDouble(input);
    }

    public static Date toDate(String input) throws ParseException {
        return DateFormat.getDateInstance().parse(input);
    }

    public static List<String> toList(String input, String delimiter) {
        return Arrays.asList(input.split(delimiter));
    }

    public static <T> T convert(String input, Class<T> type) throws ParseException {
        return convert(input, type, "-");
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(String input, Class<T> type, String delimiter) {
        if (type == String.class) {
            return (T) input;
        } else if (type == Boolean.class || type == boolean.class) {
            return (T) toBoolean(input);
        } else if (type == Integer.class || type == int.class) {
            return (T) toInteger(input);
        } else if (type == Double.class || type == double.class) {
            return (T) toDouble(input);
        } else if (type == Date.class) {
            try {
                return (T) toDate(input);
            } catch (Exception e) {
                throw new RuntimeException("Cannot parse date from input: " + input);
            }
        } else if (type == List.class) {
            if(delimiter==null){
                delimiter="-";
            }
            return (T) toList(input, delimiter);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type.getName());
        }
    }
}
