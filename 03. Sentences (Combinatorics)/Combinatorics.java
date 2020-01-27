package edu.fmi;

import java.util.ArrayList;
import java.util.HashSet;

public class Combinatorics<E> {
    private int k;
    private int[] data;
    private ArrayList<E> elements;
    private static String s = "";
    private static HashSet<String> combination = new HashSet<>();
    private static HashSet<String> cS = new HashSet<>();
    private static String c = "";
    private static String el = "";

    public Combinatorics(int k, final ArrayList<E> el) {
        if (k > 0) {
            this.k = k;
            data = new int[k];
            elements = el;
        }
    }

    public Combinatorics(Combinatorics cmb) {
        this.k = cmb.k;
        if (cmb.data != null) {
            data = new int[k];
            for (int i = 0; i < k; i++) {
                data[i] = cmb.data[i];
            }
            elements = cmb.elements;
        }
    }

    public String dataToString() {
        String s = "";
        for (int i = 0; i < k; i++) {
            s += elements.get(data[i]) + " ";
        }
        s += "\n";
        return s;
    }

    public void print() {
        System.out.print(dataToString());

    }

    public void variationWithRepetitions() {
        int n = elements.size();
        if (data == null) {
            return;
        }
        if (n <= 0) {
            return;
        }

        for (int i = 0; i < k; i++) {
            data[i] = 0;
        }
        print();

        boolean stop = false;

        while (!stop) {
            int j = k - 1;
            while ((j >= 0) && data[j] == n - 1) {
                data[j] = 0;
                j--;
            }
            if (j >= 0) {
                data[j]++;
                print();
            } else {
                stop = true;
            }
        }
    }

    public void variationWithoutRepetitions() {
        int n = elements.size();
        if (data == null) {
            return;
        }
        if (n <= 0) {
            return;
        }

        for (int i = 0; i < k; i++) {
            data[i] = 0;
        }

        boolean stop = false;

        while (!stop) {
            int j = k - 1;

            while ((j >= 0) && data[j] == n - 1) {
                data[j] = 0;
                j--;
            }

            if (j >= 0) {
                data[j]++;

                if (noRepetitions(data)) {
                    print();
                }
            } else {
                stop = true;
            }
        }
    }

    private boolean noRepetitions(int[] data) {
        HashSet<Integer> items = new HashSet<>();

        for (int i = 0; i < data.length; i++) {
            items.add(data[i]);
        }

        return items.size() == data.length;
    }

    //Combintions with repetitions
   public static void CombinationWithRepetitionsUtil(int chosen[], String arr[], int index, int r, int start, int end) {

        if (index == r) {
            for (int i = 0; i < r; i++) {
                s += arr[chosen[i]] + " ";
            }
            s += "\n";
            return;
        }

        for (int i = start; i <= end; i++) {
            chosen[index] = i;
            CombinationWithRepetitionsUtil(chosen, arr, index + 1, r, i, end);
        }
        return;
    }

    public static String getS() {
        return s;
    }

    public static void CombinationRepetition(String[] arr, int n, int r) {
        int chosen[] = new int[r + 1];

        CombinationWithRepetitionsUtil(chosen, arr, 0, r, 0, n - 1);
    }

    //Combintions without repetitions
   public static void CombinationWithoutRepetitionsUtil(int chosen[], String arr[], int index, int r, int start, int end) {
       HashSet<String> cS = new HashSet<>();
       String c = "";
       String el = "";

       if (index == r) {
           for (int i = 0; i < r; i++) {
               el = arr[chosen[i]];
               c += el + " ";
               cS.add(el + " ");
           }
           if(cS.size() > r-1){
               combination.add(c);
           }

           return;
       }

       for (int i = start; i <= end; i++) {
           chosen[index] = i;
           CombinationWithoutRepetitionsUtil(chosen, arr, index + 1, r, i, end);
       }
       return;
   }

    public static HashSet<String> getC() {
        return combination;
    }

    public static void CombinationWithoutRepetition(String[] arr, int n, int r) {
        int chosen[] = new int[r + 1];

        CombinationWithoutRepetitionsUtil(chosen, arr, 0, r, 0, n - 1);
    }
}
