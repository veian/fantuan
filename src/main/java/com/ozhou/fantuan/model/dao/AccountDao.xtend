package com.ozhou.fantuan.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ozhou.fantuan.model.Account;

@Repository
class AccountDao {

    @PersistenceContext
    EntityManager entityManager;
    
    public def get(String name) {
        var account = entityManager.find(typeof(Account), name);
        return account;
    }
    
    public def List<Account> getAll() {
        var accounts = entityManager.createQuery("SELECT a FROM Account a ORDER BY a.balance", typeof(Account)).getResultList();
        return accounts;
    }
}
