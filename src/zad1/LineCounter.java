package zad1;

import java.io.*;
import java.util.concurrent.Callable;

public class LineCounter{

    public static int countLines(File textFile) {

        int numberOfLines = 0;

        try (LineNumberReader lineReader = new LineNumberReader(new FileReader(textFile))) {

            while (lineReader.readLine() != null);
            numberOfLines = lineReader.getLineNumber();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return numberOfLines;
    }
}
