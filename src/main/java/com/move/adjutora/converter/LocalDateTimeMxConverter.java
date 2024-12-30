package com.move.adjutora.converter;

import picocli.CommandLine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LocalDateTimeMxConverter implements CommandLine.ITypeConverter<LocalDateTime> {
    private final DateTimeFormatter formatterLong = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final DateTimeFormatter formatterShort = DateTimeFormatter.ofPattern("ddMMyyyy");
    private final String formatterLongRegex = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}";
    private final String formatterShortRegex = "[0-9]{2}[0-9]{2}[0-9]{4}";

    @Override
    public LocalDateTime convert(String s) throws Exception {
        Objects.requireNonNull(s, "The given date String is null");
        if (s.matches(formatterLongRegex)) {
            return LocalDateTime.parse(s, formatterLong);
        } else if (s.matches(formatterShortRegex)) {
            return LocalDate.parse(s, formatterShort).atStartOfDay();
        }
        throw new IllegalArgumentException("The given date String is not a valid for regex: " + formatterLongRegex + " or " + formatterShortRegex);
    }
}
