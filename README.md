Take It Easy
============

This is some Java code I originally wrote back in 2009 to search for optimal solutions to the numbered tile game "Take It Easy".  Someone had told me there were 12 solutions with the maximum score of 350 points and so I thought I would automate the hunt for them.

The game consits of 27 hexagonal tiles each of which has 3 numbers and three coloured bars on it as shown in the (2009 quality) image below:

![pieces](https://cloud.githubusercontent.com/assets/4344677/7878679/3a773bb8-0613-11e5-8e99-03957f14376d.jpg)

I wrote a [Tile class]() that represents the individual tiles.

Each player has a set of tiles and a hexagonal board  which can hold 19 tiles (see the image below).  During the game one player chooses a random tile which all players must then find and place on their boards.  

![board](https://cloud.githubusercontent.com/assets/4344677/7878677/3781818e-0613-11e5-914b-b9610d447b59.jpg)

Once the board has been filled then each player calculates their score.  You only receive points for coloured ines that make it from one side of the board to the other in the vertical and two diagonal directions (let's call them "down left", and "down right"). 
