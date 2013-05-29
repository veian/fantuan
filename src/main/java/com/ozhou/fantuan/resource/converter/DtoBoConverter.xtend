package com.ozhou.fantuan.resource.converter

import com.ozhou.fantuan.model.Account
import com.ozhou.fantuan.model.MealRecord
import com.ozhou.fantuan.model.dao.AccountDao
import com.ozhou.fantuan.resource.Dto.AccountDto
import com.ozhou.fantuan.resource.Dto.MealRecordDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DtoBoConverter {
    
    @Autowired
    AccountDao accountDao;
    
    public def MealRecordDto toDto(MealRecord mealRecord) {
        var record = new MealRecordDto();
        record.amount = mealRecord.amount;
        record.payer = mealRecord.payer.name;
        var participants =  mealRecord.participants.map[m | m.name];
        record.participants = participants;
        record.restaurant = mealRecord.restaurant;
        record.date = mealRecord.date;
        
        return record;
    }
    
    public def MealRecord toBo(MealRecordDto record) {
        var mealRecord = new MealRecord();
        var payer = accountDao.get(record.getPayer());
        if (payer == null)
            throw new IllegalArgumentException("{\"error\" : \"Payer Account not exist!\"}");
        mealRecord.setPayer(payer);
        var participants = record.participants.map[p | 
            var account = accountDao.get(p);
            if (account == null)
                throw new IllegalArgumentException("{\"error\" : \"Account not exist!\"}");
            return account;
        ]
        
        mealRecord.participants = participants;
        mealRecord.amount = record.amount;
        mealRecord.restaurant = record.restaurant;
        mealRecord.date = record.date;
        
        return mealRecord;
    }
    
    public def AccountDto toDto(Account account) {
        var dto = new AccountDto();
        dto.name = account.name;
        dto.balance = account.balance;
        return dto;
    }
}