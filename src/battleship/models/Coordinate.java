package battleship.models;

import battleship.enums.Direction;

public class Coordinate {
    public final int x;
    public final int y;

    /**
     * Constructor of first ship coordinate.
     *
     * @param x number of string.
     * @param y number of column.
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default Coordinate constructor.
     * It is necessary to avoid returning null.
     */
    public Coordinate() {
        x = -1;
        y = -1;
    }

    /**
     * Constructor of Coordinate object starting from the second.
     *
     * @param prevX x coordinate of a previous ship cell
     * @param prevY y coordinate of a previous ship cell.
     * @param dir   is a Direction of ship destination relatively to first ship coordinate.
     */
    public Coordinate(int prevX, int prevY, Direction dir) {
        switch (dir) {
            case right -> {
                this.x = prevX;
                this.y = prevY + 1;
            }
            case left -> {
                this.x = prevX;
                this.y = prevY - 1;
            }
            case down -> {
                this.x = prevX + 1;
                this.y = prevY;
            }
            case up -> {
                this.x = prevX - 1;
                this.y = prevY;
            }
            default -> {
                x = 0;
                y = 0;
            }
        }
    }
}
