import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class EchoServer {
    private final int port;
    private final ExecutorService pool = Executors.newFixedThreadPool(16);

    public EchoServer(int port) { this.port = port; }

    public void start() throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("EchoServer listening on port " + port);
            while (true) {
                Socket client = server.accept(); // blocks
                pool.submit(() -> handle(client));
            }
        }
    }

    private void handle(Socket socket) {
        String remote = socket.getRemoteSocketAddress().toString();
        System.out.println("Connected: " + remote);
        try (
            socket;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true) // autoFlush
        ) {
            out.println("Hello! Type 'bye' to quit.");
            String line;
            while ((line = in.readLine()) != null) {
                if ("bye".equalsIgnoreCase(line.trim())) {
                    out.println("Goodbye!");
                    break;
                }
                out.println("echo: " + line);
            }
        } catch (IOException e) {
            System.err.println("I/O error with " + remote + ": " + e.getMessage());
        } finally {
            System.out.println("Disconnected: " + remote);
        }
    }

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 9090;
        new EchoServer(port).start();
    }
}