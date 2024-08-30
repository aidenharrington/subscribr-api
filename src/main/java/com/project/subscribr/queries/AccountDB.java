package com.project.subscribr.queries;

import com.project.subscribr.exceptions.AccountNotFoundException;
import com.project.subscribr.models.Account;
import com.project.subscribr.models.Video;

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
    public static void postVideo(int userId, Video video) {
        // Todo
    }

    // Aiden - todo
    public static Account getAccountById(int id) {
        Account account;

        // Get account from DB and replace
        account = new Account();

        return account;
    }
}
