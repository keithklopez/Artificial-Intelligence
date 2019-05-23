import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Sudoku Class
 * Homework 8
 * Artificial Intelligence
 * CSIS-4463-002
 * Solves a Sudoku Puzzle using DFS.
 * @author Faye Lopez, Donato Martin, and Ryan Steup.
 *
 */

public class Sudoku {

//    //hardest sudoku puzzle in the world
//    static int[][] board = {
//            { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
//            { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
//            { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
//            { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
//            { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
//            { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
//            { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
//            { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
//            { 0, 9, 0, 0, 0, 0, 4, 0, 0 }
//    };

    static int[][] board;

    /**
     * Solves a Sodoku puzzle using DFS.
     * @param board Current state of Sodoku board to solve
     */
    private boolean solve(int[][] board) {
        //determines if the board has any unassigned cells
        boolean empty = emptyValue(board);
        if(empty == true){
            ArrayList<Integer> values = new ArrayList<>();
            //assigns the row/col of the empty cell
            values.addAll(getEmpty(board));
            int row = values.get(0);
            int column = values.get(1);
            values.clear();
            for (int k=0; k<=9; k++){
                //if there are no constraints for inputting value k
                if(isValid(row, column, k) == true){
                    //assign k to the empty cell
                    board[row][column] = k;
                    //System.out.println("row= " + row + " col= " + column + " k= " + i);
                    //recursive step
                    if(solve(board)){
                        return true;
                    }else {
                        board[row][column] = 0;
                    }
                }
            }
        }else{
            //if board not empty
            return true;
        }
        // if no solutions return false
        return false;
    }

    /**
     * Checks if the board has any empty values.
     * @param board Current state of Sodoku board.
     */
    private boolean emptyValue(int [][]board) {
        int row;
        int column;
        //iterate through all cells
        for(row=0; row<9; row++) {
            for(column=0; column<9; column++){
                //if cell == 0(empty)
                if(board[row][column] == 0){
                    //System.out.println("Board [" + row + "][" + column + "] empty");
                    if(row==8){return true;}
                }

            }
        }
        return false;
    }

    /**
     * Return the row/col values of the empty cell in an arraylist.
     * @param board Current state of Sodoku board.
     */
    private ArrayList<Integer> getEmpty(int[][] board){
        ArrayList<Integer> values = new ArrayList<>();
        int row;
        int column;
        for(row=0; row<9; row++) {
            for(column=0; column<9; column++){
                if(board[row][column] == 0){
                    values.add(row); //index 0 in arraylist
                    values.add(column); //index 1 in arraylist
                }
                //return values;

            }
        }
        return values;
    }

    /**
     * Checks if inputting specified number violates any row/col/box constraints.
     * @param number row, int column, int number.
     */
    private boolean isValid(int row, int column, int number) {
        //System.out.println("row: " + !rowConstraints(row, number)  + " col: " +  !columnConstraints(column, number)  + " box: " +  !boxConstraints(row, column, number));
        return !rowConstraints(row, number)  &&  !columnConstraints(column, number)  &&  !boxConstraints(row, column, number);
    }

    /**
     * Checks if inputting specified number violates any row constraints. ie. "does number already belong to this row?"
     * @param number row, int number.
     */
    private boolean rowConstraints(int row, int number) {
        for (int i = 0; i < 9; i++)
            if (board[row][i] == number)
                return true;

        return false;
    }

    /**
     * Checks if inputting specified number violates any column constraints. ie. "does number already belong to this column?"
     * @param number column, int number.
     */
    private boolean columnConstraints(int column, int number) {
        for (int i = 0; i < 9; i++)
            if (board[i][column] == number)
                return true;

        return false;
    }

    /**
     * Checks if inputting specified number violates any 3x3 box constraints. ie. "does number already belong to this box?"
     * @param number row, int column, int number.
     */
    private boolean boxConstraints(int row, int column, int number) {

        int r = row - row % 3;
        int c = column - column % 3;

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (board[i][j] == number)
                    return true;

        return false;
    }

    /**
     * Prints board to console.
     */
    private void printBoard() {

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Initializes the Soduko board into a 2d array from the user inputed file
     */
    private void createBoard() throws FileNotFoundException {
        Scanner user = new Scanner(System.in);
        System.out.println("Name the file you want to solve (please add the full path)");
        String path = user.next();
        Scanner scan = new Scanner(new File(path));

        int rows = 9;
        int columns = 9;
        int[][] puzzle = new int[rows][columns];

        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < columns; ++j)
            {
                if(scan.hasNextInt())
                {
                    puzzle[i][j] = scan.nextInt();
                }
            }
        }
        board = puzzle;
    }

    /**
     * This is the tester
     * @param args
     */
    public static void main(String[] args) {
        Sudoku test = new Sudoku();
        try {
            test.createBoard();
        }
        catch(Exception e){
            System.out.println("File Not Found!");
        }
        test.printBoard();
        System.out.println();
        test.solve(board);
        System.out.println();
        test.printBoard();
    }

}