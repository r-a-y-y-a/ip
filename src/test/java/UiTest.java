import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;
import utils.Storage;
import utils.TaskList;
import utils.UI;

/**
 * Unit tests for the UI class.
 * Tests command processing, output formatting, and task management operations.
 */
public class UiTest {
    private UI ui;
    private TaskList taskList;
    private Storage storage;
    private String testFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        ui = new UI();
        taskList = new TaskList();

        // Create a temporary test file
        testFilePath = "test_data/test_fishball.txt";
        File dir = new File("test_data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
        testFile.createNewFile();

        storage = new Storage(testFilePath);
    }

    // Tests for adding tasks
    @Test
    public void testAddTodoTask() {
        String response = ui.processCommand("todo Buy groceries", taskList, storage);
        assertTrue(response.contains("Got it"));
        assertTrue(response.contains("added this task"));
        assertTrue(response.contains("Buy groceries"));
        assertEquals(1, taskList.size());
    }

    @Test
    public void testAddDeadlineTask() {
        String response = ui.processCommand("deadline Submit project /by 15-03-2024 2359", taskList, storage);
        assertTrue(response.contains("Got it"));
        assertTrue(response.contains("added this task"));
        assertTrue(response.contains("Submit project"));
        assertEquals(1, taskList.size());
    }

    @Test
    public void testAddEventTask() {
        String response = ui.processCommand("event Team meeting "
                + "/from 20-02-2024 1000 /to 20-02-2024 1100", taskList, storage);
        assertTrue(response.contains("Got it"));
        assertTrue(response.contains("added this task"));
        assertTrue(response.contains("Team meeting"));
        assertEquals(1, taskList.size());
    }

    @Test
    public void testAddEmptyTodo() {
        String response = ui.processCommand("todo", taskList, storage);
        assertTrue(response.contains("OOPS"));
        assertTrue(response.contains("description"));
        assertEquals(0, taskList.size());
    }

    @Test
    public void testAddInvalidDeadline() {
        String response = ui.processCommand("deadline No deadline specified", taskList, storage);
        assertTrue(response.contains("OOPS"));
        assertTrue(response.contains("/by"));
        assertEquals(0, taskList.size());
    }

    // Tests for list command
    @Test
    public void testListEmpty() {
        String response = ui.processCommand("list", taskList, storage);
        assertTrue(response.contains("Here are the tasks in your list"));
    }

    @Test
    public void testListWithTasks() {
        taskList.add(new Todo("Buy groceries", false));
        taskList.add(new Deadline("Submit project", false, LocalDateTime.of(2024, 3, 15, 23, 59)));

        String response = ui.processCommand("list", taskList, storage);
        assertTrue(response.contains("Here are the tasks in your list"));
        assertTrue(response.contains("1."));
        assertTrue(response.contains("2."));
        assertTrue(response.contains("Buy groceries"));
        assertTrue(response.contains("Submit project"));
    }

    // Tests for mark/unmark command
    @Test
    public void testMarkTask() {
        taskList.add(new Todo("Buy groceries", false));
        String response = ui.processCommand("mark 1", taskList, storage);
        assertTrue(response.contains("marked this task as done"));
        assertTrue(taskList.get(0).isDone());
    }

    @Test
    public void testUnmarkTask() {
        taskList.add(new Todo("Buy groceries", true));
        String response = ui.processCommand("unmark 1", taskList, storage);
        assertTrue(response.contains("marked this task as not done"));
        assertTrue(!taskList.get(0).isDone());
    }

    @Test
    public void testMarkInvalidIndex() {
        taskList.add(new Todo("Task", false));
        String response = ui.processCommand("mark 5", taskList, storage);
        assertTrue(response.contains("OOPS"));
        assertTrue(response.contains("out of range"));
    }

    @Test
    public void testUnmarkInvalidIndex() {
        taskList.add(new Todo("Task", false));
        String response = ui.processCommand("unmark 10", taskList, storage);
        assertTrue(response.contains("OOPS"));
        assertTrue(response.contains("out of range"));
    }

    // Tests for delete command
    @Test
    public void testDeleteTask() {
        taskList.add(new Todo("Task 1", false));
        taskList.add(new Todo("Task 2", false));

        String response = ui.processCommand("delete 1", taskList, storage);
        assertTrue(response.contains("removed this task"));
        assertEquals(1, taskList.size());
        assertEquals("Task 2", taskList.get(0).getTask());
    }

    @Test
    public void testDeleteInvalidIndex() {
        taskList.add(new Todo("Task", false));
        String response = ui.processCommand("delete 5", taskList, storage);
        assertTrue(response.contains("OOPS"));
        assertTrue(response.contains("out of range"));
    }

    @Test
    public void testDeleteMissingIndex() {
        String response = ui.processCommand("delete", taskList, storage);
        assertTrue(response.contains("OOPS"));
    }

    // Tests for find command
    @Test
    public void testFindTask() {
        taskList.add(new Todo("Buy groceries", false));
        taskList.add(new Todo("Read book", false));

        String response = ui.processCommand("find groceries", taskList, storage);
        assertTrue(response.contains("matching tasks"));
        assertTrue(response.contains("Buy groceries"));
        assertTrue(!response.contains("Read book"));
    }

    @Test
    public void testFindNoMatches() {
        taskList.add(new Todo("Buy groceries", false));

        String response = ui.processCommand("find nonexistent", taskList, storage);
        assertTrue(response.contains("No matching tasks found"));
    }

    @Test
    public void testFindMultipleMatches() {
        taskList.add(new Todo("Buy milk", false));
        taskList.add(new Todo("Buy bread", false));
        taskList.add(new Todo("Sell car", false));

        String response = ui.processCommand("find Buy", taskList, storage);
        assertTrue(response.contains("Buy milk"));
        assertTrue(response.contains("Buy bread"));
        assertTrue(!response.contains("Sell car"));
    }

    @Test
    public void testFindMissingKeyword() {
        String response = ui.processCommand("find", taskList, storage);
        assertTrue(response.contains("OOPS"));
        assertTrue(response.contains("keyword"));
    }

    // Tests for reminder command
    @Test
    public void testReminderNoUpcoming() {
        // Add a task with a deadline far in the future
        taskList.add(new Deadline("Far future", false, LocalDateTime.now().plusMonths(2)));
        String response = ui.processCommand("reminder", taskList, storage);
        assertTrue(response.contains("No upcoming deadlines"));
    }

    @Test
    public void testReminderWithUpcoming() {
        // Add a task with a deadline within 7 days
        taskList.add(new Deadline("Upcoming", false, LocalDateTime.now().plusDays(3)));
        String response = ui.processCommand("reminder", taskList, storage);
        assertTrue(response.contains("upcoming deadlines/events"));
        assertTrue(response.contains("Upcoming"));
    }

    @Test
    public void testReminderIgnoresCompleted() {
        // Add a completed task with a deadline within 7 days
        Task completed = new Deadline("Done task", true, LocalDateTime.now().plusDays(2));
        taskList.add(completed);

        String response = ui.processCommand("reminder", taskList, storage);
        assertTrue(response.contains("No upcoming deadlines"));
    }

    @Test
    public void testReminderWithEvent() {
        // Add an event that ends within 7 days
        taskList.add(new Event("Meeting", false,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2)));

        String response = ui.processCommand("reminder", taskList, storage);
        assertTrue(response.contains("upcoming deadlines/events"));
        assertTrue(response.contains("Meeting"));
    }

    // Tests for bye command
    @Test
    public void testByeCommand() {
        String response = ui.processCommand("bye", taskList, storage);
        assertTrue(response.contains("Bye. Hope to see you again soon!"));
    }

    // Tests for empty input
    @Test
    public void testEmptyInput() {
        String response = ui.processCommand("", taskList, storage);
        assertTrue(response.contains("OOPS"));
        assertTrue(response.contains("empty input"));
    }

    // Tests for invalid command
    @Test
    public void testInvalidCommand() {
        String response = ui.processCommand("invalid", taskList, storage);
        assertTrue(response.contains("OOPS"));
        assertTrue(response.contains("don't recognize that command"));
    }

    // Tests for command with extra parameters
    @Test
    public void testListWithExtraParameters() {
        String response = ui.processCommand("list extra params", taskList, storage);
        assertTrue(response.contains("OOPS"));
    }

    // Tests for task count updates
    @Test
    public void testTaskCountAfterAdd() {
        ui.processCommand("todo Task 1", taskList, storage);
        assertEquals(1, taskList.size());

        ui.processCommand("todo Task 2", taskList, storage);
        assertEquals(2, taskList.size());
    }

    @Test
    public void testTaskCountAfterDelete() {
        ui.processCommand("todo Task 1", taskList, storage);
        ui.processCommand("todo Task 2", taskList, storage);
        assertEquals(2, taskList.size());

        ui.processCommand("delete 1", taskList, storage);
        assertEquals(1, taskList.size());
    }

    // Tests for storage persistence
    @Test
    public void testStoragePersistenceAfterAdd() {
        ui.processCommand("todo Task 1", taskList, storage);
        ArrayList<Task> reloadedTasks = storage.load();
        assertEquals(1, reloadedTasks.size());
        assertEquals("Task 1", reloadedTasks.get(0).getTask());
    }

    @Test
    public void testStoragePersistenceAfterMark() {
        ui.processCommand("todo Task 1", taskList, storage);
        ui.processCommand("mark 1", taskList, storage);

        ArrayList<Task> reloadedTasks = storage.load();
        assertTrue(reloadedTasks.get(0).isDone());
    }

    @Test
    public void testStoragePersistenceAfterDelete() {
        ui.processCommand("todo Task 1", taskList, storage);
        ui.processCommand("todo Task 2", taskList, storage);
        ui.processCommand("delete 1", taskList, storage);

        ArrayList<Task> reloadedTasks = storage.load();
        assertEquals(1, reloadedTasks.size());
        assertEquals("Task 2", reloadedTasks.get(0).getTask());
    }

    // Cleanup after tests
    @BeforeEach
    public void cleanup() {
        // This would be called after all tests to clean up test files
    }
}
