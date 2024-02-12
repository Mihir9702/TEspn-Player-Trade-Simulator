package com.techelevator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class MenuTest {

  @Test
  public void testPlayerInitialization() {
    int jerseyNumber = 25;
    String firstName = "Brandon";
    String lastName = "Carlo";
    String position = "Defense";
    double capSpace = 4100000.00;
    Map<String, Integer> stats = new HashMap<>();
    stats.put("Goals", 61);
    stats.put("Assists", 78);

    // Act
    Player player = new Player(
      jerseyNumber,
      firstName,
      lastName,
      position,
      capSpace,
      stats
    );

    // Assert
    assertEquals(jerseyNumber, player.getJerseyNumber());
    assertEquals(position, player.getPosition());
    assertEquals(capSpace, player.getCapSpace());
    assertEquals(stats, player.getStats());
    assertEquals("Brandon Carlo", player.getName());
  }

  @Test
  public void testGetFiles() {
    Menu menu = new Menu();
    File[] files = menu.getFiles();
    assertNotNull(files);
    assertTrue(files.length > 0);
  }

  @Test
  public void testCreatePlayersAndTeams() {
    Menu menu = new Menu();
    menu.createPlayersAndTeams();
    assertFalse(menu.getTeams().isEmpty());
    menu.getTeams().forEach(team -> assertFalse(team.getPlayers().isEmpty()));
  }
}
