## Module 1 Capstone - TEspn Player Trade Simulator

You're developing an application for the newest media mogul TEspn! The new software will simulate trading players between teams.

### com.techelevator.Application requirements

1. The application will keep track of multiple teams in the league.
   - The league will have a salary cap which represents the maximum total salary for all the players.
   - Each team will have a roster of players.
   - The application reads the rosters for each team from input files in the TeamData folder when it starts. 
   - All rosters are reset each time the application starts.
 
2. A main menu must display when the software runs, presenting the following options:
    > ```
    > (1) Display Teams
    > (2) Make a Trade
    > (3) Pick up a Player
    > (4) Find Player
    > (5) Exit
    > 
    > Make a selection: 
    > ```
3. When the user selects "(1) Display Teams", there are a series of screens that they may encounter. 
Below is an example workflow.

   1. They user is first presented with a list of all teams in the league including a listing for the waiver wire. 
   They also will need an option to exit from this menu and return back to the main menu.
   
      Each team displayed must include:
       - Name of the team.
       - Team City/Location
       - Number of players current on the team's roster.
       - Cap space available for each team.
      
      > ```
      > 1) Boston Bruins       Players: 22 - Cap Space: $2,730,000.00
      > 2) Carolina Hurricanes Players: 21 - Cap Space: $5,458,583.00
      > 3) Pittsburgh Penguins Players: 21 - Cap Space: $1,774,825.00
      > 4) Seattle Kraken      Players: 22 - Cap Space: $1,551,667.00
      > 5) Waiver Wire         Players:  0
      >
      > Select a team (Q for quit): 
      > ```
   2. Selecting a Team from the list will display the teams roster:
       - All players should be sorted by Jersey number
       - The display should include Jersey number, name, Position, and current salary
   
      > ```
      > 2) Chad Ruhwedel           Defense  $800,000.00
      > 10) Drew O'Connor          Forward  $925,000.00
      > 12) Jansen Harkins         Forward  $850,000.00
      > 17) Bryan Rust             Forward  $5,125,000.00
      > 19) Reilly Smith           Forward  $5,000,000.00
      > 20) Lars Eller             Forward  $2,450,000.00
      > 27) Ryan Graves            Defense  $4,500,000.00
      > 28) Marcus Pettersson      Defense  $4,025,175.00
      > 35) Tristan Jarry          Goalie   $5,375,000.00
      > 36) Colin White            Forward  $775,000.00
      > 39) Alex Nedeljkovic       Goalie   $1,500,000.00
      > 46) Ryan Shea              Defense  $775,000.00
      > 48) Valtteri Puustinen     Forward  $775,000.00
      > 58) Kris Letang            Defense  $6,100,000.00
      > 59) Jake Guentzel          Forward  $6,000,000.00
      > 65) Erik Karlsson          Defense  $10,000,000.00
      > 67) Rickard Rakell         Forward  $5,000,000.00
      > 71) Evgeni Malkin          Forward  $6,100,000.00
      > 73) Pierre-Olivier Joseph  Defense  $825,000.00
      > 77) Jeff Carter            Forward  $3,125,000.00
      > 87) Sidney Crosby          Forward  $8,700,000.00
      > 
      > Choose a player:
      > ```
   3. Selecting the player will display the player details:
       - Name and Jersey Number
       - Salary
       - Stats line
       - Option to Waive the player (remove from team and place in the Player Waiver list). 
       - After selecting Y or N, the user is returned to the Display Teams menu.

      > ```
      > 87) Sidney Crosby Forward $8,700,000.00
      > Assists: 21
      > Goals:   27
      >
      > Waive this player (Y/N)?
      > ```
4. When the user selects "(2) Make a Trade", they're guided through the trade process:
   1. Select Trading Team 
    > ```
    > Select Team with Player to Trade
    > 
    > (1) Team 1
    > (2) Team 2
    > (3) Team 3
    > ...
    > ```
    2. Select Player to Trade
    The `Select Player to Trade` menu should allow the user to select player(s) by their jersey number:
    > ```
    > Select Player to Trade
    > 
    > (8) Player 1
    > (42) Player 2
    > (83) Player 3
    > ...
    > ```
   3. Select Team with which to trade (cannot trade to the same team)
    > ```
    > Select Team 
    > 
    > (1) Team 1
    > (2) Team 2
    > (3) Team 3
    > ...
    > ```
    4. Select Player to Trade
    The `Select Player to Trade` menu should allow the user to select player(s) by their jersey number:
    > ```
    > Select Player to Trade
    > 
    > (8) Player 1
    > (42) Player 2
    > (83) Player 3
    > ...
    > ```
    5. Make Trade
    The `Make Trade` menu should display the team names and player name with a yes/no option:
    > ```
    > Trade {Player Name(s)} for {Player Name(s)}
    > 
    > Commit Trade (Y/N)?
    > ...
    > ```
- Trade should only be made if both teams are under the salary cap after the trade
- Salary Cap for each team is $80,500,000
- Trade should only be made if both teams are within the player amounts (maximum 23 for each team)
- Two players cannot have the same jersey number on the same team. You may change the jersey number for a player being 
acquired if there is a conflict.
  6. After accepting or rejecting the trade, the user returns to the Main Menu.
5. When the user selects "(3) Pick up a Player" 
   - Provide an option for a team to claim a player off of waivers.
   - The user selects the team from the list (not showing waiver wire list)
   - The application should list all players on waivers
   - A player can be claimed by jersey number
   - Claiming a player must follow all the rules as trading a player.

      > ```
      > 1) Boston Bruins       Players: 22 - Cap Space: $2,730,000.00
      > 2) Carolina Hurricanes Players: 20 - Cap Space: $5,458,583.00
      > 3) Pittsburgh Penguins Players: 21 - Cap Space: $1,774,825.00
      > 4) Seattle Kraken      Players: 22 - Cap Space: $1,551,667.00
      > 5) Waiver Wire         Players:  1
      >
      > Select a team for the player to join (Q for quit): 2
      > ```
   
      > ```
      > 1) 88) Martin Necas Forward $3,000,000.00
      > 
      > Enter the number of the player you wish to claim: 1
      > ```
6. When the user selects "(4) Find Player"
    Searches all teams and the waiver wire for players that match entered text. The text can match first name, last name, or position.
    Denotes if the player is on a team (display team name) or not (waiver wire)
      > ```
      > Enter the name, or partial name, of the player: Sid
      > 
      > Pittsburgh Penguins
      > 87) Sidney Crosby Forward $8,700,000.00
      > ```
7. The application logs all transactions.
   - Each transaction must generate a line in a file called `Log.txt`.
   - The lines must follow the format shown in the following example.
        > ```
        > 01/01/2019 12:00:00 PM Team Name Player Name <-> Team Name Player Name Confirmed
        > 01/01/2019 12:00:10 PM Team Name Player Name <-> Team Name Player Name Denied
        > 01/01/2019 12:00:15 PM Team Name Player Name -- Waived 
        > 01/01/2019 12:00:30 PM Team Name Player Name -- Claimed 
        > ```
8. Create as many of your classes as possible to be "testable" classes. Limit console
input and output to as few classes as possible.
9. Provide unit tests demonstrating that your code works correctly.
