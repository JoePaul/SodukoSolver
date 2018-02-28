package com.example.jonathan.sodukosolver;

import java.util.Timer;

/**
 * Created by Jonathan on 2018-02-15.
 */

public class SudokuModel {
        private int[][] matrix;
        private long startTime;

    /**
     * Creates a new SudokuModel and a new game of sudoku.
     */

    public SudokuModel() {
            matrix = new int[9][9];
        }

    /**Adds a new number to the Sudoku.
     * @param number An int that represents the number in the game. Range between 1-9.
     * @param row An int that represents the row on the board. Range between 0-8.
     * @param col An int that represents the column on the board. Range between 0-8.
     *
     */
        public void add(int row, int col, int number) {
            matrix[row][col] = number;
        }

    /**
     *Resets the int matrix to its original state.
     */
        public void restart() {
        matrix = new int[9][9];
    }


        private boolean canAdd(int row, int col, int nbr) {

            for (int i = 0; i < 9; i++) {
                if (matrix[row][i] == nbr) {
                    return false;
                }
            }
            for (int i = 0; i < 9; i++) {
                if (matrix[i][col] == nbr) {
                    return false;
                }
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (matrix[row / 3 * 3 + i][col / 3 * 3 + j] == nbr) {
                        return false;
                    }
                }
            }
            return true;
        }

    /**Sets a new Sudoku.
     * @param newMatrix Adding a new Sudoku
     *
     */

        public void set(int[][] newMatrix) {
            matrix = newMatrix;
        }




        private boolean changeRow(int row, int col) {
            if (col < 8) {
                return solve(row, col + 1);
            } else {
                return solve(row + 1, 0);
            }
        }

    /**
     *Gets the number at a specific spot in the matrix.
     * @param row
     * @param col
     * @return Returns the int at the spot in the matrix of row "row" and col "col".
     */

        public int get(int row, int col) {
            return matrix[row][col];
        }

    /**
     * Solves the Sudoku according to the rules of the game.
     */

        public boolean solveSudoku(){
            return solve(0,0);
        }

        private boolean solve(int row, int col) {
            if (row == 0 && col == 0){
                startTime = System.currentTimeMillis();
                for (int i = 0; i < 9; i++){
                    for (int j = 0; j < 9; j++){
                        if (matrix[i][j] != 0){
                            int temp = matrix[i][j];
                            matrix[i][j] = 0;
                            if (!canAdd(i,j,temp)){
                                matrix[i][j] = temp;
                                return false;
                            }
                            matrix[i][j] = temp;
                        }
                    }
                }
            }
            if (System.currentTimeMillis() - startTime > 10000){
                return false;
            }
            if (row == 9) {
                return true;
            }
            if (matrix[row][col] != 0) {
                int temp = matrix[row][col];
                matrix[row][col] = 0;
                if (canAdd(row, col, temp)) {
                    add(row, col, temp);
                    if (changeRow(row, col)) {
                        return true;
                    }
                }
                add(row, col, temp);
                return false;
            }

            for (int nbr = 1; nbr <= 9; nbr++) {
                if (canAdd(row, col, nbr)) {
                    add(row, col, nbr);
                    if (changeRow(row, col)) {
                        return true;
                    } else {
                        add(row, col, 0);
                    }
                }
            }
            return false;
        }
}