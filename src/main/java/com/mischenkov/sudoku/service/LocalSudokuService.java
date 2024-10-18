package com.mischenkov.sudoku.service;

public class LocalSudokuService implements SudokuService {
    @Override
    public int[][] getSudoku() {
        return new int[][] {
                {0,0,0,2},
                {0,2,0,0},
                {0,3,0,4},
                {0,1,2,0}
        };
    }

    @Override
    public int[][] getSudokuSolution() {
        return new int[][] {
                {1,4,3,2},
                {3,2,4,1},
                {2,3,1,4},
                {4,1,2,3}
        };
    }
}
