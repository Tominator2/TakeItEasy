Take It Easy
============

This is some Java code I originally wrote back in 2009 to search for optimal solutions to the numbered tile game "Take It Easy".  Someone had told me there were 12 solutions with the maximum score of 350 points and so I thought I would automate the hunt for them.

The game consits of 27 hexagonal tiles each of which has 3 numbers and three coloured bars on it as shown in the (2009 quality) image below:

![pieces](https://cloud.githubusercontent.com/assets/4344677/7878679/3a773bb8-0613-11e5-8e99-03957f14376d.jpg)

Each player has a set of tiles and a hexagonal board  which can hold 19 tiles (see the image below).  During the game one player chooses a random tile which all players must then find and place on their boards.  

![board](https://cloud.githubusercontent.com/assets/4344677/7878677/3781818e-0613-11e5-914b-b9610d447b59.jpg)

Once the board has been filled then each player calculates their score.  You only receive points for coloured lines that make it completely from one side of the board to the other in any of the vertical or diagonal directions (let's call them "down left", and "down right"). 

It turns out [the maximum score is 307 points and there are 16 different boards](https://boardgamegeek.com/thread/399114/high-score-challenge-can-you-get-maximum-307-point).  An example run of the program is contained in the file [solutions.txt](https://github.com/Tominator2/TakeItEasy/blob/master/solutions.txt).

# Code

The [Tile class](https://github.com/Tominator2/TakeItEasy/blob/master/Tile.java) represents a single tile and the board is represented by a 2D array that describes which pieces are neighbours on each side of the 19 board tiles in the file [TakeItEasy.java](https://github.com/Tominator2/TakeItEasy/blob/master/TakeItEasy.java).

The program starts by trying each of the 27 tiles in the 19 positions on the board. When a piece is added to the board any blank neighbours for this tile are added to a queue to be processed. When each blank neighbour space is processed a tile is chosen at random from a list of possible tiles with matching values.  The scores of complete boards are calculated and any unique boards with a value greater than 306 are written to standard output.

The code could certainly be more efficient and elegant but at the time I was only interested in finding the maximal solutions and this does the trick.
