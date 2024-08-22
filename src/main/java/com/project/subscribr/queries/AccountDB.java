package com.project.subscribr.queries;

import com.project.subscribr.exceptions.AccountNotFoundException;
import com.project.subscribr.models.Account;

public class AccountDB {
    

    // Aiden - todo
    public static void subscribeToAccount(int userId, int accountId) throws AccountNotFoundException {
        Account accountToSubscribeTo = getAccountById(accountId);

        if (accountToSubscribeTo != null) {
            // Add account id to list of subscribed accounts in DB
        } else {
            throw new AccountNotFoundException();
        }

    }

    // Aiden - todo
    public static Account getAccountById(int id) {
        Account account;

        // Get account from DB and replace
        account = new Account();

        return account;
    }
}
