
# Nicolas Cage Space Simulator #


**Nicolas Cage Space Simulator**  is a simple game project written in Java8 based on the *JavaFX* library, and has been thinked for the worst famous actor ever, Nicolas Cage.

This game had to be similar to [GalCon](https://store.steampowered.com/app/294160/Galcon_2_Galactic_Conquest/).


## Prerequisites ##

The Java 8 had to be install on your computer.
Usable either on Windows or any Unix-based systems.

JUnit 5 is required to execute tests.

## Download & Install ##

The project's source is available on GitHub under GPL's license:

### Download the release .jar file
Check the differents releases tag available on the github repository :
```
  https://github.com/K1ntus/Projet-Orientee-Objet
```
If you want a specific version, feel free to check the different release tag.


### Execute the .jar file.

* Console way
```
  $ java -jar ./NCSS
```
*  or make it executable
```
  $ chmod +x NCSS
```
Then, just double-click it in your favorite file manager

# How to play #

 There's multiple faction in this game. The planets you're controlling are shown with a specific color (something between Blue and Green). 

Those commands are only available while playing and not in the game menu.

 1.  **Send troups between two planets**
```
You just had to Drag & Drop from your source planet to the one you want to send them
```

2. **Select the percent of troups to send from a planet**
```
Use your mouse scroll, the current percentage is displayed in the top-left corner
```

3. **Saving**
```
Pressing F5 save the current game state in a file.
```

4. **Loading**
```
Pressing F6 load the latest game state and apply it to your current game.
```

4. **Quit the game**
```
At any time while playing a game, you can press "Escape" button to exit the game.
In the main menu, feel free to use the appropriate buttons.
```

## Additional Game Informations ##

At startup, you will begin in a *main menu*. From there, you can directly play with the default settings (recommended), or change them by pressing the "setting" button.

## Evenements ##

#### Sun ####
```
A neutral and powerful type of "planet". It's generated with a fixed garrison value.

When his value drops under 0, every ships on the board are removed and each planets see his garrison going back to the minimal value (but they keep the same ruler).
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

At the beginning of the game, every planets are "neutral" (ie. they never attack someone and don't produce garrison troup), but there's two planets which are automatically attributed. The first one to the human player, the other one to an Artificial Intelligence.

Each planets have specific parameters, that affect the ships caracteristics:
* ***Produce speed***: The number of ships produced/seconds
* ***Ship's speed***: The speed(in pixels) of a ship on board.
* ***Ship's power***: The damage dealt by a ship when it reach an hostile destination

To win the 'round', you had to send fleet to other planets in order to get them.
* You win if there's no other aggressive player.
* You lose if you don't have any planets and fleets on board anymore.

When one ship reach is destination, few situations can happens :
* The ship and destination have the same ruler, then the planet garrison got incremented by 1.
* The ship and destination planet *DON'T* have the same ruler:
	* His garrison drops below 0, this planet have a new ruler.
	* His garrison is greater than 0, then it get decremented by the ship power.



## Known Bugs ##
* Board Generation
	* A problem when you are generating the game board which others gui size than default can happens because the generator do not find any convenient board 'ie. at least 2 planets)
	* Generation time can take a long time because of his exponential complexity. Check for another algorithm, probably just check the distance between the center of each others planets
		* Problem should be fixed, more test should be conducted to confirm that.
* Display
	* Currently unable to display animated image (like GIF) while playing because it's getting override by the background rendering. May be looking for thread running frames per frame, or superposition of canvas.
*  Ships
	* Sometimes, they do not really lift-off from the good planet side. Could be fixed by checking more point of each side, but it's increasing complexity so ...
	* Path of the ships aren't smooth. Look A* algorithm, or work with angle (some stuffs about that not finished yet)
* Planets
	* Should work to change the isInside() checking for others than SquaredPlanets
* Waiting
	* Find a way to make some loading screen (We did some trials using gif, without success), but there's mb the progressbar tools from javafx that could be used 

## Upgrade's Road-Map ##
* Update
	* Set-up an auto-update functionnality (easy by using a bootstrap, then injecting the Game .jar into this one when it's up-to-date)
* More event
* Players
	* Handle multiple faction. Currently just using 'constants', the User class should be ok like that, and just few things had to be corrected for that.
* Make this goddamn display of gif on board (explosion when a ship reach his destination) and loading gif.
* Choose the path and name of the file for saving/loading using a FileManager.

## Credits ##

* For the main music (Dolling) shared under 'Creatives Commons' by **CyberSDF**, thanks.

* **Some Sounds** Youtube Channel, for distribuing freely and without royalties the vocal 'boum' explosion sound

* **Nicolas Cage** giving us inspiration and motivation.

* **Eclipse** for his amazing shortcuts

* We don't have any right or sponsoring with CFC and the planet displayed there, or the tenders ships.

* We're sorry for not being able to find the original author of the Main Menu background animation.

## Documentation ##

  * A complete javadoc can be generated from the source code.
