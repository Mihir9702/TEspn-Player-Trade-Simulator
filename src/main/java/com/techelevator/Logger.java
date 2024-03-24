package com.techelevator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Logger {

  private String LOG_PATH;

  // ! bug -> its overwriting the file not appending

  public Logger(String path) {
    this.LOG_PATH = path;
  }

  /**
   * The log function formats the current date and time and appends it to the given message before
   * writing it to a log file.
   *
   * @param message The "message" parameter is a string that represents the log message that you want to
   * write to the log file.
   */
  public void log(String message) {
    //  > 01/01/2019 12:00:00 PM
    SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a");
    String date = dateFormat.format(new Date());

    writeLog(date + " " + message);
  }

  /**
   * The writeLog function writes a message to a log file.
   *
   * @param message The message parameter is a string that represents the log message that you want to
   * write to the log file.
   */
  public void writeLog(String message) {
    try (
      FileOutputStream fileOutputStream = new FileOutputStream(LOG_PATH, true);
      PrintWriter writer = new PrintWriter(fileOutputStream)
    ) {
      writer.println(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The logTrade function logs a trade between two teams, including the team names, player names, and
   * whether the trade was successful or not.
   *
   * @param team1Name The name of the first team involved in the trade.
   * @param team2Name The name of the second team involved in the trade.
   * @param team1Players A list of players from team 1 involved in the trade.
   * @param team2Players A list of players from team 2 involved in the trade.
   * @param isSuccessful A boolean value indicating whether the trade was successful or not.
   */
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

  /**
   * The logWaiver function logs a message indicating that a player has been waived from a team.
   *
   * @param teamName The name of the team that the player is being waived from.
   * @param playerName The playerName parameter is a String that represents the name of the player who is
   * being waived.
   */
  public void logWaiver(String teamName, String playerName) {
    String message = teamName + " " + playerName + " -- " + "Waived";
    log(message);
  }
}
