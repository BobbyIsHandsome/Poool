how to run the code?
1. gradle build
2. gradle run

Describe how to select difficulty level?
Click on the buttons below the pool game
EASY mode - to select easy difficult level
NORMAL mode - to select normal level
HARD mode - to select hard level

how to undo?
click on the UNDO button
you can undo more than once!!

cheat in your code?
1. click on the CHEAT button
2. a text field will pop up asking which color ball you'll like to cheat on
3. type the ball color
4. press ok
5. cheated!!

## Design Patterns

**Design Pattern 1 - Observer Pattern**

Participant classes:
1. PoolGame/Items/Ball
2. PoolGame/Items/CueStick
3. PoolGame/Observer/Subject
4. PoolGame/Observer/Observer
5. PoolGame/Items/Game


**Design Pattern 2 - Facade Pattern**

Participant classes
1. PoolGame/PoolTable
2. PoolGame/Game
3. PoolGame/App
4. PoolGame/Config/PocketConfig
5. PoolGame/Config/TableConfig
6. PoolGame/Items/Pocket
7. PoolGame/Items/CueStick
8. PoolGame/PocketsConfig

**Design Pattern 3 - State Pattern**

Participant classes
1.PoolGame/State/WinState
2.PoolGame/State/LoseState
3.PoolGame/State/StateControl
4.1.PoolGame/Game

**Extension 1 - Pockets and More Coloured Balls**

Participant classes:
- Configured Pockets
  1. PoolGame/Config/Configurable
  2. PoolGame/Config/GameConfig
  3. PoolGame/Config/PocketConfig
  4. PoolGame/Config/PocketsConfig
  5. PoolGame/Config/TableConfig
  6. PoolGame/Items/Pocket
  7. PoolGame/Items/PoolTable
  8. PoolGame/Game
  9. PoolGame/ConfigReader

- More Coloured Balls
  1. PoolGame/Builder/BallBuilder
  2. PoolGame/Builder/BallBuilderDirector
  3. PoolGame/Builder/BlackBallBuilder
  4. PoolGame/Builder/BlueBallBuilder
  5. PoolGame/Builder/BrownBallBuilder
  6. PoolGame/Builder/GreenBallBuilder
  7. PoolGame/Builder/OrangeBallBuilder
  8. PoolGame/Builder/PurpleBallBuilder
  9. PoolGame/Builder/RedBallBuilder
  10. PoolGame/Builder/WhiteBallBuilder
  11. PoolGame/Builder/YellowBallBuilder
  12. PoolGame/Strategy/PocketOnce
  13. PoolGameStrategy/PocketTwice
  14. PoolGame/Strategy/PocketThrice

- Player-controlled visible cue stick
  1. PoolGame/Items/Ball
  2. PoolGame/Items/CueStick
  3. PoolGame/Observer/Subject
  4. PoolGame/Observer/Observer
  5. PoolGame/Items/Game

**Extension 2 - Difficulty Level**

Participant classes
1. PoolGame/PoolTable
2. PoolGame/Config/TableConfig
3. PoolGame/Config/PocketConfig
4. PoolGame/Items/Ball
5. PoolGame/Items/Pocket
6. PoolGame/Items/CueStick
7. PoolGame/Game
8. PoolGame/App

**Extension 3 - Time and Score**

**Extension 4 - Undo and Cheat**
