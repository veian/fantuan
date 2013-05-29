package com.ozhou.fantuan.model.dao

import com.ozhou.fantuan.model.MealRecord
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.springframework.transaction.annotation.Transactional
import java.util.List
import org.springframework.stereotype.Repository

@Repository
class MealRecordDao {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional
    public def save(MealRecord mealRecord) {
        entityManager.persist(mealRecord);
    }
    
    @SuppressWarnings("unchecked")
    public def List<MealRecord> getMealRecordForUser(String user, int startPosition, int pageSize) {
        var sql = '''
            SELECT * FROM `MEALRECORD` AS m
            WHERE m.`PAYER_NAME` = ?
              OR m.`ID` IN (
                SELECT ma.`MealRecord_ID` FROM `MEALRECORD_ACCOUNT` AS ma
                WHERE ma.`participants_NAME` = ?)
                ORDER BY m.`DATE` DESC
        '''
        
        var query = entityManager.createNativeQuery(sql, typeof(MealRecord));
        if (startPosition >= 0)
            query.setFirstResult(startPosition);
        if (pageSize > 0)
            query.setMaxResults(pageSize);
        query.setParameter(1, user);
        query.setParameter(2, user);
        var List<MealRecord> records = query.getResultList();
        return records;
    }
}