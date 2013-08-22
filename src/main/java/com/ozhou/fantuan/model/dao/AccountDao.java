package com.ozhou.fantuan.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.AccountEntry;

@Repository
public class AccountDao {
    @PersistenceContext
    EntityManager entityManager;
    
    public Account get(String name) {
        Account account = entityManager.find(Account.class, name);
        return account;
    }
    
    public List<Account> getAll() {
        List<Account> accounts = entityManager.createQuery("SELECT a FROM Account a ORDER BY a.balance", Account.class).getResultList();
        return accounts;
    }
    
    public List<AccountEntry> getAccountEntryByUser(String user, int startPosition, int pageSize) {
        TypedQuery<AccountEntry> query = entityManager.createQuery(
            "SELECT e FROM AccountEntry e WHERE e.account.name = :user ORDER BY e.date DESC", AccountEntry.class);
        query.setParameter("user", user);
        if (startPosition >= 0)
            query.setFirstResult(startPosition);
        if (pageSize > 0)
            query.setMaxResults(pageSize);
        return query.getResultList();
    }
    
    public Long getAccountEntryByUserCount(String user) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(e) FROM AccountEntry e WHERE e.account.name = :user ORDER BY e.date DESC", Long.class);
        query.setParameter("user", user);
        Long count = query.getSingleResult();
        return count;
    }
    
    public void clearCache() {
        entityManager.getEntityManagerFactory().getCache().evict(Account.class);
        entityManager.getEntityManagerFactory().getCache().evict(AccountEntry.class);
    }

}
