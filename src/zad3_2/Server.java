package zad3_2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Server {

    private static final String DEFAULT_ADDRESS = "";
    private static final Integer DEFAULT_PORT = 7777;

    private static ServerSocket server;
    private static final List<ServerAdder> threadList = new ArrayList<ServerAdder>();
    private static final Scanner scanner = new Scanner(System.in);

    private static void createServer() throws IOException {
        server = new ServerSocket(DEFAULT_PORT);
    }

    private static void runServer() throws IOException {
        while (true) {
            try {
                String message = scanner.nextLine();

                for (ServerAdder client:
                     threadList) {

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        try {

            createServer();
            ServerReceiver serverReceiver = new ServerReceiver(server, threadList);
            serverReceiver.start();
            runServer();
        }

        catch (IOException e) {
            System.out.println("Błąd z połączeniem");
        }
        finally {
            System.exit(0);
        }

    }
}