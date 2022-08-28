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
        return accounts.putIfAbsent(account.id(), new Account(account.id(), account.amount())) != null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), new Account(account.id(), account.amount())) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromAccount = getById(fromId);
        Optional<Account> toAccount = getById(toId);
        if (amount <= 0 || fromAccount.isEmpty() || toAccount.isEmpty() || amount > fromAccount.get().amount()) {
            return false;
        }
        return update(new Account(fromId, fromAccount.get().amount() - amount))
        && update(new Account(toId, toAccount.get().amount() + amount));
    }
}
