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
    private static final String DATA_FILE = "data/accounts.json";
    private Gson gson;

    public DataManager() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountDeserializer())
                .setPrettyPrinting()
                .create();
    }

    // 保存账户
    public void saveAccount(Account account) {
        Map<String, Account> accounts = loadData();
        accounts.put(account.getAccountId(), account);
        saveData(accounts);
    }

    // 获取账户
    public Account getAccount(String accountId) {
        Map<String, Account> accounts = loadData();
        return accounts.get(accountId);
    }

    // 获取所有账户
    public Map<String, Account> getAccounts() {
        return loadData();
    }

    // 从JSON文件加载数据
    private Map<String, Account> loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            // 如果文件不存在,则创建新文件
            try {
                file.getParentFile().mkdirs(); // 创建父目录
                file.createNewFile(); // 创建新文件
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

    // 将数据保存到JSON文件
    private void saveData(Map<String, Account> accounts) {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            gson.toJson(accounts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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