package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ParentAccountTest {

    private ParentAccount parentAccount;
    private AccountManager accountManager;

    @BeforeEach
    void setUp() {
        parentAccount = new ParentAccount("P001", "Bob", "password123");
    }

    @Test
    void testAddChildAccount() {
        // Initially, the child account ID set should be empty
        assertTrue(parentAccount.getChildAccountIds().isEmpty(), "Child account ID set should initially be empty");

        // Add a child account ID
        parentAccount.addChildAccount("C001");
        assertTrue(parentAccount.getChildAccountIds().contains("C001"), "Child account ID should be added to the set");
        assertEquals(1, parentAccount.getChildAccountIds().size(), "There should be one child account ID in the set");

        // Add another child account ID
        parentAccount.addChildAccount("C002");
        assertTrue(parentAccount.getChildAccountIds().contains("C002"), "Second child account ID should be added to the set");
        assertEquals(2, parentAccount.getChildAccountIds().size(), "There should be two child account IDs in the set");

        // Try to add a duplicate child account ID
        parentAccount.addChildAccount("C001");
        assertEquals(2, parentAccount.getChildAccountIds().size(), "Set size should not change when adding a duplicate child account ID");
        assertTrue(parentAccount.getChildAccountIds().contains("C001"), "Duplicate ID should still be present in the set");
    }

    @Test
    void testRemoveChildAccount() {
        // Add child account IDs
        parentAccount.addChildAccount("C001");
        parentAccount.addChildAccount("C002");

        // Remove a child account ID
        parentAccount.removeChildAccount("C001");
        assertFalse(parentAccount.getChildAccountIds().contains("C001"), "Removed child account ID should not be present in the set");
        assertEquals(1, parentAccount.getChildAccountIds().size(), "There should be one child account ID remaining in the set");

        // Try to remove a non-existent child account ID
        parentAccount.removeChildAccount("C003");
        assertEquals(1, parentAccount.getChildAccountIds().size(), "Size of the set should remain unchanged after attempting to remove a non-existent ID");
    }

    @Test
    void testGetChildAccountIdsIndependence() {
        // Add child account ID and get the returned set
        parentAccount.addChildAccount("C001");
        Set<String> childIds = parentAccount.getChildAccountIds();
        assertEquals(1, childIds.size(), "Initial set should contain one child ID");

        // Modify the returned set and check if the original set is not modified
        childIds.add("C003");
        Set<String> originalChildIds = parentAccount.getChildAccountIds();
        assertEquals(1, originalChildIds.size(), "Modifying the returned set should not affect the original set");
        assertFalse(originalChildIds.contains("C003"), "Original set should not contain C003 after modification");
    }
}
