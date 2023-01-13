package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

import net.jcip.annotations.ThreadSafe;
import net.jcip.annotations.GuardedBy;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        if (from.isPresent() && to.isPresent() && !from.equals(to)) {
            Account fromAccount = from.get();
            Account toAccount = to.get();
            if (fromAccount.amount() >= amount) {
                update(new Account(fromAccount.id(), fromAccount.amount() - amount));
                update(new Account(toAccount.id(), toAccount.amount() + amount));
                result = true;
            }
        }
        return result;
    }
}
