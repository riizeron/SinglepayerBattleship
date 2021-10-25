package battleship.models;

import battleship.enums.ShipType;

import java.util.Objects;

public class Fleet {
    private final Ship[] ships;
    private BattleField battleField;
    private int torpedoesAmount;

    /**
     * Fleet constructor.
     *
     * @param args game parameters.
     * @throws Exception throw due to incorrect game parameters.
     */
    public Fleet(String[] args) throws Exception {
        setShipParams(args);
        ships = new Ship[ShipType.getWholeAmount()];
        torpedoesAmount = Integer.parseInt(args[args.length - 2]);
        if (torpedoesAmount > ships.length) {
            throw new Exception("torpedoes amount mustn't be bigger than ship amount");
        }
    }

    /**
     * Getting the ship that just been hit.
     *
     * @param x the x coordinate of the ship.
     * @param y the y coordinate of the ship.
     * @return the hit ship.
     */
    public Ship getHitShip(int x, int y) {
        for (Ship sh : getShips()) {
            for (Coordinate c : sh.getCoordinates()) {
                if (c.x == x && c.y == y) {
                    return sh;
                }
            }
        }
        return new Ship();
    }

    /**
     * Getting an amount of remaining torpedoes.
     *
     * @return the amount of torpedoes.
     */
    public int getTorpedoesAmount() {
        return torpedoesAmount;
    }

    /**
     * If a torpedo was used, so its amount decrease in one point.
     */
    public void spendTorpedo() {
        torpedoesAmount--;
    }

    /**
     * Setting a BattleField object for this Fleet object.
     *
     * @param bf instance of BattleField class.
     */
    public void setBattleField(BattleField bf) {
        battleField = bf;
    }

    /**
     * Parsing a game's parameters into the Fleet object.
     *
     * @param args game parameters.
     * @return the Fleet object.
     * @throws Exception throws in two ways
     *                   First it is about incorrect game parameters that make impossible
     *                   to parse Shing array elements into integer.
     *                   Second it is about exceeding the maximum allowable torpedoes amount.
     */
    public static Fleet tryParseCommandLineParams(String[] args) throws Exception {
        try {
            return new Fleet(args);
        } catch (Exception ex) {
            if (Objects.equals(ex.getMessage(), "torpedoes amount mustn't be bigger than ship amount")) {
                throw ex;
            }
            throw new Exception("Incorrect command line parameters");
        }
    }

    /**
     * Setting an amount of every ship type.
     *
     * @param args game parameters.
     * @throws NumberFormatException throws if it's unable to parse String array elements into integer.
     */
    private void setShipParams(String[] args) throws NumberFormatException {
        int i = 0;
        for (ShipType sh : ShipType.values()) {
            sh.setAmount(Integer.parseInt(args[i + 2]));
            i++;
        }
    }

    /**
     * Getting an array of ships of the fleet.
     *
     * @return the ship array.
     */
    public Ship[] getShips() {
        return ships;
    }

    /**
     * Creating of a ship array of the fleet.
     * Then summoning method that set coordinates for every ship of the fleet.
     * If it is impossible this method notify us about it and offer us to input game parameters again.
     *
     * @return true if it is possible to set Fleet, false otherwise.
     */
    public boolean trySetFleet() {
        int j = 0;
        for (ShipType sh : ShipType.values()) {
            for (int i = 0; i < sh.getAmount(); i++) {
                ships[j++] = new Ship(sh);
            }
        }
        if (!trySetFleetCoordinates()) {
            System.out.println("Unable to set coordinates");
            System.out.println("Input another parameters");
            return false;
        }
        return true;
    }

    /**
     * Setting the coordinates of all ships in the fleet.
     * If the number of attempts to set the coordinates is exceeded
     * the deployment of the fleet is declared impossible.
     *
     * @return true if ships were placed in the ocean
     * false if they weren't.
     */
    public boolean trySetFleetCoordinates() {
        // Максимальное число попыток
        int maxIteration = 100;
        for (int i = 0; i < ships.length; i++) {
            if (!ships[i].trySetShipCoordinates(battleField)) {
                maxIteration--;
                i = -1;
                battleField.clearMatrix();
            } else {
                battleField.changeMatrixAfterAddingShips(ships[i].getCoordinates());
            }
            if (maxIteration == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checking are the whole fleet was sunken.
     *
     * @return true if you sunk every ship
     * false in other way.
     */
    public boolean areAllShipsSunk() {
        for (Ship sh : ships) {
            if (!sh.isSunk()) {
                return false;
            }
        }
        return true;
    }
}
