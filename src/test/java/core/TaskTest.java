package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;
    private Date dueDate;

    @BeforeEach
    void setUp() {
        // Set up a Task object with predefined values for testing
        dueDate = new Date();
        task = new Task("Homework", "Complete your math homework", 10.0, dueDate);
    }

    @Test
    void testGetName() {
        // Test the getName() method to ensure it returns the expected name
        assertEquals("Homework", task.getName(), "The name should match the constructor value.");
    }

    @Test
    void testSetName() {
        // Test the setName() method to ensure it correctly updates the name
        task.setName("Science Project");
        assertEquals("Science Project", task.getName(), "The name should be updated to 'Science Project'.");
    }

    @Test
    void testGetDescription() {
        // Test the getDescription() method to ensure it returns the expected description
        assertEquals("Complete your math homework", task.getDescription(), "The description should match the constructor value.");
    }

    @Test
    void testSetDescription() {
        // Test the setDescription() method to ensure it correctly updates the description
        task.setDescription("Complete your science project");
        assertEquals("Complete your science project", task.getDescription(), "The description should be updated to 'Complete your science project'.");
    }

    @Test
    void testGetReward() {
        // Test the getReward() method to ensure it returns the expected reward
        assertEquals(10.0, task.getReward(), "The reward should match the constructor value.");
    }

    @Test
    void testSetReward() {
        // Test the setReward() method to ensure it correctly updates the reward
        task.setReward(20.0);
        assertEquals(20.0, task.getReward(), "The reward should be updated to 20.0.");
    }

    @Test
    void testIsTaskCompleted() {
        // Test the isTaskCompleted() method to ensure new tasks are not marked as completed
        assertFalse(task.isTaskCompleted(), "New tasks should be not completed.");
    }

    @Test
    void testSetCompleted() {
        // Test the setCompleted() method to ensure it correctly marks the task as completed
        task.setCompleted(true);
        assertTrue(task.isTaskCompleted(), "The task should be marked as completed.");
    }

    @Test
    void testGetDueDate() {
        // Test the getDueDate() method to ensure it returns the expected due date
        assertEquals(dueDate, task.getDueDate(), "The due date should match the constructor value.");
    }

    @Test
    void testSetDueDate() {
        // Test the setDueDate() method to ensure it correctly updates the due date
        Date newDueDate = new Date(dueDate.getTime() + 1000000000);
        task.setDueDate(newDueDate);
        assertEquals(newDueDate, task.getDueDate(), "The due date should be updated.");
    }

    //Boundary Test//

    @Test
    void testNameEmptyString() {
        // Test the Task constructor with an empty name
        Task emptyNameTask = new Task("", "Empty name task", 5.0, dueDate);
        assertEquals("", emptyNameTask.getName(), "The name should be an empty string.");
    }

    @Test
    void testNameNull() {
        // Test the Task constructor with a null name
        Task nullNameTask = new Task(null, "Null name task", 5.0, dueDate);
        assertNull(nullNameTask.getName(), "The name should be null.");
    }

    @Test
    void testRewardZero() {
        // Test the Task constructor with a zero reward
        Task zeroRewardTask = new Task("Zero Reward", "Task with zero reward", 0.0, dueDate);
        assertEquals(0.0, zeroRewardTask.getReward(), "The reward should be zero.");
    }

    @Test
    void testRewardNegative() {
        // Test the Task constructor with a negative reward
        Task negativeRewardTask = new Task("Negative Reward", "Task with negative reward", -10.0, dueDate);
        assertEquals(-10.0, negativeRewardTask.getReward(), "The reward should be negative.");
    }
}
