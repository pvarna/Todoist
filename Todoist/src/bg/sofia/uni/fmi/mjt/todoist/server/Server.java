package bg.sofia.uni.fmi.mjt.todoist.server;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandParser;
import bg.sofia.uni.fmi.mjt.todoist.command.handlers.HandlerCreator;
import bg.sofia.uni.fmi.mjt.todoist.logger.DefaultLogger;
import bg.sofia.uni.fmi.mjt.todoist.logger.Level;
import bg.sofia.uni.fmi.mjt.todoist.logger.Logger;
import bg.sofia.uni.fmi.mjt.todoist.logger.LoggerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Server implements Runnable {
    private static final int BUFFER_SIZE = 5096;
    private static final String HOST = "localhost";
    private static final String FILE_SEPARATOR = " ";
    private static final int SECOND_ARGUMENT = 1;

    private final int port;
    private boolean isServerWorking;

    private ByteBuffer buffer;
    private Selector selector;

    private CommandHandler commandHandler;
    private final Logger logger;
    private final Map<SocketChannel, String> connections;

    public Server(int port) {
        this.port = port;
        this.connections = new HashMap<>();
        this.logger = new DefaultLogger(new LoggerOptions(this.getClass(), "logs"));
    }

    @Override
    public void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            System.out.println("The server has started");

            this.selector = Selector.open();
            this.configureServerSocketChannel(serverSocketChannel, this.selector);

            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
            this.isServerWorking = true;

            int counter = 0;
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
                                ++counter;

                                this.commandHandler = HandlerCreator.of(command, user);
                                output = this.commandHandler.execute();
                                this.updateSocketChannels(command, clientChannel);

                            } catch (RuntimeException e) {
                                logger.log(Level.WARN, LocalDateTime.now(),
                                        e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
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
                    logger.log(Level.ERROR, LocalDateTime.now(),
                            e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
                    System.out.println("Error occurred while processing client request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, LocalDateTime.now(),
                    e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
            System.out.println("Failed to start server");
        }
    }

    public void loadDatabase(Path path) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {

            List<String> lines = bufferedReader.lines().toList();
            String currentUsername = null;
            for (String current : lines) {
                if (current.charAt(0) == '>') {
                    currentUsername = current.split(FILE_SEPARATOR)[SECOND_ARGUMENT];
                    continue;
                }
                Command currentCommand = CommandParser.buildCommand(current);
                this.commandHandler = HandlerCreator.of(currentCommand, currentUsername);
                this.commandHandler.execute();
            }
        } catch (IOException e) {
            System.out.println("A problem occurred while reading from a file: " + e.getMessage());
        }

        System.out.println("Database loaded");
    }

    public void saveDatabase(Path path) {
        this.commandHandler.save(path);

        System.out.println("Data saved");
    }

    public void stop() {
        this.isServerWorking = false;
        if (selector.isOpen()) {
            selector.wakeup();
        }

        System.out.println("Stopping the server");
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
}
