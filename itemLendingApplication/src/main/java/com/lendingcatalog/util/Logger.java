package com.lendingcatalog.util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void log(String message, String logFileName) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd , HH:mm:ss");
        String logMessage = LocalDateTime.now().format(dateFormatter) + "  -  " +  message  + System.lineSeparator();
        try {
            Files.write(Paths.get("src/main/resources/logs/" + logFileName), logMessage.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}

