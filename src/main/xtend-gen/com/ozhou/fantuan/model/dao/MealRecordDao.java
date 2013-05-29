package com.ozhou.fantuan.model.dao;

import com.ozhou.fantuan.model.MealRecord;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("all")
public class MealRecordDao {
  @PersistenceContext
  private EntityManager entityManager;
  
  @Transactional
  public void save(final MealRecord mealRecord) {
    this.entityManager.persist(mealRecord);
  }
  
  @SuppressWarnings("unchecked")
  public List<MealRecord> getMealRecordForUser(final String user, final int startPosition, final int pageSize) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("SELECT * FROM `MEALRECORD` AS m");
    _builder.newLine();
    _builder.append("WHERE m.`PAYER_NAME` = ?");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("OR m.`ID` IN (");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("SELECT ma.`MealRecord_ID` FROM `MEALRECORD_ACCOUNT` AS ma");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("WHERE ma.`participants_NAME` = ?)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ORDER BY m.`DATE` DESC");
    _builder.newLine();
    String sql = _builder.toString();
    Query query = this.entityManager.createNativeQuery(sql, MealRecord.class);
    boolean _greaterEqualsThan = (startPosition >= 0);
    if (_greaterEqualsThan) {
      query.setFirstResult(startPosition);
    }
    boolean _greaterThan = (pageSize > 0);
    if (_greaterThan) {
      query.setMaxResults(pageSize);
    }
    query.setParameter(1, user);
    query.setParameter(2, user);
    List<MealRecord> records = query.getResultList();
    return records;
  }
  
  public Long getMealRecordForUserCount(final String user) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("SELECT COUNT(*) FROM `MEALRECORD` AS m");
    _builder.newLine();
    _builder.append("WHERE m.`PAYER_NAME` = ?");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("OR m.`ID` IN (");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("SELECT ma.`MealRecord_ID` FROM `MEALRECORD_ACCOUNT` AS ma");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("WHERE ma.`participants_NAME` = ?)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ORDER BY m.`DATE` DESC");
    _builder.newLine();
    String sql = _builder.toString();
    Query query = this.entityManager.createNativeQuery(sql);
    query.setParameter(1, user);
    query.setParameter(2, user);
    Object _singleResult = query.getSingleResult();
    Long count = ((Long) _singleResult);
    return count;
  }
}
