package core;

import java.util.HashSet;
import java.util.Set;

public class ParentAccount extends Account {
    private Set<String> childAccountIds; // 存储孩子账户ID的集合

    public ParentAccount(String accountId, String name, String password) {
        super(accountId, name, password);
        this.accountType = "Parent";
        this.childAccountIds = new HashSet<>();
    }

    // 家长账户特有的方法：添加和移除孩子账户
    public void addChildAccount(String accountId) {
        childAccountIds.add(accountId);
    }

    public void removeChildAccount(String accountId) {
        childAccountIds.remove(accountId);
    }

    // 获取家长账户下所有孩子账户的ID
    public Set<String> getChildAccountIds() {
        return new HashSet<>(childAccountIds);
    }

    // 可能还有其他特有的方法或属性
}