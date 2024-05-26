package ParentBoundaryTest;

import core.AccountManager;
import core.ChildAccount;
import core.ParentAccount;
import data.DataManager;
import ui.parent.KidsListManagementFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.table.DefaultTableModel;


import static org.junit.jupiter.api.Assertions.*;

class KidsListMAnagementFrameTest {
    private KidsListManagementFrame kidsListManagementFrame;
    private AccountManager accountManager;
    private ParentAccount parentAccount;
    private ChildAccount childAccount;

    @BeforeEach
    void setUp() {
        DataManager dataManager = new DataManager();
        accountManager = new AccountManager(dataManager);
        parentAccount = new ParentAccount("parent1", "John Doe", "password");
        accountManager.saveAccount(parentAccount);

        ChildAccount child2 = new ChildAccount("child1", "Bob Smith", "password");
        child2.setSavings(1000.0);
        accountManager.saveAccount(child2);
        parentAccount.addChildAccount(child2.getAccountId());

        kidsListManagementFrame = new KidsListManagementFrame(accountManager, parentAccount);
    }


    @Test
    void testInitComponents() {
        // Check if the table is initialized correctly
        assertNotNull(kidsListManagementFrame.table);
        DefaultTableModel model = (DefaultTableModel) kidsListManagementFrame.table.getModel();
        assertEquals(1, model.getRowCount());
        assertEquals("child1", model.getValueAt(0, 1));
        assertEquals(1000.0, model.getValueAt(0, 2));
    }




}