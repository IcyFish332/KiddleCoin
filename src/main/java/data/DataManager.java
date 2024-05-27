package data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import core.Account;
import core.ChildAccount;
import core.ParentAccount;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the data persistence and retrieval for accounts using JSON files.
 * This class uses Gson for JSON serialization and deserialization.
 *
 * @author Siyuan Lu
 */
public class DataManager {
    private static final String DATA_FILE = "data/accounts.json";
    private Gson gson;

    /**
     * Constructs a DataManager and initializes the Gson instance with a custom deserializer.
     */
    public DataManager() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountDeserializer())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Saves the account to the JSON file.
     *
     * @param account the account to save
     */
    public void saveAccount(Account account) {
        Map<String, Account> accounts = loadData();
        accounts.put(account.getAccountId(), account);
        saveData(accounts);
    }

    /**
     * Retrieves an account by its ID from the JSON file.
     *
     * @param accountId the ID of the account to retrieve
     * @return the account with the specified ID, or null if not found
     */
    public Account getAccount(String accountId) {
        Map<String, Account> accounts = loadData();
        return accounts.get(accountId);
    }

    /**
     * Retrieves all accounts from the JSON file.
     *
     * @return a map of all accounts
     */
    public Map<String, Account> getAccounts() {
        return loadData();
    }

    /**
     * Loads account data from the JSON file.
     *
     * @return a map of accounts
     */
    private Map<String, Account> loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            // If the file does not exist, create a new file
            try {
                file.getParentFile().mkdirs(); // Create parent directories
                file.createNewFile(); // Create new file
                return new HashMap<>();
            } catch (IOException e) {
                e.printStackTrace();
                return new HashMap<>();
            }
        }

        try (FileReader reader = new FileReader(DATA_FILE)) {
            Type type = new TypeToken<Map<String, Account>>(){}.getType();
            Map<String, Account> accounts = gson.fromJson(reader, type);
            return accounts != null ? accounts : new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Saves account data to the JSON file.
     *
     * @param accounts the map of accounts to save
     */
    private void saveData(Map<String, Account> accounts) {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            gson.toJson(accounts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Custom deserializer for Account objects.
     */
    private static class AccountDeserializer implements JsonDeserializer<Account> {
        @Override
        public Account deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String accountType = jsonObject.get("accountType").getAsString();

            if (accountType.equals("Kid")) {
                return context.deserialize(json, ChildAccount.class);
            } else if (accountType.equals("Parent")) {
                return context.deserialize(json, ParentAccount.class);
            }

            throw new JsonParseException("Invalid account type: " + accountType);
        }
    }
}
