package com.techelevator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

  private static final String LOG_FILE_PATH = "Log.txt";

  public static void logTransaction(String transaction) {
    String timestamp = getCurrentTimestamp();
    String logEntry = timestamp + " " + transaction;

    try (
      BufferedWriter writer = new BufferedWriter(
        new FileWriter(LOG_FILE_PATH, true)
      )
    ) {
      writer.write(logEntry);
      writer.newLine();
    } catch (IOException e) {
      System.err.println("Error writing to log file: " + e.getMessage());
    }
  }

  private static String getCurrentTimestamp() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "MM/dd/yyyy hh:mm:ss a"
    );
    return now.format(formatter);
  }
}
