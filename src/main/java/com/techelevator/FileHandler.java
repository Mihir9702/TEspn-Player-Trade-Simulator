package com.techelevator;

import java.io.*;
import java.util.*;

public class FileHandler {

  private List<Team> teams = new ArrayList<>();
  private File[] files;
  private List<Player> players = new ArrayList<>();
  private String teamName = "";
  private List<List<String>> lines = new ArrayList<List<String>>();

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

          if (line.contains("POS")) {
            continue;
          } else {
<<<<<<< HEAD
            String[] data = line.split("\\|"); // [10, name, position]
            // System.out.println(line);
            int firstPipe = line.indexOf("|");
            String ns = line.substring(0, firstPipe); // 35
            // **********
            // **********
            // **********
            // **********
            for (List<String> oneLine : lines) {
              System.out.println(oneLine);
            }
=======
            String[] data = line.split("\\|");
>>>>>>> d7f657474e1bd9781ed87f1b4d575af6884284c7

            try {
              int jerseyNumber = Integer.parseInt(data[0]);
              String firstName = data[1];
              String lastName = data[2];
              String position = data[3];
              double capSpace = Double.parseDouble(data[4]);
              Map<String, Integer> stats = new HashMap<>();

              String statName1 = "";
              String statName2 = "";
              int statVal1 = Integer.parseInt(data[5]);
              int statVal2 = Integer.parseInt(data[6]);

              for (Map.Entry<String, String> entry : allStats.entrySet()) {
                if (position.equals(entry.getKey())) {
                  if (entry.getValue().contains("|")) {
                    String[] statsArr = entry.getValue().split("\\|");
                    statName1 = statsArr[0];
                    statName2 = statsArr[1];
                  } else {
                    String[] statsArr = entry.getValue().split("_");
                    statName1 = statsArr[0];
                    statName2 = statsArr[1];
                  }
                }
              }
              stats.put(statName1, statVal1);
              stats.put(statName2, statVal2);

              System.out.println(stats);
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
  // ! getStats method is not working
  // public Map<String, Integer> getStats(String[] data, String position) {
  //   String statName1 = "";
  //   String statName2 = "";
  //   int statVal1 = Integer.parseInt(data[5]);
  //   int statVal2 = Integer.parseInt(data[6]);

  //   // { goalie: stat1_stat2, forward: stat1_stat2, defender: stat1_stat2 }
  //   // getting key like goalie, forward, defender
  //   // if position matches key goalie = goalie but goalie != forward
  //   // we get the goalie: stat1_stat2 and split it
  //   // stat1
  //   // stat2

  //   for (Map.Entry<String, String> entry : allStats.entrySet()) {
  //     if (position.equals(entry.getKey())) {
  //       String[] stats = entry.getValue().split("_");
  //       List<String> statsList = convert(stats);
  //       System.out.println(statsList);
  //     }
  //   }

  //   return new HashMap<>(Map.of(statName1, statVal1, statName2, statVal2));
  // }
}
