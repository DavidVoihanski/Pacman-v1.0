hird assignment in object-oriented programming course, Ariel uni.

System structure:

in this UML diagram you can see the GUI related classes on the left side, game related classes on the right side and the link between these two in the middle, i.e. "Game" class and "Map" class, downward you can see the "GpsCoord" class which used for operations performed on GPS coordinates, right- down side is where the "ShortestPathAlgo" class found - which is the algorithm responsible for pacman movement, i.e. the "movement simulation", and the helping class we created called "paired"- represents a pair of fruit and a robot connected by a path.

Furthermore, we got the "File_format" package which deals with converting game to CSV/KML file types and loading CSV file's to create a game

About the move algorithm:

"shortest path algorithm" is the class which contains the algorithm responsible for pacman – toward fruits movement.

as you could easily see – running CSV examples from the "config" directory, these pacman's don’t "just" move towards the fruits "mindlessly", they ran by an algorithm which designed to make the mission of eating all the fruit from the screen efficient, i.e. without any not-needed pacman robots moving and with greatest speed possible in a given situation.

NOTICE: this algorithm's efficiency hasn't been mathematically proven to be the best for this kind of situation, the algorithm is based on a "greedy algorithm" but has been improved in some aspects in order to correctly finish the CSV given examples in the shortest time we could measure.

So, how does the algorithm work?

pseudo code:

    As long as there are fruits in the list:

    For every fruit, find the closest pacman time wise ("time" calculated as (3D distance)/(pacman's speed))

    Out of those we found in (2.) - find the closest pacman to a fruit – distance wise

    Pair these two we found in (3.)

    Add these two (from 4.) as a "paired" class instance to a "paired" collection

    Update pacman's "next target" and the time it will take it to get to the fruit (if there are more than one – last fruit)

    Remove the fruit from the list

    Back to (1.)

    After all fruits are "gone", build paths between all pairs

How to run it?

There is a class called "EntryPoint" in the GUI package, this is the only class that contains a main method – which means this is the class to "run".

Now, as you are running it – it will open GUI's (MyFrame class) window:

Now, we got 2 "game mode's"-

Creating your own game, which means placing fruits and pacmans on the screen manually by clicking on the screen

After creating a game, you could save it as a CSV file which you can later load

or you could just run the movement simulation and watch the pacman robots eat your fruit

Notice that pacmans run in "real time" based on their speed configuration.

In case you don’t want to run the game, you can always press "clear all" to clan the screen (NOTICE: you can not(!) clear the screen while simulation is running).

Now, our second "game mode": loading a default-pre-made game from the "config" directory (or any other directory on your computer)

You can load any of the six already built games (can be found on the "config" directory), or you can load any of the games you saved.

Saving as KML

As the game is occurring on a "real life" map, every pacman / fruit in the game has a "real life" GPS coordinate which describe it's location (every pixel on the screen is converted to a GPS coordinate based on "where this pixel is on the map").

This allows us saving games in KML

And running these games in google earth, which will describe the game – pacman moving through their paths and fruits getting eaten and disappear, as you can see (KML based on the example you just seen)

Now if we'll push the "time span" meter we will see the fruits disappear
