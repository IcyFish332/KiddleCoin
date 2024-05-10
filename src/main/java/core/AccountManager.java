package core;

import data.DataManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AccountManager {
    private DataManager dataManager;

    public AccountManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // 创建孩子账户的方法
    public ChildAccount createChildAccount(String name, String password) {
        String accountId = UUID.randomUUID().toString();
        ChildAccount newChildAccount = new ChildAccount(accountId, name, password);
        dataManager.saveAccount(newChildAccount); // 保存新账户
        return newChildAccount;
    }

    // 创建家长账户的方法
    public ParentAccount createParentAccount(String name, String password) {
        String accountId = UUID.randomUUID().toString();
        ParentAccount newParentAccount = new ParentAccount(accountId, name, password);
        dataManager.saveAccount(newParentAccount); // 保存新账户
        return newParentAccount;
    }

    // 链接孩子和家长账户
    public void linkChildToParent(String childAccountId, String parentAccountId) {
        Account childAccount = dataManager.getAccount(childAccountId);
        Account parentAccount = dataManager.getAccount(parentAccountId);

        if (childAccount instanceof ChildAccount && parentAccount instanceof ParentAccount) {
            ((ChildAccount) childAccount).addParentAccount(parentAccountId);
            ((ParentAccount) parentAccount).addChildAccount(childAccountId);
            dataManager.saveAccount(childAccount); // 更新孩子账户
            dataManager.saveAccount(parentAccount); // 更新家长账户
        }
    }

    // 解除孩子和家长账户的链接
    public void unlinkChildFromParent(String childAccountId, String parentAccountId) {
        Account childAccount = dataManager.getAccount(childAccountId);
        Account parentAccount = dataManager.getAccount(parentAccountId);

        if (childAccount instanceof ChildAccount && parentAccount instanceof ParentAccount) {
            ((ChildAccount) childAccount).removeParentAccount(parentAccountId);
            ((ParentAccount) parentAccount).removeChildAccount(childAccountId);
            dataManager.saveAccount(childAccount); // 更新孩子账户
            dataManager.saveAccount(parentAccount); // 更新家长账户
        }
    }

    // 获取账户
    public Account getAccount(String accountId) {
        return dataManager.getAccount(accountId);
    }

    public void saveAccount(Account account) {
        dataManager.saveAccount(account);
    }

    public Account getAccountByUsername(String username) {
        for (Account account : dataManager.getAccounts().values()) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        // 没有找到匹配的账户时返回 null
        return null;
    }


    // 获取所有孩子账户
    public Set<ChildAccount> getChildAccounts() {
        Set<ChildAccount> children = new HashSet<>();
        for (Account account : dataManager.getAccounts().values()) {
            if (account instanceof ChildAccount) {
                children.add((ChildAccount) account);
            }
        }
        return children;
    }

    // 获取所有家长账户
    public Set<ParentAccount> getParentAccounts() {
        Set<ParentAccount> parents = new HashSet<>();
        for (Account account : dataManager.getAccounts().values()) {
            if (account instanceof ParentAccount) {
                parents.add((ParentAccount) account);
            }
        }
        return parents;
    }

    // 验证账户凭证
    public boolean validateCredentials(String username, String password) {
        for (Account account : dataManager.getAccounts().values()) {
            if (account.getUsername().equals(username) &&
                    account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // 检查用户名是否存在
    public boolean isUsernameExists(String username) {
        for (Account account : dataManager.getAccounts().values()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}