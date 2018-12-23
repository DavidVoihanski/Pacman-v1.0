**Third assignment in object-oriented programming course, Ariel uni.**

**System structure:**

![first](https://user-images.githubusercontent.com/44900773/50386939-69e95200-06fa-11e9-889e-80cd275aa791.png)

in this UML diagram you can see the GUI related classes on the left side, game
related classes on the right side and the link between these two in the middle,
i.e. "Game" class and "Map" class, downward you can see the "GpsCoord" class
which used for operations performed on GPS coordinates, right- down side is
where the "ShortestPathAlgo" class found - which is the algorithm responsible
for pacman movement, i.e. the "movement simulation", and the helping class we
created called "paired"- represents a pair of fruit and a robot connected by a
path.

![second](https://user-images.githubusercontent.com/44900773/50386942-85545d00-06fa-11e9-94fb-1d0bed56c99c.png)

Furthermore, we got the "File_format" package which deals with converting game
to CSV/KML file types and loading CSV file's to create a game

**About the move algorithm:**

"shortest path algorithm" is the class which contains the algorithm responsible
for pacman – toward fruits movement.

as you could easily see – running CSV examples from the "config" directory,
these pacman's don’t "just" move towards the fruits "mindlessly", they ran by an
algorithm which designed to make the mission of eating all the fruit from the
screen efficient, i.e. without any not-needed pacman robots moving and with
greatest speed possible in a given situation.

NOTICE: this algorithm's efficiency hasn't been mathematically proven to be the
best for this kind of situation, the algorithm is based on a "greedy algorithm"
but has been improved in some aspects in order to correctly finish the CSV given
examples in the shortest time we could measure.

**So, how does the algorithm work?**

*pseudo code:*

1.  As long as there are fruits in the list:

2.  For every fruit, find the closest pacman time wise ("time" calculated as (3D
    distance)/(pacman's speed))

3.  Out of those we found in (2.) - find the closest pacman to a fruit –
    distance wise

4.  Pair these two we found in (3.)

5.  Add these two (from 4.) as a "paired" class instance to a "paired"
    collection

6.  Update pacman's "next target" and the time it will take it to get to the
    fruit (if there are more than one – last fruit)

7.  Remove the fruit from the list

8.  Back to (1.)

9.  After all fruits are "gone", build paths between all pairs

**How to run it?**


There is a class called "EntryPoint" in the GUI package, this is the only class
that contains a main method – which means this is the class to "run".

Now, as you are running it – it will open GUI's (MyFrame class) window:

![third](https://user-images.githubusercontent.com/44900773/50386945-943b0f80-06fa-11e9-90f4-9663f6692c93.png)

Now, we got 2 "game mode's"-

**Creating your own game**, which means placing fruits and pacmans on the screen
manually by clicking on the screen


![4th](https://user-images.githubusercontent.com/44900773/50386953-ac129380-06fa-11e9-8fe8-3bc4cfeb07f1.png)

After creating a game, you could save it as a CSV file which you can later load

![FIFTH](https://user-images.githubusercontent.com/44900773/50386957-bfbdfa00-06fa-11e9-901f-fe7607594861.jpg)

or you could just run the movement simulation and watch the pacman robots eat
your fruit

![6](https://user-images.githubusercontent.com/44900773/50386964-d2d0ca00-06fa-11e9-82c1-8c4059ece973.jpg)
Notice that pacmans run in "real time" based on their speed configuration.

In case you **don’t** want to run the game, you can always press "clear all" to
clan the screen (NOTICE: you can not(!) clear the screen while simulation is
running).

Now, our second "game mode": loading a default-pre-made game from the "config"
directory (or any other directory on your computer)


![7](https://user-images.githubusercontent.com/44900773/50386976-fdbb1e00-06fa-11e9-8ef4-3fc7196a9b9d.jpg)

You can load any of the six already built games (can be found on the "config"
directory), or you can load any of the games you saved.

**Saving as KML**

As the game is occurring on a "real life" map, every pacman / fruit in the game
has a "real life" GPS coordinate which describe it's location (every pixel on
the screen is converted to a GPS coordinate based on "where this pixel is on the
map").

This allows us saving games in KML

![8](https://user-images.githubusercontent.com/44900773/50386977-0dd2fd80-06fb-11e9-8aaa-eda5451c9b38.jpg)

And running these games in google earth, which will describe the game – pacman
moving through their paths and fruits getting eaten and disappear, as you can
see (KML based on the example you just seen)

![9](https://user-images.githubusercontent.com/44900773/50386980-1a575600-06fb-11e9-87bf-5695f5626e7c.jpg)

Now if we'll push the "time span" meter we will see the fruits disappear

![10](https://user-images.githubusercontent.com/44900773/50386985-24795480-06fb-11e9-8f8b-993db190e0a4.jpg)

