package hokmah.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hokmah.data.SaveHandler;
import hokmah.exception.HokmahException;
import hokmah.task.TaskList;

class CommandHandlerTest {
    private CommandHandler commandHandler;
    private TaskList taskList;
    private MessageHandler messageHandler;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        taskList = new TaskList();
        Path tempFile = tempDir.resolve("test.txt");
        SaveHandler saveHandler = new SaveHandler(tempFile.toString());
        messageHandler = new MessageHandler();
        commandHandler = new CommandHandler(taskList, saveHandler, messageHandler);
    }

    @Test
    void testAddTodo() throws HokmahException {
        String[] response = commandHandler.addTodo(new String[]{"todo", "Read Book"});

        assertTrue(response[0].contains("Ok sure, I've added this task:"));
        assertTrue(response[1].contains("Read Book"));
        assertTrue(response[2].contains("Now you have 1 tasks in the list. What else do you want?"));
        assertEquals(1, taskList.size());
    }

    @Test
    void testAddDeadline() throws HokmahException {
        String[] response = commandHandler.addDeadline(new String[]{"deadline", "Submit report /by 2024-10-10 2359"});

        assertTrue(response[0].contains("Ok sure, I've added this task:"));
        assertTrue(response[1].contains("Submit report"));
        assertTrue(response[2].contains("Now you have 1 tasks in the list. What else do you want?"));
        assertEquals(1, taskList.size());
    }

    @Test
    void testMarkTask() throws HokmahException {
        commandHandler.addTodo(new String[]{"todo", "Read Book"});
        String[] response = commandHandler.markTask(new String[]{"mark", "1"});

        assertTrue(response[0].contains("Bleh! I've masked this task as done!"));
        assertTrue(response[1].contains("[X] Read Book"));
        assertTrue(response[2].contains("Are you happy?"));
    }

    @Test
    void testUnmarkTask() throws HokmahException {
        commandHandler.addTodo(new String[]{"todo", "Read Book"});
        commandHandler.markTask(new String[]{"mark", "1"});
        String[] response = commandHandler.unmarkTask(new String[]{"unmark", "1"});

        assertTrue(response[0].contains("So you have not done this task yet?"));
        assertTrue(response[1].contains("[ ] Read Book"));
        assertTrue(response[2].contains("That's sad. I've masked it as such."));
    }

    @Test
    void testDeleteTask() throws HokmahException {
        commandHandler.addTodo(new String[]{"todo", "Read Book"});
        String[] response = commandHandler.deleteTask(new String[]{"delete", "1"});

        assertTrue(response[0].contains("Ok sure, I've removed this task"));
        assertTrue(response[1].contains("Read Book"));
        assertTrue(response[2].contains("What else do you want?"));
        assertEquals(0, taskList.size());
    }

    @Test
    void testFindTask() throws HokmahException {
        commandHandler.addTodo(new String[]{"todo", "Read Book"});
        commandHandler.addTodo(new String[]{"todo", "Write Notes"});
        String[] response = commandHandler.findCommand(new String[]{"find", "Read"});

        assertTrue(response[0].contains("Here are the matching tasks in your list:"));
        assertTrue(response[0].contains("1.[T][ ] Read Book"));
        assertFalse(response[0].contains("2.[T][ ] Write Notes"));
    }

    @Test
    void testUnsupportedCommand() {
        String[] response = commandHandler.unsupportedCommand();

        assertNotNull(response);
        assertEquals(3, response.length);
        assertTrue(response[0].contains("Ooookay? Just what are you trying to do?"));
        assertTrue(response[1].contains("Can you ask something else?"));
        assertTrue(response[2].contains("If you don't know what to ask you can use the 'help' command"));
    }

    @Test
    void testExit() {
        String[] response = commandHandler.exit();

        assertNotNull(response);
        assertEquals(3, response.length);
        assertTrue(response[0].contains("Goodbye!"));
        assertTrue(response[1].contains("I hope you don't come back soon!"));
        assertTrue(response[2].contains("ヾ(＾ ∇ ＾)."));
    }
}
