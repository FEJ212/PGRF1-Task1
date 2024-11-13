package Utils;

import java.util.ArrayList;

public class Sort {
    public static void swapElemsArray(int [] pole, int i, int j){
        int tmp = pole[i];
        pole[i] = pole[j];
        pole[j] = tmp;
    }
    public static ArrayList bubbleSort(ArrayList arr) {
        int[] numbers = new int[arr.size()];
        ArrayList<Integer> aList = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            numbers[i] = (Integer)arr.get(i);
        }
        for (int i = 0; i < numbers.length-1; i++) {
            for (int j = 1; j < numbers.length - i; j++) {
                if(numbers[j-1]>numbers[j]){
                    swapElemsArray(numbers, j-1, j);
                }
            }
        }
        for (int i = 0; i < numbers.length; i++) {
            aList.add(numbers[i]);
        }
        return aList;
    }
}
