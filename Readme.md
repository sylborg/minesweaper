# eveCharacter jQuery Popover

This is a command line prompted minesweeper game (the famous windows minus flag) done in java.
Feel free to improve this basic version

## Documentation

### Game steps:

When the game starts, the player gets prompted for the grid size (width x height) and the number of mines.
The grid is generated according to these requirements.
When the games starts, the user is prompted for the coordinates (x, y) of the first cell to uncover.
The game shows the resulting grid and prompts for new coordinates.
And so on ...
The game ends when there is no more non-mined cell to uncover or the player uncovers a mine.

### Rules

Uncover a mine, and the game ends.
Uncover an empty cell, and the player keeps playing.
In each empty uncovered cell the number of adjacent cells holding mines is displayed.
Uncovering a cell holding the number 0 (i.e. no adjacent mined cells) uncovers all adjacent cells, and so on.

### Example

   ##  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 ##
1  ##  -  -  1  0  0  0  1  1  1  0  0  0  0  0  0  0  0  0  0  0 ##
2  ##  1  1  1  0  0  0  1  -  2  1  0  0  0  0  0  0  0  0  0  0 ##
3  ##  0  0  0  0  0  0  1  2  -  1  0  0  0  0  0  0  0  0  0  0 ##
4  ##  0  0  0  0  0  0  0  1  1  1  0  0  0  0  0  0  0  0  0  0 ##
5  ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
6  ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
7  ##  0  0  0  0  0  1  1  1  0  0  0  0  0  0  0  0  0  0  0  0 ##
8  ##  0  0  0  0  0  1  -  1  0  0  0  0  0  0  0  0  1  1  1  0 ##
9  ##  0  0  0  0  0  1  1  1  0  0  0  0  0  0  0  0  1  -  1  0 ##
10 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  1  1  1  0 ##
11 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
12 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
13 ##  0  0  0  0  0  0  0  1  1  1  0  0  0  0  0  0  0  0  0  0 ##
14 ##  0  0  0  0  0  0  0  1  -  1  0  0  0  0  0  0  0  0  0  0 ##
15 ##  0  0  0  0  0  0  0  1  1  1  0  0  0  1  1  1  0  0  1  1 ##
16 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  1  -  1  0  0  1  - ##
17 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  1  1  1  0  0  1  1 ##
18 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
19 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
20 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
21 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
22 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
23 ##  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 ##
24 ##  0  0  0  0  0  0  0  0  0  0  1  1  1  0  0  0  0  0  1  1 ##
25 ##  0  0  0  0  0  0  0  0  0  0  1  -  1  0  0  0  0  0  1  - ##
   ##  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 ##

### Usage

Launch and follow the prompted instructions

## Licence

Licensed under the [MIT license](http://en.wikipedia.org/wiki/MIT_License).