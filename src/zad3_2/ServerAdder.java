package zad3_2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.DelayQueue;

public class ServerAdder extends Thread {

    private final Socket socket;

    public ServerAdder(Socket client) {
        this.socket = client;
    }

    @Override
    public void run() {

        try {

            while (true) {

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                String alarm = (String) objectInputStream.readObject();

                System.out.println("Wiadomość od " + socket.toString() + " - " + alarm.toString());

            }
        }

        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
