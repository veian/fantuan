package com.ozhou.fantuan.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.AccountEntry

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
    
    public def List<AccountEntry> getAccountEntryByUser(String user, int startPosition, int pageSize) {
        var query = entityManager.createQuery(
            "SELECT e FROM AccountEntry e WHERE e.account.name = :user ORDER BY e.date DESC", typeof(AccountEntry));
        query.setParameter("user", user);
        if (startPosition >= 0)
            query.setFirstResult(startPosition);
        if (pageSize > 0)
            query.setMaxResults(pageSize);
        return query.getResultList();
    }
    
    public def Long getAccountEntryByUserCount(String user) {
        var query = entityManager.createQuery(
            "SELECT COUNT(e) FROM AccountEntry e WHERE e.account.name = :user ORDER BY e.date DESC");
        query.setParameter("user", user);
        var Long count = (query.singleResult) as Long;
        return count;
    }
    
    public def void clearCache() {
        entityManager.entityManagerFactory.cache.evict(typeof(Account));
        entityManager.entityManagerFactory.cache.evict(typeof(AccountEntry));
    }
}
