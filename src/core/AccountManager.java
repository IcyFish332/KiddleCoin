package core;

import data.DataManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AccountManager {
    private DataManager dataManager;
    private Set<String> childAccountIds; // 存储所有孩子账户的ID集合
    private Set<String> parentAccountIds; // 存储所有家长账户的ID集合

    public AccountManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.childAccountIds = new HashSet<>();
        this.parentAccountIds = new HashSet<>();

        // 从DataManager加载现有账户
        loadAllAccounts();
    }

    // 创建孩子账户的方法
    public ChildAccount createChildAccount(String name, String password) {
        String accountId = UUID.randomUUID().toString();
        ChildAccount newChildAccount = new ChildAccount(accountId, name, password);
        childAccountIds.add(accountId);
        dataManager.saveAccount(newChildAccount); // 保存新账户
        return newChildAccount;
    }

    // 创建家长账户的方法
    public ParentAccount createParentAccount(String name, String password) {
        String accountId = UUID.randomUUID().toString();
        ParentAccount newParentAccount = new ParentAccount(accountId, name, password);
        parentAccountIds.add(accountId);
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

    // 加载所有账户
    private void loadAllAccounts() {
        for (Account account : dataManager.getAccounts().values()) {
            if (account instanceof ChildAccount) {
                childAccountIds.add(account.getAccountId());
            } else if (account instanceof ParentAccount) {
                parentAccountIds.add(account.getAccountId());
            }
        }
    }

    // 以下是获取账户、孩子账户列表和家长账户列表的方法
    public Account getAccount(String accountId) {
        return dataManager.getAccount(accountId);
    }

    public Set<ChildAccount> getChildAccounts() {
        Set<ChildAccount> children = new HashSet<>();
        for (String childId : childAccountIds) {
            Account account = dataManager.getAccount(childId);
            if (account instanceof ChildAccount) {
                children.add((ChildAccount) account);
            }
        }
        return children;
    }

    public Set<ParentAccount> getParentAccounts() {
        Set<ParentAccount> parents = new HashSet<>();
        for (String parentId : parentAccountIds) {
            Account account = dataManager.getAccount(parentId);
            if (account instanceof ParentAccount) {
                parents.add((ParentAccount) account);
            }
        }
        return parents;
    }

    public boolean validateCredentials(String username, String password) {
        for (Account account : dataManager.getAccounts().values()) {
            // 这里假设Account对象有方法getUsername()和getPassword()
            // 并且我们存储的是明文密码
            if (account.getUsername().equals(username) &&
                    account.getPassword().equals(password)) {
                // 用户名和密码匹配，返回true
                return true;
            }
        }
        // 如果没有找到匹配的账户，返回false
        return false;
    }
}