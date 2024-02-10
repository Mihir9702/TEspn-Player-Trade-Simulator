package com.techelevator;

import java.util.List;

public class WaiverPool {

  List<Player> players;

  public List<Player> getPlayers() {
    return players;
  }

  public void addPlayer(Player player) {
    players.add(player);
  }
}
