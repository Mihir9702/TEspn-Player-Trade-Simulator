package com.techelevator;

import java.util.List;

public class Team {

  private final String name;
  private List<Player> players;
  private double capSpace = 80_500_000.00;
  public int maxPlayers = 23;

  // ! bug -> removePlayer() not working ConcurrentModificationException
  // ! feature -> duplicate jerseyNumbers

  public double getCapSpace() {
    return capSpace;
  }

  public String getName() {
    return name;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public Team(String name, List<Player> players) {
    this.name = name;
    this.players = players;
  }

  /**
   * The function finds a player in a list of players based on their jersey number.
   *
   * @param jerseyNumber The jerseyNumber parameter is an integer representing the jersey number of a
   * player.
   * @return The method is returning a Player object that matches the given jersey number. If no player
   * is found with the given jersey number, it will return null.
   */
  public Player findPlayerByJerseyNumber(int jerseyNumber) {
    for (Player player : players) {
      if (player.getJerseyNumber() == jerseyNumber) {
        return player;
      }
    }
    return null;
  }

  public void show() {
    String message = getPlayers().size() + " - Cap Space: " + getCapSpace();

    switch (name) {
      case "Boston Bruins":
        System.out.println("1) Boston Bruins\tPlayers: " + message);
        break;
      case "Carolina Hurricanes":
        System.out.println("2) Carolina Hurricanes\tPlayers: " + message);
        break;
      case "Pittsburgh Penguins":
        System.out.println("3) Pittsburgh Penguins\tPlayers: " + message);
        break;
      case "Seattle Kraken":
        System.out.println("4) Seattle Kraken\tPlayers: " + message);
        break;
      default:
        System.out.println("Invalid team name");
    }
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void removePlayer(Player player) {
    players.remove(player);
  }

  public void setCapSpace(double capSpace) {
    this.capSpace = capSpace;
  }
}
