# Codewords
A java program for solving Codewords puzzles through console interface

# Method
This program scans the codewords puzzle, before using its dictionary to lazily find unsolved spaces with the fewest 
potential solutions that are real words. It will then branch off
of these and form a tree structure, where every new node is one of the many
potential solutions. It will continue this until no words remain unsolved.

# Issues

The UI currently does not support edits to input, requiring one to restart entirely if
 they enter data incorrectly, and the testing suite has poor coverage. Additionally, there is
no caching and subsequent updating of stored potential words for a specific location
on the grid. The hyperparameters used for when the parts of the grid need more
aggressive potential word evaluation are arbitrary, and need to be tested against
alternatives.

