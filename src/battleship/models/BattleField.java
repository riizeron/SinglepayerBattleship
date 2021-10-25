package battleship.models;


public class BattleField {
    public final int sizeX;
    public final int sizeY;
    public final int[][] matrix;

    public BattleField(String[] args) {
        sizeX = Integer.parseInt(args[0]);
        sizeY = Integer.parseInt(args[1]);
        matrix = new int[sizeX][sizeY];
    }

    /**
     * Parsing a game's parameters into the Battlefield object.
     *
     * @param args game parameters.
     * @return BattleField object.
     * @throws Exception throws if it is impossible to create an object due to incorrect parameters.
     */
    public static BattleField tryParseCommandLineParams(String[] args) throws Exception {
        try {
            return new BattleField(args);
        } catch (Exception ex) {
            throw new Exception("Incorrect command line parameters");
        }
    }

    /**
     * Make all elements of matrix equal to zero.
     */
    public void clearMatrix() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    // Метод изменяет матрицу таким образом,
    // чтобы на месте расположения судна оказались 9, а в ячейках рядом - 8

    /**
     * Change value of elements of matrix in such a way that
     * so that at the location of the vessel there are 9, and in the cells nearby - 8.
     *
     * @param coordinates coordinates of last added vessel
     */
    public void changeMatrixAfterAddingShips(Coordinate[] coordinates) {
        for (Coordinate c : coordinates) {
            matrix[c.x][c.y] = 9;
        }
        for (Coordinate c : coordinates) {
            if (c.x - 1 >= 0) {
                if (matrix[c.x - 1][c.y] != 9) {
                    matrix[c.x - 1][c.y] = 8;
                }
                if (c.y - 1 >= 0) {
                    if (matrix[c.x - 1][c.y - 1] != 9) {
                        matrix[c.x - 1][c.y - 1] = 8;
                    }
                }
                if (c.y + 1 < sizeY) {
                    if (matrix[c.x - 1][c.y + 1] != 9) {
                        matrix[c.x - 1][c.y + 1] = 8;
                    }
                }
            }
            if (c.x + 1 < sizeX) {
                if (matrix[c.x + 1][c.y] != 9) {
                    matrix[c.x + 1][c.y] = 8;
                }
                if (c.y - 1 >= 0) {
                    if (matrix[c.x + 1][c.y - 1] != 9) {
                        matrix[c.x + 1][c.y - 1] = 8;
                    }
                }
                if (c.y + 1 < sizeY) {
                    if (matrix[c.x + 1][c.y + 1] != 9) {
                        matrix[c.x + 1][c.y + 1] = 8;
                    }
                }
            }
            if (c.y - 1 >= 0) {
                if (matrix[c.x][c.y - 1] != 9) {
                    matrix[c.x][c.y - 1] = 8;
                }
            }
            if (c.y + 1 < sizeY) {
                if (matrix[c.x][c.y + 1] != 9) {
                    matrix[c.x][c.y + 1] = 8;
                }
            }
        }
    }

    /**
     * Change value of elements of matrix in such a way that
     * so that at the location of the sunken vessel there are 3.
     *
     * @param coordinates coordinates of the vessel
     */
    public void changeMatrixAfterSunk(Coordinate[] coordinates) {
        for (Coordinate c : coordinates) {
            matrix[c.x][c.y] = 3;
        }
    }

    /**
     * Change value of elements of matrix in such a way that
     * so that at the location of the recover vessel there are
     * 9 just like as at the beginning.
     *
     * @param coordinates coordinates of recovered vessel.
     */
    public void changeMatrixAfterRecover(Coordinate[] coordinates) {
        for (Coordinate c : coordinates) {
            matrix[c.x][c.y] = 9;
        }
    }

    /**
     * Overriding toString method to get view on battlefield.
     * If the element of the matrix equal to 1 that means that you attacked this cell and hit the ship.
     * And '*' will be added to result string
     * If equal to 2, so you attacked this cell and miss and result string get '#'.
     * If equal to 3, so you sunk the ship at this cell and result string get 'x'.
     * In other ways result string get 'o'.
     *
     * @return string showing the battlefield matrix.
     */
    @Override
    public String toString() {
        String result = "\\  ";
        int item;
        for (int i = 0; i < sizeY; i++) {
            result += i + 1 + " ";
        }
        result += System.lineSeparator();
        for (int i = 0; i < sizeX; i++) {
            result += i + 1 + "  ";
            for (int j = 0; j < sizeY; j++) {
                item = matrix[i][j];
                // hit
                if (item == 1) {
                    result += "*";
                }
                // miss
                else if (item == 2) {
                    result += "#";
                }
                // sunk
                else if (item == 3) {
                    result += "x";
                }
                // default
                else {
                    result += "o";
                }
                result += " ";
            }
            result += System.lineSeparator();
        }
        return result;
    }

    /**
     * Get view on enemy's fleet destination.
     * This method was created for testing.
     *
     * @return the string describes destination of ships.
     */
    public String toHackString() {
        String str = "";
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                str += matrix[i][j] + " ";
            }
            str += System.lineSeparator();
        }
        return str;
    }
}
