import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 9090;

        try (
            Socket socket = new Socket(host, port);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Read the server greeting
            System.out.println(serverIn.readLine());

            String line;
            System.out.print("> ");
            while ((line = stdin.readLine()) != null) {
                serverOut.println(line);
                String reply = serverIn.readLine(); // blocks until server replies
                if (reply == null) break;
                System.out.println(reply);
                if ("Goodbye!".equalsIgnoreCase(reply)) break;
                System.out.print("> ");
            }
        }
    }
}