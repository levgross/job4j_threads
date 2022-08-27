package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        int id = account.id();
        if (getById(id).isPresent()) {
            return false;
        }
        accounts.put(id, new Account(id, account.amount()));
        return true;
    }

    public synchronized boolean update(Account account) {
        int id = account.id();
        if (getById(id).isEmpty()) {
            return false;
        }
        accounts.replace(id, new Account(id, account.amount()));
        return true;
    }

    public synchronized boolean delete(int id) {
        if (getById(id).isEmpty()) {
            return false;
        }
        accounts.remove(id);
        return true;
    }

    public synchronized Optional<Account> getById(int id) {
        return accounts.containsKey(id) ? Optional.of(new Account(id, accounts.get(id).amount())) : Optional.empty();
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromAccount = getById(fromId);
        Optional<Account> toAccount = getById(toId);
        if (amount <= 0 || fromAccount.isEmpty() || toAccount.isEmpty()) {
            return false;
        }
        accounts.replace(fromId, new Account(fromId, fromAccount.get().amount() - amount));
        accounts.replace(toId, new Account(toId, toAccount.get().amount() + amount));
        return true;
    }
}
