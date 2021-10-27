package zad3_2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String DEFAULT_ADDRESS = "localhost";
    private static final Integer DEFAULT_PORT = 7777;

    private transient static Socket connection;

    private static ClientListener listener;

    private static void setupClient() throws IOException {

        connection = new Socket(DEFAULT_ADDRESS, DEFAULT_PORT);

        listener = new ClientListener(connection);
        listener.start();
    }

    private static void closeClient() throws IOException {

        listener.stopListener();
        connection.close();
    }

    private static String createMessage(Scanner input) {

        System.out.println("Podaj wiadomosc:");

        return input.nextLine();
    }

    private static boolean sendMessage(String message) throws IOException {

        OutputStream outputStream = connection.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(message);
        System.out.println("Wiadomość została wysłana");

        return true;
    }

    private static void userLoop() throws IOException {

        Scanner input = new Scanner(System.in);

        boolean isLoopRunning = true;

        while (isLoopRunning) {

            System.out.println("Wybierz opcje (0 - wyjscie, 1 - wpisz wiadomość)");
            int option = Integer.parseInt(input.nextLine());

            if (option == 0) {
                isLoopRunning = false;
            }
            else if (option == 1) {

                try {
                    String message = createMessage(input);
                    sendMessage(message);
                }
                catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    public static void main(String[] args) {

        try {

            setupClient();

            userLoop();

            closeClient();
        }

        catch (IOException e) {
            System.out.println("Połączenie z serwerem nie powiodło się");
            System.exit(0);
        }
    }
}