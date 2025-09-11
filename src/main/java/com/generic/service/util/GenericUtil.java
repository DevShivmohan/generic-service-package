package com.generic.service.util;

import com.fasterxml.uuid.Generators;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

public class GenericUtil {
    public static LocalDateTime getLocalDateTimeInUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDateTime getLocalDateTimeInIST() {
        return LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }

    public static UUID generateTimeBaseUUID() {
        return Generators.timeBasedReorderedGenerator().generate();
    }
}
