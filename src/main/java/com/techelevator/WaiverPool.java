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
}
