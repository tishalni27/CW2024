
# Coursework Project

## GitHub Repository

You can access the source code for this project at the following GitHub repository:

[GitHub Repository Link](https://github.com/tishalni27/CW2024.git)

The repository contains all the source code, including the implementation, test cases, and any resources used in this project. Below are the main details:

- **Branch Structure**:  
  - `master`: The main branch containing the latest stable version of the project.
  - `test`: A feature or testing branch where experimental or in-progress changes are being tested. This branch is used for testing new features before merging them into `master`.
- test 2

- **Commits**:  
  - Each commit is documented with a message describing the changes made in that particular commit.
  - Important commits related to bug fixes, new features, and other significant changes are well documented for easy reference.


Please ensure to explore the branches and commits to view the full history of changes made during the development of this project.

## Compilation Instructions

Before compiling make sure it is java make sure java version "1.8.0_381". Sdk is SDK Oracle OpenJDK version 22
To compile and run the project, follow these steps:

1. Download Zip file from git repository.
2. Git Clone from repository
3. Open file in IntelliJ
4. Build file
5. Click on src
6. click on main
7. Click java
8. Click com.example.demo
9. Click controller
10. Click Main
11. Click Run




## Implemented and Working Properly



--Refactoring--

1. ** Refactoring images to make it meaningful **
- firball.png to bossProjectile.png to indicate its the Boss's projectile
- enemyplane.png to enemyLevel1.png to indicate the enemy plane in each level
- userfire.png to userProjectile to indicate the user's projectile
- shield.jpg to shield.png

2. **Refactoring Packages**
- Shrinking factor. Cropped all the actors in the game so that collision is Accurate. Modified size of each image to fit screen seamlessly.

3. **Refactoring Class**
- refactored clas for timeline issue. Added stopGame() with timeline.stop to stop the Game loop.

4. **Code arrangement**
- All the code in the classes in my project is arranged correctly. Starting with the package declaration, followed by the imports, then class declarations, constructors, methods, and finally, any inner classes or helper methods. This structure ensures readability and maintainability of the code, making it easier for future developers  to understand and modify as needed. The code is also consistently formatted, with proper indentation and spacing, ensuring that the logical structure is clear and follows Java conventions.

5. **Encapsulation design**

My code follows the principles of encapsulation by ensuring that the internal state and functionality of each class are hidden from the outside world, and only accessible through well-defined public methods. For instance, private instance variables like `scoreText`, `killCountText`, and `levelParent` are not directly accessible from other classes. Instead, public methods like `updateScore()`, `updateKillCount()`, and `updateActor()` allow controlled access and modification of these variables. Additionally, classes such as `UserProjectile` and `LevelView` encapsulate behavior like movement or UI updates within their respective methods, preventing direct manipulation of their internal states. This design promotes data security, reduces coupling, and ensures that each class can be interacted with in a predictable, safe manner.


--Additions--

1. ** Meaningful Image Additions **
- background3.png for Level3's background
- background4.png for custom background for Level 4
- enemyLevel3.png, a bigger enemy for Level3 with higher health
- exitBtn, exit button for user to close the game whenever needed
- explosion.png, explosion image to indicate the collision
- homeScreen.png, custom homeScreen for as a game enhancement
- HowToPlay.png, a guide for users before playing the game
- levelChooseBackground.png, an interface for users to choose their level
-levelsBtn.png, a levels button in the pause screen for users to go back to levels screen
-pauseBtn.png, for users to pause game when intended
-pauseScreen.png, a panel background containing all the buttons
- quitBtn.png, button to quit to main menu
- resumeBtn.png, for users to click to resume game
- retryBtn.png, for users to retry game
- scoreBoardScreen.png, for users to view their score
- UserShield.png, used in level 3 to protect user plane

2. **FXML document**

HomeScreen.fxml: Defines the layout and UI components for the home screen, including buttons for play and exit functionality, along with the background image. 

LevelChoose.fxml: Defines the layout and UI components for the level Choose Screen, including buttons for Level 1,2,3, Challenge and "how to play". User is also allowed to go back. 

GameRules.fxml: Defines the layout for the HowToPlay image to guide users on the game.

3. ** Packages **

- Level1 package, to encapsulate all Level 1 related entities in 1 package. 
- Level1AndChallenge package, this indicates the common elements that are shared in Level 1 and Level Challenge.
- Level2, to group all classes related to Level2 such as LevelTwo and enemies in level two.
- Level3, to group all classes that involve Level 3 in the game
- LevelCommonElements, used for elements int the game that are used in all the levels such as actors and visuals that are common between the levels.
- Levels, indicates the manager in all the levels.This package includes LevelParent that controls the overall game flow of each levels, Actor Manager, and UIManager.
- Screen, group GameOver, PauseScreen and win image together
- User, specifically used for user actors such as user plane and user projectile.


4. **Nested Packages **
- EnemyLevelTwo, as the enemy which is the boss in level two contains a number of classes, it was easier to group all of them to increase code readability.
- Actor,as the actor is under common elements, grouping both active actor and active actor destructible would make it easier to locate
- visuals, visuals is in levelCommonElements as well, this is to show the common elements that are visuals in each level.
- Pause, to encapsulate PauseScreen. 

5. **Pause Screen **
- Each level has a pause button which leads to a pause Screen. I created a class called PauseScreen to handle all Pause function. This pauseScreen button connects to LevelParent as there is a PauseGame() function





## Implemented but Not Working Properly


1. ** Retry button in pause Screen. **
- Retry button was implemented initially. Unfortunately, it was difficult to connect it after the user ends the game. I decided to put the retry button on the Game Over screen as it had higher precedence over the one in the pause Screen


2. **Active Actor Clas**

-ActiveActor was meant to be a branch or portion of LevelParent to reduce the functionalities in LevelParent. This class was meant to handle all actor related activities that involves the user plane, enemy and projectile. This class will act as a central registry for all actors in the game. It will hold lists for different types of actors like friendly, enemy, projectiles and provide methods to add, remove, update, and retrieve them.

Way to Fix:

- Delegate actor-specific tasks: Move actor-related tasks from LevelParent to ActorManager. For example adding or removing actors (e.g., addActor, removeActor). Updating all actors (e.g., updateAllActors).Handling collisions (delegating collision detection logic, if needed).
Querying actors based on type or state (e.g., retrieve all destroyed actors).

- Integrate with LevelParent: Replace the actor lists in LevelParent with a reference to ActorManager. Use the ActorManager to manage actors rather than directly manipulating lists in LevelParent.

- Ensure cohesion: Keep game logic (like spawning, scoring, or game state updates) in LevelParent while keeping actor-specific logic in ActorManager.

- This division will make the LevelParent class less cluttered and ensure that actor management is reusable and centralized.

3. **UIManager**
This class was meant to be the centralized UI Control. Unfortunately, the parts involved were not removed from LevelParent on time which caused more errors later on. It was supposed to ave a single class manage all UI elements (score, health, pause screen, etc.), updating and removing components as needed.

-UI Components: Handle text updates (score, kill count), health display, and game state UI (win, loss, pause). Ensure all UI elements are responsive to game state changes.

-Game Integration: Ensure UIManager communicates with the game (e.g., update score, health) and manages UI during different game states (pause, game over).

-Separation of Concerns: Keep UI logic separate from game mechanics, focusing purely on displaying data and responding to user inputs.

-Reusability & Performance: Design the UIManager to be extendable and lightweight, updating only necessary UI elements for better performance.

-Event Handling: Handle user input (key presses, buttons) to trigger UI changes (pause, retry) and provide immediate feedback.







## Features Not Implemented

1. ** Background Music **
Reason of not implementing: Lack of time

Ideas on implementation: 
- Audio Initialization: Create an audio player object (for example, using JavaFX’s MediaPlayer or a similar library) that loads the background music file.
- Looping Music: Set the media player to loop indefinitely, ensuring the background music continues playing throughout the game.
- Volume Control: Implement a method to adjust the volume of the music. This could be a slider in the settings menu that modifies the volume value of the media player.
- Contextual Music Changes: Set up event listeners for key game moments like level transition, boss fight, so the audio can switch to a new track dynamically based on the context.
- Create a MusicManager class to handle audio.

2. ** Different EnemyProjectile in Level 3 **
Reason of not implementing: Lack of time

Ideas on implementation:
- Projectile Class Variants: Create different projectile classes or subclasses, each defining its own behavior like speed, damage and effect. For example, you could have a NormalProjectile, HomingProjectile, and ExplosiveProjectile.

- Randomized Projectile Selection: In the enemy logic, when spawning a projectile, randomly pick one of the available projectile types to make enemy attacks more varied.
Projectile Visual Effects: Implement special effects for certain projectiles, like explosions for explosive projectiles or trails for homing projectiles. This could be done by triggering visual animations upon collision.

- Example add a method to the Enemy class to fire specific projectiles or create a subclass of Projectile like Level3Projectile that has a custom behavior.


3. ** Damage count for boss **
Reason of not implementing: Lack of time and planning for the classes
Ideas on implementation: 
- Boss Health Tracking: Create a variable to store the boss's health and decrement it as the player successfully hits the boss with a projectile.
- Health Bar or Count Display: Add a visual component to show the boss's health. This could be a health bar that fills and empties based on the boss's current health, or a numeric counter that updates as the player damages the boss.
- Damage Feedback: Each time the player hits the boss, show visual feedback, like flashing the boss, playing a damage sound, or displaying a particle effect like fire.

4. **LevelViewLevel3 **
Reason of not implementing: Lack of time
Ideas on implementation: 
- Custom UI for Level 3: Define custom UI components for Level 3, such as a unique heart display or a special background. You may add visual elements like a custom title, score display, or timer.
- Unique Level Background: Use a different background for Level 3 to visually differentiate it from other levels, giving the player a sense of progression.
- Level-Specific Effects: Implement animations that transition between levels, such as fading in or zooming in/out, which creates a smoother user experience and emphasizes the change in context when the player reaches Level 3.
- How: create a new class called LevelViewLevel3 and transfer the UI part from LevelThree.


5. ** LevelViewLevelChallenge **

- Make the code shorter using LevelViewLevelChallenge. To increase reusability and mantainability of a programmer. 

6. ** Increase the speed of Enemies in LevelChallenge as user progress further**
- Track the score in LevelChallenge, for every +20 in the score SPEED+0.1.

7.  ** java.util.Observer change** 
As Observer is deprecated since Version 9, I wanted to switch it to PropertyChangeListener which is compatible with newer java versions. Unfortunately due to the lack of time and planning I could not achieve that.




## New Java Classes

 1. **UIController.java**

   - Description: The `UIController` class handles user interactions for navigating between different scenes (or screens) in the game. It is responsible for managing the home screen, level selection, and various other game-related actions. The class contains methods for transitioning to new scenes, such as starting different levels, showing game rules, or exiting the game.
   

   - Key Features:
     - Scene Navigation : The class makes it easy to switch between scenes based on user input (e.g., starting the game, going to the level selection screen, viewing the game rules).
     - Error Handling : Provides error handling with user-friendly error dialogs in case of issues when loading scenes or starting levels.
     - Exit Confirmation : Before exiting the game, the user is prompted with a confirmation dialog to prevent accidental game closure.
     - Game State : It maintains a `gameLevel` variable, which tracks the current level being played.

2. LevelOneConfig

The LevelOneConfig class contains all the configuration constants needed for the first level of the game. These constants define various game settings, including the background image, player health, enemy spawning parameters, and UI configuration. By centralizing these constants in one place, the class ensures that any changes to level configuration can be easily managed and updated without modifying multiple parts of the code. This approach improves code maintainability, scalability, and reduces the risk of inconsistencies.

3. BossConfig

The `BossConfig` class holds constants for the boss in Level 2, including fire rate, shield probability, initial health, and shield duration. Centralizing these values improves maintainability and consistency, making the game easier to update and scale.

4. LevelTwoConfig

The LevelTwoConfig class defines constants for Level 2, including the background image, the next level, and the player's initial health. Centralizing these values enhances maintainability, ensuring that level-specific configurations are easy to modify and consistently applied throughout the game.

5. EnemyLevelThree

As the game progress, the level is intended to get harder, EnemyLevelThree contains the enhanced behavious of a plane. I changed the health to 10 so it is harder to beat each Enemy.

6. LevelThreeConfig

This class contains configuration constants for Level 3 of the game, including the background image, the number of enemies, and the conditions required to advance to the next level. It also defines the player's initial health and the enemy spawn probability. Additionally, the class includes custom bounds for enemy spawning, specifying the minimum and maximum Y positions for enemy placement, offering flexibility in controlling enemy spawn locations while ensuring a consistent gameplay experience.

7. UserShield

As the enemy in level two has a shield to protect itself. A shield is rewarded to the user in Level Three to protect itself. The shield is activated for 7 seconds. After that, user has to wait another 5 seconds to use the shield again. This is implemented by the methods in LevelThree called deactivateShieldAfterTimeout and waitForShieldCooldown. During collision, Collision handler skips the actor when isShieldAllowed is true

8. LevelChallenge

This level is a competitive level for users to beat their highest score. There is no limit to the kill count. Users play this level until they lose. The kill count method is removed from this class.

9. ChallengeConfig
 
This class defines configuration constants for the game's challenge mode, including the background image, enemy spawning parameters, and player health. It sets the total number of enemies, their spawn probability, and the kill threshold required for progression. Additionally, it specifies the UI layout for displaying the score, including position and styling. The class encapsulates all challenge-specific settings to enhance code readability and reuse, while preventing instantiation to maintain its role as a purely static configuration utility. 

10. Scoreboard

This class allows users to record the highest Score. Only the top 3 scores are shown in a text file called Scores.txt. It is then displayed on the screen using displayScoreBoard in UIManager.java

11. ExplosionImage

To enhance the user experience, I have added an explosion image for each time a enemy or user is destroyed. This would allow clarity in seeing the kills of enemies. The `ExplosionImage` class creates and manages explosion visuals in the game, inheriting from `ImageView` to handle the display of an explosion image at specified coordinates. It encapsulates the explosion image's size, positioning, and resource loading in a single constructor, ensuring reusability and modularity. By centralizing the explosion's configuration  and maintaining a clear, single-purpose design, the class adheres to good coding practices, improving readability and making it easy to update or extend.

12. Actor Manager

This class efficiently handles game entities like the player, enemies, and projectiles by managing updates, collisions, and removals, while adding visual effects like explosions for destroyed enemies. Its modular design centralizes these responsibilities, simplifying implementation and maintenance. This approach improves extensibility, allowing easy addition of new entities or behaviors while keeping the game logic organized and scalable.

13. UIManager

Used specifically for displaying UI elements like killCount, ScoreText and Scoreboard as well. 
This class has a single responsibility design which is to display text.

14. PauseScreen

This class implements a robust pause system for a game, allowing players to pause, resume, retry, quit, or navigate to the level selection screen. It dynamically scales UI elements, including buttons and overlays, based on the stage dimensions, ensuring adaptability across different screen sizes. A `Pause` button pauses the game and overlays a visually appealing pause screen using a `StackPane` with a background, buttons, and an image. Each button (Resume, Retry, Quit, Levels) is designed with specific actions, like resuming gameplay or returning to the home screen. The code effectively separates concerns, uses clear methods for modularity, and ensures smooth transitions between game states, enhancing user experience.




## Modified Java Classes

1. Naming of Java Classes
- controller to GameController, to indicate a specific controller as there are multiple controllers in the project
- EnemyLevel to EnemyLevel1, to indicate the enemy used in Level 1
- 

2. Main.java 
The new `Main` class shifts from a fixed-size windowed application to a dynamic, full-screen, undecorated JavaFX application. Unlike the old version, which set a static resolution (`1300x750`) and included a standard window bar, the new version uses `StageStyle.UNDECORATED` to remove the window bar and dynamically scales the application's width and height to match the user's screen dimensions using `Screen.getPrimary()`. Additionally, it leverages an `FXML` file (`HomeScreen.fxml`) for layout management and binds the `AnchorPane` dimensions to the scene's width and height, ensuring responsive design. These changes emphasize flexibility and enhance the visual presentation of the application in full-screen mode.

3. GameController

Previously known as Controller. The `GameController` class introduces key improvements over the old `Controller` class, such as dynamic level handling where the level class is passed as a parameter, allowing for more flexible game management. It also includes improved error handling with a dedicated `showErrorAlert` method for better user feedback, and a retry mechanism is added through a "Retry Level" button, enabling players to restart a level after losing. Furthermore, the new class supports a more general approach to launching levels, making it adaptable to different game stages, while the old class was limited to a single, hardcoded level. These changes enhance the game's scalability and user experience.

4. LevelOne

The  `LevelOne` class is different from the old one, primarily in how it manages enemies, user interface elements, and general configuration. In the new class, the `LevelOne` constructor accepts additional parameters, such as the `GameController`, making it more flexible for integration with different levels and controllers. It also utilizes a constant configuration for the kill count text positioning and style, dynamically positioning it based on screen size with the `positionKillCountText` method. The enemy spawning logic is updated to use a random initial Y position within a defined range (`getEnemyMaximumYPosition()`) and spawns `EnemyLevel1` units, whereas the old class spawned `EnemyPlane` units with a fixed spawn probability. Additionally, the new version includes a more modular approach to initializing friendly units and setting up the level view, whereas the old version uses static values for background and next level transitions. These changes result in a more flexible and maintainable design that supports future expansions and modifications.

5. Boss.java
The `Boss` class introduces several enhancements compared to the old version. Firstly, it is now tied to the `LevelViewLevelTwo` class, allowing it to update the shield's position dynamically based on the boss’s movement, whereas the old class had a simpler setup without this visual connection. The new version also improves the boss's movement pattern by shuffling the vertical movement directions, ensuring a more dynamic and unpredictable movement. Additionally, the shield mechanics have been enhanced with more control, such as activating the shield conditionally and managing its duration with `MAX_FRAMES_WITH_SHIELD`. The new class also includes more robust functionality for managing the boss's projectile firing, including the ability to fire in random frames, and uses a more complex initialization of the move pattern. The `takeDamage` method is now shield-aware, preventing damage when the shield is active. Furthermore, the `Boss` class in the new version features better encapsulation with methods like `updateShieldPosition` and `updateShield`, offering clearer control over the shield's lifecycle and position relative to the boss. These changes result in a more sophisticated and visually interactive boss character, with improved gameplay mechanics such as shield activation and position updates.

6. Shield.java

The new `ShieldImage` class improves upon the old version by dynamically loading the shield image using `getClass().getResource()`, making it more flexible and adaptable to changes in the project structure. It also uses clearer naming for the shield's dimensions (`SHIELD_WIDTH` and `SHIELD_HEIGHT`) and allows dynamic positioning with the `showShield` method, providing more control over its placement. The old version had a fixed image path and size, limiting flexibility. Additionally, the new class prints the shield’s position when shown, which helped me in debugging.

7. LevelViewLevelTwo

The updated `LevelViewLevelTwo` class provides more control over the shield's position and visibility. The `showShield(double x, double y)` method positions the shield at specified coordinates and ensures it is only added to the root once. The `updateShieldPosition(double x, double y)` method allows real-time adjustments to the shield's location, such as following the boss’s movement. This approach enhances flexibility and efficiency in managing the shield's display, improving gameplay by ensuring the shield is properly placed and updated when needed.


8. ActiveActorDestructible

The changes in the new `ActiveActorDestructible` class mainly involve the reorganization of the package structure. The core functionality remains the same: the class extends `ActiveActor` and implements the `Destructible` interface. It includes a `destroy()` method that sets the `isDestroyed` flag to true, signaling that the actor is destroyed. Additionally, it has an abstract method `updateActor()` and a concrete method `updatePosition()`. The key difference in the new version is the move to a more specific package which is com.example.demo.LevelCommonElements.Actor`, which is better organization for the project.

9. LevelParent.java

The `LevelParent` class in the updated package `com.example.demo.Levels` manages the core logic of the game level, including scene initialization, game loop, collision detection, and actor management. It uses a `Timeline` for continuous updates (every 50 milliseconds) that handle the movement, firing, and destruction of both friendly and enemy units. The class also includes detailed methods for projectile generation, handling collisions between actors (planes and projectiles), and updating the game state after each event.

Key features include: 
- scene Initialization**: The class sets up the background, friendly units, and pause screen. It uses the `initializeScene()` method to prepare the stage, showing the user’s health and incorporating the `LevelView` for visual representation of health.
- **Game Loop**: The `timeline` object runs the game loop, calling `updateScene()` every 50 milliseconds. This method updates the position of all units, spawns new enemies, and checks for game-over conditions.
- **Collision Detection**: The game has dedicated methods (`handlePlaneCollisions()`, `handleUserProjectileCollisions()`, `handleEnemyProjectileCollisions()`) to check for intersections between different types of actors. If a collision occurs, both actors take damage, and score is deducted if the player’s plane is hit.
- **Projectile Management**: The class allows the user to fire projectiles via the `fireProjectile()` method. It also spawns enemy projectiles and handles their removal when they hit a target or go off-screen.
- **Enemy Penetration**: The class checks if enemies pass beyond the screen boundary, triggering damage to the user’s plane.
- **Game Control**: The game can be paused, resumed, or stopped through `pauseGame()`, `resumeGame()`, and `stopGame()` methods. The class also integrates a method to handle level transitions (`goToNextLevel()`), including stopping the timeline when a level is won or lost. 
- **Scoreboard**: A `Scoreboard` object tracks and displays the player's score, updating every time a new enemy is destroyed.
- Additionally, the class interacts with the `UserPlane` and `FighterPlane` objects, ensuring correct projectile firing and damage handling. The user’s health is visually represented by a heart display, updated after each hit. Game-over and win conditions are checked, showing respective screens and offering a retry option. 

10. UserPlane

- The UserPlane class in the new package com.example.demo.User is an updated version of the class that represents the player's plane in the game. It builds upon the old version by introducing features such as the ability to manage a shield and handle user input for shield activation. Here's a detailed comparison of the updates made in the new implementation:

Key Differences and Updates:
- Shield Mechanism:

- Introduces the UserShield object, allowing the user to activate and deactivate the shield by pressing the Enter key. This feature is encapsulated in the UserPlane class, and the shield's position is updated every time the plane moves.

- The UserPlane class listens for key events and toggles the shield's activation state when the Enter key is pressed (handleKeyPress method). It also delegates shield updates to the UserShield object.

Updated Movement Boundaries:

-The upper Y-bound is set to 70, and the lower Y-bound is 750.0, which defines the vertical limits within which the plane can move.

- Projectile Positioning:
The X-position for the projectile is set to 180, with a Y-offset of 30 when fired.

- Shield Management:
The UserPlane class has methods to add the shield to the scene (addShieldToScene) and manage whether the shield is allowed (setShieldAllowed).

Code Structure and Improvements:
New Version: The shield logic is modularized into the UserShield class, which keeps the UserPlane class focused on handling the plane's movements, firing, and kill count. The event handling logic for the Enter key makes the shield feature responsive to user input.
The old class is more straightforward, with simpler movement and firing mechanics but no advanced features like shield management.



## Unexpected Problems
1. Maven issue
fixed with new pom.xml file












