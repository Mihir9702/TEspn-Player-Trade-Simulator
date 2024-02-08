package com.techelevator;

import java.util.HashMap;
import java.util.Map;

public class Player {

  private String firstName = "";
  private String lastName = "";
  private double capSpace = 0.0;
  private int jerseyNumber = 0;
  private String position = "";
  Map<String, Integer> stats = new HashMap<>();

  public Player(
    String firstName,
    String lastName,
    double capSpace,
    int jerseyNumber,
    String position,
    Map<String, Integer> stats
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.capSpace = capSpace;
    this.jerseyNumber = jerseyNumber;
    this.position = position;
    this.stats = stats;
  }
}
