package bg.sofia.uni.fmi.mjt.todoist.client;

import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    private static final int NUMBER_OF_PORTS = 65535;
    private static final int BUFFER_SIZE = 5096;

    private ByteBuffer buffer;

    public void connectToServer(String serverHost, int serverPort) {
        Utils.assertNonNull(serverHost, "Server host");
        this.assertValidPort(serverPort);

        try (SocketChannel socketChannel = SocketChannel.open();
            Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(serverHost, serverPort));

            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);

            System.out.println("Connected to the server");

            while (true) {
                System.out.print("> ");
                String message = scanner.nextLine();

                if (message.equals("quit")) {
                    break;
                }

                this.sendMessageToServer(socketChannel, message);

                String reply = this.getServerReply(socketChannel);
                System.out.println(reply);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

    private void sendMessageToServer(SocketChannel socketChannel, String message) throws IOException {
        this.buffer.clear(); // switch to writing mode
        this.buffer.put(message.getBytes()); // buffer fill
        this.buffer.flip(); // switch to reading mode
        socketChannel.write(this.buffer); // buffer drain
    }

    private String getServerReply(SocketChannel socketChannel) throws IOException {
        this.buffer.clear(); // switch to writing mode
        socketChannel.read(this.buffer); // buffer fill
        this.buffer.flip(); // switch to reading mode

        // if buffer is a non-direct one, is has a wrapped array and we can get it
        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);
        return new String(byteArray, StandardCharsets.UTF_8); // buffer drain
    }

    private void assertValidPort(int port) {
        if (port < 0 || port >= NUMBER_OF_PORTS) {
            throw new IllegalArgumentException("The port number must be between 0 and 65534");
        }
    }

    public static void main(String[] args) {
        Client client = new Client();

        client.connectToServer("localhost", 3945);
    }
}
