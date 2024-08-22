package com.project.subscribr.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.subscribr.exceptions.AccountNotFoundException;
import com.project.subscribr.models.Account;
import com.project.subscribr.queries.AccountDB;

@RestController
@RequestMapping("/users/{id}")
public class AccountController {
    
    @PostMapping
    public ResponseEntity<Account> subscribe(@PathVariable int accountId,
     @RequestBody Account userAccount) {
        try {
            AccountDB.subscribeToAccount(userAccount.getAccountId(), accountId);
            return ResponseEntity.ok(userAccount);
        } catch (AccountNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
