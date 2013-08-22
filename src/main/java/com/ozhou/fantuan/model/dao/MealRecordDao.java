package com.ozhou.fantuan.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ozhou.fantuan.model.MealRecord;

@Repository
public class MealRecordDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
    public void save(MealRecord mealRecord) {
        entityManager.persist(mealRecord);
    }
    
    public List<MealRecord> getMealRecordForUser(String user, int startPosition, int pageSize) {
//        String sql = 
//            "SELECT * FROM `MEALRECORD` AS m " +
//            "WHERE m.`PAYER_NAME` = ? " +
//              "OR m.`ID` IN ( " +
//                "SELECT ma.`MealRecord_ID` FROM `MEALRECORD_ACCOUNT` AS ma " +
//                "WHERE ma.`participants_NAME` = ?) " +
//            "ORDER BY m.`DATE` DESC";       
        String sql = 
        		"SELECT m FROM MealRecord m " +
        		"WHERE m.payer.name = :name " +
        		  "OR m.id IN ( " +
        		    "SELECT m.id FROM MealRecord m " +
        		    "INNER JOIN m.participants p " + 
        		    "WHERE p.name = :name) " +
        		"ORDER BY m.date desc";
        
        TypedQuery<MealRecord> query = entityManager.createQuery(sql, MealRecord.class);
        if (startPosition >= 0)
            query.setFirstResult(startPosition);
        if (pageSize > 0)
            query.setMaxResults(pageSize);
        query.setParameter("name", user);
        List<MealRecord> records = query.getResultList();
        return records;
    }
    
    public Long getMealRecordForUserCount(String user) {
    	String sql = 
        		"SELECT COUNT(m) FROM MealRecord m " +
        		"WHERE m.payer.name = :name " +
        		  "OR m.id IN ( " +
        		    "SELECT m.id FROM MealRecord m " +
        		    "INNER JOIN m.participants p " + 
        		    "WHERE p.name = :name) " +
        		"ORDER BY m.date desc";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("name", user);
        Long count = query.getSingleResult();
        return count;
    }
}
