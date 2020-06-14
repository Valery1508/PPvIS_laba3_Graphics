package controller;

import model.Point;
import model.TableOfValues;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.currentThread;

public class ArrayThread implements Runnable {

    private int n;
    private final ConcurrentLinkedQueue<Integer> queue;
    private TableOfValues table;

    public ArrayThread(int n, ConcurrentLinkedQueue<Integer> queue, TableOfValues table) {
        this.n = n;
        this.queue = queue;
        this.table = table;
    }

    @Override
    public void run() {
        int a = 2;
        while (a <= n && !Thread.interrupted()){

            int[][] array = generateArrays(a);
            int y = (Math.toIntExact(getAverageTimeOfSorting(array, a))) / (a*1000);

            table.updateTable(new Point(a, y));
            queue.add(y);

            try{
                Thread.sleep(250);
            } catch(InterruptedException e){
                break;
            }
            a += 1;
        }
        currentThread().interrupt();
    }

    private int[][] generateArrays(Integer number){
        int amount = (int) Math.round((Math.random() * 9) + 1); // диапазон (1;10)

        int[][] arrayOfArrays = new int[amount][number];

        for(int i = 0; i < amount; i++){
            for (int j = 0; j < number; j++){
                arrayOfArrays[i][j] = (int) Math.round((Math.random() * 9) + 1);
            }
        }
        return arrayOfArrays;
    }

    private long getAverageTimeOfSorting(int[][] array, int a){
        long start = System.nanoTime();

        for(int i = 0; i < array.length; i++){
            sortArray(array[i]);
        }

        long finish = System.nanoTime();
        return finish - start;
    }

    private void sortArray(int[] array){
        int[] buffer1 = Arrays.copyOf(array, array.length);
        int[] buffer2 = new int[array.length];
        int[] result = mergeSortInner(buffer1, buffer2, 0, array.length);
    }

    private int[] mergeSortInner(int[] buffer1, int[] buffer2, int startIndex, int endIndex){
        if (startIndex >= endIndex - 1) {
            return buffer1;
        }

        int middle = startIndex + (endIndex - startIndex) / 2;
        int[] sorted1 = mergeSortInner(buffer1, buffer2, startIndex, middle);
        int[] sorted2 = mergeSortInner(buffer1, buffer2, middle, endIndex);

        int index1 = startIndex;
        int index2 = middle;
        int destIndex = startIndex;
        int[] result = sorted1 == buffer1 ? buffer2 : buffer1;
        while (index1 < middle && index2 < endIndex) {
            result[destIndex++] = sorted1[index1] < sorted2[index2]
                    ? sorted1[index1++] : sorted2[index2++];
        }
        while (index1 < middle) {
            result[destIndex++] = sorted1[index1++];
        }
        while (index2 < endIndex) {
            result[destIndex++] = sorted2[index2++];
        }
        return result;
    }

}
