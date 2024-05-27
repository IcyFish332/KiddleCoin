import core.AccountManager;
import data.DataManager;
import ui.MainFrame;

/**
 * The entry point of the application.
 * This class initializes the data manager, account manager, and the main frame, and makes the main frame visible.
 *
 * @author Siyuan Lu
 */
public class Main {
    /**
     * The main method to start the application.
     * It uses SwingUtilities to ensure that the GUI creation and updates are performed on the Event Dispatch Thread.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            DataManager dataManager = new DataManager();
            AccountManager accountManager = new AccountManager(dataManager);
            MainFrame mainFrame = new MainFrame(accountManager);
            mainFrame.setVisible(true);
        });
    }
}
