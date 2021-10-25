package battleship.models;

import battleship.utils.ArrayUtils;

import java.util.Objects;
import java.util.Scanner;

public class Game {
    private BattleField battleField;
    private Fleet fleet;
    private boolean isRecoveryModEnabled;
    private boolean isTorpedoesEnabled;
    private int attackCount = 0;
    private Ship lastAttackedShip;
    private String[] gameParams;
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Game constructor.
     * Attempts to create an object will continue until the correct game parameters are entered.
     * This method catches all errors that may occur in the constructors
     * of other objects during their initialization due to incorrect game parameters.
     *
     * @param args game parameters.
     */
    public Game(String[] args) {
        boolean flag = true;
        while (flag) {
            gameParams = getCommandLineParams(args);
            try {
                battleField = BattleField.tryParseCommandLineParams(gameParams);
                fleet = Fleet.tryParseCommandLineParams(gameParams);
                fleet.setBattleField(battleField);
                isRecoveryModEnabled = Objects.equals(gameParams[8], "on");
                isTorpedoesEnabled = !Objects.equals(gameParams[7], "0");
                flag = false;
                printBeginningText();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println("please, try one more time");
            }
        }
    }

    /**
     * Parsing command line input parameters.
     * If there is nothing to parse or the data is incomplete, then the missing
     * parameters are read from the console.
     * Ultimately, an array of game parameters is returned.
     *
     * @param args command line parameters.
     * @return an array of game parameters
     */
    private String[] getCommandLineParams(String[] args) {
        if (args == null) {
            args = new String[0];
        }
        if (args.length == 9) {
            return args;
        } else if (args.length == 7) {
            System.out.print("Enter torpedoes amount (0 if there are no torpedoes): ");
            String torpedoesAmount = scanner.nextLine();
            System.out.print("Type 'on' to enable ship recovery mod (or something else in other way): ");
            String recoveryMod = scanner.nextLine();
            args = ArrayUtils.concatenate(args, new String[]{torpedoesAmount, recoveryMod});
        } else if (args.length == 5) {
            System.out.print("Enter field size separated by space:");
            String[] sizeField = scanner.nextLine().split(" ");
            System.out.print("Enter torpedoes amount (0 if there are no torpedoes): ");
            String torpedoesAmount = scanner.nextLine();
            System.out.print("Type 'on' to enable ship recovery mode (or something else in other way): ");
            String recoveryMod = scanner.nextLine();
            args = ArrayUtils.concatenate(sizeField,
                    ArrayUtils.concatenate(args, new String[]{torpedoesAmount, recoveryMod}));
        } else if (args.length == 2) {
            System.out.println("Field size - " + args[0] + "на" + args[1]);
            System.out.print("Enter the amount of ships of each type separated by a space:");
            String[] shipsCount = scanner.nextLine().split(" ");
            System.out.print("Enter torpedoes amount (0 if there are no torpedoes): ");
            String torpedoesAmount = scanner.nextLine();
            System.out.print("Type 'on' to enable ship recovery mode (or something else in other way): ");
            String recoveryMod = scanner.nextLine();
            args = ArrayUtils.concatenate(args,
                    ArrayUtils.concatenate(shipsCount, new String[]{torpedoesAmount, recoveryMod}));
        } else if (args.length == 0) {
            System.out.println("you can type 'exit' to leave the game");
            System.out.print("Enter the size of the field, separated by a space:");
            String[] sizeField = scanner.nextLine().split(" ");
            if (Objects.equals(sizeField[0], "exit")) {
                System.exit(0);
            }
            System.out.print("Enter the amount of ships of each type separated by a space:");
            String[] shipsCount = scanner.nextLine().split(" ");
            System.out.print("Enter torpedoes amount (0 if there are no torpedoes): ");
            String torpedoesAmount = scanner.nextLine();
            System.out.print("Type 'on' to enable ship recovery mode (or something else in other way): ");
            String recoveryMod = scanner.nextLine();
            args = ArrayUtils.concatenate(sizeField,
                    ArrayUtils.concatenate(shipsCount, new String[]{torpedoesAmount, recoveryMod}));
        } else {
            System.out.println("Incorrect amount of console parameters");
            System.out.println("Try to input it through console");
            return getCommandLineParams(new String[0]);
        }
        return args;
    }

    /**
     * Printing an introductory text with information about game parameters.
     */
    public void printBeginningText() {
        System.out.println("GAME PARAMETERS: ");
        System.out.println();
        System.out.println("Field size - " + gameParams[0] + " " + gameParams[1]);
        System.out.println("Amount of:");
        System.out.println("\tCarriers\t-\t" + gameParams[2]);
        System.out.println("\tBattleships\t-\t" + gameParams[3]);
        System.out.println("\tCruisers\t-\t" + gameParams[4]);
        System.out.println("\tDestroyers\t-\t" + gameParams[5]);
        System.out.println("\tSubmarines\t-\t" + gameParams[6]);
        System.out.println();
    }

    /**
     * Reading game command method.
     * Depending on what command was entered the various methods will summon.
     */
    public void readCommand() {
        showField();
        System.out.println("type 'help' if you unfamiliar");
        System.out.print("Enter command: ");
        String[] command = scanner.nextLine().split(" ");
        switch (command[0]) {
            case "attack" -> {
                tryAttack(command);
            }
            case "show_fleet" -> {
                System.out.println("Fleet");
                showFleet();
            }
            case "exit" -> {
                System.out.println("Exit......");
                System.exit(0);
            }
            case "help" -> {
                showCommands();
            }
            default -> System.out.println("Incorrect command");
        }
    }

    /**
     * Method to start the game
     * Summon trySetFleet method.
     */
    public void start() {
        System.out.println("Ships go out to sea......");
        if (!fleet.trySetFleet()) {
            getCommandLineParams(new String[0]);
        }
    }

    /**
     * In this method, an attack is called if the parameters are correct.
     * Otherwise, there will be no attack, and an error message will be displayed.
     *
     * @param attackParams in fact, this is the same game command line.
     */
    private void tryAttack(String[] attackParams) {
        if (Objects.equals(attackParams[1], "-T")) {
            if (isTorpedoesEnabled && fleet.getTorpedoesAmount() != 0) {
                try {
                    int x = Integer.parseInt(attackParams[2]);
                    int y = Integer.parseInt(attackParams[3]);
                    attackTorpedo(x - 1, y - 1);
                } catch (Exception ex) {
                    System.out.println("Incorrect attack command");
                }
            } else {
                System.out.println("no torpedoes available");
            }
        } else {
            try {
                int x = Integer.parseInt(attackParams[1]);
                int y = Integer.parseInt(attackParams[2]);
                attack(x - 1, y - 1);
            } catch (Exception ex) {
                System.out.println("Incorrect attack command");
            }
        }
    }

    /**
     * Main attack method.
     *
     * @param x the x coordinate of matrix.
     * @param y the y coordinate of matrix.
     * @return true if you are hit,
     * false otherwise.
     */
    public boolean attack(int x, int y) {
        if (x < battleField.sizeX && x >= 0 && y < battleField.sizeY && y >= 0) {
            attackCount++;
            int item = battleField.matrix[x][y];
            if (item == 9) {
                System.out.println("Hit");
                battleField.matrix[x][y] = 1;
                lastAttackedShip = fleet.getHitShip(x, y);
                lastAttackedShip.hit();
                if (lastAttackedShip.isSunk()) {
                    System.out.println("You just have sunk a " + lastAttackedShip);
                    battleField.changeMatrixAfterSunk(lastAttackedShip.getCoordinates());
                    if (fleet.areAllShipsSunk()) {
                        win();
                    }
                }
                return true;
            } else if (item == 2 || item == 1 || item == 3) {
                System.out.println("You already strike this cell");
            } else {
                // Если recovery mode включен, предыдущий корабль не был потоплен,
                // вы сначала попали по кораблю, а потом промахнулись, то он восстановится
                if (isRecoveryModEnabled && lastAttackedShip != null && !lastAttackedShip.isSunk()) {
                    battleField.changeMatrixAfterRecover(lastAttackedShip.getCoordinates());
                    lastAttackedShip.recover();
                    System.out.println("Miss");
                    System.out.println("Due to your missing the ship " + lastAttackedShip + " was recover");
                } else {
                    System.out.println("Miss");
                    battleField.matrix[x][y] = 2;
                }
            }
        } else {
            System.out.println("Incorrect coordinates!");
            System.out.println("Try one more time");
        }
        return false;
    }

    /**
     * Torpedo attack method.
     * If our shot was successful, that is, the main attack method is return true,
     * then the attacked ship will sink.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    private void attackTorpedo(int x, int y) {
        fleet.spendTorpedo();
        if (attack(x, y)) {
            Ship sh = fleet.getHitShip(x, y);
            sh.sunk();
            battleField.changeMatrixAfterSunk(sh.getCoordinates());
            System.out.println("You just have sunk a " + sh);
            if (fleet.areAllShipsSunk()) {
                win();
            }
        }
    }

    private void win() {
        showField();
        System.out.println("Congratulations!!");
        System.out.println("You win!");
        System.out.println("You was needed in " + attackCount + " attacks");
        System.exit(0);
    }

    private void showField() {
        System.out.println("Field");
        System.out.println(battleField);
    }

    private void showFleet() {
        System.out.println(battleField.toHackString());
    }

    private void showCommands() {
        System.out.println("Available commands:");
        System.out.println("attack x y\t\t-\tcause attack to cell x y");
        System.out.println("attack -T x y\t\t-\tcause torpedo attack to cell x y");
        System.out.println("exit\t\t-\tleave game");
        System.out.println("help\t\t-\tget look at the available commands");
        System.out.println();
    }
}
