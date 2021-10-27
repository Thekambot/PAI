package zad1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SingleThreadLineCounter {

    private static ArrayList<File> filenamesToArrayOfFiles(String[] filenames) {

        ArrayList<File> files = new ArrayList<>();

        for (String filename: filenames) {
            files.add(new File(filename));
        }

        return files;
    }

    public static void main(String[] args) {

        List<File> files = filenamesToArrayOfFiles(args);
        int numberOfLines = 0;

        long startTime = System.currentTimeMillis();

        for (File file: files) {
            numberOfLines += LineCounter.countLines(file);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Time of execution: " + (endTime - startTime) + "milliseconds");
        System.out.println("Number of lines: " + numberOfLines);
    }
}
