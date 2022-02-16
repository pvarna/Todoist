package bg.sofia.uni.fmi.mjt.todoist.server;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandParser;
import bg.sofia.uni.fmi.mjt.todoist.command.handlers.HandlerCreator;
import bg.sofia.uni.fmi.mjt.todoist.command.handlers.HelpHandler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {
    private static final int BUFFER_SIZE = 2048;
    private static final String HOST = "localhost";

    private final int port;
    private boolean isServerWorking;

    private ByteBuffer buffer;
    private Selector selector;

    private CommandHandler commandHandler;
    private final Map<SocketChannel, String> connections;

    public Server(int port) {
        this.port = port;
        this.connections = new HashMap<>();
    }

    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            this.selector = Selector.open();
            this.configureServerSocketChannel(serverSocketChannel, this.selector);

            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
            this.isServerWorking = true;

            while (this.isServerWorking) {
                try {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }

                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isReadable()) {
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            String clientInput = getClientInput(clientChannel);

                            if (clientInput == null) {
                                System.out.println("User " + this.getUser(clientChannel) + " disconnected from the server");
                                continue;
                            }

                            Command command = null;
                            String output;
                            try {
                                command = CommandParser.buildCommand(clientInput);
                                String user = this.getUser(clientChannel);
                                CommandHandler.assertCommandIsValid(command, user);

                                this.commandHandler = HandlerCreator.of(command, user);
                                System.out.println(this.commandHandler instanceof HelpHandler);
                                output = this.commandHandler.execute();
                                this.updateSocketChannels(command, clientChannel);

                            } catch (RuntimeException e) {
                                output = e.getMessage();
                            }

                            if (this.connections.containsKey(clientChannel)) {
                                System.out.println("Command received from " + this.connections.get(clientChannel) + ": " + command);
                            }

                            writeClientOutput(clientChannel, output);

                        } else if (key.isAcceptable()) {
                            accept(this.selector, key);
                        }

                        keyIterator.remove();
                    }

                } catch (IOException e) {
                    /// TODO: something else
                    System.out.println("Error occurred while processing client request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            /// TODO: something else
            throw new UncheckedIOException("failed to start server", e);
        }
    }

    public void stop() {
        this.isServerWorking = false;
        if (selector.isOpen()) {
            selector.wakeup();
        }
    }

    private void configureServerSocketChannel(ServerSocketChannel channel, Selector selector) throws IOException {
        channel.bind(new InetSocketAddress(HOST, this.port));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    private String getClientInput(SocketChannel clientChannel) throws IOException {
        this.buffer.clear();

        int readBytes = clientChannel.read(this.buffer);
        if (readBytes < 0) {
            clientChannel.close();
            return null;
        }

        this.buffer.flip();

        byte[] clientInputBytes = new byte[this.buffer.remaining()];
        this.buffer.get(clientInputBytes);

        return new String(clientInputBytes, StandardCharsets.UTF_8);
    }

    private void writeClientOutput(SocketChannel clientChannel, String output) throws IOException {
        this.buffer.clear();
        this.buffer.put(output.getBytes());
        this.buffer.flip();

        clientChannel.write(this.buffer);
    }

    private void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = socketChannel.accept();

        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
    }

    private String getUser(SocketChannel channel) {
        return this.connections.getOrDefault(channel, null);
    }

    private void updateSocketChannels(Command command, SocketChannel clientChannel) {
        if (command.mainCommand().equals("LOGIN")) {
            this.connections.put(clientChannel, command.arguments().get(0));
        }
    }

    public static void main(String[] args) {
        Server server = new Server(3945);

        server.start();
    }
}
