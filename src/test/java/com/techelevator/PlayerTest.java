package com.techelevator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerTest {

  @Test
  public void testGetCapSpace() {
    Player player = new Player(1, "John", "Doe", "Forward", 10.0, null);
    assertEquals(10.0, player.getCapSpace());
  }

  @Test
  public void testGetJerseyNumber() {
    Player player = new Player(1, "John", "Doe", "Forward", 10.0, null);
    assertEquals(1, player.getJerseyNumber());
  }

  @Test
  public void testGetPosition() {
    Player player = new Player(1, "John", "Doe", "Forward", 10.0, null);
    assertEquals("Forward", player.getPosition());
  }

  @Test
  public void testGetName() {
    Player player = new Player(1, "John", "Doe", "Forward", 10.0, null);
    assertEquals("John Doe", player.getName());
  }
}
