package br.com.dio.persistence.converter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;


public final class OffsetDateTimeConverter {

    public OffsetDateTimeConverter() {
    }

    public static OffsetDateTime toOffsetDateTime(final Timestamp value){
        return nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), UTC) : null;
    }
}
