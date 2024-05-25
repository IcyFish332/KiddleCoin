package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SavingGoalTest {
    private SavingGoal savingGoal;

    @BeforeEach
    void setUp() {
        // Set up a SavingGoal object with predefined values for testing
        savingGoal = new SavingGoal("New Bike", "Save money for a new bike", 500.0, 50.0);
    }

    @Test
    void testGetName() {
        // Test the getName() method to ensure it returns the expected name
        assertEquals("New Bike", savingGoal.getName(), "Name should be 'New Bike'");
    }

    @Test
    void testSetName() {
        // Test the setName() method to ensure it correctly updates the name
        savingGoal.setName("New Laptop");
        assertEquals("New Laptop", savingGoal.getName(), "Name should be updated to 'New Laptop'");
    }

    @Test
    void testGetDescription() {
        // Test the getDescription() method to ensure it returns the expected description
        assertEquals("Save money for a new bike", savingGoal.getDescription(), "Description should be 'Save money for a new bike'");
    }

    @Test
    void testSetDescription() {
        // Test the setDescription() method to ensure it correctly updates the description
        savingGoal.setDescription("Save money for a new laptop");
        assertEquals("Save money for a new laptop", savingGoal.getDescription(), "Description should be updated to 'Save money for a new laptop'");
    }

    @Test
    void testGetTargetAmount() {
        // Test the getTargetAmount() method to ensure it returns the expected target amount
        assertEquals(500.0, savingGoal.getTargetAmount(), "Target amount should be 500.0");
    }

    @Test
    void testSetTargetAmount() {
        // Test the setTargetAmount() method to ensure it correctly updates the target amount
        savingGoal.setTargetAmount(1000.0);
        assertEquals(1000.0, savingGoal.getTargetAmount(), "Target amount should be updated to 1000.0");
    }

    @Test
    void testGetReward() {
        // Test the getReward() method to ensure it returns the expected reward
        assertEquals(50.0, savingGoal.getReward(), "Reward should be 50.0");
    }

    @Test
    void testSetReward() {
        // Test the setReward() method to ensure it correctly updates the reward
        savingGoal.setReward(100.0);
        assertEquals(100.0, savingGoal.getReward(), "Reward should be updated to 100.0");
    }

    @Test
    void testIsGoalReached() {
        // Test the isGoalReached() method to ensure it correctly determines if the goal is reached
        assertFalse(savingGoal.isGoalReached(400.0), "Goal should not be reached with 400.0 savings");
        assertTrue(savingGoal.isGoalReached(500.0), "Goal should be reached with 500.0 savings");
        assertTrue(savingGoal.isGoalReached(600.0), "Goal should be reached with 600.0 savings");
    }

}

