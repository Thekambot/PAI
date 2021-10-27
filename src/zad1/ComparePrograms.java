package zad1;

public class ComparePrograms {

    public static void main(String[] args) {

        System.out.println("\n Single Thread:");
        SingleThreadLineCounter.main(args);

        System.out.println("\n Multi Thread:");
        MultiThreadLineCounter.main(args);
    }
}
