package com.techelevator;

import java.util.ArrayList;
import java.util.List;

public class WaiverPool {

  List<Player> players;

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
