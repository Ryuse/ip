package hokmah;

import java.util.Scanner;

import hokmah.command.CommandHandler;
import hokmah.command.InputHandler;
import hokmah.command.MessageHandler;
import hokmah.data.SaveHandler;
import hokmah.exception.HokmahException;
import hokmah.task.TaskList;


/**
 * view.Main application class for the task management system.
 * Initializes core components and manages the program lifecycle.
 */
public class Hokmah {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";
    public static final String DEFAULT_FILE_DATA_LOCATION = "data/tasks.txt";
    public static final String LOGO = """
                ,--.  ,--.         ,--.                         ,--.
                |  '--'  |  ,---.  |  |,-.  ,--,--,--.  ,--,--. |  ,---.
                |  .--.  | | .-. | |     /  |        | ' ,-.  | |  .-.  |
                |  |  |  | ' '-' ' |  \\  \\  |  |  |  | \\ '-'  | |  | |  |
                `--'  `--'  `---'  `--'`--' `--`--`--'  `--`--' `--' `--'
                """;


    public static final String[] EXIT_COMMANDS = {"bye", "exit", "quit", "cya"};

    private static final Scanner scanner = new Scanner(System.in);
    protected TaskList tasks;
    protected MessageHandler ui;
    protected SaveHandler storage;
    protected InputHandler inputHandler;
    protected CommandHandler commandHandler;

    /**
     * Initializes application components.
     *
     * @param filePath Path for task data storage
     */
    public Hokmah(String ... filePath) {
        if (filePath.length < 1) {
            filePath = new String[]{DEFAULT_FILE_DATA_LOCATION};
        }

        tasks = new TaskList();
        ui = new MessageHandler();
        storage = new SaveHandler(filePath[0]);
        tasks.setTaskArrayList(storage.loadFromFile());

        commandHandler = new CommandHandler(tasks, storage, ui);
        inputHandler = new InputHandler(commandHandler);

        System.out.println(ui.getWelcomeMessage());

    }


    /**
     * Starts main application loop.
     */
    public void run() {
        messageHandler();
    }

    /**
     * Handles continuous user input processing.
     */
    public void messageHandler() {
        while (true) {

            String input = scanner.nextLine();

            System.out.println(ui.getMessageSeparatorLine());
            try {
                inputHandler.process(input);
            } catch (HokmahException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(ui.getMessageSeparatorLine());
        }
    }

    public String getResponse(String input) {
        try {
            return inputHandler.process(input);
        } catch (HokmahException e) {
            return e.getMessage();
        }
    }

    public static void main(String[] args) {
        new Hokmah(DEFAULT_FILE_DATA_LOCATION).run();
    }

}
