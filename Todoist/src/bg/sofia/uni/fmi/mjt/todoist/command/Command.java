package bg.sofia.uni.fmi.mjt.todoist.command;

import java.util.List;

public record Command(String mainCommand, List<String> arguments) {
}
