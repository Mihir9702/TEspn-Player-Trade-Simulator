package com.techelevator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TeamTest {

  @Test
  public void testAddPlayer() {
    Team team = new Team("Test Team", new ArrayList<>());
    Player player = new Player(1, "John", "Doe", "Forward", 10.0, null);
    team.addPlayer(player);
    assertEquals(1, team.getPlayers().size());
    assertTrue(team.getPlayers().contains(player));
  }

  @Test
  public void testRemovePlayer() {
    List<Player> players = new ArrayList<>();
    Player player = new Player(1, "John", "Doe", "Forward", 10.0, null);
    players.add(player);
    Team team = new Team("Test Team", players);
    team.removePlayer(player);
    assertEquals(0, team.getPlayers().size());
    assertFalse(team.getPlayers().contains(player));
  }

  @Test
  public void testFindPlayerByJerseyNumber() {
    List<Player> players = new ArrayList<>();
    Player player1 = new Player(1, "John", "Doe", "Forward", 10.0, null);
    Player player2 = new Player(2, "Jane", "Doe", "Defense", 12.0, null);
    players.add(player1);
    players.add(player2);
    Team team = new Team("Test Team", players);
    assertEquals(player1, team.findPlayerByJerseyNumber(1));
    assertEquals(player2, team.findPlayerByJerseyNumber(2));
    assertNull(team.findPlayerByJerseyNumber(3)); // Player with jersey number 3 does not exist
  }
}
