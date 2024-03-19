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

public class DataManager {

    private static final String DATA_FILE = "accounts.json";
    private final Gson gson;
    private Map<String, Account> accounts;

    public DataManager() {
        // Initialize Gson with a custom deserializer for polymorphic types
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountDeserializer())
                .create();
        accounts = new HashMap<>();
        loadAccounts();
    }

    private void loadAccounts() {
        try (Reader reader = new FileReader(DATA_FILE)) {
            Type type = new TypeToken<Map<String, Account>>() {}.getType();
            accounts = gson.fromJson(reader, type);
            if (accounts == null) {
                accounts = new HashMap<>();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while loading accounts: " + e.getMessage());
            accounts = new HashMap<>();
        }
    }

    public void saveAccounts() {
        String tempDataFile = DATA_FILE + ".tmp";
        try (Writer writer = new FileWriter(tempDataFile)) {
            gson.toJson(accounts, writer);
            File tempFile = new File(tempDataFile);
            File dataFile = new File(DATA_FILE);
            if (!tempFile.renameTo(dataFile)) {
                throw new IOException("Failed to rename temp file to data file.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while saving accounts: " + e.getMessage());
        }
    }

    // 添加方法来保存单个账户
    public void saveAccount(Account account) {
        accounts.put(account.getAccountId(), account);
        saveAccounts(); // You can optimize this to avoid saving all accounts every time
    }

    // 添加方法来获取单个账户
    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    private static class AccountDeserializer implements JsonDeserializer<Account> {
        @Override
        public Account deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject accountObject = json.getAsJsonObject();
            JsonElement accountTypeElement = accountObject.get("accountType");

            if (accountTypeElement != null) {
                String accountType = accountTypeElement.getAsString();
                if ("CHILD".equals(accountType)) {
                    return context.deserialize(json, ChildAccount.class);
                } else if ("PARENT".equals(accountType)) {
                    return context.deserialize(json, ParentAccount.class);
                }
            }

            throw new JsonParseException("Unknown account type");
        }
    }
}