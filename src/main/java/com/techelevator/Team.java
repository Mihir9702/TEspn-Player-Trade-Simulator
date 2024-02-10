package com.techelevator;

import java.util.List;

public class Team {

  private final String name;
  private List<Player> players;
  private double capSpace = 80_500_000.00;

  public int maxPlayers = 23;

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

  public Player findPlayerByJerseyNumber(int jerseyNumber) {
    for (Player player : players) {
      if (player.getJerseyNumber() == jerseyNumber) {
        return player;
      }
    }
    return null;
  }

  public void show() {
    // > 1) Boston Bruins       Players: 22 - Cap Space: $2,730,000.00
    System.out.println(
      name + "\tPlayers: " + players.size() + " - Cap Space: $" + capSpace
    );
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
