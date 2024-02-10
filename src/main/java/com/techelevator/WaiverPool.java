package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WaiverPool {

  List<Player> players;
  Scanner userInput = new Scanner(System.in);

  public WaiverPool() {
    this.players = new ArrayList<>();
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void removePlayer(Player player) {
    players.remove(player);
  }

  public void makeTrade(List<Team> teams) {
    System.out.println("Select Trading Team:");
    for (int i = 0; i < teams.size(); i++) {
      System.out.println("(" + (i + 1) + ")" + teams.get(i).getName());
    }
    System.out.print("\nMake a selection: ");
    int tradingTeamIndex = Integer.parseInt(userInput.nextLine()) - 1;
    Team tradingTeam = teams.get(tradingTeamIndex);
    System.out.println("Select Player(s) to Trade:");
    tradingTeam.show();
    System.out.println(
      "Enter the jersey number(s) of the player(s) you would like to trade (separated by spaces):"
    );
    String[] jerseyNumbers = userInput.nextLine().split(" ");

    List<Player> playersToTrade = new ArrayList<>();
    for (String jerseyNumber : jerseyNumbers) {
      Player player = tradingTeam.findPlayerByJerseyNumber(
        Integer.parseInt(jerseyNumber)
      );
      if (player != null) {
        playersToTrade.add(player);
      }
    }
  }
}
