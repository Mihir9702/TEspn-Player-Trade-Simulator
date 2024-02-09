package com.techelevator;

import java.io.*;
import java.util.*;

public class FileHandler {

  private List<Team> teams = new ArrayList<>();
  private File[] files;

  private String teamName = "";

  // * helper method for dev
  public void show() {
    teams.forEach(team -> {
      if (team.getPlayers().size() > 0) {
        for (Player p : team.getPlayers()) {
          System.out.println(p.getName());
        }
      }
    });
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
  private Player[] Player;

  public String run() {
    File folder = new File("TeamData"); // folder path
    files = folder.listFiles();

    int fileIndex = 0;

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
            String[] data = line.split("\\|");

            try {
              int jerseyNumber = Integer.parseInt(data[0]);
              String firstName = data[1];
              String lastName = data[2];
              String position = data[3];
              double capSpace = Double.parseDouble(data[4]);

              players.add(
                new Player(
                  firstName,
                  lastName,
                  capSpace,
                  jerseyNumber,
                  position
                )
              );
            } catch (NumberFormatException e) {}
          }
        }

        if (fileIndex % 3 == 0 && fileIndex != 0) {
          teams.add(new Team(teamName, players));
          players.clear();
        }

        fileIndex++;
      } catch (FileNotFoundException e) {}
    }
    show();
  }

  public String[] wordSearch() {
    for (Player p : Player) {}
  }
}
