package hokmah.exception;

import static hokmah.Hokmah.DATETIME_INPUT_FORMAT;

/**
 * Custom exception class for task management errors.
 * Encapsulates specific error types and formatted messages.
 */
public class HokmahException extends Exception {

    /**
     * Enumeration of possible exception types.
     */
    public enum ExceptionType {
        NO_SAVE_FILE,
        NO_NAME,
        NO_INDEX,
        INVALID_DEADLINE_FORMAT,
        EVENT_NO_TIME_START,
        EVENT_NO_TIME_END,
        EVENT_END_BEFORE_START,
        TASK_NOT_FOUND,
        NO_UPCOMING_ON_DATE,
        SEARCH_FAILED

    }

    /**
     * Constructs exception with specific error type.
     *
     * @param type Category of exception
     * @throws HokmahException Always throws new instance
     */
    public HokmahException(ExceptionType type) throws HokmahException {
        String message = "";


        if (type == ExceptionType.NO_SAVE_FILE) {
            message = """
                    Seems like this is our first time meeting.
                    """;

        } else if (type == ExceptionType.NO_NAME) {
            message = """
                    Just what are you going to do?
                    Can you at least tell me the name?
                    """;
        } else if (type == ExceptionType.NO_INDEX) {
            message = """
                    Can you at least give me a proper index for the command?
                    """;
        } else if (type == ExceptionType.INVALID_DEADLINE_FORMAT) {
            message = """
                    Ok? Can you put the deadline command properly?
                    The format is: deadline [name] /by [{input_datetime_format}]
                    """;

        } else if (type == ExceptionType.EVENT_NO_TIME_START) {
            message = """
                    Can you put when your event will start and end properly?
                    The format is: event [name] /from [{input_datetime_format}] /to [{input_datetime_format}]
                    """;


        } else if (type == ExceptionType.EVENT_NO_TIME_END) {
            message = """
                    Can you put when your event will end properly?
                    The format is: event [name] /from [{input_datetime_format}] /to [{input_datetime_format}]
                    """;


        } else if (type == ExceptionType.EVENT_END_BEFORE_START) {
            message = """
                    Wait, is your event start date seriously after the end date?
                    Check your dates again!
                    The format is: event [name] /from [{input_datetime_format}] /to [{input_datetime_format}]
                    """;
        } else if (type == ExceptionType.TASK_NOT_FOUND) {
            message = """
                    The task you are trying to perform an action on cannot be found.
                    Are you sure you put the right task index?""";

        } else if (type == ExceptionType.NO_UPCOMING_ON_DATE) {
            message = """
                    Can you put when want to see the upcoming tasks properly?
                    Format is: upcomingOn [{input_datetime_format}]""";

        } else if (type == ExceptionType.SEARCH_FAILED) {
            message = """
                    Can you specify a search term? You're bad at following commands.
                    The format is: find [keyword]""";

        } else {
            message = """
                    Something went wrong..
                    This should not happen!
                    """;

        }

        message = message.replace("{input_datetime_format}", DATETIME_INPUT_FORMAT);

        throw new HokmahException(message);

    }

    public HokmahException(String message) {
        super(message);
    }

    public String[] getMessageLines() {
        return this.getMessage().split("\n");
    }
}
