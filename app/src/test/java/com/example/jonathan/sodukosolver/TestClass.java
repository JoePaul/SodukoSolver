package com.example.jonathan.sodukosolver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestClass {
    private SudokuModel sm;

    @Before
    public void start(){
        sm = new SudokuModel();
    }

    @After
    public void done(){
        sm.restart();
    }

    @Test
    public void empty(){
        assertTrue("Should be solved", sm.solveSudoku());
    }

    @Test
    public void cantSolve(){
        sm.add(0,0,5);
        sm.add(0,1,5);
        assertFalse("Sholud not be able to solve in same row", sm.solveSudoku());
        sm.restart();
        sm.add(0,0,5);
        sm.add(1,0,5);
        assertFalse("Should not be able to solve in same col", sm.solveSudoku());
        sm.restart();
        sm.add(0,0,5);
        sm.add(2,2,5);
        assertFalse("Should not be able to solve in same box", sm.solveSudoku());
    }

    @Test
    public void solveExample(){
        sm.set(new int[][]{{0,0,8,0,0,9,0,6,2},
                {0,0,0,0,0,0,0,0,5},
                {1,0,2,5,0,0,0,0,0},
                {0,0,0,2,1,0,0,9,0},
                {0,5,0,0,0,0,6,0,0},
                {6,0,0,0,0,0,0,2,8},
                {4,1,0,6,0,8,0,0,0},
                {8,6,0,0,3,0,1,0,0},
                {0,0,0,0,0,0,4,0,0}
        });
        assertTrue("Should be able to solve example Sudoku", sm.solveSudoku());
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}