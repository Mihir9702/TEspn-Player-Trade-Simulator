package com.techelevator;

import java.io.*;
import java.util.*;

public class Menu {

  /* to do
   * print trade confirmation
   * waiver pool
   * log
   */

  private List<Team> teams = new ArrayList<>();
  private List<Player> players = new ArrayList<>();
  private WaiverPool waiverPool = new WaiverPool();
  private File[] files;

  private String teamName = "";
  private Scanner userInput = new Scanner(System.in);

  private String team1Name = "";
  private String team2Name = "";

  public void run() {
    files = getFiles();
    createPlayersAndTeams();
    printMainMenu();
  }

  /**
   * The function `printMainMenu` displays a menu with options for displaying teams, making a trade,
   * picking up a player, finding a player, or exiting the program, and prompts the user to make a
   * selection.
   */
  public void printMainMenu() {
    System.out.println(
      "\n(1) Display Teams\n(2) Make a Trade\n(3) Pick up a Player\n(4) Find Player\n(5) Exit\n\nMake a selection:"
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
        // ! print players from waiver pool after user selects team and add player to team (follow rules of trading player)
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
    boolean isTrade = false;

    System.out.println("Pick up a Player");

    for (Team team : teams) {
      team.show();
    }

    System.out.println("Select Team to Pick Up Player");

    String input = userInput.nextLine();

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

    printPlayers(teamName, isTrade);

    System.out.println(
      "Enter the jersey number of the player you would like to pick up:"
    );

    int jerseyNumber = Integer.parseInt(userInput.nextLine());

    for (Team team : teams) {
      if (team.getName().equals(teamName)) {
        for (Player player : team.getPlayers()) {
          if (player.getJerseyNumber() == jerseyNumber) {
            team.removePlayer(player);
            waiverPool.addPlayer(player);
          }
        }
      }
    }

    printMainMenu();
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

    System.out.println(
      "Enter the first name, last name, or position of the player you would like to find:"
    );

    String input = userInput.nextLine();

    for (Team team : teams) {
      for (Player player : team.getPlayers()) {
        if (
          player.getName().contains(input) ||
          player.getPosition().contains(input)
        ) {
          player.show();
        }
      }
    }

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
   * The function `makeTrade` checks if two teams have enough players and cap space to make a trade, and
   * if so, it performs the trade by removing players from one team and adding them to the other team.
   *
   * @param team1Players A list of players that team 1 wants to trade.
   * @param team2Players The `team2Players` parameter is a list of players that are being traded from
   * `team2` to `team1`.
   */
  public void makeTrade(List<Player> team1Players, List<Player> team2Players) {
    Team team1 = null;
    Team team2 = null;

    for (Team team : teams) {
      if (team.getName().equals(team1Name)) {
        team1 = team;
      } else if (team.getName().equals(team2Name)) {
        team2 = team;
      }
    }

    double team1TotalCapSpace = getTradingPlayersTotalCapSpace(0, team1Players);
    double team2TotalCapSpace = getTradingPlayersTotalCapSpace(0, team2Players);

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

    if (
      team1HasEnoughPlayers &&
      team2HasEnoughPlayers &&
      team1HasEnoughCapSpace &&
      team2HasEnoughCapSpace
    ) {
      for (Player player : team1Players) {
        team1.removePlayer(player);
        team2.addPlayer(player);
      }

      for (Player player : team2Players) {
        team2.removePlayer(player);
        team1.addPlayer(player);
      }
    } else {
      System.out.println("Trade not possible. Cap space exceeded.");
    }
  }

  /**
   * The function prints a trade menu, allows the user to select teams and players to trade, makes the
   * trade, and then prints the main menu.
   */
  public void printTradeMenu() {
    System.out.println("Select Team with Player to Trade");
    System.out.println();
    System.out.println(
      "\n(1) Boston Bruins\n(2) Carolina Hurricanes\n(3) Pittsburgh Penguins\n(4) Seattle Kraken\n\nMake a selection:"
    );

    List<Player> team1Players = new ArrayList<>();
    List<Player> team2Players = new ArrayList<>();

    team1Players = selectTeamAndShowPlayersToTrade(team1Players, team1Name);
    team2Players = selectTeamAndShowPlayersToTrade(team2Players, team2Name);

    makeTrade(team1Players, team2Players);
    printMainMenu();
  }

  /**
   * The function calculates the total cap space of a team by summing up the cap space of each player.
   *
   * @param teamCapSpace The initial cap space of the team before considering any players. It is a double
   * value.
   * @param players A list of Player objects representing the players on a team.
   * @return The method is returning the total cap space of a team, which is a double value.
   */
  public double getTradingPlayersTotalCapSpace(
    double teamCapSpace,
    List<Player> players
  ) {
    for (Player player : players) {
      teamCapSpace += player.getCapSpace();
    }
    return teamCapSpace;
  }

  /**
   * The function allows the user to select a team and then displays the players available for trade from
   * that team.
   *
   * @param players A list of Player objects representing all the players in the system.
   * @param teamName The `teamName` parameter is a String that represents the name of the team for which
   * the players are selected and shown for trading.
   * @return The method is returning a List<Player> object.
   */
  public List<Player> selectTeamAndShowPlayersToTrade(
    List<Player> players,
    String teamName
  ) {
    String input = userInput.nextLine();
    boolean isTrade = true;

    switch (input) {
      case "1":
        printMainMenu();
        break;
      case "2":
        System.exit(0);
        break;
      case "3":
        teamName = "Boston Bruins"; // have to check if this works
        printPlayers(teamName, isTrade);
        players = getPlayersFromTeamToTrade(teamName);
        break;
      case "4":
        teamName = "Carolina Hurricanes";
        printPlayers(teamName, isTrade);
        players = getPlayersFromTeamToTrade(teamName);
        break;
      case "5":
        teamName = "Pittsburgh Penguins";
        printPlayers(teamName, isTrade);
        players = getPlayersFromTeamToTrade(teamName);
        break;
      case "6":
        teamName = "Seattle Kraken";
        printPlayers(teamName, isTrade);
        players = getPlayersFromTeamToTrade(teamName);
        break;
      default:
        System.out.println("Invalid selection. Please try again.");
        printTeams();
    }

    return players;
  }

  /**
   * The function retrieves a list of players from a specific team based on the jersey numbers provided
   * by the user.
   *
   * @param teamName The teamName parameter is a String that represents the name of the team from which
   * you want to get the players to trade.
   * @return The method is returning a List of Player objects.
   */
  public List<Player> getPlayersFromTeamToTrade(String teamName) {
    System.out.println(
      "Enter the jersey numbers of the players you would like to trade (separated by spaces):"
    );

    List<Player> players = new ArrayList<>();
    String[] jerseyNumbers = userInput.nextLine().split("\\ ");
    for (Team team : teams) {
      if (team.getName().equals(teamName)) {
        for (String jerseyNumber : jerseyNumbers) {
          for (Player player : team.getPlayers()) {
            if (player.getJerseyNumber() == Integer.parseInt(jerseyNumber)) {
              players.add(player);
            }
          }
        }
      }
    }

    return players;
  }

  public void tradePlayers() {
    System.out.println("Trade Players");
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
  public void printPlayers(String teamName, boolean isTrade) {
    List<Player> players = new ArrayList<>();
    for (Team team : teams) {
      if (team.getName().equals(teamName)) {
        players = team.getPlayers();
      }
    }

    players.sort(Comparator.comparing(Player::getJerseyNumber));

    for (Player player : players) {
      player.show();
    }

    if (isTrade) {
      System.out.println("Select Team with Player to Trade");
    } else {
      System.out.println("\n(1) Main Menu\n(2) Exit\n\nMake a selection:");
    }

    String input = userInput.nextLine();

    switch (input) {
      case "1":
        printMainMenu();
        break;
      case "2":
        System.exit(0);
        break;
      default:
        System.out.println("Invalid selection. Please try again.");
        printPlayers(teamName, isTrade);
    }
  }

  /**
   * The function "displayTeams" displays a list of all teams in a league, including their name,
   * location, number of players on the roster, and available cap space, and provides options for the
   * user to navigate to other menus or exit the program.
   */
  public void printTeams() {
    /**
     * 
   1. They user is first presented with a list of all teams in the league including a listing for the waiver wire.
      They also will need an option to exit from this menu and return back to the main menu.

      Each team displayed must include: - Name of the team. - Team City/Location - Number of players current on the team's roster. - Cap space available for each team.
     */

    for (Team team : teams) {
      team.show();
    }

    String input = userInput.nextLine();
    boolean isTrade = false;

    switch (input) {
      case "1":
        printMainMenu();
        break;
      case "2":
        System.exit(0);
        break;
      case "3":
        printPlayers("Boston Bruins", isTrade);
        break;
      case "4":
        printPlayers("Carolina Hurricanes", isTrade);
        break;
      case "5":
        printPlayers("Pittsburgh Penguins", isTrade);
        break;
      case "6":
        printPlayers("Seattle Kraken", isTrade);
        break;
      default:
        System.out.println("Invalid selection. Please try again.");
        printTeams();
    }
  }

  /**
   * The function creates players and teams by reading data from files and populating the players and
   * teams lists accordingly.
   */
  public void createPlayersAndTeams() {
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
  }

  /**
   * The function "getFiles" returns an array of File objects representing the files in the "TeamData"
   * folder.
   *
   * @return The method is returning an array of File objects.
   */
  public File[] getFiles() {
    File folder = new File("TeamData");
    return folder.listFiles();
  }
}
