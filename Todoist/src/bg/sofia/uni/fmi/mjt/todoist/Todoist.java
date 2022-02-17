package bg.sofia.uni.fmi.mjt.todoist;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandParser;
import bg.sofia.uni.fmi.mjt.todoist.server.Server;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

public class Todoist {

    private static final Set<String> availableServerCommands;
    private static final int PORT = 3945;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Server server = new Server(PORT);
        Thread thread = new Thread(server);

        String commandLine;

        while (true) {
            commandLine = scanner.nextLine();

            Command command = CommandParser.buildCommand(commandLine);
            execute(command, thread, server);

            if (command.mainCommand().equals("STOP")) {
                break;
            }
        }
    }

    private static void execute(Command command, Thread thread, Server server) throws InterruptedException {
        switch (command.mainCommand()) {
            case "START" -> start(thread);
            case "LOAD" -> loadFromFile(command.arguments().get(0), server);
            case "SAVE" -> saveToFile(command.arguments().get(0), server);
            case "STOP" -> stop(thread, server);
            default -> System.out.println("Invalid command, try again");
        }
    }

    private static void start(Thread thread) {
        thread.start();
    }

    private static void loadFromFile(String pathStr, Server server) {
        server.loadDatabase(Paths.get(pathStr));
    }

    private static void saveToFile(String pathStr, Server server) {
        server.saveDatabase(Paths.get(pathStr));
    }

    private static void stop(Thread thread, Server server) throws InterruptedException {
        server.stop();
        thread.join();
    }

    static {
        availableServerCommands = Set.of(
                "START",
                "LOAD",
                "SAVE",
                "STOP"
        );
    }
}
