package keef.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    void addTask_increasesSize() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("Buy milk");

        assertEquals(0, taskList.getSize());

        taskList.addTask(todo);

        assertEquals(1, taskList.getSize());
        assertEquals("Buy milk", taskList.getTask(0).getDescription());
    }

    @Test
    void deleteTask_decreasesSize() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("Walk dog");

        taskList.addTask(todo);
        assertEquals(1, taskList.getSize());

        taskList.deleteTask(todo);
        assertEquals(0, taskList.getSize());
    }

    @Test
    void markTask_marksAsDone() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("Read book");

        taskList.addTask(todo);
        todo.markAsDone();

        assertTrue(todo.isDone());
    }

    @Test
    void unmarkTask_marksAsUndone() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("Write essay");

        taskList.addTask(todo);
        todo.markAsDone();
        assertTrue(todo.isDone());

        todo.markAsUndone();
        assertFalse(todo.isDone());
    }
}
