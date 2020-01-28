package edu.fmi;

import java.util.ArrayList;
import java.util.HashSet;

public class ColoredArea {
    private static int data[][];
    private static ArrayList<Integer> ranges = new ArrayList<>();

    public ColoredArea(int[][] arr) {
        int colls = arr[0].length + 2;
        int rows = arr.length + 2;
        data = new int[rows][colls];
        
        //Left and right colls
        for (int i = 0; i < rows; i++) {
            data[i][0] = 0;
            data[i][colls - 1] = 0;
        }
        //Up and down colls
        for (int i = 0; i < colls; i++) {
            data[0][i] = 0;
            data[rows - 1][i] = 0;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                data[i + 1][j + 1] = arr[i][j];
            }
        }
    }

    private static void findRange(int x, int y) {

        if (data[x][y] != data[x + 1][y] && data[x][y] != data[x - 1][y] &&
                data[x][y] != data[x][y + 1] && data[x][y] != data[x][y - 1]) {
            data[x][y] = 0;

            return;
        }

        if (data[x][y] == 0) {
            return;
        }

        if (data[x][y] == data[x + 1][y]) {
            if (data[x][y] != 0) {
                ranges.add(data[x][y]);
            }
            data[x][y] = 0;
            findRange(x + 1, y);
        }

        if (data[x][y] == data[x][y + 1]) {
            if (data[x][y] != 0) {
                ranges.add(data[x][y]);
            }
            data[x][y] = 0;
            findRange(x, y + 1);
        }
        if (data[x][y] == data[x - 1][y]) {
            if (data[x][y] != 0) {
                ranges.add(data[x][y]);
            }
            data[x][y] = 0;
            findRange(x - 1, y);
        }
        if (data[x][y] == data[x][y - 1]) {
            if (data[x][y] != 0) {
                ranges.add(data[x][y]);
            }
            data[x][y] = 0;
            findRange(x, y - 1);
        }
    }

    public static HashSet<Integer> ranges() {
        HashSet<Integer> ranges1 = new HashSet<>();

        for (int i = 1; i < data.length-1; i++) {
            for (int j = 1; j < data[0].length-1; j++) {
                if (data[i][j] != 0) {
                    ranges1.add(data[i][j]);
                    findRange(i, j);
                }
            }
        }
        return ranges1;
    }

    public static ArrayList<Integer> rangeSizes(){
        ArrayList<Integer> rangeSizes = new ArrayList<>();
        ranges.add(0);

        int start = 0;

        for (int i = 0; i < ranges.size()-1; i++) {

            if (!ranges.get(i).equals(ranges.get(i + 1))) {
                rangeSizes.add(i+2-start);
                start = i+1;
            }
        }
        return rangeSizes;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                s += data[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
}
