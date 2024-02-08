package com.techelevator;

import java.io.*;
import java.util.*;

public class FileHandler {

  private List<Team> teams = new ArrayList<>();
  private File[] files;
  private List<Player> players = new ArrayList<>();
  private String teamName = "";

  public void show() {
    teams.forEach(team -> System.out.println(team.getPlayers().size()));
  }

  private Map<String, String> allStats = new HashMap<>(
    Map.of(
      "Goalie",
      "ShotAgainst_Saves",
      "Defender",
      "Hits_BlockedShots",
      "Forward",
      "Goals|Assists"
    )
  );

  public void run() {
    File folder = new File("TeamData"); // folder path
    files = folder.listFiles();

    int fileIndex = 1;

    for (File file : files) {
      String fileName = file.getName();
      String[] fileNameSplit = fileName.split("_");

      teamName = fileNameSplit[0] + " " + fileNameSplit[1];

      try (Scanner fileScanner = new Scanner(file)) {
        while (fileScanner.hasNextLine()) {
          String line = fileScanner.nextLine();
          String[] data = line.split("|"); // [10, name, position]

          try {
            int jerseyNumber = Integer.parseInt(data[0]); // ! Stuck on first / second or third line with the first line
            String firstName = data[1];
            String lastName = data[2];
            String position = data[3];
            double capSpace = Double.parseDouble(data[4]);
            Map<String, Integer> stats = getStats(data, position);

            players.add(
              new Player(
                firstName,
                lastName,
                capSpace,
                jerseyNumber,
                position,
                stats
              )
            );
          } catch (NumberFormatException e) {}
        }
      } catch (FileNotFoundException e) {}

      if (fileIndex % 3 == 0 && fileIndex != 0) {
        teams.add(new Team(teamName, players));
        players.clear();
      }

      fileIndex++;
    }

    show();
  }

  public Map<String, Integer> getStats(String[] data, String position) {
    String statName1 = "";
    String statName2 = "";

    for (Map.Entry<String, String> entry : allStats.entrySet()) { // { goalie: stat1_stat2, forward: stat1_stat2, defender: stat1_stat2 }
      String key = entry.getKey(); // getting key like goalie, forward, defender
      if (key.equals(position)) { // if position matches key goalie = goalie but goalie != forward
        String[] bothStats = entry.getValue().split("_"); // we get the goalie: stat1_stat2 and split it
        statName1 = bothStats[0]; // stat1
        statName2 = bothStats[1]; // stat2
      }
    }

    return new HashMap<String, Integer>(
      Map.of(
        statName1,
        Integer.parseInt(data[5]),
        statName2,
        Integer.parseInt(data[6])
      )
    );
  }
}
