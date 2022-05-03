# Getting Started

### Prerequisites
Java 11, Maven

### How to run application
Build application with maven: *mvn clean install*

Please run *EvolutionInterviewApplication* class

### How to play
1. Create a new player
2. Create a new game
3. Play a round with information about bet amount and game mode (free or paid)

### How to run tests
Please run *RunCucumberIT* class

### API
Swagger for API is available here: **http://localhost:8082/swagger-ui.html**

### Limitations
- New player is created with random (10 chars ) identifier
- To create a new game you have to provide payerId. New game is created with random (10 chars ) identifier
- When player uses all his money from the balance he is not able to bet more
- There is no possibility to top up balance during the game. Player can use only initial balance
- Player can play more than one game at the same time
- Game cannot be played by more than one player
- All game scores disappears when application is restarted

### Decisions made
- There is no authentication and authorization
- All data are stored in map data structure
- Those properties can be configured from config yaml file: 
  - initial balance
  - min bet amount
  - max bet amount

