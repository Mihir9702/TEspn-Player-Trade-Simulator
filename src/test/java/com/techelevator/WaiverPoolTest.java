package com.techelevator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class WaiverPoolTest {

  @Test
  public void testAddPlayer() {
    WaiverPool waiverPool = new WaiverPool();
    Player player = new Player(1, "John", "Doe", "Forward", 10.0, null);
    waiverPool.addPlayer(player);
    assertEquals(1, waiverPool.getPlayers().size());
    assertTrue(waiverPool.getPlayers().contains(player));
  }

  @Test
  public void testRemovePlayer() {
    WaiverPool waiverPool = new WaiverPool();
    Player player = new Player(1, "John", "Doe", "Forward", 10.0, null);
    waiverPool.addPlayer(player);
    waiverPool.removePlayer(player);
    assertEquals(0, waiverPool.getPlayers().size());
    assertFalse(waiverPool.getPlayers().contains(player));
  }
}
