package fr.sz.me;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sylborg on 22/01/2016.
 */
public class MinesWeeperBasic {

    /**
     * The main program
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean confirm = false, end = false;
        short rows = 0, columns = 0, mines = 0, rowNum = 0, columnNum = 0;
        while (!confirm) {
            System.out.println("Welcome to my Minesweeper! Please enter now the game inputs");
            System.out.print("Enter the row length: ");
            rows = scanner.nextShort();
            System.out.print("Enter the column length: ");
            columns = scanner.nextShort();
            System.out.print("Enter the number of mines: ");
            mines = scanner.nextShort();
            System.out.println(String.format("You choose the following parameters: %d x %d x %d",
                    rows, columns, mines));
            System.out.print("Would you like to confirm your selection? (y/n)");
            String confirmInput = scanner.next();
            confirm = confirmInput.equals("y") || confirmInput.equals("Y");
            System.out.println();
        }

        MinesWeeperGrid minesWeeperGrid;
        if (rows != 0 && columns != 0 && mines != 0) {
            minesWeeperGrid = new MinesWeeperGrid(rows, columns, mines);
        } else {
            minesWeeperGrid = new MinesWeeperGrid();
        }

        System.out.println(minesWeeperGrid.toString());
        System.out.println();
        while (!end) {
            System.out.println("\nNow uncover a grid's point");
            try {
                System.out.print("First choose a row number: ");
                minesWeeperGrid.setRowNumberSelected(scanner.nextShort() - 1);
                System.out.print("Then choose a column number: ");
                minesWeeperGrid.setColumnNumberSelected(scanner.nextShort() - 1);
                if (lose(minesWeeperGrid)) {
                    System.out.println("You loose!!");
                    break;
                }
                minesWeeperGrid.uncoverWithSelection(minesWeeperGrid.getRowNumberSelected(),
                        minesWeeperGrid.getColumnNumberSelected());
                System.out.println();
                System.out.println(minesWeeperGrid.toString());
                System.out.println();
                if (win(minesWeeperGrid)) {
                    System.out.println("Congrats, you won!!");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.print("Your writing was wrong, please start again.");
                scanner.nextLine();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("The row and/or column selected is not in the grid range");
                scanner.nextLine();
            }
        }
    }

    /**
     * Check is the user lost or not
     *
     * @param minesWeeperGrid
     * @return true if the game is lost, false otherwise
     */
    public static boolean lose(MinesWeeperGrid minesWeeperGrid) {
        int currentSelection = (minesWeeperGrid.getNbRows() * minesWeeperGrid.getColumnNumberSelected())
                + minesWeeperGrid.getRowNumberSelected();
        return (minesWeeperGrid.getMines()[currentSelection]);
    }

    /**
     * Check is the user won or not
     *
     * @param minesWeeperGrid
     * @return true if the game is won, false otherwise
     */
    public static boolean win(MinesWeeperGrid minesWeeperGrid) {
        int countUncovered = 0;
        for (int x = 0; x < minesWeeperGrid.getUncoverPoints().length; x++) {
            if (minesWeeperGrid.getUncoverPoints()[x]) {
                 countUncovered++;
            }
        }
        return ((minesWeeperGrid.getUncoverPoints().length - countUncovered) == minesWeeperGrid.getNbMines());
    }
}
