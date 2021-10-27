package zad1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadLineCounter {

    private static ArrayList<LineCounterThread>filenamesToArrayOffLineCounters(String[] filenames) {

        ArrayList<LineCounterThread> threadArray = new ArrayList<>();

        for (String filename: filenames) {
            threadArray.add(new LineCounterThread(new File(filename)));
        }

        return threadArray;
    }

    public static void main(String[] args) {

        List<LineCounterThread> threads = filenamesToArrayOffLineCounters(args);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futureTasks = new ArrayList<>();
        Integer numberOfLines = 0;

        long startTime = System.currentTimeMillis();

        for (LineCounterThread thread : threads) {
            futureTasks.add(executor.submit(thread));
        }

        for (Future<Integer> task : futureTasks) {
            try {
                numberOfLines += task.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Time of execution: " + (endTime - startTime) + "milliseconds");
        System.out.println("Number of lines: " + numberOfLines);

        executor.shutdown();
    }
}
