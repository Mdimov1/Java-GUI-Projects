package edu.fmi;

import java.util.ArrayList;

public class TheEightQueens {
    private static ArrayList<int[][]> solutions;

    public TheEightQueens(){
        this.solutions = new ArrayList<>();
    }

    static void addSolution(int[][] board)
    {
        int[][] el = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                el[i][j] = board[i][j];
            }
        }
        solutions.add(el);

    }

    static boolean isSafe(int board[][], int row, int col)
    {
        int i, j;

        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        for (i = row, j = col; j >= 0 && i < 8; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    static boolean solve8QUtil(int board[][], int col)
    {

        if (col == 8)
        {
            addSolution(board);
            return true;
        }

        boolean res = false;
        for (int i = 0; i < 8; i++)
        {

            if ( isSafe(board, i, col) )
            {
                board[i][col] = 1;

                res = solve8QUtil(board, col + 1) || res;

                board[i][col] = 0;
            }
        }

        return res;
    }

    static void findSolutions()
    {
        int board[][] = new int[8][8];
        solve8QUtil(board, 0);

        return;
    }

    public ArrayList<int[][]> getAllSolutions(){
        return solutions;
    }
}