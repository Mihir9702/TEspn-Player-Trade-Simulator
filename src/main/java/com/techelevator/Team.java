package com.techelevator;

import java.util.List;

public class Team {

  // private final String BOSTON_BRUINS = "";
  // private final String CAROLINA_HURRICANES = "";
  // private final String PITTSBURGH_PENGUINS = "";
  // private final String SEATTLE_KRAKEN = " ";
  private final String name;
  private final List<Player> players;
  
  public Team(String name, List<Player> players) {
    this.name = name;
    this.players = players;
  }
}
