package battleship.enums;

public enum ShipType {
    Carrier(5, 0),
    Battleship(4, 0),
    Cruiser(3, 0),
    Destroyer(2, 0),
    Submarine(1, 0);


    private final int size;
    private int amount;

    ShipType(int size, int amount) {
        this.size = size;
        this.amount = amount;
    }

    /**
     * Getting an amount of ships
     *
     * @return the sum of amounts of ship types
     */
    public static int getWholeAmount() {
        return Carrier.getAmount() + Battleship.getAmount() +
                Cruiser.getAmount() + Destroyer.getAmount() +
                Submarine.getAmount();
    }

    /**
     * Setting an amount of ShipType
     *
     * @param amount amount you want to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Getting a size of ship type.
     *
     * @return the size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Getting an amount of ships of definite ship type.
     *
     * @return the amount.
     */
    public int getAmount() {
        return amount;
    }
}
