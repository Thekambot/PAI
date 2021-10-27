package zad1;

import java.io.File;
import java.util.concurrent.Callable;

public class LineCounterThread extends LineCounter implements Callable<Integer> {

    private final File textFile;

    public LineCounterThread(File textFile) {
        this.textFile = textFile;
    }

    @Override
    public Integer call() {
        return countLines(textFile);
    }
}
