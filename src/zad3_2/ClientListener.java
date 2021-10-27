package zad3_2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListener extends Thread {

    private ObjectInputStream objectInputStream;
    private final Socket socket;

    ClientListener(Socket socket) throws IOException {

        this.socket = socket;
    }

    public void run() {

        try {
            while (!Thread.currentThread().isInterrupted()) {

                objectInputStream = new ObjectInputStream(socket.getInputStream());

                String serverResponse = (String) objectInputStream.readObject();
                System.out.println(serverResponse);

            }
        }

        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopListener() throws IOException {

        objectInputStream.close();
    }
}
