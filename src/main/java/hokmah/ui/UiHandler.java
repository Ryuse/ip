package hokmah.ui;

public class UiHandler {

    public void showLine() {
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
    }

    public void showWelcomeMessage() {
        String logo = ",--.  ,--.         ,--.                         ,--.     \n"
                + "|  '--'  |  ,---.  |  |,-.  ,--,--,--.  ,--,--. |  ,---. \n"
                + "|  .--.  | | .-. | |     /  |        | ' ,-.  | |  .-.  |\n"
                + "|  |  |  | ' '-' ' |  \\  \\  |  |  |  | \\ '-'  | |  | |  |\n"
                + "`--'  `--'  `---'  `--'`--' `--`--`--'  `--`--' `--' `--' \n";

        String message = "Hello, I am\n" +
                logo + "\n" +
                "What can I do for you?";
        System.out.println(message);
        showLine();
    }

    public void showExitMessage() {
        String message = "Goodbye! I hope you come back soon! ヾ(＾ ∇ ＾).";
        System.out.println(message);
    }
}
