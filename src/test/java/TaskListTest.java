import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.Todo;
import tasks.Deadline;
import tasks.Event;
import utils.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;
    private Task todoTask;
    private Task deadlineTask;
    private Task eventTask;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        todoTask = new Todo("Buy groceries", false);
        deadlineTask = new Deadline("Submit assignment", false, LocalDateTime.of(2026, 2, 15, 18, 0));
        eventTask = new Event("Team meeting", false,
                LocalDateTime.of(2026, 2, 10, 10, 0),
                LocalDateTime.of(2026, 2, 10, 11, 0));
    }

    @Test
    public void testEmptyTaskListSize() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void testAddSingleTask() {
        taskList.add(todoTask);
        assertEquals(1, taskList.size());
    }

    @Test
    public void testAddMultipleTasks() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);
        taskList.add(eventTask);
        assertEquals(3, taskList.size());
    }

    @Test
    public void testGetTask() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);

        Task retrieved = taskList.get(0);
        assertEquals(todoTask, retrieved);
        assertEquals("Buy groceries", retrieved.getTask());
    }

    @Test
    public void testRemoveTask() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);
        taskList.add(eventTask);

        assertEquals(3, taskList.size());
        taskList.remove(1);
        assertEquals(2, taskList.size());
    }

    @Test
    public void testRemoveFirstTask() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);

        taskList.remove(0);
        assertEquals(1, taskList.size());
        assertEquals(deadlineTask, taskList.get(0));
    }

    @Test
    public void testRemoveLastTask() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);
        taskList.add(eventTask);

        taskList.remove(2);
        assertEquals(2, taskList.size());
        assertEquals(deadlineTask, taskList.get(1));
    }

    @Test
    public void testGetAllTasks() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);
        taskList.add(eventTask);

        ArrayList<Task> allTasks = taskList.getAll();
        assertEquals(3, allTasks.size());
        assertEquals(todoTask, allTasks.get(0));
        assertEquals(deadlineTask, allTasks.get(1));
        assertEquals(eventTask, allTasks.get(2));
    }

    @Test
    public void testConstructorWithInitialTasks() {
        ArrayList<Task> initialTasks = new ArrayList<>();
        initialTasks.add(todoTask);
        initialTasks.add(deadlineTask);

        TaskList newTaskList = new TaskList(initialTasks);
        assertEquals(2, newTaskList.size());
        assertEquals(todoTask, newTaskList.get(0));
        assertEquals(deadlineTask, newTaskList.get(1));
    }

    @Test
    public void testToString() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);

        String output = taskList.toString();
        assertTrue(output.contains("1."));
        assertTrue(output.contains("2."));
        assertTrue(output.contains("Buy groceries"));
        assertTrue(output.contains("Submit assignment"));
    }

    @Test
    public void testTaskListOrder() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);
        taskList.add(eventTask);

        assertEquals(todoTask, taskList.get(0));
        assertEquals(deadlineTask, taskList.get(1));
        assertEquals(eventTask, taskList.get(2));
    }

    @Test
    public void testGetInvalidIndex() {
        taskList.add(todoTask);

        assertThrows(IndexOutOfBoundsException.class, () -> taskList.get(5));
    }

    @Test
    public void testRemoveInvalidIndex() {
        taskList.add(todoTask);

        assertThrows(IndexOutOfBoundsException.class, () -> taskList.remove(5));
    }

    @Test
    public void testAddAndRemoveSequence() {
        taskList.add(todoTask);
        taskList.add(deadlineTask);
        taskList.remove(0);
        taskList.add(eventTask);

        assertEquals(2, taskList.size());
        assertEquals(deadlineTask, taskList.get(0));
        assertEquals(eventTask, taskList.get(1));
    }
}
