package com.ozhou.fantuan.model.dao;

import com.ozhou.fantuan.model.Account;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("all")
public class AccountDao {
  @PersistenceContext
  private EntityManager entityManager;
  
  public Account get(final String name) {
    Account account = this.entityManager.<Account>find(Account.class, name);
    return account;
  }
  
  public List<Account> getAll() {
    TypedQuery<Account> _createQuery = this.entityManager.<Account>createQuery("SELECT a FROM Account a ORDER BY a.balance", Account.class);
    List<Account> accounts = _createQuery.getResultList();
    return accounts;
  }
}
