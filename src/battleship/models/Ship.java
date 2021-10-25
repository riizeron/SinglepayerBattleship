package battleship.models;

import battleship.enums.Direction;
import battleship.enums.ShipType;

import java.util.Random;

public class Ship {
    private static final Random rnd;
    private final ShipType type;
    private int headPoints;
    private Coordinate[] coordinates;

    static {
        rnd = new Random();
    }

    /**
     * Default Ship constructor.
     * Needed to not return null.
     */
    public Ship() {
        type = ShipType.Cruiser;
        coordinates = new Coordinate[]{new Coordinate()};
    }

    /**
     * Ship constructor.
     *
     * @param type ship type.
     */
    public Ship(ShipType type) {
        this.type = type;
        this.headPoints = type.getSize();
    }

    /**
     * Setting ship destination in the battlefield.
     * Placing of the ship declare impossible if attempts amount of placing exceed 100.
     *
     * @return true if it is possible to place the ship, false otherwise.
     */
    public boolean trySetShipCoordinates(BattleField battleField) {
        coordinates = new Coordinate[type.getSize()];

        // Максимально допустимое количество попыток задать координаты для корабля
        // То есть при превышении этого количества, корабли будут расстанавливаться заново
        // С самого начала
        int maxIteration = 100;
        // Первая координата выбирается случайно на территории поля
        // Но она все должна выбираться корректно
        do {
            if (maxIteration == 0) {
                return false;
            }
            coordinates[0] = new Coordinate(rnd.nextInt(battleField.sizeX),
                    rnd.nextInt(battleField.sizeY));
            maxIteration--;
        } while (battleField.matrix[coordinates[0].x][coordinates[0].y] == 9
                || battleField.matrix[coordinates[0].x][coordinates[0].y] == 8);

        // Направление корабля выбирается случайно;
        Direction dir = Direction.values()[rnd.nextInt(3)];
        maxIteration = 100;
        // Последующие координаты задаются с учетом направления и предыдущих координат
        for (int i = 1; i < type.getSize(); i++) {
            coordinates[i] = new Coordinate(coordinates[i - 1].x, coordinates[i - 1].y, dir);
            if (!checkNextCoordinate(coordinates[i], battleField)) {
                dir = Direction.values()[rnd.nextInt(3)];
                coordinates[0] = new Coordinate(rnd.nextInt(battleField.sizeX),
                        rnd.nextInt(battleField.sizeY));
                i = 0;
                maxIteration--;
            }
            if (maxIteration == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Call if ship was hit.
     */
    public void hit() {
        headPoints--;
    }

    /**
     * Call if ship was sunk.
     * But only by torpedo.
     * In other cases it is not needed.
     */
    public void sunk() {
        headPoints = 0;
    }

    /**
     * Call if ship was recover
     * For ship recover mode.
     */
    public void recover() {
        headPoints = type.getSize();
    }

    private boolean checkNextCoordinate(Coordinate c, BattleField battleField) {
        // Проверка на то что координата находится на территории поля
        if (c.x >= battleField.sizeX || c.x < 0 || c.y >= battleField.sizeY || c.y < 0) {
            return false;
        }
        // 8 - значение на которое нельзя поставить корабль, тк рядом уже есть корабль
        // 9 - значение на которм уже стоит корабль
        if (battleField.matrix[c.x][c.y] == 9 || battleField.matrix[c.x][c.y] == 8) {
            return false;
        }
        return true;
    }

    /**
     * Getting coordinates of the ship.
     *
     * @return the Coordinate array of the ship.
     */
    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    /**
     * Check if the vessel is sunk.
     *
     * @return true if vessel is sunk, false otherwise.
     */
    public boolean isSunk() {
        return headPoints == 0;
    }

    /**
     * @return name of type of ship.
     */
    @Override
    public String toString() {
        return type.name();
    }
}
