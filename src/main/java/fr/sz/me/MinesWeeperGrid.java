package fr.sz.me;

import java.util.Random;

/**
 * Created by sylborg on 23/01/2016.
 */
public class MinesWeeperGrid  {

    /**
     * The constant DEFAULT_ROWS_NUMBER
     */
    protected static final short DEFAULT_ROWS_NUMBER = 10;

    /**
     * The constant DEFAULT_COLUMNS_NUMBER
     */
    protected static final short DEFAULT_COLUMNS_NUMBER = 10;

    /**
     * The constant DEFAULT_NUM_MINES
     */
    protected static final short DEFAULT_NUM_MINES = 10;

    /**
     * The constant TOP_DOWN_DELIMITER
     */
    private static final String TOP_DOWN_DELIMITER = "##";

    /**
     * The constant LEFT_DELIMITER
     */
    private static final String LEFT_DELIMITER = "##";

    /**
     * The constant RIGHT_DELIMITER
     */
    private static final String RIGHT_DELIMITER = " ##";

    /**
     * The constant HIDDEN
     */
    private static final Character HIDDEN = '-';


    /**
     * Gives the number of rows for the grid
     */
    private short nbRows;

    /**
     * Gives the number of columns for the grid
     */
    private short nbColumns;

    /**
     * Gives the number of mines hidden into the grid
     */
    private short nbMines;

    /**
     * Gives the presence or absence of a mine for every position in the grid
     */
    private boolean [] mines;

    /**
     * Gives the cover status for every position in the grid
     */
    private boolean [] uncoverPoints;

    /**
     * Gives the number of mines in the neighbourhood for every position in the grid
     */
    private short [] nbAdjacentMines;

    /**
     * Gives the current row number selected by the user
     */
    private int rowNumberSelected;

    /**
     * Gives the current column number selected by the user
     */
    private int columnNumberSelected;

    /**
     * The default constructor building a <code>MinesWeeperGrid</code>
     */
    public MinesWeeperGrid() {
        this(DEFAULT_ROWS_NUMBER, DEFAULT_COLUMNS_NUMBER, DEFAULT_NUM_MINES);
    }

    /**
     * A construction building a <code>MinesWeeperGrid</code>
     *
     * @param nbRows the number of rows requested
     * @param nbColumns the number of columns requested
     * @param nbMines the number of mines to hide into the grid
     */
    public MinesWeeperGrid(short nbRows, short nbColumns, short nbMines) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.nbMines = nbMines;
        this.mines = new boolean [nbRows * nbColumns];
        this.nbAdjacentMines = new short [nbRows * nbColumns];
        this.uncoverPoints = new boolean [nbRows * nbColumns];
        this.fillWithMines();
        this.fillAdjacentMinesCounting();
    }

    /**
     * Fill mines into <code>mines</code> array.
     */
    private void fillWithMines() {
        for (int x = 0; x < nbMines; x++) {
            boolean isMinedTmp = false;
            while (!isMinedTmp) {
                int position = new Random().nextInt(nbRows * nbColumns);
                if (!mines[position]) {
                    mines[position] = true;
                    isMinedTmp = true;
                }
            }
        }
    }

    /**
     * Fill the numbers of adjacents mines into <code>nbAdjacentMines</code> array.
     */
    private void fillAdjacentMinesCounting() {
        for (int x = 0; x < nbRows; x++) {
            for (int y = 0; y < nbColumns; y++) {
                int cur = (nbRows * y) + x;
                if (mines[cur]) {
                    nbAdjacentMines[cur] = 0;
                    continue;
                }
                short temp = 0;
                boolean isBorderLeft = (x - 1) < 0;
                boolean isBorderRight = (x + 1) >= nbRows;
                boolean isBorderUp = (y - 1) < 0;
                boolean isBorderDown = (y + 1) >= nbColumns;
                int left = (nbRows * (y)) + (x - 1);
                int right = (nbRows * (y)) + (x + 1);
                if (!isBorderUp) {
                    temp += countTopZoneMines(x, y);
                }
                if (!isBorderDown) {
                    temp += countDownZoneMines(x, y);
                }
                if (!isBorderLeft) {
                    if (mines[left]) {
                        temp++;
                    }
                }
                if (!isBorderRight) {
                    if (mines[right]) {
                        temp++;
                    }
                }
                nbAdjacentMines[cur] = temp;
            }
        }
    }

    /**
     * Gives the number of mines in the top zone
     *
     * @param x the row
     * @param y the column
     * @return the number of mines in the top zone
     */
    private int countTopZoneMines(int x, int y) {
        short temp = 0;
        int up = (nbRows * (y - 1)) + (x);
        int upleft = (nbRows * (y - 1)) + (x - 1);
        int upright = (nbRows * (y - 1)) + (x + 1);
        boolean isBorderLeft = (x - 1) < 0;
        boolean isBorderRight = (x + 1) >= nbRows;
        if (mines[up]) {
            temp++;
        }
        if (!isBorderLeft) {
            if (mines[upleft]) {
                temp++;
            }
        }
        if (!isBorderRight) {
            if (mines[upright]) {
                temp++;
            }
        }
        return temp;
    }

    /**
     * Gives the number of mines in the down zone
     *
     * @param x the row
     * @param y the column
     * @return the number of mines in the down zone
     */
    private int countDownZoneMines(int x, int y) {
        short temp = 0;
        int down = (nbRows * (y + 1)) + (x);
        int downleft = (nbRows * (y + 1)) + (x - 1);
        int downright = (nbRows * (y + 1)) + (x + 1);
        boolean isBorderLeft = (x - 1) < 0;
        boolean isBorderRight = (x + 1) >= nbRows;
        if (mines[down]) {
            temp++;
        }
        if (!isBorderLeft) {
            if (mines[downleft]) {
                temp++;
            }
        }
        if (!isBorderRight) {
            if (mines[downright]) {
                temp++;
            }
        }
        return temp;
    }

    /**
     * Fill the <code>uncoverPoints</code> array to know which position
     * should be uncovered during the next display for a given position
     * into the grid
     *
     * @param x
     * @param y
     */
    protected void uncoverWithSelection(int x, int y) {
        int current = (nbRows * y) + x;
        boolean isBorderLeft = (x - 1) < 0;
        boolean isBorderRight = (x + 1) >= nbRows;
        if (!uncoverPoints[current]) {
            uncoverPoints[current] = true;
            if (nbAdjacentMines[current] == 0) {
                if (isBorderLeft) {
                    uncoverLeftSide(x, y);
                } else if (isBorderRight) {
                    uncoverRightSide(x, y);
                } else {
                    uncoverMiddle(x, y);
                }
            }
        }
    }

    /**
     * Fill the <code>uncoverPoints</code> array for the left side
     * part of the grid.
     *
     * @param x
     * @param y
     */
    private void uncoverLeftSide(int x, int y) {
        boolean isBorderUp = (y - 1) < 0;
        boolean isBorderDown = (y + 1) >= nbColumns;
        uncoverWithSelection((x + 1), y);
        if (isBorderUp || (!isBorderUp && !isBorderDown)) {
            uncoverWithSelection((x + 1), (y + 1));
            uncoverWithSelection(x, (y + 1));
        }
        if (isBorderDown || (!isBorderUp && !isBorderDown)) {
            uncoverWithSelection(x, (y - 1));
            uncoverWithSelection((x + 1), (y - 1));
        }
    }

    /**
     * Fill the <code>uncoverPoints</code> array for the right side
     * part of the grid.
     *
     * @param x
     * @param y
     */
    private void uncoverRightSide(int x, int y) {
        boolean isBorderUp = (y - 1) < 0;
        boolean isBorderDown = (y + 1) >= nbColumns;
        uncoverWithSelection((x - 1), y);
        if (isBorderUp || (!isBorderUp && !isBorderDown)) {
            uncoverWithSelection((x - 1), (y + 1));
            uncoverWithSelection(x, (y + 1));
        }
        if (isBorderDown || (!isBorderUp && !isBorderDown)) {
            uncoverWithSelection((x - 1), (y - 1));
            uncoverWithSelection(x, (y - 1));
        }
    }

    /**
     * Fill the <code>uncoverPoints</code> array for the middle
     * part of the grid (neither left or right).
     *
     * @param x
     * @param y
     */
    private void uncoverMiddle(int x, int y) {
        boolean isBorderUp = (y - 1) < 0;
        boolean isBorderDown = (y + 1) >= nbColumns;
        uncoverWithSelection((x - 1), y);
        uncoverWithSelection((x + 1), y);
        if (!isBorderDown) {
            uncoverWithSelection((x - 1), (y + 1));
            uncoverWithSelection(x, (y + 1));
            uncoverWithSelection((x + 1), (y + 1));
        }
        if (!isBorderUp) {
            uncoverWithSelection((x - 1), (y - 1));
            uncoverWithSelection(x, (y - 1));
            uncoverWithSelection((x + 1), (y - 1));
        }
    }

    /**
     * Check if the column is the left border
     *
     * @param column
     * @return true if column is the left border
     */
    public boolean isBorderLeft(short column) {
        return column == 0;
    }

    /**
     * Check if the column is the right border
     *
     * @param column
     * @return true if column is the right border
     */
    public boolean isBorderRight(short column) {
        return column + 1 == nbColumns;
    }

    /**
     * Renders spaces well adapted to a required length limit
     *
     * @param tmpPrefix
     * @param lengthLimit
     */
    private String renderSpaceFilling(String tmpPrefix, int lengthLimit) {
        while (tmpPrefix.length() != lengthLimit + 1) {
            tmpPrefix += " ";
        }
        return tmpPrefix;
    }

    /**
     * Renders the top and/or down border to display
     *
     * @param rowNumberSelected
     * @param columnPrefixLength
     * @return the top/down border rendered
     */
    private String renderTopDownBorders(int rowNumberSelected, int columnPrefixLength) {
        StringBuilder tmpResult = new StringBuilder();
        tmpResult.append(renderSpaceFilling("", rowNumberSelected));
        tmpResult.append(TOP_DOWN_DELIMITER);
        for (short y = 1; y <= nbColumns; y++) {
            tmpResult.append(renderSpaceFilling("", (columnPrefixLength - (Short.toString(y).length() - 1)) - 1));
            tmpResult.append(y);
        }
        tmpResult.append(" ");
        tmpResult.append(TOP_DOWN_DELIMITER);
        return tmpResult.toString();
    }

    /**
     * Display the <code>MinesWeeperGrid</code> into the command line out.
     * Renders with indices to let you know which are the coordinates.
     *
     * @return the grid as a <code>String</code>
     */
    public String toString() {
        int rowPrefixLength = Short.toString(this.getNbRows()).length();
        int columnPrefixLength = Short.toString(this.getNbColumns()).length();
        StringBuilder gridResult = new StringBuilder();
        gridResult.append(renderTopDownBorders(rowPrefixLength, columnPrefixLength));
        gridResult.append("\n");
        boolean isUncovered;
        String valueDisplayed = HIDDEN.toString();
        for (short x = 0; x < nbRows; x++) {
            for (short y = 0; y < nbColumns; y++) {
                isUncovered = uncoverPoints[(nbRows * y) + x];
                if (isUncovered) {
                    valueDisplayed = Short.toString(nbAdjacentMines[(nbRows * y) + x]);
                }
                if (isBorderLeft(y)) {
                    gridResult.append(renderSpaceFilling(((x + 1) + " "), rowPrefixLength));
                    gridResult.append(LEFT_DELIMITER);
                    gridResult.append(renderSpaceFilling("", columnPrefixLength - 1));
                    gridResult.append(valueDisplayed);
                } else if (isBorderRight(y)) {
                    gridResult.append(renderSpaceFilling("", columnPrefixLength - 1));
                    gridResult.append(valueDisplayed).append(RIGHT_DELIMITER);
                } else {
                    gridResult.append(renderSpaceFilling("", columnPrefixLength - 1));
                    gridResult.append(valueDisplayed);
                }
                valueDisplayed = HIDDEN.toString();
            }
            gridResult.append("\n");
        }
        gridResult.append(renderTopDownBorders(rowPrefixLength, columnPrefixLength));
        return gridResult.toString();
    }

    /**
     * Gets the number of rows
     *
     * @return the number of rows
     */
    public short getNbRows() {
        return nbRows;
    }

    /**
     * Sets the number of rows
     *
     * @param nbRows
     */
    public void setNbRows(short nbRows) {
        this.nbRows = nbRows;
    }

    /**
     * Gets the number of columns
     *
     * @return the number of columns
     */
    public short getNbColumns() {
        return nbColumns;
    }

    /**
     * Sets the number of columns
     *
     * @param nbColumns
     */
    public void setNbColumns(short nbColumns) {
        this.nbColumns = nbColumns;
    }

    /**
     * Sets the number of mines
     *
     * @return the number of mines
     */
    public short getNbMines() {
        return nbMines;
    }

    /**
     * Sets the number of columns
     *
     * @param nbMines the number of mines
     */
    public void setNbMines(short nbMines) {
        this.nbMines = nbMines;
    }

    /**
     * Gets the row number selected
     *
     * @return the row number selected
     */
    public int getRowNumberSelected() {
        return rowNumberSelected;
    }

    /**
     * Sets the row number selected
     *
     * @param rowNumberSelected the row number selected
     */
    public void setRowNumberSelected(int rowNumberSelected) {
        this.rowNumberSelected = rowNumberSelected;
    }

    /**
     /**
     * Gets the column number selected
     *
     * @return the column number selected
     */
    public int getColumnNumberSelected() {
        return columnNumberSelected;
    }

    /**
     * Sets the column number selected
     *
     * @param columnNumberSelected the column number selected
     */
    public void setColumnNumberSelected(int columnNumberSelected) {
        this.columnNumberSelected = columnNumberSelected;
    }

    /**
     * Gets the <code>mines</code> array
     *
     * @return the <code>mines</code> array
     */
    public boolean[] getMines() {
        return mines;
    }

    /**
     * Sets the <code>mines</code> array
     *
     * @param mines the <code>mines</code> array
     */
    public void setMines(boolean[] mines) {
        this.mines = mines;
    }

    /**
     * Gets a <code>uncoverPoints</code> array
     *
     * @return the <code>uncoverPoints</code> array
     */
    public boolean[] getUncoverPoints() {
        return uncoverPoints;
    }

    /**
     * Sets the <code>uncoverPoints</code> array
     *
     * @param uncoverPoints the <code>uncoverPoints</code> array
     */
    public void setUncoverPoints(boolean[] uncoverPoints) {
        this.uncoverPoints = uncoverPoints;
    }
}
