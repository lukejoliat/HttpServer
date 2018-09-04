import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private Socket csocket;

    public Server() {}

    public Server(Socket csocket) {
        this.csocket = csocket;
    }


    public void listen() {
        try {
            ServerSocket ssock = new ServerSocket(8080);
            System.out.println("Listening");

            while (true) {
                Server myServer = new Server(ssock.accept());
                new Thread(myServer).start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
            PrintWriter out = new PrintWriter(csocket.getOutputStream());

            // Start sending our reply
            out.print("HTTP/ 200 \r\n");
            out.print("Content-Type: text/plain\r\n");
            out.print("Connection: close\r\n");
            out.print("\r\n");

            String line;
            while ((line = in.readLine()) != null) {
                if (line.length() == 0)
                    break;
            }
            out.print("Hello World");

            // close in, out, and socket
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}