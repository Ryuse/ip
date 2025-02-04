package hokmah;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hokmah.exception.HokmahException;
import hokmah.task.Deadline;
import hokmah.task.Event;
import hokmah.task.Task;
import hokmah.task.ToDo;

public class HokmahTest {
    private Hokmah hokmah;
    private Path tempFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        tempFile = tempDir.resolve("test.txt");
        hokmah = new Hokmah(tempFile.toString());
    }

    @Test
    public void testAddTodo() throws HokmahException {
        hokmah.inputHandler.process("todo Buy milk");
        ArrayList<Task> tasks = hokmah.tasks.getTaskArrayList();

        assertEquals(1, tasks.size());
        assertInstanceOf(ToDo.class, tasks.get(0));
        assertEquals("Buy milk", tasks.get(0).getName());
    }

    @Test
    public void testAddDeadline() throws HokmahException {
        hokmah.inputHandler.process("deadline Submit report /by 2023-12-31 2359");
        ArrayList<Task> tasks = hokmah.tasks.getTaskArrayList();

        assertEquals(1, tasks.size());
        Deadline deadline = (Deadline) tasks.get(0);
        assertEquals("Submit report", deadline.getName());
        assertEquals(LocalDateTime.of(2023, 12, 31, 23, 59), deadline.getTimeEnd());
    }

    @Test
    public void testAddEvent() throws HokmahException {
        hokmah.inputHandler.process("event Meeting /from 2023-11-01 1400 /to 2023-11-01 1600");
        ArrayList<Task> tasks = hokmah.tasks.getTaskArrayList();

        assertEquals(1, tasks.size());
        Event event = (Event) tasks.get(0);
        assertEquals("Meeting", event.getName());
        assertEquals(LocalDateTime.of(2023, 11, 1, 14, 0), event.getTimeStart());
        assertEquals(LocalDateTime.of(2023, 11, 1, 16, 0), event.getTimeEnd());
    }

    @Test
    public void testMarkUnmarkTask() throws HokmahException {
        hokmah.inputHandler.process("todo Test task");
        hokmah.inputHandler.process("mark 1");
        assertTrue(hokmah.tasks.getTaskArrayList().get(0).isDone());

        hokmah.inputHandler.process("unmark 1");
        assertFalse(hokmah.tasks.getTaskArrayList().get(0).isDone());
    }

    @Test
    public void testDeleteTask() throws HokmahException {
        hokmah.inputHandler.process("todo Task to delete");
        assertEquals(1, hokmah.tasks.size());

        hokmah.inputHandler.process("delete 1");
        assertEquals(0, hokmah.tasks.size());
    }

    @Test
    public void testInvalidDeadlineCommand() {
        assertThrows(HokmahException.class, () ->
                hokmah.inputHandler.process("deadline Missing time")
        );
    }

    @Test
    public void testInvalidEventCommand() {
        assertThrows(HokmahException.class, () ->
                hokmah.inputHandler.process("event Invalid event format")
        );
    }

    @Test
    public void testSaveAndLoad() throws HokmahException {
        // Add test data
        hokmah.inputHandler.process("todo Saved task");
        hokmah.inputHandler.process("deadline Important /by 2023-12-31 2359");

        // Save and reload
        hokmah.storage.saveToFile(hokmah.tasks.getTaskArrayList());
        ArrayList<Task> loadedTasks = hokmah.storage.loadFromFile();


        assertEquals(2, loadedTasks.size());
        assertEquals("Saved task", loadedTasks.get(0).getName());
        assertEquals("Important", loadedTasks.get(1).getName());
    }

    @Test
    public void testTaskToString() throws HokmahException {
        hokmah.inputHandler.process("todo Test toString");
        Task task = hokmah.tasks.getTaskArrayList().get(0);
        assertTrue(task.toString().contains("[T][ ] Test toString"));

        hokmah.inputHandler.process("mark 1");
        assertTrue(task.toString().contains("[T][X] Test toString"));
    }

    @Test
    public void testInvalidTaskNumber() {
        assertThrows(HokmahException.class, () ->
                hokmah.inputHandler.process("mark 99")
        );
    }
}
