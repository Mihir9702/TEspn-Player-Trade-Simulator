package com.techelevator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

public class MenuTest {

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
