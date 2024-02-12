import static org.junit.jupiter.api.Assertions.assertEquals;

import com.techelevator.Menu;
import com.techelevator.Player;
import com.techelevator.Team;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

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
}
