package bg.sofia.uni.fmi.mjt.todoist.command;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    private static final int NUMBER_OF_QUOTES_PER_SINGLE_ARGUMENT = 2;
    private static final int START_INDEX_OF_ARGUMENTS = 1;
    private static final int WRONG_QUOTES_REMAINDER = 1;
    private static final int MAIN_COMMAND = 0;
    private static final char QUOTES = '\"';

    public static Command buildCommand(String commandLine) {
        Utils.assertNonNull(commandLine, "Command line");
        Utils.assertNonEmpty(commandLine, "Command line");

        if (getNumberOfQuotes(commandLine) % NUMBER_OF_QUOTES_PER_SINGLE_ARGUMENT == WRONG_QUOTES_REMAINDER) {
            throw new InvalidCommandException("Invalid number of quotes");
        }

        return parseCommandLine(commandLine);
    }

    private static long getNumberOfQuotes(String line) {
        return line.chars().filter(ch -> ch == QUOTES).count();
    }

    private static String getQuotedText(String text, int index) {
        ++index;
        StringBuilder result = new StringBuilder();

        while (text.charAt(index) != QUOTES) {
            result.append(text.charAt(index++));
        }

        return result.toString();
    }

    private static String getArgument(String text, int index) {
        StringBuilder result = new StringBuilder();

        while (index < text.length() && !Character.isSpaceChar(text.charAt(index))) {
            if (text.charAt(index) == QUOTES) {
                String toAdd = CommandParser.getQuotedText(text, index);

                result.append("\"").append(toAdd).append("\"");
                index += toAdd.length() + NUMBER_OF_QUOTES_PER_SINGLE_ARGUMENT;

            } else {
                result.append(text.charAt(index++));
            }
        }

        return result.toString();
    }

    private static Command parseCommandLine(String commandLine) {

        List<String> arguments = new ArrayList<>();

        for (int i = 0; i < commandLine.length(); ++i) {
            String toAdd = "";

            if (commandLine.charAt(i) == QUOTES) {
                toAdd = CommandParser.getQuotedText(commandLine, i);
                ++i;
            } else if (!Character.isSpaceChar(commandLine.charAt(i))) {
                toAdd = CommandParser.getArgument(commandLine, i);
            }

            if (!toAdd.isEmpty()) {
                arguments.add(toAdd);
                i += toAdd.length();
            }
        }

        return new Command(arguments.get(MAIN_COMMAND).toUpperCase(),
                arguments.subList(START_INDEX_OF_ARGUMENTS, arguments.size()));
    }
}
