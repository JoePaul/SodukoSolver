package com.example.jonathan.sodukosolver;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

/**
 * Created by Jonathan on 2018-02-15.
 */


public class SudokuAdapter extends BaseAdapter {
    private SudokuModel sudokuModel;
    private EditText [][] matrix;


    /**
     *Creates a new matrix of EditTexts.
     * @param activity Takes a Activity
     */
    public void onCreate(Activity activity){
        sudokuModel = new SudokuModel();
        matrix = new EditText[9][9];
        int id = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                final EditText n = new EditText(activity);
                n.setId(id);
                n.setInputType(InputType.TYPE_CLASS_NUMBER);
                n.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
                n.setGravity(Gravity.CENTER);
                if (setColor(i,j)){
                    n.setBackgroundColor(Color.parseColor("#B0B0B0"));
                }else{
                    n.setBackgroundColor(Color.parseColor("#D3D3D3"));
                }
                matrix[i][j] = n;
                id++;
            }
        }
    }

    private EditText get(int id){
        int row = id/9;
        int col = id%9;
        return matrix[row][col];
    }

    /**
     * Clears the board and restarts the sudokumodel.
     */
    public void clear(){
        for (int i = 0; i < getCount(); i++){
            get(i).getText().clear();
        }
        sudokuModel.restart();

    }
    private void add(){
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int temp = 0;
                    if (!matrix[i][j].getText().toString().isEmpty()) {
                        temp = Integer.parseInt(matrix[i][j].getText().toString());
                    }
                        sudokuModel.add(i, j, temp);
                }
            }
    }

    /**
     * Adds all the elements in on the board and solves the sudoku if possible.
     * @return Returns an boolean. If the sudoku could be solved it returns true else false.
     */
    public boolean solve(){
            add();
            return sudokuModel.solveSudoku();
            //fill();
            //return sodukuModel.solveSudoku();
    }

    /**
     * Updates the board if the sudoku could be solved, else restarts the board.
     * @return Returns true if the game was solved, else false.
     */
    public boolean update(){
        if (solve()){
            fill();
            return true;
        } else{
            sudokuModel.restart();
            return false;
        }
    }
    private void fill(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                matrix[i][j].getText().clear();
                if (sudokuModel.get(i, j) != 0){
                    matrix[i][j].setText(Integer.toString(sudokuModel.get(i, j)));
                }
            }
        }
    }

    /**
     * Fills the board with a new, preset game, updates
     * the board in the model and then fills the board.
     */
    public void fillExample(){
        int[][] array = new int[][]{{0,0,8,0,0,9,0,6,2},
                {0,0,0,0,0,0,0,0,5},
                {1,0,2,5,0,0,0,0,0},
                {0,0,0,2,1,0,0,9,0},
                {0,5,0,0,0,0,6,0,0},
                {6,0,0,0,0,0,0,2,8},
                {4,1,0,6,0,8,0,0,0},
                {8,6,0,0,3,0,1,0,0},
                {0,0,0,0,0,0,4,0,0}
        };
        sudokuModel.set(array);
        fill();
    }

    /**
     * Updates the board with a sudoku made by a japanese supercomputer.
     */
    public void fillJapan(){
        int[][] array = new int[][]{{0,6,1,0,0,7,0,0,3},
                {0,9,2,0,0,3,0,0,0},
                {0,0,0,0,0,0,0,0,0},
                {0,0,8,5,3,0,0,0,0},
                {0,0,0,0,0,0,5,0,4},
                {5,0,0,0,0,8,0,0,0},
                {0,4,0,0,0,0,0,0,1},
                {0,0,0,1,6,0,8,0,0},
                {6,0,0,0,0,0,0,0,0}
        };
        sudokuModel.set(array);
        fill();
    }
    private boolean setColor(int row, int col){
        return (row < 3 && col < 3 ||
                row > 5 && col < 3 ||
                row < 3 && col > 5 ||
                row > 5 && col > 5 ||
                row > 2 && row < 6 && col > 2 && col < 6
        );
    }

    /**
     *
     * @return Returns the number of elements in the matrix.
     */
    @Override
    public int getCount() {
        return 81;
    }


    /**
     * Not implemented method.
     * @param i
     * @return An object, in this case null.
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    /**
     * Not implemented method.
     * @param i
     * @return Returns a long, but in this case 0.
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Gets the view that is called for.
     * @param i The id of the edittext that is sought after.
     * @param view
     * @param viewGroup
     * @return Returns a view.
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int row = i/9;
        int col = i%9;
        return matrix[row][col];
    }
}
