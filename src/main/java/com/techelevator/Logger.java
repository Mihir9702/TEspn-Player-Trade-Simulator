package com.techelevator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Logger {

  private static final String LOG_PATH = "log.txt";

  public void log(String message) {
    //  > 01/01/2019 12:00:00 PM
    SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a");
    String date = dateFormat.format(new Date());

    writeLog(date + " " + message);
  }

  public void writeLog(String message) {
    try (PrintWriter writer = new PrintWriter(LOG_PATH)) {
      writer.println(message);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void logTrade(
    String team1Name,
    String team2Name,
    List<Player> team1Players,
    List<Player> team2Players,
    boolean isSuccessful
  ) {
    //  > 01/01/2019 12:00:00 PM (Team Name) [Player Name, Player Name] <-> (Team Name) [Player Name] (Confirmed / Denied)

    String status = isSuccessful ? "Confirmed" : "Denied";
    String team1Message = team1Name + " " + team1Players;
    String team2Message = team2Name + " " + team2Players;
    String message = team1Message + " <-> " + team2Message + " " + status;

    log(message);
  }

  public void logWaiver(String teamName, String playerName) {
    String message = teamName + " " + playerName + " -- " + "Waived";
    log(message);
  }
}
