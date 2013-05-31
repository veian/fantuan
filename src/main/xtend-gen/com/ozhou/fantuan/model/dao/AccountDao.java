package com.ozhou.fantuan.model.dao;

import com.ozhou.fantuan.model.Account;
import com.ozhou.fantuan.model.AccountEntry;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
  
  public List<AccountEntry> getAccountEntryByUser(final String user, final int startPosition, final int pageSize) {
    TypedQuery<AccountEntry> query = this.entityManager.<AccountEntry>createQuery(
      "SELECT e FROM AccountEntry e WHERE e.account.name = :user ORDER BY e.date DESC", AccountEntry.class);
    query.setParameter("user", user);
    boolean _greaterEqualsThan = (startPosition >= 0);
    if (_greaterEqualsThan) {
      query.setFirstResult(startPosition);
    }
    boolean _greaterThan = (pageSize > 0);
    if (_greaterThan) {
      query.setMaxResults(pageSize);
    }
    return query.getResultList();
  }
  
  public Long getAccountEntryByUserCount(final String user) {
    Query query = this.entityManager.createQuery(
      "SELECT COUNT(e) FROM AccountEntry e WHERE e.account.name = :user ORDER BY e.date DESC");
    query.setParameter("user", user);
    Object _singleResult = query.getSingleResult();
    Long count = ((Long) _singleResult);
    return count;
  }
}
