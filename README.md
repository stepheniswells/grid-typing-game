# grid-typing-game
Test your typing abilities by racing around a grid of letters, battling an enemy NPC at collecting treasures against the clock!  

## Rules and Gameplay

* Navigate to tiles adjacent to you (the green tile) by typing their displayed letter (you cannot move diagonally)
* On game start, the clock ticks down, if it reaches zero, you lose
* Collect treasure (blue tiles) to score points AND give yourself more time
* The enemy NPC (red tile) tries to get to the treasure first, denying you precious time

## Features 

### 2D Grid of Randomly Generated Letters
The game map is unique each time, and generated such that no tile can have two or more neighboring tiles with the same letter value. This ensures the player always has 4 distinct choices.

### Game Timer
Once you start typing the timer begins, putting the pressure on to collect treasures

### Enemy NPC
The red tile moves efficiently around the grid, vying for control of the treasure to make your struggle with the clock even harder. The enemy NPC is unphased by your green tile and can move freely underneath it - there are no collisions between enemy and player tiles. 

### End Game screen
When your clock strikes zero, you are presented with the option to quit or to press on without the pressure from the clock, and simply race the NPC to each treasure.

## Built With

* Java
* [JavaFX](https://openjfx.io/) - GUI library for desktop applications

## Roadmap
Ideas for the future: 
* Pause/Resume game
* Difficulty settings, possibly a slider
* Starting menu
* Ability to restart game

## Demo Billboards
![Game Start](https://imgur.com/a/ZCbGcJ6g)

![Game Play](https://imgur.com/a/qAjXAdW)

![Game End](https://imgur.com/a/teNmimo)







