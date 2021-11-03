# GAME INSTRUCTION

First, I'll tell you about the command line input parameters.

If there are no parameters, then all 9 pieces will be set via the console with knobs.

If there are 2 parameters, the size of the ocean, then the missing 7 are set via the console.

If there are 5 parameters, then they are taken as the parameters of the fleet, that is, they set the number of ships of each type. In this case, the player will need to add 4 parameters to the console, the size of the field and all sorts of game modifications, both of which I have, between a word, implemented.

If there are 9 parameters, that is, everything, then everything is ok, you will not have to enter anything from the console.

And if there is only one parameter and it looks something like this - 'exit', then an exit occurs.

Next, checks will be carried out for these parameters and if they turn out to be incorrect, then the program politely, in English, will ask the player to set them again, but completely and completely through the console.

!!! IMPORTANT !!!
From the beginning we select the row number - x, and then the column - y.

Now let's move on to the commands:

- attack x y - well, that's understandable, the usual attack on the cell (x y)
- attack -T x y - attack with a torpedo
- show_fleet - shows the arrangement of ships in the ocean. In the help, I did not indicate this, because this command is more for testing than for playing.
- help - displays help for commands
- exit - it's clear, exit)
