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
    String position
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.capSpace = capSpace;
    this.jerseyNumber = jerseyNumber;
    this.position = position;
  }

  public double getCapSpace() {
    return capSpace;
  }

  public int getJerseyNumber() {
    return jerseyNumber;
  }

  public String getPosition() {
    return position;
  }

  public Map<String, Integer> getStats() {
    return stats;
  }

  public String getName() {
    return firstName + " " + lastName;
  }
}
