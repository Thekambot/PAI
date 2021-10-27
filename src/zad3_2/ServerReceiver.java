package zad3_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerReceiver extends Thread {

    private final ServerSocket server;
    private final List<ServerAdder> threadList;

    public ServerReceiver(ServerSocket server, List<ServerAdder> threadList) {
        this.server = server;
        this.threadList = threadList;
    }

    private Socket newConnection() throws IOException {
        return server.accept();
    }

    private ServerAdder initThreadForClient(Socket client) throws IOException {
        ServerAdder thread = new ServerAdder(client);
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Czekam na połączenie z klientem");
                Socket newClient = newConnection();
                System.out.println("Utworzono połączenie z klientem");

                ServerAdder clientThread = initThreadForClient(newClient);
                threadList.add(clientThread);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
