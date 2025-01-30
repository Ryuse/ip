package hokmah.ui;

public class UiHandler {

    public UiHandler(){

    }

    /**
     * Displays decorative separator line.
     */
    public void showLine(){
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
    }

    /**
     * Displays application welcome message and logo.
     */
    public void showWelcomeMessage(){
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

    /**
     * Displays exit message when closing application.
     */
    public void showExitMessage(){
        String message = "Goodbye! I hope you come back soon! ヾ(＾ ∇ ＾).";
        System.out.println(message);
    }
}
