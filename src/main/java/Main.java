import core.AccountManager;
import data.DataManager;
import ui.MainFrame;


public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            DataManager dataManager = new DataManager();
            AccountManager accountManager = new AccountManager(dataManager);
            MainFrame mainFrame = new MainFrame(accountManager);
            mainFrame.setVisible(true);
        });
    }
}
