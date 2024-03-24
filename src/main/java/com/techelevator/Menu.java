package com.techelevator;

import java.io.*;
import java.util.*;

public class Menu {

  private List<Team> teams = new ArrayList<>();
  private List<Player> players = new ArrayList<>();
  private WaiverPool waiverPool = new WaiverPool();
  private File[] files = getFiles();

  private String teamName = "";
  private Scanner userInput = new Scanner(System.in);

  private String team1Name = "";
  private String team2Name = "";

  private Logger logger = new Logger("log.txt");

  public List<Player> getPlayers() {
    return players;
  }

  public List<Team> getTeams() {
    return teams;
  }

  public void run() {
    createPlayersAndTeams();
    printMainMenu();
  }

  public void printMainMenu() {
    System.out.println();
    System.out.print(
      "\n(1) Display Teams\n(2) Make a Trade\n(3) Pick up a Player\n(4) Find Player\n(5) Exit\n\nMake a selection: "
    );

    String input = userInput.nextLine();

    switch (input) {
      case "1":
        printTeams();
        break;
      case "2":
        printTradeMenu();
        break;
      case "3":
        pickUpPlayer();
        break;
      case "4":
        findPlayer();
        break;
      case "5":
        System.exit(0);
        break;
      default:
        System.out.println("Invalid selection. Please try again.");
        printMainMenu();
    }
  }

  // public void tradeMenu() {
  //   getPlayersfromteam1();
  //   getPlayersfromteam2();
  //   if (validate()) {
  //     makeTrade();
  //     logTrade();
  //   }
  // }

  /**
 * The `pickUpPlayer()` function allows the user to select a team, view the players available for
 * pickup from the waiver pool on that team, select a player to pick up, and add the player to their
 * team while removing them from the waiver pool.
 * ************************************
   display all teams
   display all players from waiver pool that are on that team
   select player to pick up
   add player to team
   remove player from waiver pool
 */
  public void pickUpPlayer() {
    System.out.println();
    System.out.println("Pick up a Player");

    for (Team team : teams) {
      team.show();
    }

    System.out.println("Select a team for the player to join (Q for quit): ");
    String input = userInput.nextLine();

    if (input.equals("Q") || input.equals("q")) {
      printMainMenu();
    }

    switch (input) {
      case "1":
        teamName = "Boston Bruins";
        break;
      case "2":
        teamName = "Carolina Hurricanes";
        break;
      case "3":
        teamName = "Pittsburgh Penguins";
        break;
      case "4":
        teamName = "Seattle Kraken";
        break;
      default:
        System.out.println("Invalid selection. Please try again.");
        pickUpPlayer();
    }

    printWaiverPlayers();

    System.out.println();
    System.out.print(
      "Enter the jersey number of the player you would like to pick up: "
    );

    int jerseyNumber = Integer.parseInt(userInput.nextLine());

    if (jerseyNumber == 0) {
      printMainMenu();
    }

    for (Player player : waiverPool.getPlayers()) {
      if (player.getJerseyNumber() == jerseyNumber) {
        for (Team team : teams) {
          if (
            team.getName().equals(teamName) &&
            team.getCapSpace() - player.getCapSpace() >= 0 &&
            team.getPlayers().size() < team.maxPlayers
          ) {
            team.addPlayer(player);
            waiverPool.removePlayer(player);
            logger.logWaiver(teamName, player.getName());
          }
        }
      }
    }
  }

  /**
 * The `findPlayer()` function allows the user to search for a player by their first name, last name,
 * or position and displays the player's information if found in a team or waiver pool.
 * ************************************
   find player in team or waiver pool
   display player info
   search by firstName, lastName or position
 */
  public void findPlayer() {
    System.out.println("Find Player");
    System.out.println();
    System.out.print(
      "Enter the first name, last name, or position of the player you would like to find: "
    );

    String input = userInput.nextLine();

    // find player in team
    for (Team team : teams) {
      for (Player player : team.getPlayers()) {
        if (
          player.getName().toLowerCase().contains(input) ||
          player.getPosition().toLowerCase().contains(input)
        ) {
          player.show();
        }
      }
    }

    // find player in waiver pool
    for (Player player : waiverPool.getPlayers()) {
      if (
        player.getName().contains(input) || player.getPosition().contains(input)
      ) {
        player.show();
      }
    }

    printMainMenu();
  }

  /**
   * The `confirmTrade` function in Java confirms a trade between two teams by checking if both teams
   * have enough players and cap space, and then making the trade if conditions are met.
   *
   * @param team1Players A list of players from team 1 who are involved in the trade.
   * @param team2Players A list of players from team 2 who are involved in the trade.
   */
  public void confirmTrade(
    List<Player> team1Players,
    List<Player> team2Players
  ) {
    Team team1 = null;
    Team team2 = null;

    for (Team team : teams) {
      if (team.getName().equals(team1Name)) {
        team1 = team;
      } else if (team.getName().equals(team2Name)) {
        team2 = team;
      }
    }

    double team1TotalCapSpace = getTradingPlayersTotalCapSpace(team1Players);
    double team2TotalCapSpace = getTradingPlayersTotalCapSpace(team2Players);

    System.out.println("Trade ");
    team1Players.forEach(player -> System.out.print(player.getName() + " "));
    System.out.println("for ");
    team2Players.forEach(player -> System.out.print(player.getName() + " "));
    System.out.println();
    System.out.print("Commit Trade (Y/N)? ");

    if (userInput.nextLine().toLowerCase().equals("y")) {
      boolean team1HasEnoughPlayers =
        team1.getPlayers().size() -
        team1Players.size() +
        team2Players.size() <=
        team1.maxPlayers;

      boolean team2HasEnoughPlayers =
        team2.getPlayers().size() -
        team2Players.size() +
        team1Players.size() <=
        team2.maxPlayers;

      boolean team1HasEnoughCapSpace =
        team1.getCapSpace() - team2TotalCapSpace >= 0;

      boolean team2HasEnoughCapSpace =
        team2.getCapSpace() - team1TotalCapSpace >= 0;

      boolean isTradeable =
        team1HasEnoughPlayers &&
        team2HasEnoughPlayers &&
        team1HasEnoughCapSpace &&
        team2HasEnoughCapSpace;

      makeTrade(team1Players, team2Players, team1, team2, isTradeable);
    } else printMainMenu();
  }

  /**
   * The makeTrade function performs a trade between two teams by removing players from one team and
   * adding them to the other, and logs the trade using a logger.
   *
   * @param team1Players A list of players from team 1 that will be traded to team 2.
   * @param team2Players A list of players from team 2 that will be traded to team 1.
   * @param team1 The parameter `team1` represents the first team involved in the trade.
   * @param team2 The parameter `team2` represents the first team involved in the trade.
   * @param isTradeable A boolean value indicating whether the trade is allowed or not based on cap space
   * constraints.
   */
  public void makeTrade(
    List<Player> team1Players,
    List<Player> team2Players,
    Team team1,
    Team team2,
    boolean isTradeable
  ) {
    if (isTradeable) {
      for (Player player : team1Players) {
        team1.removePlayer(player);
        team2.addPlayer(player);
      }

      for (Player player : team2Players) {
        team2.removePlayer(player);
        team1.addPlayer(player);
      }

      System.out.println();
      System.out.println("Trade successful.");
      logger.logTrade(team1Name, team2Name, team1Players, team2Players, true);
    } else {
      System.out.println();
      System.out.println("Trade not possible. Cap space exceeded.");
      logger.logTrade(team1Name, team2Name, team1Players, team2Players, false);
    }
  }

  public void printTradeMenu() {
    System.out.println("Select Team with Player to Trade");
    System.out.println();

    for (Team team : teams) {
      team.show();
    }

    System.out.println();
    System.out.print("Select Team: ");

    String input = userInput.nextLine();

    switch (input) {
      case "1":
        team1Name = "Boston Bruins";
        break;
      case "2":
        team1Name = "Carolina Hurricanes";
        break;
      case "3":
        team1Name = "Pittsburgh Penguins";
        break;
      case "4":
        team1Name = "Seattle Kraken";
        break;
      default:
        System.out.println("Invalid selection. Please try again.");
        printTradeMenu();
    }

    System.out.println();

    for (Team team : teams) {
      if (team.getName().equals(team1Name)) {
        for (Player player : team.getPlayers()) {
          player.show();
        }
      }
    }

    System.out.println();
    System.out.print("Select Player to Trade: ");

    String[] team1JerseyNumbers = userInput.nextLine().split("\\ ");
    List<Player> team1Players = new ArrayList<>();

    for (String team1JerseyNumber : team1JerseyNumbers) {
      for (Team team : teams) {
        if (team.getName().equals(team1Name)) {
          for (Player player : team.getPlayers()) {
            if (
              player.getJerseyNumber() == Integer.parseInt(team1JerseyNumber)
            ) {
              team1Players.add(player);
            }
          }
        }
      }
    }

    System.out.println();
    System.out.println(
      "Select Team with which to trade (cannot trade to the same team)"
    );
    System.out.println();

    for (Team team : teams) {
      if (!team.getName().equals(team1Name)) {
        team.show();
      }
    }

    System.out.println();
    System.out.print("Select Team: ");

    input = userInput.nextLine();

    switch (input) {
      case "1":
        team2Name = "Boston Bruins";
        break;
      case "2":
        team2Name = "Carolina Hurricanes";
        break;
      case "3":
        team2Name = "Pittsburgh Penguins";
        break;
      case "4":
        team2Name = "Seattle Kraken";
        break;
      default:
        System.out.println("Invalid selection. Please try again.");
        printTradeMenu();
    }

    System.out.println();

    for (Team team : teams) {
      if (team.getName().equals(team2Name)) {
        for (Player player : team.getPlayers()) {
          player.show();
        }
      }
    }

    System.out.println();
    System.out.print("Select Player to Trade: ");

    String[] team2JerseyNumbers = userInput.nextLine().split("\\ ");
    List<Player> team2Players = new ArrayList<>();

    for (String team2JerseyNumber : team2JerseyNumbers) {
      for (Team team : teams) {
        if (team.getName().equals(team2Name)) {
          for (Player player : team.getPlayers()) {
            if (
              player.getJerseyNumber() == Integer.parseInt(team2JerseyNumber)
            ) {
              team2Players.add(player);
            }
          }
        }
      }
    }

    confirmTrade(team1Players, team2Players);
    printMainMenu();
  }

  /**
   * The function calculates the total cap space of a team by summing up the cap space of each player.
   *
   * @param players A list of Player objects representing the players on a team.
   * @return The method is returning the total cap space of a team, which is a double value.
   */
  public double getTradingPlayersTotalCapSpace(List<Player> players) {
    double teamCapSpace = 0.00;
    for (Player player : players) {
      teamCapSpace += player.getCapSpace();
    }
    return teamCapSpace;
  }

  public void printWaiverPlayers() {
    if (waiverPool.getPlayers().size() == 0) {
      System.out.println();
      System.out.println("No players available for pickup.");
      printMainMenu();
    }

    for (Player player : waiverPool.getPlayers()) {
      player.show();
    }
  }

  /**
   * The function prints the players of a given team, sorts them by jersey number, and prompts the user
   * for further actions.
   *
   * @param teamName The team name for which you want to print the players.
   * @param isTrade A boolean value indicating whether the user is in a trade scenario or not. If isTrade
   * is true, the method will prompt the user to select a team to trade with. If isTrade is false, the
   * method will display a menu with options to go back to the main menu or exit the program
   */
  public void printPlayers(String teamName) {
    List<Player> players = new ArrayList<>();

    for (Team team : teams) {
      if (team.getName().equals(teamName)) {
        players = team.getPlayers();
      }
    }

    players.sort((a, b) -> a.getJerseyNumber() - b.getJerseyNumber());
    // players.sort(Comparator.comparing(Player::getJerseyNumber));

    for (Player player : players) {
      player.show();
    }

    System.out.println();
    System.out.print("Choose a player: ");

    String input = userInput.nextLine();

    for (Player player : players) {
      if (player.getJerseyNumber() == Integer.parseInt(input)) {
        player.show();
        player.showStats();
      }
    }

    // if select display teams we give option to waive player
    System.out.println();
    System.out.print("Waive this player (Y/N)? ");
    String waive = userInput.nextLine();

    Team t = null;
    Player p = null;
    if (waive.toLowerCase().equals("y")) {
      for (Team team : teams) {
        if (team.getName().equals(teamName)) {
          for (Player player : team.getPlayers()) {
            if (player.getJerseyNumber() == Integer.parseInt(input)) {
              waiverPool.addPlayer(player);
              t = team;
              p = player;
              logger.logWaiver(teamName, player.getName());
            }
          }
        }
      }

      if (p != null && t != null) {
        t.removePlayer(p);
      }

      printMainMenu();
    } else {
      printMainMenu();
    }
  }

  public void printTeams() {
    for (Team team : teams) {
      team.show();
    }

    selectTeam();
  }

  public void selectTeam() {
    System.out.println();
    System.out.print("Select a team to view players (Q for quit): ");
    String input = userInput.nextLine();

    if (input.equals("Q") || input.equals("q")) {
      printMainMenu();
    }

    switch (input) {
      case "1":
        teamName = "Boston Bruins";
        break;
      case "2":
        teamName = "Carolina Hurricanes";
        break;
      case "3":
        teamName = "Pittsburgh Penguins";
        break;
      case "4":
        teamName = "Seattle Kraken";
        break;
      default:
        System.out.println("Invalid selection. Please try again.");
        selectTeam();
    }

    System.out.println();
    printPlayers(teamName);
  }

  public void displayPlayers(String teamName) {
    for (Team team : teams) {
      if (team.getName().equals(teamName)) {
        for (Player player : team.getPlayers()) {
          player.show();
        }
      }
    }
  }

  public void createPlayersAndTeams() {
    int fileIndex = 0;

    for (File file : files) {
      try (Scanner fileScanner = new Scanner(file)) {
        while (fileScanner.hasNextLine()) {
          String line = fileScanner.nextLine();

          if (line.contains("POS")) {
            continue;
          }
          // System.out.println(line);

          String[] playerData = line.split("\\|");

          int jerseyNumber = Integer.parseInt(playerData[0]);
          String firstName = playerData[1];
          String lastName = playerData[2];
          String position = playerData[3];
          double capSpace = Double.parseDouble(playerData[4]);

          int stat1 = Integer.parseInt(playerData[5]);
          int stat2 = Integer.parseInt(playerData[6]);
          Map<String, Integer> statsMap = new HashMap<>();

          // getting player stats
          switch (position) {
            case "Goalie":
              statsMap.put("Shots Against", stat1);
              statsMap.put("Saves", stat2);
              break;
            case "Defense":
              statsMap.put("Hits", stat1);
              statsMap.put("Blocked Shots", stat2);
              break;
            case "Forward":
              statsMap.put("Goals", stat1);
              statsMap.put("Assists", stat2);
              break;
            default:
              break;
          }

          Player player = new Player(
            jerseyNumber,
            firstName,
            lastName,
            position,
            capSpace,
            statsMap
          );
          players.add(player);
        } // every line
      } catch (FileNotFoundException e) {
        System.out.println("File not found");
      }
      fileIndex++;
      if (fileIndex % 3 == 0) {
        String[] teamNameArr = files[fileIndex - 1].getName().split("\\_");
        String teamName = teamNameArr[0] + " " + teamNameArr[1];
        teams.add(new Team(teamName, players));
        players = new ArrayList<>();
      }
    } // every file
  }

  /**
   * The function "getFiles" returns an array of File objects representing the files in the "TeamData"
   *
   * @return The method is returning an array of File objects.
   */
  public File[] getFiles() {
    File folder = new File("TeamData");
    return folder.listFiles();
  }
}
