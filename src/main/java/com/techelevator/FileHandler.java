package com.techelevator;

import java.io.*;
import java.util.*;

public class FileHandler {

  private List<Team> teams;
  private File[] files = new File[12];
  private List<Player> players = new ArrayList<>();
  private String teamName = "";

  public void show() {
    System.out.println(teams);
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
    files = folder.listFiles(); // returns an array of files

    int fileIndex = 1;

    for (File file : folder.listFiles()) { // looping through all files in the folder
      handleFile(file, fileIndex);
    }
  }

  public void handleFile(File file, int fileIndex) {
    // whatever we wanna do for each file
    // for each file we want to manipulate the data
    // 10|name|position|etc

    String fileName = file.getName();
    String[] fileNameSplit = fileName.split("_");

    teamName = fileNameSplit[0] + " " + fileNameSplit[1];

    int i = 0;
    try (Scanner fileScanner = new Scanner(file)) {
      while (fileScanner.hasNextLine()) {
        if (i == 0) {
          i++;
        } else {
          String line = fileScanner.nextLine();
          handleLine(line);
        }
      }
      i = 0;
    } catch (FileNotFoundException e) {}

    if (fileNameSplit[2].equals("Goalies")) {
      teams.add(new Team(teamName, players));
    }

    fileIndex++;
  }

  public void handleLine(String line) {
    String[] data = line.split("\\|"); // [10, name, position]

    try {
      int jerseyNumber = Integer.parseInt(data[0]);
      String firstName = data[1];
      String lastName = data[2];
      String position = data[3];
      double capSpace = Double.parseDouble(data[4]);
      Map<String, Integer> stats = getStats(data, position);

      players.add(
        new Player(firstName, lastName, capSpace, jerseyNumber, position, stats)
      );
    } catch (NumberFormatException e) {
      // System.out.println("num format exception");
    }
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
