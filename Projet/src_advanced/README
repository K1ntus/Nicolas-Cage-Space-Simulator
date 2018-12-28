

# Nicolas Cage Space Simulator #


**Nicolas Cage Space Simulator**  is a simple game project written in Java8 based on the *JavaFX* library, and has been thought for the worst famous actor ever, Nicolas Cage.

This game had to be similar to [GalCon](https://store.steampowered.com/app/294160/Galcon_2_Galactic_Conquest/).


## Prerequisites ##

The Java 8 had to be install on your computer.
Usable either on Windows or any Unix-based systems.

 - *JUnit 5 is required to execute source-code tests.*

A minimal RAM amount of 4Go should be sufficient to play this game safely.

## Download & Install ##

The project's source is available on GitHub under GPL's license:

### Download the release .jar file
Check the different releases tag available on the github repository :
```
  https://github.com/K1ntus/Projet-Orientee-Objet
```
If you want a specific version, feel free to check the different release tag.


### Execute the .jar file.

* ***Console way***
```
  $ java -jar ./<jar_name>.jar
```
*  **or** make it ***executable***
```
  $ chmod +x <jar_name>.jar
```
Then, just double-click it in your favorite file manager

# How to play #

 There's multiple faction in this game. The planets you're controlling are shown with a specific color (something between Blue and Green). 

Those commands are only available while playing and not in the game menu.

 1.  **Send troops between two planets**
```
You just had to Drag & Drop from your source planet to the one you want to send them
```

2. **Select the percent of troops to send from a planet**
```
Use your mouse scroll, the current percentage is displayed in the top-left corner
```

3. **Saving**
```
Pressing F5 will open a file explorer. 
You can go wherever you want, but it will open on the default save directory.

Don't worry while saving your game. The game will wait you.
```

4. **Loading**
```
Press F6 to open a file navigator, and select your game save file.
```

4. **Quit the game**
```
At any time while playing a game, you can press "Escape" button to exit the game.
In the main menu, feel free to use the appropriate buttons.
```

## Additional Game Information ##

At startup, you will begin in a *main menu*. From there, you can directly play with the default settings (recommended), or change them by pressing the "setting" button.

## Evenements ##

#### Sun ####
```
A neutral and powerful type of "planet". 
It's generated with a fixed garrison value.

When his value drops under 0, every ships on the board are removed and each planets see his garrison going back to the minimal value (but planets keep the same ruler).
```
#### Rebellion ####
```
Can be summoned since you, or any other player have 2 or more planets.

A variable sized hostile fleet is sent from one of his planet and attack another one with the same ruler.
```
#### Pirate Assault ####
```
Few space pirate are randomly generated on board and attack randomly planets
```

#### Planet Sickness ####
```
Planet randomly see his planet productivity quantity drop for a while
```


## Rules ##

The goal of this game is to rule every planets of a galactic system. A board is generated with a maximum number of planets, and have at least 2 planets.

At the beginning of the game, every planets are "neutral" (ie. they never attack someone and don't produce garrison troop), but there's two planets which are automatically attributed. The first one to the human player, the other one to an Artificial Intelligence.

Each planets have specific parameters, that affect the ships characteristics:
* ***Produce speed***: The number of ships produced/seconds
* ***Ship's speed***: The speed(in pixels) of a ship on board.
* ***Ship's power***: The damage dealt by a ship when it reach an hostile destination

To win the 'round', you had to send fleet to other planets in order to get them.
* You win if there's no other aggressive player.
* You lose if you don't have any planets and fleets on board anymore.

When one ship reach is destination, few situations can happens :
* The ship and destination have the same ruler, then the planet garrison will get incremented by one.
* The ship and destination planet *DON'T* have the same ruler:
	* His garrison drops below 0, this planet have a new ruler.
	* His garrison is greater than 0, then it get decremented by the ship power.



## Known Bugs ##
* **Board Constants**
	* ~~Constants time can take a long time because of his exponential complexity. Check for another algorithm, probably just check the distance between the center of each others planets~~
* **Display**
	* Currently unable to display animated image (like GIF) while playing because it's getting override by the background rendering. May be looking for thread running frames per frame, or superposition of canvas.
*  **Ships**
	* ~~Sometimes, they do not really lift-off from the good planet side. Could be fixed by checking more point of each side, but it's increasing complexity so ...~~
	* Path of the ships aren't totally smooth. Look for A* algorithm, or work with angle (some stuffs using them, can be upgraded)
* **Planets**
	* Need to rework collision handler for round planets, and for any other possible shape of planets, ofc. The collision algorithm should be rework for that
* **Waiting**
	* Find a way to make some loading screen (We did some trials using gif, without success), but there's may be the progressbar tools from javafx that could be used 
* ~~The game exit if no playable could be generated, instead of another Constants try.~~

## Upgrade's Road-Map ##
* **Update**
	* Set-up an auto-update functionality (easy by using a java bootstrap, then injecting the Game .jar into this one when it's up-to-date)
* **More event**
* **Players**
	* ~~Handle multiple faction. Currently just using 'constants', the User class should be ok like that, and just few things had to be corrected for that.~~
* Make this goddamn display of gif on board (explosion when a ship reach his destination) and loading gif.
* ~~Choose the path and name of the file for saving/loading using a FileManager.~~
* **Achievement & Statistics**
	* Implement a system of achievement, and win/losses ratio for example. 
* Improved setting menu
	* Add more settings editable directly In-game, like the number of AI, the human color, ...

## Credits ##

* For the main music (Dolling) shared under 'Creatives Commons' by **CyberSDF**, thanks.

* **Some Sounds** Youtube Channel, for distributing freely and without royalties the vocal 'boom' explosion sound

* **Nicolas Cage** giving us inspiration and motivation.

* We don't have any right or sponsoring with CFC for the planet sprite, or the tenders ships.

* We're sorry for not being able to find the original author of the Main Menu background animation.

## Documentation ##

  * A complete javadoc can be generated from the source 
